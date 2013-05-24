package cn.com.vistech.tz.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_BaiduMap")
public class BaiduMapBean implements Serializable {
	private static final long serialVersionUID = -8478889556044092486L;
	
	@Id
	private Integer id;
	
	private String lo;
	
	private String la;
	
	private Double offLo;
	
	private Double offLa;
	
	public Double getOffLo() {
		return offLo;
	}

	public void setOffLo(Double offLo) {
		this.offLo = offLo;
	}

	public Double getOffLa() {
		return offLa;
	}

	public void setOffLa(Double offLa) {
		this.offLa = offLa;
	}
	
	public String getLo() {
		return lo;
	}

	public void setLo(String lo) {
		this.lo = lo;
	}

	public String getLa() {
		return la;
	}

	public void setLa(String la) {
		this.la = la;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
