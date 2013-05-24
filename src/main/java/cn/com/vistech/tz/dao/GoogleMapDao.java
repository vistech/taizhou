package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.GoogleMapBean;

public interface GoogleMapDao extends CrudRepository<GoogleMapBean, Integer> {
	
	
	GoogleMapBean findByLaAndLo(String la,String lo);
}
