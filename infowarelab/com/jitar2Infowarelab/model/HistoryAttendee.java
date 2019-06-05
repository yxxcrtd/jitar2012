package com.jitar2Infowarelab.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 历史会议参加者
 * @author dell
 *
 */
@XStreamAlias("historyattendee")
public class HistoryAttendee {
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String email;
	
	@XStreamAsAttribute
	private String phone;
	
	@XStreamAsAttribute
	private Date jointime;
	
	@XStreamAsAttribute
	private Date leavetime;
	
	@XStreamAsAttribute
	private int duration;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getJointime() {
		return jointime;
	}
	public void setJointime(Date jointime) {
		this.jointime = jointime;
	}
	public Date getLeavetime() {
		return leavetime;
	}
	public void setLeavetime(Date leavetime) {
		this.leavetime = leavetime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	
}
