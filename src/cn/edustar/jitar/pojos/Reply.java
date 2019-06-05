package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 主题回复实体对象.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 业务键: userId, createDate.
 */
public class Reply implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 8490818485523620008L;
	
	/** 对象标识*/
	private int replyId;
	
	/** 对象(主题)标识 */
	private int topicId;
	
	/** 对象(论坛版面)标识 */
	private int groupId;
	
	/** 发贴的用户标识 */
	private int userId;
	
	/** 此回复是给哪一个回复的, 如果 = 0 表示是给主题的回复 */
	private int targetReply;
	
	/** 回复内容的标题 */
	private String title;
	
	/** 回复的内容 */
	private String content;
	
	/** 回复的时间 */
	private Date createDate = new Date();
	
	/** 是否是回复中的精华帖 */
	private boolean isBest;
	
	/** 是否被删除标志. */
	private boolean isDeleted;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof Reply))
			return false;
		Reply other = (Reply)obj;
		return PojoHelper.equals(this.userId, other.userId) &&
			PojoHelper.equals(this.createDate, other.createDate);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.userId, this.createDate);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reply{replyId=" + this.replyId +
				", userId = " + this.userId +
				", createDate = " + this.createDate + "}";
	}
	
	/**
	 * 得到友好显示字符串.
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.title) + "'(id=" + this.replyId + ")";
	}

	/** 对象标识*/
	public int getReplyId() {
		return replyId;
	}

	/** 对象标识*/
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	
	/** 对象(主题)标识 */
	public int getTopicId() {
		return topicId;
	}
	
	/** 对象(主题)标识 */
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	/** 对象(论坛版面)标识 */
	public int getGroupId() {
		return groupId;
	}
	/** 对象(论坛版面)标识 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	/** 发贴的用户标识 */
	public int getUserId() {
		return userId;
	}
	
	
	/** 发贴的用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/** 此回复是给哪一个回复的, 如果 = 0 表示是给主题的回复 */
	public int getTargetReply() {
		return targetReply;
	}
	
	/** 此回复是给哪一个回复的, 如果 = 0 表示是给主题的回复 */
	public void setTargetReply(int targetReply) {
		this.targetReply = targetReply;
	}

	/** 回复内容的标题 */
	public String getTitle() {
		return title;
	}

	/** 回复内容的标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 回复的内容 */
	public String getContent() {
		return content;
	}
	

	/** 回复的内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 是否是回复中的精华帖 */
	public boolean getIsBest() {
		return isBest;
	}

	/** 是否是回复中的精华帖 */
	public void setIsBest(boolean isBest) {
		this.isBest = isBest;
	}

	/** 是否被删除标志. */
	public boolean getIsDeleted() {
		return isDeleted;
	}

	/** 是否被删除标志. */
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/** 回复的时间 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/** 回复的时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
