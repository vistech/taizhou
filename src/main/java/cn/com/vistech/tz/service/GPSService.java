package cn.com.vistech.tz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import cn.com.vistech.tz.bean.BaiduMapBean;
import cn.com.vistech.tz.bean.DeviceBean;
import cn.com.vistech.tz.bean.GPSBean;
import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GoogleMapBean;
import cn.com.vistech.tz.bean.HotAreaBean;
import cn.com.vistech.tz.bean.HotAreaCheckInBean;
import cn.com.vistech.tz.bean.MOutBoxBean;
import cn.com.vistech.tz.bean.OpenMAS_MOutBox;
import cn.com.vistech.tz.dao.BaiduMapDao;
import cn.com.vistech.tz.dao.DeviceDao;
import cn.com.vistech.tz.dao.ExecProDao;
import cn.com.vistech.tz.dao.GPSDao;
import cn.com.vistech.tz.dao.GPSMediaDao;
import cn.com.vistech.tz.dao.GPSTraceDao;
import cn.com.vistech.tz.dao.GoogleMapDao;
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
	@Autowired
	private ExecProDao execProxDao;
	@Autowired
	private BaiduMapDao baiduMapDao;
	@Autowired
	private GoogleMapDao googleMapDao;

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

		for (HotAreaBean hotArea : hotAreas) {
			Double lgtd = hotArea.getpLgtd(); // 经度
			Double lttd = hotArea.getpLttd(); // 纬度

			BaiduMapBean baiduMap = baiduMapDao.findByLaAndLo(
					String.valueOf(lttd), String.valueOf(lgtd));

			GoogleMapBean googleMap = googleMapDao.findByLaAndLo(
					String.valueOf(lttd), String.valueOf(lgtd));
			if (baiduMap != null && googleMap != null) {

				hotArea.setpLgtd(lgtd + baiduMap.getOffLo()
						- googleMap.getOffLo());
				hotArea.setpLttd(lttd + baiduMap.getOffLa()
						- googleMap.getOffLa());
			}
		}

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

	public void setInOrOut(String sim, Boolean checkIn) {
		DeviceBean device = deviceDao.findBySim(sim);

		if (device != null) {
			device.setIsCheckIn(checkIn);
			deviceDao.save(device);
		}
	}

	public Integer gpsLoginCheck(String userName, String pass) {
		String callSql = "{call opengps_LoginCheck (:userName,:pass)}";
		Map<String, Object> params = Maps.newHashMap();
		params.put("userName", userName);
		params.put("pass", pass);

		List<Integer> result = execProxDao.findSome(null, callSql, params);
		Integer sign = result.get(0);

		return sign;
	}

	public Integer gpsLoginPassword(String userName, String pass) {

		String callSql = "{call opengps_ChangePWD (:userName,:pass)}";
		Map<String, Object> params = Maps.newHashMap();
		params.put("userName", userName);
		params.put("pass", pass);

		try {
			execProxDao.execPro(callSql, params);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

}
