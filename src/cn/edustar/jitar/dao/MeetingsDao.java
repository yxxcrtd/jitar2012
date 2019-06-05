package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.Meetings;

public interface MeetingsDao extends Dao {
	
	@Deprecated
	public Meetings getMeetingsByObjAndObjId(String obj, int objId);
	
	public void saveMeetings(Meetings meetings);
	
	public void delMeetings(Meetings meetings);

	public List<Meetings> getMeetings(String obj, int objId);
	public List<Meetings> getMeetings();
	public List<Meetings> getUnfinishedMeetings();
	public Meetings getMeetings(int id);
	public void addMeetings(Meetings m);
	public void updateMeetings(Meetings m);
	public void deleteMeetings(int id);	
}
