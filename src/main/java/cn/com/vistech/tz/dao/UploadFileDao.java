package cn.com.vistech.tz.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.vistech.tz.bean.UploadFileBean;

public interface UploadFileDao extends CrudRepository<UploadFileBean, Integer> {
}
