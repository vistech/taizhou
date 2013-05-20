package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GPSTracePKBean;

public interface GPSTraceDao extends
		CrudRepository<GPSTraceBean, GPSTracePKBean>,
		GPSTraceDaoPlus<GPSTraceBean, GPSTracePKBean> {

}
