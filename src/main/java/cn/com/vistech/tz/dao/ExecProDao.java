package cn.com.vistech.tz.dao;

import java.util.Map;


public interface ExecProDao {
	
	Boolean execPro(String callPro,Map<String,Object> params);
}
