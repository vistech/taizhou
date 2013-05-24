package cn.com.vistech.tz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.vistech.tz.bean.DeviceBean;
import cn.com.vistech.tz.bean.GPSBean;
import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.HotAreaBean;
import cn.com.vistech.tz.bean.HotAreaCheckInBean;
import cn.com.vistech.tz.bean.MOutBoxBean;
import cn.com.vistech.tz.bean.OpenMAS_MOutBox;
import cn.com.vistech.tz.dao.DeviceDao;
import cn.com.vistech.tz.dao.GPSDao;
import cn.com.vistech.tz.dao.GPSMediaDao;
import cn.com.vistech.tz.dao.GPSTraceDao;
import cn.com.vistech.tz.dao.HotAreaCheckInDao;
import cn.com.vistech.tz.dao.HotAreaDao;
import cn.com.vistech.tz.dao.MoutBoxDao;
import cn.com.vistech.tz.dao.OpenMAS_MOutBoxDao;

@Service
@Transactional
public class GPSService {

	@Autowired
	private GPSDao gPSDao;
	@Autowired
	private GPSTraceDao traceDao;
	@Autowired
	private HotAreaDao hotAreaDao;
	@Autowired
	private HotAreaCheckInDao hotAreaCheckInDao;
	@Autowired
	private MoutBoxDao moutBoxDao;
	@Autowired
	private GPSMediaDao gpsMediaDao;
	@Autowired
	private OpenMAS_MOutBoxDao openMAS_MOutBoxDao;
	@Autowired
	private DeviceDao deviceDao;
	
	
	public void addText(GPSBean gps) {
		gPSDao.save(gps);
	}

	public void addOpenMAS_MOutBox(OpenMAS_MOutBox mob) {
		openMAS_MOutBoxDao.save(mob);
	}

	public void addText(GPSTraceBean trace) {
		traceDao.pullNewTraceByProc(trace);
	}

	public List<HotAreaBean> getHotArea(String sim) {
		List<HotAreaBean> hotAreas = hotAreaDao.findByHotareapkSim(sim);
		return hotAreas;
	}

	public void addGPSPhone(String id) {
		HotAreaBean hotArea = hotAreaDao.findByPid(Integer.parseInt(id));
		HotAreaCheckInBean hotAreaCheckIn = new HotAreaCheckInBean(hotArea
				.getHotareapk().getSim(), hotArea.getHotareapk().getAreaId());
		hotAreaCheckInDao.save(hotAreaCheckIn);
	}

	public void sendMsg(String sim, String context) {
		MOutBoxBean mout = new MOutBoxBean();
		mout.setMessageTcp(context);
		mout.setSim(sim);
		mout.setTimes(new Date());

		moutBoxDao.save(mout);
	}

	public void isSign(String id, boolean isCheck) {
		GPSMediaBean mediaBean = gpsMediaDao.findOne(Integer.parseInt(id));
		mediaBean.setIsHide(isCheck);
		gpsMediaDao.save(mediaBean);
	}

	public void isRead(String id, boolean isCheck) {
		GPSMediaBean mediaBean = gpsMediaDao.findOne(Integer.parseInt(id));
		mediaBean.setIsRead(isCheck);
		gpsMediaDao.save(mediaBean);
	}
	
	public void setInOrOut(String sim,Boolean checkIn){
		DeviceBean device = deviceDao.findBySim(sim);
		
		if(device!=null){
			device.setIsCheckIn(checkIn);
			deviceDao.save(device);
		}
	}
	
	
}
