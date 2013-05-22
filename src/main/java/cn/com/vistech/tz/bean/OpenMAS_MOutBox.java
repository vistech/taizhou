package cn.com.vistech.tz.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "openMAS_MOutBox")
public class OpenMAS_MOutBox implements Serializable {
	private static final long serialVersionUID = -6704216115900983946L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date dt;
	private String sim;
	private String info;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Boolean getHasRead() {
		return hasRead;
	}

	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

	public Boolean getHasSend() {
		return hasSend;
	}

	public void setHasSend(Boolean hasSend) {
		this.hasSend = hasSend;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	private Boolean hasRead;
	private Boolean hasSend;
	private Date sendTime;
}
