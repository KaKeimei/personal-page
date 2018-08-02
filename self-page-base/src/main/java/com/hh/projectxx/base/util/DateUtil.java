package com.hh.projectxx.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期（时间）工具类
 * 
 * @author Frank
 *
 */
public class DateUtil {

	/**
	 * 判断生日
	 * @param birthday
	 * @param date
	 * @return
	 */
	public static boolean isBirthday(Date birthday, Date date) {
		if (date == null || birthday == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(birthday);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		int birthYear = cal1.get(Calendar.YEAR);
		if (cal2.get(Calendar.YEAR) <= birthYear) {
			return false;
		}
		return (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
	}

	/**
	 * 判断妇女节
	 * @param date
	 * @return
	 */
	public static boolean isWomensDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 8);
	}
	
	/**
	 * 判断五一节日后第一天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isPostLaborsDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) == 4 && cal.get(Calendar.DAY_OF_MONTH) == 4);
	}
	
	/**
	 * 判断母亲节
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isMothersDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int week1 = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		return (month == 4 && week1 == 2 && day == Calendar.SUNDAY);
	}
	
	/**
	 * 由宝宝生日，计算今年的母亲节是第几个母亲节。
	 * 若返回 0，则无母亲节。
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int getSerialForMotherDay(Date birthDay) {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		
		cal.setTime(birthDay);
		int birthYear = cal.get(Calendar.YEAR);
		
		int yearDiff = currentYear - birthYear;
		if (yearDiff < 0) {
			return 0;
		}
		
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		Date motherDayOfBirthYear = cal.getTime();
		
		if (birthDay.after(motherDayOfBirthYear)) {
			return yearDiff;
		} else {
			return yearDiff + 1;
		}
	}
}
