package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.HotAreaCheckInBean;

public interface HotAreaCheckInDao extends
		CrudRepository<HotAreaCheckInBean, Integer> {

}
