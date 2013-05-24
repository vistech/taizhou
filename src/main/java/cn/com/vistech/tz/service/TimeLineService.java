package cn.com.vistech.tz.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.vistech.tz.bean.DeviceBean;
import cn.com.vistech.tz.bean.GPSMediaBean;
import cn.com.vistech.tz.bean.GoogleMapBean;
import cn.com.vistech.tz.bean.show.TimelineBean;
import cn.com.vistech.tz.bean.show.TimelineBean.Asset;
import cn.com.vistech.tz.dao.DeviceDao;
import cn.com.vistech.tz.dao.GPSMediaDao;
import cn.com.vistech.tz.dao.GoogleMapDao;

@Service
@Transactional
public class TimeLineService {
	@Autowired
	private GPSMediaDao mediaDao;

	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private GoogleMapDao googleMapDao;

	private DateFormat dfTime = new SimpleDateFormat("M/d/yyyy HH:mm:ss");

	private String mp4Url;

	private String txtCon;

	private String mp3Url;
	
	private Logger logger = Logger.getLogger(TimeLineService.class);
	
	/**
	 * 生成时间轴 json，特殊的，用于 会议管理 —— 查看
	 * 
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Object[] buildShowTimeLine(Date startDate, Date endDate,
			String userType) throws IOException {
		Properties proper = PropertiesLoaderUtils
				.loadAllProperties("/system.properties");
		mp4Url = proper.getProperty("mp4Url");
		// mapUrl = proper.getProperty("mapUrl");
		txtCon = proper.getProperty("txtCon");

		mp3Url = proper.getProperty("mp3Url");
		String txtCon_none = proper.getProperty("txtCon_none");

		Date nowDate = new Date();

		if (startDate == null && endDate == null) {
			startDate = DateUtils.addDays(nowDate, -2);
			endDate = nowDate;
		}

		TimelineBean jsonTimeLine = new TimelineBean();
		List<TimelineBean> date = null;
		Asset asset = null;

		List<GPSMediaBean> mediaList = mediaDao.findByFileTimeBetweenAndIsHide(
				new Sort(Direction.ASC, new String[] { "fileTime" }),
				startDate, endDate, false);

		DecimalFormat df = new DecimalFormat("#.00");

		Integer i = 0;
		for (GPSMediaBean media : mediaList) {
			DeviceBean device = deviceDao.findBySim(media.getSIM());

			String mediaType = media.getMediaType();

			String mediaUrl = "uploadDir/" + media.getFileUrl();

			if (mediaType.equals("mp4")) {
				mediaUrl = MessageFormat.format(mp4Url, media.getFileUrl());
			} else if (mediaType.equals("txt")) {
				mediaUrl = media.getRemarks();
			} else if (mediaType.equals("mp3")) {
				mediaUrl = MessageFormat.format(mp3Url, media.getFileUrl());
			}

			Date upDate = media.getFileTime();
			String startDateStr = dfTime.format(upDate);

			String risksImg = "1.png";
			String risks = StringUtils.trim(media.getRisks());

			if (risks.equals("关注")) {
				risksImg = "2.png";
			} else if (risks.equals("严重")) {
				risksImg = "3.png";
			}

			Double lgtd = media.getLgtd();
			Double lttd = media.getLttd();
			if (lgtd == null || lttd == null || lgtd == 0d || lttd == 0d) {
				if (device != null) {
					Double dlgtd = device.getLo();
					Double dlttd = device.getLa();
					if (dlgtd == null || dlttd == null || dlgtd == 0d
							|| dlttd == 0d) {
						lgtd = 121.19056d;
						lttd = 29.18766d;
					} else {
						lgtd = dlgtd;
						lttd = dlttd;
					}

				} else {
					lgtd = 121.19056d;
					lttd = 29.18766d;
				}
			}

			GoogleMapBean googleBean = googleMapDao.findByLaAndLo(
					df.format(lttd), df.format(lgtd));

			if (googleBean != null) {
				logger.info("转换前 --> "+lttd+","+lgtd);
				
				Double gOffLa = googleBean.getOffLa();
				Double gOffLo = googleBean.getOffLo();

				lttd = lttd +  gOffLa;
				lgtd = lgtd +  gOffLo;
				
				logger.info("转换后 --> "+lttd+","+lgtd);
			}
			
			

			String txtArea = null;
			if (userType.equals("admin")) {
				txtArea = MessageFormat.format(txtCon, risksImg, media
						.getRemarks(), media.getSIM(), lgtd, lttd, String
						.valueOf(new Date().getTime()), String.valueOf(media
						.getId()), media.getIsRead() ? "checked" : "");
			} else {
				txtArea = MessageFormat.format(txtCon_none, risksImg, media
						.getRemarks(), media.getSIM(), lgtd, lttd, String
						.valueOf(new Date().getTime()), String.valueOf(media
						.getId()), media.getIsRead() ? "checked" : "");
			}

			if (i == 0) {
				jsonTimeLine.headline = device == null ? "无" : device
						.getDeviceName();// 手机号
				jsonTimeLine.type = "default";
				jsonTimeLine.startDate = startDateStr;
				jsonTimeLine.text = txtArea;

				asset = jsonTimeLine.asset;
				asset.media = mediaUrl;
				asset.caption = "";
				asset.credit = "";

				jsonTimeLine.asset = asset;

				date = new ArrayList<TimelineBean>();
				jsonTimeLine.date = date;
			} else {
				TimelineBean timeLine = new TimelineBean();
				date.add(timeLine);

				timeLine.headline = device == null ? "无" : device
						.getDeviceName();// 手机号
				timeLine.startDate = startDateStr;
				timeLine.text = txtArea;

				asset = timeLine.asset;
				asset.media = mediaUrl;
				asset.caption = "";
				asset.credit = "";

				timeLine.asset = asset;
			}

			i++;
		}

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("timeline", jsonTimeLine);

		// String result = gson.toJson(root);

		UUID uuid = UUID.randomUUID();
		String jsonName = uuid.toString();

		String path = this.getClass().getClassLoader().getResource("")
				.getPath();

		path = path.substring(0, path.indexOf("WEB-INF"))
				+ "resources/timeline_json/" + jsonName + ".json";

		ObjectMapper mapper = new ObjectMapper();
		JsonGenerator jsonG = mapper.getJsonFactory().createJsonGenerator(
				new File(path), JsonEncoding.UTF8);
		jsonG.writeObject(root);

		// FileReaderWriter.writerFile(path, result);

		int mediaSize = mediaList.size() - 1;

		return new Object[] { jsonName, mediaSize };
	}
}
