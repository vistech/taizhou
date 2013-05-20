package cn.com.vistech.tz.dao;

import java.io.Serializable;

import cn.com.vistech.tz.bean.GPSTraceBean;

public interface GPSTraceDaoPlus<T, ID extends Serializable> {

	Boolean pullNewTraceByProc(GPSTraceBean trace);
}
