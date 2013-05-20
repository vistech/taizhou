package cn.com.vistech.tz.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opengps_HotArea")
public class HotAreaBean implements Serializable {

	private static final long serialVersionUID = -5199038803758625000L;

	@EmbeddedId
	private HotAreaPKBean hotareapk;
	
	@Column(name="lgtd")
	private Double pLgtd;
	@Column(name="lttd")
	private Double pLttd;
	@Column(name="name")
	private String pName;

	private String memo;

	private byte[] photo;

	private boolean hasSuccess;
	@Column(name="id")
	private int pid;

	private boolean hasSet;

	public HotAreaPKBean getHotareapk() {
		return hotareapk;
	}

	public void setHotareapk(HotAreaPKBean hotareapk) {
		this.hotareapk = hotareapk;
	}

	public Double getpLgtd() {
		return pLgtd;
	}

	public void setpLgtd(Double pLgtd) {
		this.pLgtd = pLgtd;
	}

	public Double getpLttd() {
		return pLttd;
	}

	public void setpLttd(Double pLttd) {
		this.pLttd = pLttd;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public boolean isHasSuccess() {
		return hasSuccess;
	}

	public void setHasSuccess(boolean hasSuccess) {
		this.hasSuccess = hasSuccess;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public boolean isHasSet() {
		return hasSet;
	}

	public void setHasSet(boolean hasSet) {
		this.hasSet = hasSet;
	}
}
