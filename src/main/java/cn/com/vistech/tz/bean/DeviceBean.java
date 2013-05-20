package cn.com.vistech.tz.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_device")
public class DeviceBean {
	
	private String DeviceName;
	@Id
	private String sim;
	
	private Double lo;
	
	private Double la;
	
	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}
	
	public Double getLo() {
		return lo;
	}

	public void setLo(Double lo) {
		this.lo = lo;
	}

	public Double getLa() {
		return la;
	}

	public void setLa(Double la) {
		this.la = la;
	}

}
