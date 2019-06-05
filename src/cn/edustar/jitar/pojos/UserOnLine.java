package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 用户在线对象
 *
 * @author Yang xinxin
 * @version 1.0.0 Mar 25, 2009 10:53:59 AM
 */
public class UserOnLine implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 5894846391599755870L;

	/** 对象标识 */
	private int Id;
	
	/** 用户Id，如为游客，则可以为空 */
	private Integer userId;
	
	/** 用户名，如为游客，则可以为：Guest */
	private String userName;
	
	/** 时间的毫秒数 */
	private long onlineTime;
	
	/** Construct */
	public UserOnLine() {
		// 
	}

	// Get and set
	/** 对象标识 */
	public int getId() {
		return Id;
	}

	/** 对象标识 */
	public void setId(int id) {
		Id = id;
	}

	/** 用户Id，如为游客，则可以为空 */
	public Integer getUserId() {
		return userId;
	}

	/** 用户Id，如为游客，则可以为空 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 用户名，如为游客，则可以为：Guest */
	public String getUserName() {
		return userName;
	}

	/** 用户名，如为游客，则可以为：Guest */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 时间的毫秒数 */
	public long getOnlineTime() {
		return onlineTime;
	}

	/** 时间的毫秒数 */
	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}
	
}
