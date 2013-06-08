package cn.com.vistech.tz.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
		gpsMedia.setLttd(Double.parseDouble(lttd));
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
				.split(risks == null ? "" : risks);

		Iterable<String> lgtdIter = Splitter.on(andSpliter).omitEmptyStrings()
				.split(lgtd == null ? "" : lgtd);

		Iterable<String> lttdIter = Splitter.on(andSpliter).omitEmptyStrings()
				.split(lttd == null ? "" : lttd);

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

					String risk = Iterables.isEmpty(risksIter) ? "" : Iterables
							.get(risksIter, index);
					String signLgtd = Iterables.isEmpty(lgtdIter) ? ""
							: Iterables.get(lgtdIter, index);
					String signLttd = Iterables.isEmpty(lttdIter) ? ""
							: Iterables.get(lttdIter, index);

					GPSMediaBean fileBean = FileOperateUtil.upload(f, request);

					fileBean.setSIM(telnum);
					fileBean.setRemarks(fileRemark);

					String fileName = fileBean.getFileName();
					String fileType = fileName.substring(fileName
							.lastIndexOf('.') + 1);

					String path = FileOperateUtil.getFullParentPath(request)
							+ "/";
					String appPath = path + "ffmpeg/bin/ffmpeg";
					String fileNameNotSuffix = fileBean.getFileUrl().substring(
							0, fileBean.getFileUrl().lastIndexOf('.'));
					if (fileType.equals("amr")) { // amr to mp3
						ProcessBuilder process = new ProcessBuilder(appPath,
								"-i", path + fileNameNotSuffix + ".amr", path
										+ fileNameNotSuffix + ".mp3");
						this.execProc(process);

						fileType = "mp3";

						fileBean.setFileUrl(fileBean.getFileUrl().replace(
								"amr", "mp3"));
						fileBean.setFileName(fileBean.getFileName().replace(
								"amr", "mp3"));

						FileSystemResource fileR = new FileSystemResource(path
								+ fileNameNotSuffix + ".amr");
						if (fileR.exists())
							fileR.getFile().delete();
					} else if (fileType.equals("3gp")) {// 3gp to mp4
						ProcessBuilder process = new ProcessBuilder(appPath,
								"-i", path + fileNameNotSuffix + ".3gp",
								"-acodec", "copy", path + fileNameNotSuffix
										+ ".mp4");
						this.execProc(process);

						fileType = "mp4";

						fileBean.setFileUrl(fileBean.getFileUrl().replace(
								"3gp", "mp4"));
						fileBean.setFileName(fileBean.getFileName().replace(
								"3gp", "mp4"));

						FileSystemResource fileR = new FileSystemResource(path
								+ fileNameNotSuffix + ".3gp");
						if (fileR.exists())
							fileR.getFile().delete();

					} else if (fileType.equals("mp4")) {// mp4 to mp4 修复
						ProcessBuilder process = new ProcessBuilder(appPath,
								"-i", path + fileNameNotSuffix + ".mp4",
								"-vcodec", "libx264", path + fileNameNotSuffix
										+ "_r.mp4");

						this.execProc(process);

						fileType = "mp4";

						fileBean.setFileUrl(fileBean.getFileUrl().replace(".",
								"_r."));

						FileSystemResource fileR = new FileSystemResource(path
								+ fileNameNotSuffix + ".mp4");
						if (fileR.exists())
							fileR.getFile().delete();
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

	private Integer execProc(ProcessBuilder process) throws IOException,
			InterruptedException {
		process.redirectErrorStream(true);
		Process pro = process.start();
		// 需要处理标准输入
		BufferedReader br = new BufferedReader(new InputStreamReader(
				pro.getInputStream()));
		String tmp = null;
		while ((tmp = br.readLine()) != null) {
			TraceInterceptor.logger4J.info(tmp);
		}
		return pro.waitFor();

	}
}
