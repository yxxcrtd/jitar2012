package cn.edustar.jitar.service.impl;
import java.util.List;

import cn.edustar.jitar.dao.CalendarDao;
import cn.edustar.jitar.pojos.Calendar;
import cn.edustar.jitar.service.CalendarService;

public class CalendarServiceImpl   implements CalendarService{
	
	private CalendarDao calendarDao;
	
	public CalendarServiceImpl(){
	}
	
	public void setCalendarDao(CalendarDao calendarDao){
		this.calendarDao=calendarDao;
	}
	
	public Calendar getCalendarById(int calendarId){
		return calendarDao.getCalendarById(calendarId);
	}
	
	public List<Calendar> getCalendars(String objectGuid,String objectType){
		return calendarDao.getCalendars(objectGuid, objectType);
	}
	
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month){
		return calendarDao.getCalendars(objectGuid, objectType, Year, Month);
	}
	
	public List<Calendar> getCalendars(String objectGuid,String objectType,int Year,int Month,int day)
	{
		return calendarDao.getCalendars(objectGuid, objectType, Year, Month,day);
	}
	
	public void DelCalendar(int id){
		calendarDao.DelCalendar(id);
	}
	
	public void createCalendars(Calendar calendar){
		calendarDao.createCalendars(calendar);
	}
	
	public void updateCalendars(Calendar calendar){
		calendarDao.updateCalendars(calendar);
	}
}
