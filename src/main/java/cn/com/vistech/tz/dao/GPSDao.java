package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.GPSBean;

public interface GPSDao extends CrudRepository<GPSBean, Integer> {
	
}
