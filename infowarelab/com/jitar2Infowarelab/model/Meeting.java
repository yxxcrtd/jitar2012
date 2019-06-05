package com.jitar2Infowarelab.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 视频会议对象 
 * @author dell
 *
 */
@XStreamAlias("meeting")
public class Meeting {
	@XStreamAsAttribute
	private String confKey;
	@XStreamAsAttribute
	private String subject;
	@XStreamAsAttribute
	private String hostName;
	@XStreamAsAttribute
	private int status;
	@XStreamAsAttribute
	private String startTime;
	@XStreamAsAttribute
	private String endTime;
	@XStreamAsAttribute
	private int duringTime;
	@XStreamAsAttribute
	private int confType;
	
	public String getConfKey() {
		return confKey;
	}
	public void setConfKey(String confKey) {
		this.confKey = confKey;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public int getDuringTime() {
		return duringTime;
	}
	public void setDuringTime(int duringTime) {
		this.duringTime = duringTime;
	}
	public int getConfType() {
		return confType;
	}
	public void setConfType(int confType) {
		this.confType = confType;
	}

	
}
