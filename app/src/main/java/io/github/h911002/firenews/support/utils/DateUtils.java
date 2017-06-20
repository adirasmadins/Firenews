package io.github.h911002.firenews.support.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @ClassName: DateUtil
 * @Description: 强大的日期工具类(引用了ctrip)
 * @author qao 2014年9月29日 下午4:06:06
 */
public class DateUtils {
	public static final String TIMEZONE_CN = "Asia/Shanghai";

	/**
	 * 星期数组
	 */
	private final static String[] WEEKNAME_CHINESE = new String[] { "星期日",
			"星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private final static String[] WEEKNAME_CHINESE2 = new String[] { "周日",
			"周一", "周二", "周三", "周四", "周五", "周六" };

	public final static String SIMPLEFORMATTYPESTRING2 = "yyyy-MM-dd HH:mm:ss";

	public final static String SIMPLEFORMATTYPESTRING4 = "yyyy-MM-dd HH:mm";

	public final static String SIMPLEFORMATTYPESTRING7 = "yyyy-MM-dd";

	public final static String SIMPLEFORMATTYPESTRING13 = "HH:mm";

	public final static String SIMPLEFORMATTYPESTRING17 = "MM-dd";


	/**
	 * 今天 ，明天，后天，数组
	 */
	private final static String[] THREEDAYARR = new String[] { "今天", "明天", "后天" };//


	/**
	 * 获取当前日期
	 *
	 * @return Calendar
	 */
	public static Calendar getCurrentCalendar() {
		Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_CN));
		return currentCalendar;
	}

	/**
	 * 将日期字串转为 Calendar ,dateStr(2014-09-20)需超过8位且不能为空,否则返回null
	 *
	 * @param dateStr
	 * @return Calendar
	 */
	public static Calendar getCalendarByDateStrEx(String dateStr) {
		if (TextUtils.isEmpty(dateStr) || dateStr.length() < 8)
			return null;
		Calendar calendar = getCurrentCalendar();
		int year = Integer.valueOf(dateStr.substring(0, 4));
		int month = Integer.valueOf(dateStr.substring(5, 7));
		int day = Integer.valueOf(dateStr.substring(8, 10));
		calendar.set(year, month - 1, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}


	/**
	 * @Description: 将2014-09-20 转化成09月05
	 * @author:qao 2015年1月12日
	 * @param dateStr
	 * @see #formatDate(String, String,String)
	 */
	@Deprecated
	public static String getChangeCalendarEx(String dateStr) {
		return formatDate(getCalendarByDateStrEx(dateStr), "MM月dd日");
	}

	/**
	 * 根据日期返回对应的星期
	 *
	 * @param calendar
	 *            if calendar 为null则返回空字串
	 * @return "星期*"
	 * @see #WEEKNAME_CHINESE
	 * @see #getWeek
	 */
	public static String getShowWeekByCalendar(Calendar calendar) {
		String weekString = "";
		if (calendar == null) {
			return weekString;
		}
		if (getWeek(calendar) != -1) {
			weekString = WEEKNAME_CHINESE[getWeek(calendar)];
		}
		return weekString;
	}

	/**
	 * 根据日期返回对应的周几
	 *
	 * @param calendar
	 *            if calendar 为null则返回空字串
	 * @return "星期*"
	 * @see #WEEKNAME_CHINESE
	 * @see #getWeek
	 */
	public static String getShowWeekByCalendar2(Calendar calendar) {
		String weekString = "";
		if (calendar == null) {
			return weekString;
		}
		if (getWeek(calendar) != -1) {
			weekString = WEEKNAME_CHINESE2[getWeek(calendar)];
		}
		return weekString;
	}

	/**
	 * 返回星期的索引，
	 * @param calendar
	 * @return 索引; calendar == null则返回-1;
	 * @see Calendar#DAY_OF_WEEK
	 */
	public static int getWeek(Calendar calendar) {
		if (calendar != null) {
			return calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return -1;
	}

	/**
	 * 返回，今天/明天/后天
	 *
	 * @param count
	 *            0，1，2 其他则返回空字串
	 * @return
	 * @see #THREEDAYARR
	 */
	public static String getThreeDayDes(int count) {
		if (count >= 0 && count <= 2) {
			return THREEDAYARR[count];
		}
		return "";
	}

	/**
	 * @param dateStr yyyyMMdd 格式的日期
	 * @return 今天/明天/后天 or 星期*
	 * @see #getShowWeek(String, String)
	 * @see #getShowWeek(Calendar)
	 */
	public static String getShowWeek(String dateStr) {
		return getShowWeek(dateStr, "yyyyMMdd");
	}

	/**
	 * @param dateStr
	 * @param format
	 * @return 今天/明天/后天 or 星期*
	 * @see #getShowWeek(Calendar)
	 * @see #getShowWeek(String, String)
	 */
	public static String getShowWeek(String dateStr, String format) {
		Calendar calendar = strToCalendar(dateStr,format);
		if (calendar != null) {
			return getShowWeek(calendar);
		}
		return "";
	}

	/**
	 * @param calendar
	 * @return 今天/明天/后天 or 星期*
	 * @see #getShowWeek(String)
	 * @see #getShowWeek(String, String)
	 * @see #getWeek(String)
	 */
	public static String getShowWeek(Calendar calendar) {
		final Calendar now = getCurrentCalendar();
		final int betweenDays = getDates(calendar, now);
		if (0 <= betweenDays && betweenDays <= 2) {
			return getThreeDayDes(betweenDays);
		} else {
			return getShowWeekByCalendar(calendar);
		}
	}

	/**
	 * 今天/明天 or 星期*
	 * @param dateStr yyyy-MM-dd 格式的日期
	 * @return
	 */
	public static String getWeek(String dateStr) {
		final Date date = StrToDate(dateStr, "yyyy-MM-dd");
		if (date != null) {
			final Calendar now = getCurrentCalendar();
			final long todayLong = roundDate(now.getTime()).getTime();
			//do not need roundDate();
			final long dateLong = date.getTime();
			final int betweenDays = (int) ((dateLong - todayLong) / (24 * 60 * 60 * 1000));
			if (0 <= betweenDays && betweenDays <= 1) {
				return getThreeDayDes(betweenDays);
			} else {
				now.setTime(date);
				return getShowWeekByCalendar(now);
			}
		} else {
			return "";
		}
	}


	/**
	 * date格式化yyyy-MM-dd HH:mm:sst格式
	 * @param date
	 * @see #DateToStr(Date, String)
	 * @see #formatDate(Calendar)
	 * @see #formatDate(Calendar, String)
	 */
	public static String DateToStr(Date date) {
		return DateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * date格式化format格式
	 * @param date
	 * @param format
	 * @see #formatDate(Calendar)
	 * @see #formatDate(Calendar, String)
	 *
	 */
	public static String DateToStr(Date date, String format) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			String str = simpleDateFormat.format(date);
			return str;
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * 将date转化成yyyy-MM-dd格式
	 * @param date
	 * @see #DateToStr(Date)
	 * @see #DateToStr(Date, String)
	 */
	public static String formatDate(Calendar date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 将date转化成format格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Calendar date, String format) {
		if (date == null) return "";
		return DateToStr(date.getTime(), format);
	}

	/**
	 * 将yyyy-MM-dd格式的date日期，转换成format格式
	 * @param date 格式为yyyy-MM-dd的日期
	 * @param format 返回的日期格式
	 * @see #formatDate(String, String, String)
	 */
	public static String formatDate(String date, String format) {
		return formatDate(date ,SIMPLEFORMATTYPESTRING7, format);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的date日期，转换成format格式
	 * @param date 格式为yyyy-MM-dd HH:mm:ss的日期
	 * @param format 返回的日期格式
	 * @see #formatDate(String, String, String)
	 */
	public static String formatDate2(String date, String format) {
		return formatDate(date ,SIMPLEFORMATTYPESTRING2, format);
	}

	/**
	 * 将format1格式的date日期转化为格式为format2的字符串
	 * @param date 待处理日期
	 * @param format1 date的日期格式
	 * @param format2 返回日期格式
	 * @return
	 */
	public static String formatDate(String date, String format1, String format2) {
		try {
			Date _date = StrToDate(date, format1);
			return DateToStr(_date, format2);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将字符串转换成date yyyy-MM-dd HH:mm:ss
	 *
	 * @param str
	 * @see #StrToDate(String, String)
	 * @see #strToCalendar(String)
	 */
	@Deprecated
	public static Date StrToDate(String str) {
		return StrToDate(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date StrToDate(String str, String format1) {
		SimpleDateFormat format = new SimpleDateFormat(format1);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {}
		return date;
	}

	/**
	 * @param str yyyy-MM-dd
	 * @return
	 */
	@Deprecated
	public static Calendar strToCalendar(String str) {
		return strToCalendar(str, "yyyy-MM-dd");
	}

	public static Calendar strToCalendar(String str, String _format) {
		Date d = StrToDate(str, _format);
		if (d != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal;
		} else {
			return null;
		}

	}

	/**
	 * 看不出来什么需求
	 * @param date
	 * @return
	 */
	@Deprecated
	public static Calendar DateToCal(Date date) {
		String s = DateToStr(date, "yyyy-MM-dd HH:mm:ss");
		return strToCalendar(s, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @see #roundDate(Date)
	 */
	@Deprecated
	public static Calendar DateToCal(Date date, String format) {
		String s = DateToStr(date, format);
		return strToCalendar(s, format);
	}



	/**
	 * @param milliseconds
	 * @param format
	 * @return String 日期时间字符串
	 */
	public static String longToString(long milliseconds, String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_CN));
		calendar.setTimeInMillis(milliseconds);
		return formatDate(calendar, format);
	}

	public static boolean isToday(Date date) {
		Calendar cal = getCurrentCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(date);
		return (year == cal.get(Calendar.YEAR)
				&& (month == cal.get(Calendar.MONTH)) && day == cal
				.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 得到两个日期相差的天数
	 */
	public static int getDates(Calendar p_start, Calendar p_end) {
		int betweenDay = p_end.get(Calendar.DAY_OF_YEAR)
				- p_start.get(Calendar.DAY_OF_YEAR);
		int y2 = p_end.get(Calendar.YEAR);
		if (p_start.get(Calendar.YEAR) != y2) {
			Calendar temp = (Calendar) p_start.clone();
			do {
				betweenDay += temp.getActualMaximum(Calendar.DAY_OF_YEAR);
				temp.add(Calendar.YEAR, 1);
			} while (temp.get(Calendar.YEAR) != y2);
		}
		return betweenDay;
	}
	/**
	 * 获取整天的日期，去除时分秒
	 *
	 * @param date
	 * @return
	 */
	public static Date roundDate(Date date) {
		return roundDate(date,0);
	}

	public static Date roundDate(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}



	/**
	 * @description 和当前时间时间进行比较
	 * @auther xLiu
	 *            endTime,String format
	 * @return boolean currentTime>endTime
	 */
	public static boolean isOutCurrentTime(String endTime, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			Date endDate = simpleDateFormat.parse(endTime);
			long endTimeLong = endDate.getTime();

			Date currDate = new Date(System.currentTimeMillis());
			String currTime = simpleDateFormat.format(currDate);
			currDate = simpleDateFormat.parse(currTime);
			long currentTime = currDate.getTime();

			return currentTime > endTimeLong;
		} catch (ParseException e) {}
		return false;
	}


	/**
	 * time为"HH:mm"格式
	 */
	public static int getMinsByStr(String time) {
		if (time == null)
			return 0;
		String[] ss = time.split(":");
		if (ss.length != 2)
			return 0;
		try {
			return Integer.parseInt(ss[0]) * 60 + Integer.parseInt(ss[1]);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 在当前日期加上多少天
	 *
	 * @param number
	 * @param dateStr
	 * @return
	 */
	public static String addDay(int number, String dateStr) {
		Calendar calendar = DateUtils.strToCalendar(dateStr);
		calendar.add(Calendar.DATE, number);
		return DateUtils.formatDate(calendar, "yyyy-MM-dd");
	}

	/**
	 * 在当前日期加上多少分钟
	 *
	 * @param number
	 * @return
	 */
	public static String addMinute(int number, String minuteStr) {
		Calendar calendar = DateUtils.strToCalendar(minuteStr,"yyyy-MM-dd HH:mm");
		calendar.add(Calendar.MINUTE, number);
		return DateUtils.formatDate(calendar, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 比较两个日期相差的月份.
	 *
	 * @return
	 * @author wujb
	 * @date 2016-8-18
	 */
	public static int compareMonth(String p_start, String p_end) {
		if (TextUtils.isEmpty(p_start) || TextUtils.isEmpty(p_end)) {
			return 0;
		}
		return compareMonth(strToCalendar(p_start), strToCalendar(p_end));
	}

	public static int compareMonth(Calendar p_start, Calendar p_end) {
		int firstYear = p_start.get(Calendar.YEAR);
		int secondYear = p_end.get(Calendar.YEAR);
		int firstMonth = p_start.get(Calendar.MONTH);
		int secondMonth = p_end.get(Calendar.MONTH);
		int firstDay = p_start.get(Calendar.DAY_OF_MONTH);
		int secondDay = p_end.get(Calendar.DAY_OF_MONTH);
		// 如果不足天数未超过一个月，则减一。如4.25与5.20相差0个月，4.20与5.20相差0个月，4.15与5.20相差一个月。
		int result = (secondYear - firstYear) * 12 + (secondMonth - firstMonth);
		if (secondDay <= firstDay) {
			result--;
		}
		return result;
	}

	/**
	 * 比较相差多少天.
	 *
	 * @param p_start
	 * @param p_end
	 * @return
	 * @author wujb
	 * @date 2016-8-18
	 */
	public static int compareDay(String p_start, String p_end) {
		if (TextUtils.isEmpty(p_start) || TextUtils.isEmpty(p_end)) {
			return 0;
		}
		return getDates(strToCalendar(p_start), strToCalendar(p_end));
	}

	/**
	 * 通过分钟数获取时间描述
	 *
	 * @param mins
	 * @return
	 */
	public static String getTimeDesByMins(int mins) {
		int d = mins / (24 * 60);
		int h = (mins % (24 * 60)) / 60;
		int m = mins % 60;
		return (d > 0 ? d + "d" : "") + (h > 0 ? h + "h" : "") + (m > 0 ? m + "m" : "");
	}

	/**
	 * 通过分钟数获取时间描述
	 *
	 * @param mins
	 * @return
	 */
	public static String getTimeDesCHByMins(int mins) {
		int d = mins / (24 * 60);
		int h = (mins % (24 * 60)) / 60;
		int m = mins % 60;
		return (d > 0 ? d + "天" : "") + (h > 0 ? h + "时" : "") + (m > 0 ? m + "分" : "");
	}

	/**
	 * 通过分钟数获取时间描述
	 *
	 * @param mins
	 * @return
	 */
	public static String getTimeDesCHByMins2(int mins) {
		int h = mins / 60;
		int m = mins % 60;
		return  (h > 0 ? h + "时" : "") + (m > 0 ? m + "分" : "");
	}


	public static int getIntervelMin(String startDate,String format1,String endDate, String format2){
		Date date1 = StrToDate(startDate , format1);
		Date date2 = StrToDate(endDate , format2);
		return getMins(date1,date2);
	}

	/**
	 * 得到两个时间相差的分钟数
	 */
	public static int getMins(Date p_start, Date p_end) {
		if (p_start == null || p_end == null) {
			return 0;
		}
		return (int) (p_end.getTime() - p_start.getTime()) / (1000 * 60);

	}

	/**
	 * 相差多少分钟
	 *
	 * @param p_start
	 * @param p_end
	 * @return
	 */
	public static int compareMins(String p_start, String p_end, String format) {
		if (TextUtils.isEmpty(p_start) || TextUtils.isEmpty(p_end)) {
			return 0;
		}
		return getMins(StrToDate(p_start, format), StrToDate(p_end, format));
	}


}
