package cn.com.vistech.tz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.vistech.tz.bean.report.CheckOnReport;
import cn.com.vistech.tz.dao.CheckOnReportDao;

@Service
@Transactional
public class CheckOnReportService {
	@Autowired
	private CheckOnReportDao checkOnReportDao;
	
	public List<CheckOnReport> getCheckOnReport(String reportName){
		List<?> list= checkOnReportDao.Opengps_spReport(reportName);
		List<CheckOnReport> crList=new ArrayList<CheckOnReport>();
		for (int i = 0; i < list.size(); i++) {
			@SuppressWarnings("unchecked")
			List<String> o = (List<String>) list.get(i);
			CheckOnReport cr=new CheckOnReport();
			cr.setCategory(o.get(0));
			cr.setFullAttendance(o.get(1));
			cr.setFullAttendanceRate(o.get(2));
			cr.setbDate(o.get(3));
			cr.seteDate(o.get(4));
		}
		return crList;
	}
}
