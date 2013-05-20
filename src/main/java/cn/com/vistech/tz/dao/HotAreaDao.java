package cn.com.vistech.tz.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.HotAreaBean;
import cn.com.vistech.tz.bean.HotAreaPKBean;

public interface HotAreaDao extends CrudRepository<HotAreaBean, HotAreaPKBean> {

	List<HotAreaBean> findByHotareapkSim(String sim);

	HotAreaBean findByPid(int id);
}
