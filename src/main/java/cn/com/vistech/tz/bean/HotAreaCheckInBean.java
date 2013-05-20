package cn.com.vistech.tz.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_HotAreaCheckin")
public class HotAreaCheckInBean implements Serializable {
	private static final long serialVersionUID = 4088665656949627685L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ids;
	
	private String id;
	
	private Date dt;
	
	private String hotAreaID;


	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getHotAreaID() {
		return hotAreaID;
	}

	public void setHotAreaID(String hotAreaID) {
		this.hotAreaID = hotAreaID;
	}
	
	@PrePersist
	public void prePersist() {
		Date now = new Date();
		if (dt == null)
			dt = now;
	}

	public HotAreaCheckInBean(String id, String hotAreaID) {
		super();
		this.id = id;
		this.hotAreaID = hotAreaID;
	}

	public HotAreaCheckInBean() {
		super();
	}

}
