package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;

import cn.edustar.jitar.dao.MeetingsDao;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.service.MeetingsService;

public class MeetingsServiceImpl implements MeetingsService {

	private MeetingsDao meetingsDao;
	
	public void setMeetingsDao(MeetingsDao meetingsDao) {
		this.meetingsDao = meetingsDao;
	}

	/**
	 * 不要使用
	 */
	@Deprecated
	public Meetings getMeetingsByObjAndObjId(String obj, int objId) {
		return meetingsDao.getMeetingsByObjAndObjId(obj, objId);
	}

	/**
	 * 不要使用
	 */
	@Deprecated
	public void openMeetings(String obj, int objId) {
		if (null == meetingsDao.getMeetingsByObjAndObjId(obj, objId)) {
			Meetings meetings = new Meetings();
			meetings.setObj(obj);
			meetings.setObjId(objId);
			meetingsDao.saveMeetings(meetings);
		}
	}

	/**
	 * 不要使用
	 */
	@Deprecated
	public void closeMeetings(String obj, int objId) {
		Meetings meetings = meetingsDao.getMeetingsByObjAndObjId(obj, objId);
		if (null != meetings) {
			meetingsDao.delMeetings(meetings);
		}
	}
	
	public Meetings getMeetings(int id){
		return meetingsDao.getMeetings(id);
	}
	
	public List<Meetings> getMeetings(String obj, int objId){
		return meetingsDao.getMeetings(obj, objId);
	}
	
	public List<Meetings> getMeetings(){
		return meetingsDao.getMeetings();
	}
	
	public List<Meetings> getUnfinishedMeetings(){
		return meetingsDao.getUnfinishedMeetings();
	}
	
	public void addMeetings(Meetings m){
		meetingsDao.addMeetings(m);
	}
	public void updateMeetings(Meetings m){
		meetingsDao.updateMeetings(m);
	}
	public void deleteMeetings(int id){
		meetingsDao.deleteMeetings(id);
	}
	
}
