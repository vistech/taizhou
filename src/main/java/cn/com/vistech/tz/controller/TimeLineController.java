package cn.com.vistech.tz.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.vistech.tz.service.TimeLineService;
import cn.com.vistech.tz.util.DateEditor;

@Controller
public class TimeLineController {
	@Autowired
	private TimeLineService timeLineService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 时间轴数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showTimeLine")
	public @ResponseBody
	Map<String, Object> showTimeLine(Date startDate, Date endDate,
			String userType, String sim, String userName, String alertType) {
		Map<String, Object> result = new HashMap<String, Object>();
		Object[] showTimeResult = null;

		try {
			showTimeResult = timeLineService.buildShowTimeLine(startDate,
					endDate, userType, sim, userName, alertType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		result.put("json_name", showTimeResult[0]);
		result.put("json_size", showTimeResult[1]);
		String startDateStr = null;
		if (startDate != null) {
			startDateStr = DateFormatUtils.format(startDate, "yyyy-M-d");
			result.put("start_date", startDateStr);
		}

		String endDateStr = null;
		if (startDate != null) {
			endDateStr = DateFormatUtils.format(endDate, "yyyy-M-d");
			result.put("end_date", endDateStr);
		}

		return result;
	}
}
