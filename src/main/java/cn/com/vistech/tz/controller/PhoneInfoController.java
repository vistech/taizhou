package cn.com.vistech.tz.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "phoneInfo")
public class PhoneInfoController {

	@RequestMapping("/version")
	@ResponseBody
	public String getIndex() throws IOException {
		Properties proper = PropertiesLoaderUtils.loadAllProperties("/system.properties");
		String version = proper.getProperty("phone_vesion");
		
		return version;
	}

	@RequestMapping("/download")
	public void download(HttpServletResponse response) throws IOException {
		Resource resource = new ClassPathResource("/phone/openGPS.apk");
		File file = resource.getFile();

		InputStream input = FileUtils.openInputStream(file);
		byte[] data = IOUtils.toByteArray(input);

		String fileName = URLEncoder.encode(file.getName(), "UTF-8");

		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		
		IOUtils.write(data, response.getOutputStream());
		IOUtils.closeQuietly(input);
	}

}
