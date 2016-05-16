package com.boyaa.mf.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.DateUtil;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.servlet.ResultState;

@SuppressWarnings("all")
public class ServletUtil {
	static Logger logger = Logger.getLogger(ServletUtil.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	private static final String CONTENT_TYPE = "application/x-javascript";
	
	public static void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(com.boyaa.mf.constants.Constants.DEFAULT_ENCODING);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(com.boyaa.mf.constants.Constants.DEFAULT_ENCODING);
		logger.info("request ip:" + ServletUtil.getRemortIP(request));
	}
	
	/**
	 * 获取客户端的访问ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		return ip == null ? request.getRemoteAddr() : ip;
	}

	/**
	 * 检查required key是否存在: action/table name/md5 time/md5 sig
	 * 
	 * @param request
	 * @return
	 */
	public static ResultState checkParams(HttpServletRequest request) {
		String msg = null;
		if (request.getParameter("action") == null) {
			msg = "action is null";
		}
		if (request.getParameter(com.boyaa.mf.constants.Constants.DEFAULT_TABLENAME_PARAM) == null) {
			msg = "table name is null";
		}
		if(StringUtils.isEmpty(Constants.COLUMN_NAME_TM) && StringUtils.isEmpty(request.getParameter(Constants.COLUMN_NAME_STARTTIME)) && StringUtils.isEmpty(request.getParameter(Constants.COLUMN_NAME_ENDTIME))) {
			msg = "_tm/startTime/endTime参数都为空：至少提供_tm参数或者startTime和endTime参数!";
		}
		if (msg == null)
			return null;
		errorLogger.error(msg);
		return new ResultState(ResultState.FAILURE, msg);
	}
	
	/**
	 * 从request中提取字段和比较条件到json中
	 * @param request
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static ResultState getParams(HttpServletRequest request, JSONObject json) throws JSONException {
		Enumeration<String> paramNames = request.getParameterNames();
		JSONObject conditions = JSONUtil.getJSONObject();
		String paramName = null;
		String paramValue = null;
		while (paramNames.hasMoreElements()) {
			paramName = StringUtils.trim(paramNames.nextElement());
			paramValue = StringUtils.trim(request.getParameter(paramName));
			// 如果值为空或者""，不存储该值
			if (StringUtils.isEmpty(paramValue))
				continue;
			// 参数中是否包含了条件符号，用两个下划线分开s
			if (paramName.contains(Constants.condition_segment)) {
				String[] name__condition = paramName.split(Constants.condition_segment);
				if (name__condition.length != 2) {
					String msg = paramName + " with condition format error: [name]__[condition]=[value]";
					errorLogger.error(msg);
					return new ResultState(ResultState.FAILURE, msg);
				} else {
					// 如果有包含"name__condition"格式的key，拆分key后把真实name和value保存
					// 然后把真实"name + __"作为其运算符的key，把condition作为value保存
					// 例如：name__0=123，json中将保存"name=123"和"name__=0"两个对象
					// json.put(name__condition[0], paramValue);
					// json.put(name__condition[0] +
					// ServletConstants.servlet_condition_segment,
					// name__condition[1]);

					String[] value_condtion = { paramValue, name__condition[1] };
					conditions.put(name__condition[0], value_condtion);

					json.put(name__condition[0], paramValue);
				}
			} else {
				json.put(paramName, paramValue);
			}
		}
		json.put(Constants.comparator_condition, conditions);
		return null;
	}

	public static void setDefaultValue(HttpServletRequest request, JSONObject json) throws ParseException, JSONException {
		String daynum = DateUtil.getDayNum(request.getParameter(Constants.daynum_name));
		// 回溯天数 客户端没有传daynum值，默认设为10
		json.put(Constants.daynum_name, daynum);

//		String zone = getTimeZone(request);
//		String zone = "GMT+8";
//		json.put(Constants.COLUMN_NAME_TZONE, zone);

		SimpleDateFormat sdf = null;
		String starttime = request.getParameter(Constants.COLUMN_NAME_STARTTIME);
		String endtime = request.getParameter(Constants.COLUMN_NAME_ENDTIME);
		if(StringUtils.isNotEmpty(starttime) && starttime.length()> 10) {
			if(DateUtil.datetimePattern.matcher(starttime).matches()) {
				sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
//				sdf.setTimeZone(TimeZone.getTimeZone(zone));
				// 已经在新接口中减1处理
				json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getEndTimestamp(endtime, sdf));
			} else if(DateUtil.dateminutePattern.matcher(starttime).matches()) {
				sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_MINUTE_FORMAT);
//				sdf.setTimeZone(TimeZone.getTimeZone(zone));
				// 已经在新接口中减1处理
				json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getEndTimestamp(endtime, sdf));
			}else if(DateUtil.datehourPattern.matcher(starttime).matches()){
				sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_HOUR_FORMAT);
//				sdf.setTimeZone(TimeZone.getTimeZone(zone));
				// 已经在新接口中减1处理
				json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getEndTimestamp(endtime, sdf));
			}
		} else {
			sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT);
//			sdf.setTimeZone(TimeZone.getTimeZone(zone));
			json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getDayEnd(sdf.parse(endtime)).getTime()/1000);
		}
		json.put(Constants.COLUMN_NAME_STARTTIME, DateUtil.getStartTimestamp(starttime, sdf, daynum));
		// 如果是查询多条记录操作，并且客户端没有设置rangepage，则使用默认10页
//		if (request.getParameter(ServletConstants.servlet_rangepage) == null) {
//			json.put(ServletConstants.servlet_rangepage, ServletConstants.servlet_rangepage_default);
//		}

		// 请求的页号，即当前页，没有则默认为第一页
		if (!json.containsKey(com.boyaa.mf.constants.Constants.DEFAULT_PAGE_PARAM))
			json.put(com.boyaa.mf.constants.Constants.DEFAULT_PAGE_PARAM, 1);

		// 每页大小
		if (!json.containsKey(com.boyaa.mf.constants.Constants.DEFAULT_PAGESIZE_PARAM))
			json.put(com.boyaa.mf.constants.Constants.DEFAULT_PAGESIZE_PARAM,com.boyaa.mf.constants.Constants.DEFAULT_PAGESIZE_PARAM);
	}

	public static String responseRecords(HttpServletResponse response, String callback, ResultState resultState) throws IOException {
		return responseRecords(response, callback, resultState, false);
	}
	
	public static String responseRecords(HttpServletResponse response, String callback, ResultState resultState, boolean gzip) throws IOException {
		StringBuffer sb = new StringBuffer();
		if (callback == null)
			sb.append("{");
		else
			sb.append(callback).append("({");

		sb.append("\"ok\":").append(resultState.getOk()).append(",");
		sb.append("\"msg\":\"").append(resultState.getMsg()).append("\",");
		if(resultState.getOther().size() != 0)
			sb.append("\"other\":").append(resultState.getOther().toString()).append(",");
		if (resultState.getMaxPage() != null)
			sb.append("\"maxPage\":").append(resultState.getMaxPage()).append(",");
		if(resultState.getNum() != null) {
			sb.append("\"num\":").append(resultState.getNum()).append(",");
		}
		if(gzip)
			sb.append("\"loop\":").append(ZipUtils.gzip(resultState.getData()));
		else
			sb.append("\"loop\":").append(resultState.getData());

		if (callback == null)
			sb.append("}");
		else
			sb.append("})");

		String result = sb.toString();
		response.getWriter().write(result);
		return result;
	}

	public static String responseRecord(HttpServletResponse response, String callback, ResultState resultState) throws IOException {
		return responseRecord(response, callback, resultState, false);
	}
	
	public static String responseRecord(HttpServletResponse response, String callback, ResultState resultState, boolean gzip) throws IOException {
		StringBuffer sb = new StringBuffer();
		if (callback == null)
			sb.append("{");
		else
			sb.append(callback).append("({");

		sb.append("\"ok\":").append(resultState.getOk()).append(",");
		sb.append("\"msg\":\"").append(resultState.getMsg()).append("\",");

		sb.append("\"other\":").append(resultState.getOther().toString()).append(",");

		if(gzip)
			sb.append("\"data\":").append(ZipUtils.gzip(resultState.getData()));
		else
			sb.append("\"data\":").append(resultState.getData());

		if (callback == null)
			sb.append("}");
		else
			sb.append("})");

		String result = sb.toString();
		response.getWriter().write(result);
		return result;
	}
	
	/**
	 * 从请求中解析出开始时间和结束时间，保存到json中
	 * @param request
	 * @param json
	 * @throws ParseException
	 * @throws JSONException
	 */
	public static void setTime(HttpServletRequest request, JSONObject json) throws ParseException, JSONException {
		String starttime = request.getParameter(Constants.COLUMN_NAME_STARTTIME);
		String endtime = request.getParameter(Constants.COLUMN_NAME_ENDTIME);
		setTime(request,json,starttime,endtime);
	}
	
	public static void setTime(HttpServletRequest request, JSONObject json,String startTime, String endTime) throws ParseException, JSONException {
		String daynum = DateUtil.getDayNum(request.getParameter(Constants.daynum_name));
//		String zone = getTimeZone(request);
//		SimpleDateFormat sdf = null;
//
//		if(StringUtils.isNotEmpty(startTime) && startTime.length()> 10) {
//			sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
//			sdf.setTimeZone(TimeZone.getTimeZone(zone));
//		} else {
//			sdf = DateUtil.getSimpleDateFormat(zone);
//		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
		json.put(Constants.COLUMN_NAME_STARTTIME, DateUtil.getStartTimestamp(startTime, sdf, daynum));

		// 减1避免把明天的24:00数据查询出来，在做查询时，需要把endrowkey-1
		if (StringUtils.isEmpty(endTime)) {
			json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getDayEnd(new Date()).getTime()/1000);
		}else{
			json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getDayEnd(sdf.parse(endTime)).getTime()/1000);
		}
	}
	
	public static void setTime(JSONObject json,String startTime, String endTime) throws ParseException, JSONException {
		String daynum = DateUtil.getDayNum(null);
//		String zone = DateUtil.getTimeZone(null);
//		SimpleDateFormat sdf = null;
//
//		if(StringUtils.isNotEmpty(startTime) && startTime.length()> 10) {
//			sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
//			sdf.setTimeZone(TimeZone.getTimeZone(zone));
//		} else {
//			sdf = DateUtil.getSimpleDateFormat(zone);
//		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
		json.put(Constants.COLUMN_NAME_STARTTIME, DateUtil.getStartTimestamp(startTime, sdf, daynum));

		// 减1避免把明天的24:00数据查询出来，在做查询时，需要把endrowkey-1
		if (StringUtils.isEmpty(endTime)) {
			json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getDayEnd(new Date()).getTime()/1000);
		}else{
			json.put(Constants.COLUMN_NAME_ENDTIME, DateUtil.getDayEnd(sdf.parse(endTime)).getTime()/1000);
		}
	}
	
	/**
	 * 
	 * @param date yyyy-MM-dd
	 * @param startOrEnd startTime:true,endTime:false
	 * @return
	 * @throws ParseException 
	 */
	public static long date2Int(String date,boolean startOrEnd) throws ParseException{
		SimpleDateFormat sdf = DateUtil.getSimpleDateFormat(Constants.DEFAULT_TIMEZONE);
		if(startOrEnd){
			return  DateUtil.getStartTimestamp(date, sdf, "0");
		}else{
			return DateUtil.getDayEnd(sdf.parse(date)).getTime()/1000;
		}
	}
	
	public static String getTaskName(String taskname, String plat, String sid_bpid, String startTime, String endTime) {
		StringBuffer name = new StringBuffer(taskname);
		if(StringUtils.isNotBlank(plat))
			name.append("_").append(plat);
		if(StringUtils.isNotBlank(sid_bpid))
			name.append("_").append(sid_bpid.substring(0, sid_bpid.indexOf("_")));
		if(StringUtils.isNotBlank(startTime))
			name.append("_").append(startTime);
		if(StringUtils.isNotBlank(endTime))
			name.append("_").append(endTime);
		name.append("_").append(System.currentTimeMillis());
		
		
		if(name.length()>100){
			return name.substring(0, 100);
		}
		
		return name.toString();
	}
	
}
