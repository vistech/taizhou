package cn.com.vistech.tz.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.com.vistech.tz.bean.GPSTraceBean;
import cn.com.vistech.tz.bean.GPSTracePKBean;
import cn.com.vistech.tz.dao.GPSTraceDaoPlus;

@Component
public class GPSTraceDaoImpl implements
		GPSTraceDaoPlus<GPSTraceBean, GPSTracePKBean> {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public Boolean pullNewTraceByProc(GPSTraceBean trace) {
		String querySql = "{call opengps_NewTrace(:id,:dt,:lgtd,:lttd,:speed,:direction)}";

		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("id", trace.getTracePK().getId());
		paramMap.put("dt", trace.getTracePK().getDt());
		paramMap.put("lgtd", trace.getLgtd());
		paramMap.put("lttd", trace.getLttd());
		paramMap.put("speed", trace.getSpeed());
		paramMap.put("direction", trace.getDirection());
		
		
		
		Query query = em.createNativeQuery(querySql);

		for (String paramName : paramMap.keySet()) {
			query.setParameter(paramName, paramMap.get(paramName));
		}
		
		int resul = query.executeUpdate();

		return resul == 1 ? true : false;
	}

}
