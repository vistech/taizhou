package cn.com.vistech.tz.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.vistech.tz.bean.report.CheckOnReport;
import cn.com.vistech.tz.bean.report.ChuQingSort_ReportBean;
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
		map.put("reportName", "tzfb");
		return map;
	}

	@RequestMapping(value = "/checkOnReport")
	public Map<String, Object> checkOnReport(String reportName) throws ParseException {
		List<CheckOnReport> crs = checkOnReportService.getCheckOnReport(reportName);
		List<ChuQingSort_ReportBean> cqs = checkOnReportService.getChuQingSort_ReportBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportName", reportName);
		map.put("crs", crs);
		map.put("cqs", cqs);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/getCheckOnReportJson")
	public List<CheckOnReport> getCheckOnReportJson(String reportName)
			throws ParseException {
		List<CheckOnReport> crs = checkOnReportService
				.getCheckOnReport(reportName);
		return crs;
	}

	@ResponseBody
	@RequestMapping(value = "/getChuQingSort_Reportjson")
	public List<ChuQingSort_ReportBean> getChuQingSort_Reportjson() {
		List<ChuQingSort_ReportBean> cqs = checkOnReportService
				.getChuQingSort_ReportBean();
		return cqs;
	}
}
