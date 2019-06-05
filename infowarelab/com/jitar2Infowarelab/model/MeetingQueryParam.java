package com.jitar2Infowarelab.model;

/**
 * 视频服务查询参数 
 * 
 * @author dell
 *
 */
public class MeetingQueryParam {
	
	/**
	 * 用户名	1 ，
	 * 用户ID2， 
	 * 电子邮件	3 ，
	 *  手机	4
	 */
	private int type;	
	private String value;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
