package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 表示一个事件, 用于支持 '朋友新鲜事' 模块.
 * 
 *   以 userId, createDate 做为业务键
 *   
 *
 * 
 */
public class FriendThing implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 对象标识 */
	private int id;
	
	/** 产生事件的用户 */
	private int userId;
	
	/** 发生事件的时间 */
	private Date createDate = new Date();
	
	/** 事件的简要标题 */
	private String title;
	
	/** 可链接的 URL 地址 */
	private String url;
	
	/** 事件的详细一些的内容 */
	private String content;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "FriendThing{id=" + this.id + 
			",user=" + this.userId +
			",date=" + this.createDate +
			",title=" + this.title + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(userId, createDate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof FriendThing)) return false;
		FriendThing other = (FriendThing)o;
		return PojoHelper.equals(userId, other.userId) &&
			PojoHelper.equals(createDate, other.createDate);
	}
	
	/** 对象标识 */
	public int getId() {
		return id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 产生事件的用户 */
	public int getUserId() {
		return userId;
	}

	/** 产生事件的用户 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 发生事件的时间 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 发生事件的时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 事件的简要标题 */
	public String getTitle() {
		return title;
	}

	/** 事件的简要标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 可链接的 URL 地址 */
	public String getUrl() {
		return this.url;
	}

	/** 可链接的 URL 地址 */
	public void setUrl(String url) {
		this.url = url;
	}

	/** 事件的详细一些的内容 */
	public String getContent() {
		return content;
	}

	/** 事件的详细一些的内容 */
	public void setContent(String content) {
		this.content = content;
	}
}
