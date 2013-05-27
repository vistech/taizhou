package cn.com.vistech.tz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.vistech.tz.bean.report.CheckOnReport;
import cn.com.vistech.tz.service.CheckOnReportService;

@Controller
@RequestMapping(value = "report")
public class ReportController {
	@Autowired
	private CheckOnReportService checkOnReportService;

	@RequestMapping(value = "/helloReport", method = RequestMethod.GET)
	public Map<String, Object> helloReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chengziqing", "程子清");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/getCheckOnReportJson", method = RequestMethod.GET)
	public List<CheckOnReport> getCheckOnReportJson() {
		List<CheckOnReport> crs = checkOnReportService.getCheckOnReport("tzfb");
		return crs;
	}
}
