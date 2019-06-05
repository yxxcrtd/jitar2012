package com.jitar2Infowarelab.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 历史会议信息
* <confId>100000000001018</confId>
* <conferenceType>1</conferenceType>
* <duration>752</duration>
* <endTime>2013-10-29T03:13:27</endTime>
* <hostName>admin</hostName>
* <openType>true</openType>
* <peak_attendee>3</peak_attendee>
* <startTime>2013-10-29T03:00:55</startTime>
* <subject>admin的会议</subject>
* <total_attendee>4</total_attendee>
 * @author dell
 *
 */
@XStreamAlias("historymeeting")
public class HistoryMeeting {
	@XStreamAsAttribute
	private String confId;
	
	@XStreamAsAttribute
	private String subject;
	
	@XStreamAsAttribute
	private String hostName;
	
	@XStreamAsAttribute
	private String startTime;
	
	@XStreamAsAttribute
	private String endTime;
	
	@XStreamAsAttribute
	private int duration;
	
	@XStreamAsAttribute
	private int conferenceType;				//0：全公开；1：注册用户可见；2：不公开
	
	@XStreamAsAttribute
	private boolean openType;
	
	@XStreamAsAttribute
	private int peak_attendee;
	
	@XStreamAsAttribute
	private int total_attendee;
	
	public String getConfId() {
		return confId;
	}
	public void setConfId(String confId) {
		this.confId = confId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getConferenceType() {
		return conferenceType;
	}
	public void setConferenceType(int conferenceType) {
		this.conferenceType = conferenceType;
	}
	public boolean isOpenType() {
		return openType;
	}
	public void setOpenType(boolean openType) {
		this.openType = openType;
	}
	public int getPeak_attendee() {
		return peak_attendee;
	}
	public void setPeak_attendee(int peak_attendee) {
		this.peak_attendee = peak_attendee;
	}
	public int getTotal_attendee() {
		return total_attendee;
	}
	public void setTotal_attendee(int total_attendee) {
		this.total_attendee = total_attendee;
	}

	
}
