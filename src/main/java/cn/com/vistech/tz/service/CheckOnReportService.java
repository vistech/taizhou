package cn.com.vistech.tz.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
			cr.setCategory(o[0].toString());
			cr.setFullAttendance(o[1].toString());
			cr.setFullAttendanceRate(o[2].toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String bdate = new java.text.SimpleDateFormat("MM-dd").format(sdf
					.parse(o[4].toString()));
			cr.setbDate(bdate);
			String edate = new java.text.SimpleDateFormat("MM-dd").format(sdf
					.parse(o[5].toString()));
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
			bean.setCity(objs[0].toString());
			bean.setManqin(objs[1].toString());
			bean.setManqinlv(objs[2].toString());
			cqsr.add(bean);
		}
		return cqsr;
	}
}
