package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import cn.edustar.jitar.dao.CalendarDao;
import cn.edustar.jitar.pojos.Calendar;

public class CalendarDaoHibernate  extends BaseDaoHibernate implements CalendarDao {
	/**
	 * 得到一个Calendar
	 * @param calendarId
	 * @return
	 */
	public Calendar getCalendarById(int calendarId){
		return (Calendar)this.getSession().get(Calendar.class, calendarId);
	}
	/**
	 * 得到Calendar列表(得到对象的所有Calendar)
	 * @param objectId 对应的对象ID,例如User的ID,或群组ID
	 * @param objectType 对象类型:用户,群组.....
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Calendar> getCalendars(String objectGuid,String objectType){
		String hql = "FROM Calendar WHERE objectGuid = ? and objectType = ? order by eventTimeBegin";
		List<Calendar> list = this.getSession().createQuery(hql).setString(0, objectGuid).setString(1, objectType).list();
		return list;
	}
	/**
	 * 得到Calendar列表(得到对象的某个月的Calendar)
	 * @param objectId 对象ID:User的ID,或群组ID
	 * @param objectType 对象类型:用户,群组...
	 * @param Month 1:1月 2:2月 3:3月.... 12:12月
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month){
		String hql = "FROM Calendar WHERE objectGuid = ? and objectType = ? and ((Year(eventTimeBegin) = ?  and Month(eventTimeBegin) = ?) or (Year(eventTimeEnd) = ?  and Month(eventTimeEnd) = ?) ) Order by eventTimeBegin";
		List<Calendar> list = this.getSession().createQuery(hql)
				.setString(0, objectGuid)
				.setString(1, objectType)
				.setInteger(2, Year)
				.setInteger(3, Month)
				.setInteger(4, Year)
				.setInteger(5, Month)
				.list();
		return list;
	}
	

	/**
	 * 得到Calendar列表(得到对象的某个月的Calendar)
	 * @param objectId 对象ID:User的ID,或群组ID
	 * @param objectType 对象类型:用户,群组...
	 * @param Month 1:1月 2:2月 3:3月.... 12:12月
	 * @param day 日期
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month,int day){
		String hql = "FROM Calendar WHERE objectGuid = ? and objectType = ? and ((Year(eventTimeBegin) = ?  and Month(eventTimeBegin) = ?  and Day(eventTimeBegin) = ?) or (Year(eventTimeEnd) = ?  and Month(eventTimeEnd) = ?  and Day(eventTimeEnd) = ?)) Order by eventTimeBegin";
		List<Calendar> list = this.getSession().createQuery(hql)
				.setString(0, objectGuid)
				.setString(1, objectType)
				.setInteger(2, Year)
				.setInteger(3, Month)
				.setInteger(4, day)
				.setInteger(5, Year)
				.setInteger(6, Month)
				.setInteger(7, day)
				.list();
				
		return list;
	}
	
	public void DelCalendar(int id){
		String hql = "DELETE FROM Calendar WHERE id = ?";
		this.getSession().createQuery(hql).setInteger(0, id).executeUpdate();
		
	}

	/**
	 * 生成保存calendar
	 * @param calendar
	 */
	public void createCalendars(Calendar calendar){
		this.getSession().save(calendar);
		this.getSession().flush();
	}

	/**
	 * 更新calendar
	 * @param calendar
	 */
	public void updateCalendars(Calendar calendar){
		this.getSession().update(calendar);
		this.getSession().flush();
	}
}
