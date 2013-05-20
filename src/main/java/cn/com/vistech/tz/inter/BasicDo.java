package cn.com.vistech.tz.inter;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 基础操作
 * @author XX
 * @param <S>
 *
 */
@Service
public interface BasicDo<S> {
	List<S> query(Object[] args);
	Boolean add(S entity);
	Boolean modify(S entity);
	Boolean remove(Integer id);
}
