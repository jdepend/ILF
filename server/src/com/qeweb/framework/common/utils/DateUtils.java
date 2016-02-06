package com.qeweb.framework.common.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;


/**
 * 日期工具类
 *
 * @author Pengt
 *
 */
public class DateUtils {

	public static final String DATE_FORMAT_YY = "yy";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDD_HH_MM = "yyyyMMdd-HHmm";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	/**
	 * 跟据模版返回sql.date时间
	 *
	 * @param str
	 * @param parsePatterns
	 * @return
	 */
	public static Date parseSqlDate(String str, String[] parsePatterns) {

		java.sql.Date sdate = null;
		try {
			java.util.Date udate = org.apache.commons.lang3.time.DateUtils.parseDate(str, parsePatterns);
			sdate = new java.sql.Date(udate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdate;

	}

	/**
	 * 跟据模版返回util.date时间
	 *
	 * @param str
	 * @param parsePatterns
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date parseUtilDate(String str,
			String[] parsePatterns) throws ParseException {
		if(str.length() > parsePatterns[0].length())
			str = str.substring(0, parsePatterns[0].length());
		java.util.Date udate = org.apache.commons.lang3.time.DateUtils.parseDate(str, parsePatterns);

		return udate;
	}

	/**
	 * 返回yyyy-MM-dd 格式的util.date时间
	 *
	 * @param str
	 * @return
	 */
	public static java.util.Date parseUtilDate(String str) {
		try {
			return StringUtils.isEmpty(str) ? null : parseUtilDate(str, new String[]{DATE_FORMAT_YYYY_MM_DD});
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前时间Timestamp
	 *
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取Timestamp
	 * @param str
	 * @return
	 */
	public static Timestamp getTimestamp(String str) {
		if(StringUtils.isEmpty(str))
			return null;

		//yyyy-MM-dd HH:mm:ss 格式
		if(str.length() > 10){
			if(str.length() == 13)
				return Timestamp.valueOf(str + ":00:00");
			else if(str.length() == 16)
				return Timestamp.valueOf(str + ":00");
			else
				return Timestamp.valueOf(str);
		}
		//yyyy
		else if(str.length() == 4) {
			return Timestamp.valueOf(str + "-01-01 00:00:00");
		}
		//yyyy-mm
		else if(str.length() == 7) {
			return Timestamp.valueOf(str + "-01 00:00:00");
		}
		else {
			return Timestamp.valueOf(str + " 00:00:00");
		}
	}

	/**
	 * 将Date类型日期转化成String类型"任意"格式
	 * java.sql.Date,java.sql.Timestamp类型是java.util.Date类型的子类
	 *
	 * @param date
	 *            Date
	 * @param format
	 *            String "2003-01-01"格式 "yyyy年M月d日" "yyyy-MM-dd HH:mm:ss"格式
	 * @return String
	 * @author sbt 2007-09-02
	 */
	public static String dateToString(java.util.Date date, String format) {
		if (date == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 获得当前时间并转换成yyyyMMdd-HHmm格式
	 * @return
	 */
	public static String getNowStr() {
		return dateToString(new java.util.Date(), DATE_FORMAT_YYYYMMDD_HH_MM);
	}

	/**
	 * 将Date类型日期转化成String类型:yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String dateToString(java.util.Date date) {
		return dateToString(date, DATE_FORMAT_YYYY_MM_DD);
	}

	/**
	 * 字符串转时间
	 * @param dateStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String dateStr) {
		return stringToTimestamp(dateStr, DATE_FORMAT_YYYY_MM_DD);
	}

	public static Timestamp stringToTimestamp(String dateStr, String fmt) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}

		if (StringUtils.isEmpty(fmt)) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(fmt);
		java.util.Date d = null;
		try {
			d = format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

		Assert.assertNotNull(d);
		return new Timestamp(d.getTime());
	}

	/**
	 * 获取当前时间毫秒
	 * @return
	 */
	public static long getCurrentTimeInMillis(){
		Calendar c = new GregorianCalendar();
		return c.getTimeInMillis();
	}

	/**
	 * 获取当前年
	 */
	public static int getCurrentYear(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月
	 */
	public static int getCurrentMonth(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static int getCurrentDate(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.DATE);
	}

	/**
	 * 获取当前小时
	 * @return
	 */
	public static int getCurrentHour(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取当前分钟
	 * @return
	 */
	public static int getCurrentMinute(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取当前秒
	 * @return
	 */
	public static int getCurrentSecond(){
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前季度
	 */
	public static int getCurrentQuarter(){
		Calendar c = new GregorianCalendar();
		return (c.get(Calendar.MONTH) / 3) + 1;
	}

	/**
	 * 获取上个月对应的年份
	 * @return
	 */
	public static int getYearOfLastMonth(){
		return ((getCurrentMonth() - 1) == 0 ? (getCurrentYear() - 1) : getCurrentYear());
	}

	/**
	 * 获取上个季度对应的年份
	 * @return
	 */
	public static int getYearOfLastQuarter(){
		return ((getCurrentQuarter() - 1) == 0 ? (getCurrentYear() - 1) : getCurrentYear());
	}

	/**
	 * 获取上个月
	 * @return
	 */
	public static int getLastMonth(){
		return ((getCurrentMonth() - 1) == 0 ? 12 : getCurrentMonth() - 1);
	}

	/**
	 * 获取上个季度
	 * @return
	 */
	public static int getLastQuarter(){
		return ((getCurrentQuarter() - 1) == 0 ? 4 : getCurrentQuarter() - 1);
	}

	/**
	 * 获取上一年
	 * @return
	 */
	public static int getLastYear(){
		return (getCurrentYear() - 1);
	}

	/**
	 * 获取指定时间的前几天或后几天
	 * @param date
	 * @param day  待加减的天数
	 * @return
	 */
	public static java.util.Date addDay(java.util.Date date, int day) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DATE, day);
		return rightNow.getTime();
	}
}

