package cn.com.vistech.tz.bean.report;
import java.io.Serializable;

public class ChuQingSort_ReportBean implements Serializable {
	private static final long serialVersionUID = 7502588486900038261L;
	private String city;
	private String manqin;
	private String manqinlv;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getManqin() {
		return manqin;
	}

	public void setManqin(String manqin) {
		this.manqin = manqin;
	}

	public String getManqinlv() {
		return manqinlv;
	}

	public void setManqinlv(String manqinlv) {
		this.manqinlv = manqinlv;
	}
}
