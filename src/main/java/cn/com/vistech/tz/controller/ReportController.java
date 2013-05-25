package cn.com.vistech.tz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "report")
public class ReportController {
	@RequestMapping(value = "/helloReport", method = RequestMethod.GET)
	public Map<String, Object> helloReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chengziqing", "程子清");
		return map;
	}
}
