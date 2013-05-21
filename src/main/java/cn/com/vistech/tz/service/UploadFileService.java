package cn.com.vistech.tz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.bean.UploadFileBean;
import cn.com.vistech.tz.dao.GPSMediaDao;
import cn.com.vistech.tz.dao.UploadFileDao;

@Service
public class UploadFileService  {

	@Autowired
	private UploadFileDao uploadFileDao;
	
	@Autowired
	private GPSMediaDao mediaDao;

	public void save(UploadFileBean uploadFile) {
		uploadFileDao.save(uploadFile);
	}
	
	public void save(GPSMediaBean mediaBean){
		mediaDao.save(mediaBean);
	}
}