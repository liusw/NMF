package com.boyaa.mf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtils {
	static Logger errorLogger = Logger.getLogger("errorLogger");
	
	/**
	 * 字符串转日期类型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date str2Date(String date, String pattern){
		try {
			if(pattern==null){
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取指定日期的星期
	 * @param date
	 * @return
	 */
	public static String getWeek(Date date) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
}
