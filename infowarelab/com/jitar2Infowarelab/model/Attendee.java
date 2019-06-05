package com.jitar2Infowarelab.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 视频会议的参加者
 * @author dell
 *
 */
@XStreamAlias("attendee")
public class Attendee {
	
	@XStreamAsAttribute
	private String name;
	
	@XStreamAsAttribute
	private String email;
	
	@XStreamAsAttribute
	private String phone;
	
	@XStreamAsAttribute
	private String fax;
	
	@XStreamAsAttribute
	private String mobilePhone;
	
	private int userId;
	
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
}
