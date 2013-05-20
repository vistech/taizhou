package cn.com.vistech.tz.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "openGPS_Media")
public class GPSMediaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "fileName", length = 50)
	private String fileName;

	@Column(name = "fileUrl", length = 50)
	private String fileUrl;

	@Column(name = "fileSize", length = 20)
	private String fileSize;
	@Column(name = "fileTime")
	private Date fileTime;

	private String remarks;

	private String SIM;

	private String mediaType;

	private String risks;

	private Double lgtd;

	private Double lttd;

	private Boolean isHide;

	private Boolean isRead;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Date getFileTime() {
		return fileTime;
	}

	public void setFileTime(Date fileTime) {
		this.fileTime = fileTime;
	}

	@PrePersist
	public void prePersist() {
		Date now = new Date();
		if (fileTime == null)
			fileTime = now;
		
		isHide = false;
		isRead = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSIM() {
		return SIM;
	}

	public void setSIM(String sIM) {
		SIM = sIM;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getRisks() {
		return risks;
	}

	public void setRisks(String risks) {
		this.risks = risks;
	}

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

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
}
