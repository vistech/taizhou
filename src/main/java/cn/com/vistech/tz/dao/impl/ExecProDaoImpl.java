package cn.com.vistech.tz.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import cn.com.vistech.tz.dao.ExecProDao;

import com.google.common.collect.Lists;

@Component
public class ExecProDaoImpl implements ExecProDao {
	@PersistenceContext
	protected EntityManager em;

	@Override
	public Boolean execPro(String callPro, Map<String, Object> params) {

		Query query = em.createNativeQuery(callPro);

		if (params != null) {
			for (String paramName : params.keySet()) {
				query.setParameter(paramName, params.get(paramName));
			}
		}
		int resul = 0;
		try {
			resul = query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return resul == 1 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findSome(Class<T> clazz, String callPro,
			Map<String, Object> params) {
		Query query = null;
		if (clazz != null) {
			query = em.createNativeQuery(callPro, clazz);
		} else {
			query = em.createNativeQuery(callPro);
		}

		if (params != null) {
			for (String paramName : params.keySet()) {
				query.setParameter(paramName, params.get(paramName));
			}
		}

		List<T> result = query.getResultList();
		Iterator<T> iter = result.iterator();
		List<T> mediaList = Lists.newArrayList();
		while (iter.hasNext()) {
			T media = (T) iter.next();
			mediaList.add(media);
		}
		return mediaList;
	}

}
