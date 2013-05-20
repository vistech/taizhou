package cn.com.vistech.tz.bean.show;

import java.util.List;

public class TimelineBean {

	public String headline;
	public String type;
	public String startDate;
	public String text;
	public Asset asset = new Asset();

	public List<TimelineBean> date;

	public class Asset {
		public String media;
		public String credit;
		public String caption;
	}
}
