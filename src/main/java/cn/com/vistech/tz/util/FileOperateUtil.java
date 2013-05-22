package cn.com.vistech.tz.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.com.vistech.tz.bean.GPSMediaBean;

/**
 * 
 * @author geloin
 * @date 2012-5-5 下午12:05:57
 */
public class FileOperateUtil {
	private static final String UPLOADDIR = "uploadDir/";

	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午3:39:53
	 * @param name
	 * @return
	 */
	private static String rename(String name) {
		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		Long random = (long) (Math.random() * now);
		String fileName = now + "" + random;

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * 上传文件
	 * 
	 * @author geloin
	 * @date 2012-5-5 下午12:25:47
	 * @param request
	 * @param params
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static GPSMediaBean upload(MultipartFile file,
			HttpServletRequest request) {

		GPSMediaBean uploadFile = null;
		if (file.getSize() != 0) {
			uploadFile = new GPSMediaBean();

			String path = getFullParentPath(request);

			String realFileName = file.getOriginalFilename();
			String aliasFileName = rename(realFileName);
			long fileSizeOther = file.getSize();

			String fileSize = reportTraffic(fileSizeOther);

			uploadFile.setFileName(realFileName);
			uploadFile.setFileUrl(aliasFileName);
			uploadFile.setFileSize(fileSize);

			File targetFile = new File(path, aliasFileName);

			// 保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return uploadFile;
	}

	/**
	 * byte 转换成 gb mb kb or byte
	 * 
	 * @param trafficPrint
	 * @return
	 */
	private static String reportTraffic(long trafficPrint) {
		if ((trafficPrint / 1000000000) > 0) {
			return (trafficPrint / 1000000000 + "");
		} else if ((trafficPrint / 1000000) > 0) {
			return (trafficPrint / 1000000 + "");
		} else if ((trafficPrint / 1000) > 0) {
			return (trafficPrint / 1000 + "");
		} else {
			return (trafficPrint + "");
		}
	}

	/**
	 * 下载
	 * 
	 * @author geloin
	 * @date 2012-5-5 下午12:25:39
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = getFullParentPath(request);
		File file = new File(ctxPath, storeName);

		long fileLength = file.length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(file.getPath()));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}

	public static void deleteFile(HttpServletRequest request,
			String aliasFileName) {
		String path = getFullParentPath(request);
		File file = new File(path, aliasFileName);
		if (file.exists())
			file.delete();
	}

	public static String getFullParentPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext()
				.getRealPath(FileOperateUtil.UPLOADDIR);

		return path;
	}

	public static String getShortParentPath() {
		return FileOperateUtil.UPLOADDIR;
	}
}
