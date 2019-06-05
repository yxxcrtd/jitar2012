package com.jitar2Infowarelab.model;

/**
 * 视频会议服务器创建会议后返回的 会议信息
 * 
 * @author dell
 *
 */
public class CreatedMeeting {
	
	private String attendeeUrl;
	private String confId;
	private String confKey;
	private String hostUrl;
	
	public String getAttendeeUrl() {
		return attendeeUrl;
	}
	public void setAttendeeUrl(String attendeeUrl) {
		this.attendeeUrl = attendeeUrl;
	}
	public String getConfId() {
		return confId;
	}
	public void setConfId(String confId) {
		this.confId = confId;
	}
	public String getConfKey() {
		return confKey;
	}
	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}
	public String getHostUrl() {
		return hostUrl;
	}
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	
}
