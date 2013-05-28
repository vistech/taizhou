package cn.com.vistech.tz.dao;

import java.util.List;
import java.util.Map;


public interface ExecProDao {
	
	Boolean execPro(String callPro,Map<String,Object> params);
	
	<T> List<T> findSome(Class<T> clazz,String callPro,Map<String,Object> params);
}
