package cn.com.vistech.tz.util;

/**
 * 显示辅助类，分页
 * @author XX
 *
 */
public class ShowPageHelper {
	
	private Integer total;
	private Object rows;
		
	public ShowPageHelper(){}
	
	public ShowPageHelper(Integer total,Object rows){
		this.total =total;
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
	
}
