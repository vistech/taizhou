package cn.com.vistech.tz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.vistech.tz.bean.report.CheckOnReport;
import cn.com.vistech.tz.bean.report.ChuQingSort_ReportBean;
import cn.com.vistech.tz.dao.CheckOnReportDao;

@Service
@Transactional
public class CheckOnReportService {
	@Autowired
	private CheckOnReportDao checkOnReportDao;

	public List<CheckOnReport> getCheckOnReport(String reportName)
			throws ParseException {
		List<?> list = checkOnReportDao.Opengps_spReport(reportName);
		List<CheckOnReport> crList = new ArrayList<CheckOnReport>();
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			CheckOnReport cr = new CheckOnReport();
			cr.setCategory(String.valueOf(o[0]));
			cr.setFullAttendance(String.valueOf(o[1]));
			cr.setFullAttendanceRate(String.valueOf(o[2]));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String bdate = new java.text.SimpleDateFormat("MM-dd").format(sdf
					.parse(String.valueOf(o[4])));
			cr.setbDate(bdate);
			String edate = new java.text.SimpleDateFormat("MM-dd").format(sdf
					.parse(String.valueOf(o[5])));
			cr.seteDate(edate);
			crList.add(cr);
		}
		return crList;
	}

	/*
	 * 功能：得到月度巡查满勤率排行榜
	 */
	public List<ChuQingSort_ReportBean> getChuQingSort_ReportBean() {
		List<?> list = checkOnReportDao.ChuQingSort_Report();
		List<ChuQingSort_ReportBean> cqsr = new ArrayList<ChuQingSort_ReportBean>();
		for (Object obj : list) {
			Object[] objs = (Object[]) obj;
			ChuQingSort_ReportBean bean = new ChuQingSort_ReportBean();
			bean.setCity(String.valueOf(objs[0]));
			bean.setManqin(String.valueOf(objs[1]));
			bean.setManqinlv(String.valueOf(objs[2]));
			cqsr.add(bean);
		}
		return cqsr;
	}
}
