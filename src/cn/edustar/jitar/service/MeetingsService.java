package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Meetings;

public interface MeetingsService {
	
	@Deprecated
	public Meetings getMeetingsByObjAndObjId(String obj, int objId);
	@Deprecated
	public void openMeetings(String obj, int objId);
	@Deprecated
	public void closeMeetings(String obj, int objId);
	
	public List<Meetings> getMeetings(String obj, int objId);
	public List<Meetings> getMeetings();
	public List<Meetings> getUnfinishedMeetings();
	public Meetings getMeetings(int id);
	public void addMeetings(Meetings m);
	public void updateMeetings(Meetings m);
	public void deleteMeetings(int id);
	
}
