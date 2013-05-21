package cn.com.vistech.tz.controller;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.vistech.tz.aop.TraceInterceptor;
import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.service.UploadFileService;
import cn.com.vistech.tz.util.FileOperateUtil;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * 
 * @author geloin
 * @date 2012-5-5 上午11:56:35
 */
@Controller
@RequestMapping(value = "file")
public class UploadFileController {
	@Autowired
	private UploadFileService uploadFileService;

	@RequestMapping(value = "uploadTxt")
	@ResponseBody
	public Object updateTxt(String telnum, String remarks, String risks,
			String lgtd, String lttd) {
		GPSMediaBean gpsMedia = new GPSMediaBean();
		gpsMedia.setLgtd(Double.parseDouble(lgtd));
		gpsMedia.setLgtd(Double.parseDouble(lttd));
		gpsMedia.setRisks(risks);
		gpsMedia.setRemarks(remarks);
		gpsMedia.setSIM(telnum);
		gpsMedia.setMediaType("txt");

		try {
			uploadFileService.save(gpsMedia);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 上传文件
	 * 
	 */
	@RequestMapping(value = "upload")
	@ResponseBody
	public Object upload(
			String telnum,
			String remarks,
			@RequestParam(value = "file", required = true) MultipartFile[] file,
			HttpServletRequest request, String risks, String lgtd, String lttd)
			throws Exception {
		Character andSpliter = '&';

		Iterable<String> remkarsIter = Splitter.on(andSpliter)
				.omitEmptyStrings().split(remarks);

		Iterable<String> risksIter = Splitter.on(andSpliter).omitEmptyStrings()
				.split(risks);

		Iterable<String> lgtdIter = Splitter.on(andSpliter).omitEmptyStrings()
				.split(lgtd);

		Iterable<String> lttdIter = Splitter.on(andSpliter).omitEmptyStrings()
				.split(lttd);

		Boolean flag = true;

		try {
			if (Iterables.size(remkarsIter) == file.length) {

				for (int index = 0; index < file.length; index++) {
					MultipartFile f = file[index];
					String remkarOutStr = Iterables.get(remkarsIter, index);
					Iterable<String> innerStrIter = Splitter.on('@')
							.omitEmptyStrings().split(remkarOutStr);
					String fileRemark = (Iterables.size(innerStrIter) == 2 ? Iterables
							.get(innerStrIter, 1) : "");

					String risk = Iterables.get(risksIter, index);
					String signLgtd = Iterables.get(lgtdIter, index);
					String signLttd = Iterables.get(lttdIter, index);

					GPSMediaBean fileBean = FileOperateUtil.upload(f, request);

					fileBean.setSIM(telnum);
					fileBean.setRemarks(fileRemark);

					String fileName = fileBean.getFileName();
					String fileType = fileName.substring(fileName
							.lastIndexOf('.') + 1);

					if (fileType.equals("amr")) {
						String path = FileOperateUtil
								.getFullParentPath(request) + "/";

						String common = "{0}ffmpeg -i {0}{1}.amr {0}{1}.mp3";

						common = MessageFormat.format(
								common,
								path,
								fileBean.getFileUrl().substring(0,
										fileBean.getFileUrl().indexOf('.')));

						fileType = "mp3";

						Runtime.getRuntime().exec(common);

						fileBean.setFileUrl(fileBean.getFileUrl().replace(
								"amr", "mp3"));
						fileBean.setFileName(fileBean.getFileName().replace(
								"amr", "mp3"));
					}

					fileBean.setMediaType(fileType);

					fileBean.setRisks(risk);

					fileBean.setLgtd(Double.parseDouble(signLgtd));
					fileBean.setLttd(Double.parseDouble(signLttd));

					uploadFileService.save(fileBean);
				}

			}
		} catch (Exception e) {
			flag = false;
			TraceInterceptor.logger4J.error("error", e);
		}

		return flag;
	}

}
