package cn.com.vistech.tz.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.bean.UploadFileBean;
import cn.com.vistech.tz.dao.ExecProDao;
import cn.com.vistech.tz.dao.UploadFileDao;

import com.google.common.collect.Maps;

@Service
public class UploadFileService {

	@Autowired
	private UploadFileDao uploadFileDao;

//	@Autowired
//	private GPSMediaDao mediaDao;

	@Autowired
	private ExecProDao execProDao;

	public void save(UploadFileBean uploadFile) {
		uploadFileDao.save(uploadFile);
	}
	
	@Transactional
	public void save(GPSMediaBean mediaBean) {
		String callPro = "{call opengps_NewMedia(:sim,:filename,"
				+ ":filesize,:fileurl,:remarks,:mediatype,:risks,"
				+ ":lgtd,:lttd)}";
		Map<String, Object> params = Maps.newHashMap();
		params.put("sim", mediaBean.getSIM());
		params.put("filename", mediaBean.getFileName());
		params.put("filesize", mediaBean.getFileSize());
		params.put("fileurl", mediaBean.getFileUrl());
		params.put("remarks", mediaBean.getRemarks());
		params.put("mediatype", mediaBean.getMediaType());
		params.put("risks", mediaBean.getRisks());
		params.put("lgtd", mediaBean.getLgtd());
		params.put("lttd", mediaBean.getLttd());

		execProDao.execPro(callPro, params);
	}
}