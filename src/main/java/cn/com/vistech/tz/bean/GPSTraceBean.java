package cn.com.vistech.tz.bean;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_Trace")
public class GPSTraceBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GPSTracePKBean tracePK;
	
	private Double lgtd;
	
	private Double lttd;
	
	private Double speed;
	
	private Double direction;

	
	public Double getLgtd() {
		return lgtd;
	}

	public void setLgtd(Double lgtd) {
		this.lgtd = lgtd;
	}

	public Double getLttd() {
		return lttd;
	}

	public void setLttd(Double lttd) {
		this.lttd = lttd;
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

	public GPSTracePKBean getTracePK() {
		return tracePK;
	}

	public void setTracePK(GPSTracePKBean tracePK) {
		this.tracePK = tracePK;
	}
}
