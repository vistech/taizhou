package cn.com.vistech.tz.bean.report;

import java.io.Serializable;
import java.util.Date;

public class CheckOnReport implements Serializable {
	private static final long serialVersionUID = 2480738482062607726L;
	private String category;
	private String fullAttendance;
	private String fullAttendanceRate;
	private String bDate;
	private String eDate;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFullAttendance() {
		return fullAttendance;
	}
	public void setFullAttendance(String fullAttendance) {
		this.fullAttendance = fullAttendance;
	}
	public String getFullAttendanceRate() {
		return fullAttendanceRate;
	}
	public void setFullAttendanceRate(String fullAttendanceRate) {
		this.fullAttendanceRate = fullAttendanceRate;
	}
	public String getbDate() {
		return bDate;
	}
	public void setbDate(String bDate) {
		this.bDate = bDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
}
