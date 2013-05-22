package cn.com.vistech.tz.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import cn.com.vistech.tz.dao.ExecProDao;

@Component
public class ExecProDaoImpl implements ExecProDao {
	@PersistenceContext
	protected EntityManager em;
	

	@Override
	public Boolean execPro(String callPro, Map<String, Object> params) {
		
		Query query = em.createNativeQuery(callPro);

		for (String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}
		int resul = 0;
		try{
			resul = query.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}
		return resul == 1 ? true : false;
	}

}
