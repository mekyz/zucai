package com.lrcall.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTools
{
	/**
	 * 获取指定时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp getTimestamp(long time)
	{
		Date tm = new Date(time);
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(from.format(tm));
	}

	/**
	 * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
	 */
	public static Timestamp getCurrentDateTime()
	{
		return getTimestamp(System.currentTimeMillis());
	}

	/**
	 * 获取指定时间，格式为yyyy-MM-dd HH:mm:ss
	 *
	 * @param time
	 * @return
	 */
	public static String getDateTimeString(long time)
	{
		Date tm = new Date(time);
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = Timestamp.valueOf(from.format(tm)).toString();
		if (timestamp.lastIndexOf(".") > -1)
		{
			timestamp = timestamp.substring(0, timestamp.lastIndexOf("."));
		}
		return timestamp;
	}

	/**
	 * 获取指定格式的时间 long类型
	 * 
	 * @param tmp
	 *            String类型时间
	 * @return
	 */
	public static long getTime(String tmp)
	{
		Timestamp t = Timestamp.valueOf(tmp);
		return t.getTime();
	}

	/**
	 * 计算从某年1月1日0点0分0秒到现在的时间差
	 * 
	 * @param year
	 *            年份
	 * @return
	 */
	public static long getFromMillis(int year)
	{
		long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, 1, 1, 0, 0, 0);
		return current - calendar.getTimeInMillis();
	}

	/**
	 * 计算从2016年1月1日0点0分0秒到现在的时间差
	 * 
	 * @return
	 */
	public static long getFromMillis()
	{
		return getFromMillis(2016);
	}

	/**
	 * 返回当前日期，比如20160901
	 * 
	 * @return
	 */
	public static long getTodayDateLong()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) * 10000L + (calendar.get(Calendar.MONTH) + 1) * 100L + calendar.get(Calendar.DAY_OF_MONTH) * 1L;
	}

	/**
	 * 返回当前日期，比如2016年9月1日
	 * 
	 * @return
	 */
	public static String getTodayDateString()
	{
		Calendar calendar = Calendar.getInstance();
		return String.format("%d年%d月%d日", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 返回当前月份，比如201609
	 * 
	 * @return
	 */
	public static long getTodayYearMonthLong()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) * 100L + (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 返回日期最初的第一秒时间
	 * 
	 * @return
	 */
	public static long getDayBeginDateTimeLong(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		// 这样设置相差12小时
		// calendar.set(Calendar.HOUR, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 返回当前日期最初的第一秒时间
	 * 
	 * @return
	 */
	public static long getTodayBeginDateTimeLong()
	{
		return getDayBeginDateTimeLong(System.currentTimeMillis());
	}

	/**
	 * 返回当前日期最后的第一秒时间
	 * 
	 * @return
	 */
	public static long getTodayEndDateTimeLong()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		// 这样设置相差12小时
		// calendar.set(Calendar.HOUR, 23);
		// calendar.set(Calendar.MINUTE, 59);
		// calendar.set(Calendar.SECOND, 59);
		// calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime().getTime();
	}

	/**
	 * 返回当前日期最后的第一秒时间
	 * 
	 * @return
	 */
	public static int getWeekday(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		int res = calendar.get(Calendar.DAY_OF_WEEK);// 这里是星期天为第一天，需要转换一下
		res = res - 1;
		if (res == 0)
		{
			res = 7;
		}
		return res;
	}

	/**
	 * 返回当前年份，比如2016
	 * 
	 * @return
	 */
	public static String getTodayYearString()
	{
		Calendar calendar = Calendar.getInstance();
		return String.format("%d", calendar.get(Calendar.YEAR));
	}

	/**
	 * 返回时间的秒
	 * 
	 * @return
	 */
	public static int getSecondInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 返回时间的分钟
	 * 
	 * @return
	 */
	public static int getMinuteInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回时间的小时
	 * 
	 * @return
	 */
	public static int getHourInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回时间的天
	 * 
	 * @return
	 */
	public static int getDayInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回时间的月
	 * 
	 * @return
	 */
	public static int getMonthInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回时间的年
	 * 
	 * @return
	 */
	public static int getYearInTime(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param tm
	 * @return
	 */
	public static boolean isLeapMonth(long tm)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tm);
		int year = calendar.get(Calendar.YEAR);
		if (year % 4 != 0)
		{
			return false;
		}
		if (year % 100 == 0 && year % 400 != 0)
		{
			return false;
		}
		return true;
	}

	/**
	 * 获取指定月的月初时间
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthStartTimeLong(int year, int month)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 获取指定月的月末时间
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthEndTimeLong(int year, int month)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, 1, 0, 0, 0);
		return calendar.getTime().getTime() - 1;
	}

	/**
	 * 获取本月初的时间
	 * 
	 * @return
	 */
	public static long getTodayMonthStartTimeLong()
	{
		long current = System.currentTimeMillis();
		return getMonthStartTimeLong(getYearInTime(current), getMonthInTime(current));
	}

	/**
	 * 获取本月末的时间
	 * 
	 * @return
	 */
	public static long getTodayMonthEndTimeLong()
	{
		long current = System.currentTimeMillis();
		return getMonthEndTimeLong(getYearInTime(current), getMonthInTime(current));
	}

	/**
	 * 获取星期
	 * 
	 * @return
	 */
	public static String getWeekdayStr(int weekday)
	{
		if (weekday == 1)
		{
			return "Mon";
		}
		else if (weekday == 2)
		{
			return "Tue";
		}
		else if (weekday == 3)
		{
			return "Wed";
		}
		else if (weekday == 4)
		{
			return "Thu";
		}
		else if (weekday == 5)
		{
			return "Fri";
		}
		else if (weekday == 6)
		{
			return "Sat";
		}
		else if (weekday == 7)
		{
			return "Sun";
		}
		return "";
	}

	/**
	 * 获取星期
	 * 
	 * @return
	 */
	public static String getMonthStr(int month)
	{
		if (month == 1)
		{
			return "Jan";
		}
		else if (month == 2)
		{
			return "Feb";
		}
		else if (month == 3)
		{
			return "Mar";
		}
		else if (month == 4)
		{
			return "Apr";
		}
		else if (month == 5)
		{
			return "May";
		}
		else if (month == 6)
		{
			return "Jun";
		}
		else if (month == 7)
		{
			return "Jul";
		}
		else if (month == 8)
		{
			return "Aug";
		}
		else if (month == 9)
		{
			return "Sep";
		}
		else if (month == 10)
		{
			return "Oct";
		}
		else if (month == 11)
		{
			return "Nov";
		}
		else if (month == 12)
		{
			return "Dec";
		}
		return "";
	}

	public static void main(String[] args)
	{
		System.out.println("今天是星期：" + getWeekday(System.currentTimeMillis()));// + 5 * 24 * 60 * 60
																				// * 1000
		long monthStart = getTodayMonthStartTimeLong();
		long monthEnd = getTodayMonthEndTimeLong();
		System.out.println("本月月初：" + monthStart + "，" + getDateTimeString(monthStart));
		System.out.println("本月月末：" + monthEnd + "，" + getDateTimeString(monthEnd));
	}
}
