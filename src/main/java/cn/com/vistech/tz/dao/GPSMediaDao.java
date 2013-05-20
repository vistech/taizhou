package cn.com.vistech.tz.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.GPSMediaBean;

public interface GPSMediaDao extends CrudRepository<GPSMediaBean, Integer> {

	public List<GPSMediaBean> findAll(Sort sort);

	public List<GPSMediaBean> findByFileTimeBetweenAndIsHide(Sort sort, Date startDate,
			Date endDate,boolean isHide);

}
