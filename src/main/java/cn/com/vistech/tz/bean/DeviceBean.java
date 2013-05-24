package cn.com.vistech.tz.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_device")
public class DeviceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4839969538093212285L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String DeviceName;

	private String sim;

	private Double lo;

	private Double la;

	private Boolean isCheckIn;

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

	public Boolean getIsCheckIn() {
		return isCheckIn;
	}

	public void setIsCheckIn(Boolean isCheckIn) {
		this.isCheckIn = isCheckIn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
