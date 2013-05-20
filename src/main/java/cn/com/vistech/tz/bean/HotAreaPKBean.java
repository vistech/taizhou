package cn.com.vistech.tz.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HotAreaPKBean implements Serializable {

	private static final long serialVersionUID = -189589020390719333L;

	@Column(name = "SIM", length = 50)
	private String sim;

	@Column(name = "AreaID")
	private String areaId;

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public HotAreaPKBean(String sim, String areaId) {
		super();
		this.sim = sim;
		this.areaId = areaId;
	}

	public HotAreaPKBean() {
		super();
	}
	
	

}
