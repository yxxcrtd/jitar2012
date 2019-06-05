package cn.edustar.jitar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期的辅助函数.
 * 
 */
public class DateUtil {

	// 构造函数
	private DateUtil() {

	}

	/**
	 * 转换为标准中文日期输出格式 'yyyy-MM-dd hh:mm:ss'
	 * 
	 * @param date
	 * @return
	 */
	public static final String toStandardString(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return f.format(date);
	}

	/**
	 * 判断一个字符串是否是日期
	 */
	public static boolean isDateTime(String d1) {
		if (d1 == null || d1.length() < 1) {
			return false;
		}
		try {
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d1);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 比较两个日期变量，如果前者大于后者，则返回 1，相等返回0，小于返回-1
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareDateTime(Date d1, Date d2) {
		if (d1.getTime() > d2.getTime())
			return 1;
		else if (d1.getTime() < d2.getTime())
			return -1;
		else
			return 0;
	}

	/**
	 * 判断当前日期是否在两个日期之间
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean todayBetween(Date d1, Date d2) {
		if (compareDateTime(d1, new Date()) < 1
				&& compareDateTime(new Date(), d2) < 1) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static int compareDateTimeEx(Date d11, Date d22) {
		Date d1 = new Date(d11.getYear(), d11.getMonth(), d11.getDate());
		Date d2 = new Date(d22.getYear(), d22.getMonth(), d22.getDate());
		if (d1.getTime() > d2.getTime())
			return 1;
		else if (d1.getTime() < d2.getTime())
			return -1;
		else
			return 0;
	}

	/**
	 * 得到当前日期时间.
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 给指定日期添加指定天数.
	 * 
	 * @param date
	 * @param add_days
	 *            要增加的天数.
	 * @return
	 */
	public static Date addDays(Date date, double add_days) {
		long time = date.getTime() + ((Double) add_days).longValue()
				* (24L * 60L * 60L * 1000L);
		return new Date(time);
	}

	/**
	 * 给指定日期添加指定天数.
	 * 
	 * @param date
	 * @param add_days
	 *            要增加的天数.
	 * @return
	 */
	public static Date addDays(Date date, int add_days) {
		long time = date.getTime() + (long) add_days
				* (24L * 60L * 60L * 1000L);
		return new Date(time);
	}

	/**
	 * 某日期至今天之间相差的天数
	 * 
	 * @param Date1
	 * @param Date2
	 * @return
	 * @throws ParseException
	 */
	public static long daysNum(Date Date1) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		long timethis = calendar.getTimeInMillis();
		calendar.setTime(Date1);
		long timeend = calendar.getTimeInMillis();
		long theday = (timeend - timethis) / (1000L * 60L * 60L * 24L);
		return theday;
	}

	public static long hoursNum(Date Date1) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		long timethis = calendar.getTimeInMillis();
		calendar.setTime(Date1);
		long timeend = calendar.getTimeInMillis();
		long theday = (timeend - timethis) / (1000L * 60L * 60L);
		return theday;
	}

	public static long minutesNum(Date Date1) throws ParseException {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());
		long timethis = calendar.getTimeInMillis();

		calendar.setTime(Date1);
		long timeend = calendar.getTimeInMillis();

		long theday = (timeend - timethis) / (1000L * 60L);

		return theday;
	}

}
