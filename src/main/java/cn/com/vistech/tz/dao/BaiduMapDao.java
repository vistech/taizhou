package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.BaiduMapBean;

public interface BaiduMapDao extends CrudRepository<BaiduMapBean, Integer> {
	
	
	BaiduMapBean findByLaAndLo(String la,String lo);
}
