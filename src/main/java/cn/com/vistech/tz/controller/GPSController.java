package cn.com.vistech.tz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.vistech.tz.bean.GPSBean;
import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GPSTracePKBean;
import cn.com.vistech.tz.bean.HotAreaBean;
import cn.com.vistech.tz.bean.OpenMAS_MOutBox;
import cn.com.vistech.tz.service.GPSService;
import cn.com.vistech.tz.util.DateEditor;

@Controller
@RequestMapping(value = "gpsView")
public class GPSController {
	@Autowired
	private GPSService gPSService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@RequestMapping(value = "/addText")
	public @ResponseBody
	String addText(GPSBean gpsBean, String pid) {
		GPSTraceBean traceB = new GPSTraceBean();
		GPSTracePKBean tracePKBean = new GPSTracePKBean();
		tracePKBean.setId(gpsBean.getTelNum());
		tracePKBean.setDt(gpsBean.getTime());

		traceB.setDirection(gpsBean.getDirection());
		traceB.setLgtd(gpsBean.getLongiTude());
		traceB.setLttd(gpsBean.getLatiTude());
		traceB.setSpeed(gpsBean.getSpeed());

		traceB.setTracePK(tracePKBean);

		gPSService.addText(traceB);
		return "OK";
	}

	@RequestMapping(value = "/isok")
	public @ResponseBody
	String isOK() {
		return "OK";
	}

	@RequestMapping(value = "/get/gpsPois")
	public @ResponseBody
	List<HotAreaBean> getGPSPois(String mcode) {
		List<HotAreaBean> has = gPSService.getHotArea(mcode);
		// 将错就错,手机端写反了
		for (int i = 0; i < has.size(); i++) {
			Double t = has.get(i).getpLgtd();
			has.get(i).setpLgtd(has.get(i).getpLttd());
			has.get(i).setpLttd(t);
		}

		return has;
	}

	@RequestMapping(value = "/add/gpsPhone")
	public @ResponseBody
	Object addGPSPhone(String pid) {
		try {
			gPSService.addGPSPhone(pid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Object sendMsg(String sim, String context) {
		try {
			gPSService.sendMsg(sim, context);
			OpenMAS_MOutBox mob = new OpenMAS_MOutBox();
			mob.setDt(new Date());
			mob.setSim(sim);
			mob.setInfo(context);
			gPSService.addOpenMAS_MOutBox(mob);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/isSign")
	@ResponseBody
	public Object isSign(String id, boolean isCheck) {
		try {
			gPSService.isSign(id, isCheck);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/isRead")
	@ResponseBody
	public Object isRead(String id, boolean isCheck) {
		try {
			gPSService.isRead(id, isCheck);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/get/inClient")
	@ResponseBody
	public Object gpsPois(String mcode) {
		try {
			gPSService.setInOrOut(mcode, true);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/get/extClient")
	@ResponseBody
	public Object extClient(String mcode) {
		try {
			gPSService.setInOrOut(mcode, false);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
