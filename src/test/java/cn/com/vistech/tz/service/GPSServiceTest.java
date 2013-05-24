package cn.com.vistech.tz.service;

import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GPSTracePKBean;
import cn.com.vistech.tz.dao.ExecProDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GPSServiceTest {

	@Autowired
	private GPSService gpsService;
	@Test
	public void addText() throws ParseException {
		GPSTraceBean trace = new GPSTraceBean();
		GPSTracePKBean tracePK = new GPSTracePKBean();
		tracePK.setDt(DateUtils.parseDate("2013-04-22 13:32:27",
				new String[] { "yyyy-M-d HH:ss:mm" }));
		tracePK.setId("13956069299");
		trace.setTracePK(tracePK);
		trace.setDirection(null);
		trace.setLgtd(117.21282d);
		trace.setLttd(30.83752d);
		trace.setSpeed(0.0000d);

		gpsService.addText(trace);
	}

	@Autowired
	private ExecProDao execProDao;

	
	@Transactional
	public void testPro() {
		String callPro = "{call opengps_NewMedia(:sim,:filename,"
				+ ":filesize,:fileurl,:remarks,:mediatype,:risks,"
				+ ":lgtd,:lttd)}";

		Map<String, Object> params = Maps.newHashMap();
		params.put("sim", "000000000");
		params.put("filename", "文件名");
		params.put("filesize", "10.5");
		params.put("fileurl", "文件路径");
		params.put("remarks", "备注");
		params.put("mediatype", "测试");
		params.put("risks", "级别");
		params.put("lgtd", 10.2);
		params.put("lttd", 10.1);
//
		execProDao.execPro(callPro, params);
	}
}
