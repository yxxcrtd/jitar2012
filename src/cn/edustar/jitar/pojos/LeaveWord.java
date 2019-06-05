package cn.edustar.jitar.pojos;

import java.util.Date;
import java.io.Serializable;

/**
 * 留言实体.
 * 业务键: objType, objId, createDate
 * @author Yang Xinxin
 * @version 1.0.0 Mar 28, 2008 11:33:09 PM
 * @remark 
 *   为了加速查询, 里面保留了用户的 loginName, nickName 字段.
 */
public class LeaveWord implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 2323765849410244341L;

	/** 对象标识 */
	private int id;

	/** 写留言的人，如果未登录则为 null */
	private Integer userId;
	
	/** 对象标识(用户或群组)*/
	private int objId;
	
	/** 留言的对象类型 */
	private int objType;
	
	/** 留言标题 */
	private String title = "";
	
	/** 留言的内容 */
	private String content;

	/** 留言的发送时间 */
	private Date createDate = new Date();

	/** 发表留言的人的登录名 */
	private String loginName;
	
	/** 发送者的呢称 */
	private String nickName;

	/** 发送者的 E-Mail */
	private String email;

	/** 发送者的IP地址 */
	private String ip;

	/** 回复次数 */
	private int replyTimes;
	
	/** 接收者的回复内容 */
	private String reply;

	/** 是否匿名 */
	private boolean isAnon;

	/** Default Constructor */
	public LeaveWord() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// 先自己与自己比较.
		if (this == obj)
			return true;

		// 对象是否为空，并且检测对象的类型.
		if (obj == null || !(obj instanceof LeaveWord))
			return false;

		LeaveWord other = (LeaveWord) obj;
		
		return PojoHelper.equals(this.objType, other.objType) &&
			PojoHelper.equals(this.objId, other.objId) &&
			PojoHelper.equals(this.createDate, other.createDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LeaveWord{id = " + this.id + ", userId = " + this.userId + "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.id, this.userId, this.createDate);
	}

	// Property accessors

	/** 对象标识 */
	public int getId() {
		return this.id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 接收者Id */
	public Integer getUserId() {
		return this.userId;
	}

	/** 接收者Id */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 留言标题 */
	public String getTitle() {
		return this.title;
	}

	/** 留言标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 留言的内容 */
	public String getContent() {
		return this.content;
	}

	/** 留言的内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 留言的发送时间 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 留言的发送时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 发表留言的人的登录名 */
	public String getLoginName() {
		return this.loginName;
	}

	/** 发表留言的人的登录名 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 发送者的呢称 */
	public String getNickName() {
		return this.nickName;
	}

	/** 发送者的呢称 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/** 发送者的 E-Mail */
	public String getEmail() {
		return this.email;
	}

	/** 发送者的 E-Mail */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 发送者的IP地址 */
	public String getIp() {
		return this.ip;
	}

	/** 发送者的IP地址 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/** 回复次数 */
	public int getReplyTimes() {
		return this.replyTimes;
	}

	/** 回复次数 */
	public void setReplyTimes(int replyTimes) {
		this.replyTimes = replyTimes;
	}

	/** 接收者的回复内容 */
	public String getReply() {
		return this.reply;
	}

	/** 接收者的回复内容 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/** 是否匿名 */
	public boolean getIsAnon() {
		return this.isAnon;
	}

	/** 是否匿名 */
	public void setIsAnon(boolean isAnon) {
		this.isAnon = isAnon;
	}
	/** 对象标识(用户或群组)*/
	public int getObjId() {
		return objId;
	}
	/** 对象标识(用户或群组)*/
	public void setObjId(int objId) {
		this.objId = objId;
	}
	/** 留言的对象类型 */
	public int getObjType() {
		return objType;
	}
	/** 留言的对象类型 */
	public void setObjType(int objType) {
		this.objType = objType;
	}

}