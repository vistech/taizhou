package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.DeviceBean;

public interface DeviceDao extends CrudRepository<DeviceBean, String> {
	DeviceBean findBySim(String sim);
}
