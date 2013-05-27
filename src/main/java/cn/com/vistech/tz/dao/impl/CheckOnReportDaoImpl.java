package cn.com.vistech.tz.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import cn.com.vistech.tz.dao.CheckOnReportDao;

@Component
public class CheckOnReportDaoImpl implements CheckOnReportDao {
	@PersistenceContext
	protected EntityManager em;
	@Override
	public List<?> Opengps_spReport(String reportName) {
		Query query = em.createNativeQuery("{call opengps_spReport(:reportName)}");
		query.setParameter("reportName", reportName);
		return query.getResultList();
	}

}
