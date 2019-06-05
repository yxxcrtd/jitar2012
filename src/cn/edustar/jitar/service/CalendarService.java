package cn.edustar.jitar.service;

import java.util.List;
import cn.edustar.jitar.pojos.Calendar;

public interface CalendarService {
	/**
	 * 得到一个Calendar
	 * @param calendarId
	 * @return
	 */
	public Calendar getCalendarById(int calendarId);
	/**
	 * 得到Calendar列表(得到对象的所有Calendar)
	 * @param objectId 对应的对象ID,例如User的ID,或群组ID
	 * @param objectType 对象类型:用户,群组.....user group
	 * @return
	 */
	public List<Calendar> getCalendars(String objectGuid,String objectType);
	/**
	 * 得到Calendar列表(得到对象的某个月的Calendar)
	 * @param objectId 对象ID:User的ID,或群组ID
	 * @param objectType 对象类型:用户,群组...
	 * @param Year 年
	 * @param Month 1:1月 2:2月 3:3月.... 12:12月
	 * @return
	 */
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month);
	
	//删除
	public void DelCalendar(int id);
	
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month,int day);
	
	/**
	 * 生成保存calendar
	 * @param calendar
	 */
	public void createCalendars(Calendar calendar);

	/**
	 * 更新calendar
	 * @param calendar
	 */
	public void updateCalendars(Calendar calendar);
}
