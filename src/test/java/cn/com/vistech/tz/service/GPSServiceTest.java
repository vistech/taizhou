package cn.com.vistech.tz.service;

import java.text.ParseException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GPSTracePKBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GPSServiceTest {
	 
	@Autowired 
	private GPSService gpsService;
	@Test
	public void addText() throws ParseException{
		GPSTraceBean trace = new GPSTraceBean();
		GPSTracePKBean tracePK = new GPSTracePKBean();
		tracePK.setDt(DateUtils.parseDate("2013-04-22 13:32:27", new String[]{"yyyy-M-d HH:ss:mm"}));
		tracePK.setId("13956069299");
		trace.setTracePK(tracePK);
		trace.setDirection(null);
		trace.setLgtd(117.21282d);
		trace.setLttd(30.83752d);
		trace.setSpeed(0.0000d);
		
		gpsService.addText(trace);
	}
}
