package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 论坛主题.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 业务键: userId + createDate.
 */
public class Topic implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 9050031916637853319L;
	
	/** 对象标识 */
	private int topicId;
	
	/** 对象(群组)标识 */
	private int  groupId;
	
	/** 主题的标题. */
	private String title;
	
	/** 发贴的用户标识 */
	private int userId;
	
	/** 主题的内容 */
	private String content;
	
	/** 发贴时间 */
	private Date createDate = new Date();
	
	/** 是否是精华贴. */
	private boolean isBest;
	
	/** 是否是置顶贴. */
	private boolean isTop;
	
	/** 是否被删除标志. */
	private boolean isDeleted;

	/** 回帖数量 */
	private int replyCount;

	/** 点击次数 */
	private int viewCount;
	
	/** 所使用的标签. */
	private String tags;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null || !(obj instanceof Topic))
			return false;
		Topic other = (Topic)obj;
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
		return "Topic{topicId=" + this.topicId +
						", userId =" + this.userId +
						", createDate = " + this.createDate + "}";
	}
	
	/**
	 * 得到 log 显示的友好字符串.
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.title) + "'(id=" + this.topicId + ")";
	}
	
	/** 对象标识 */
	public int getTopicId() {
		return topicId;
	}
	
	/** 对象标识 */
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

	/** 主题的标题. */
	public String getTitle() {
		return title;
	}
	
	/** 主题的标题. */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 发贴的用户标识 */
	public int getUserId() {
		return userId;
	}
	
	
	/** 发贴的用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	/** 主题的内容 */
	public String getContent() {
		return content;
	}
	
	/** 主题的内容 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	/** 点击次数 */
	public int getViewCount() {
		return viewCount;
	}
	
	/** 点击次数 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	
	/** 标题的创建时间 */
	public Date getCreateDate() {
		return createDate;
	}
	
	
	/** 标题的创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/** 是否是精华贴. */
	public boolean getIsBest() {
		return isBest;
	}
	
	/** 是否是精华贴. */
	public void setIsBest(boolean isBest) {
		this.isBest = isBest;
	}
	
	/** 是否是置顶贴. */
	public boolean getIsTop() {
		return isTop;
	}
	
	/** 是否是置顶贴. */
	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}
	
	
	/** 是否被删除标志. */
	public boolean getIsDeleted() {
		return isDeleted;
	}
	
	/** 是否被删除标志. */
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	/** 回帖数量 */
	public int getReplyCount() {
		return replyCount;
	}
	
	/** 回帖数量 */
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	
	
	/** 所使用的标签. */
	public String getTags() {
		return tags;
	}
	
	/** 所使用的标签. */
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
}
