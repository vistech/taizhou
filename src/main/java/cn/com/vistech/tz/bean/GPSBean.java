package cn.com.vistech.tz.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GPS")
public class GPSBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7237923120382198679L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String telNum;
	
	private Double longiTude;
	
	private Double latiTude;
	
	private Double speed;
	
	private Double direction;
	
	private Date time;
	
	private String status;
	
	private String mileage;
	
	private String oil;
	
	private Integer isBasestation;
	
	private Integer isPosition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public Double getLongiTude() {
		return longiTude;
	}

	public void setLongiTude(Double longiTude) {
		this.longiTude = longiTude;
	}

	public Double getLatiTude() {
		return latiTude;
	}

	public void setLatiTude(Double latiTude) {
		this.latiTude = latiTude;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getDirection() {
		return direction;
	}

	public void setDirection(Double direction) {
		this.direction = direction;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public Integer getIsBasestation() {
		return isBasestation;
	}

	public void setIsBasestation(Integer isBasestation) {
		this.isBasestation = isBasestation;
	}

	public Integer getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(Integer isPosition) {
		this.isPosition = isPosition;
	}

	public String getOil() {
		return oil;
	}

	public void setOil(String oil) {
		this.oil = oil;
	}
	
	
	
}
