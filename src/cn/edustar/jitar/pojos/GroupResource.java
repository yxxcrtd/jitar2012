package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 群组与资源的关系对象.
 *
 *
 */
public class GroupResource implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1874395350791248875L;

	/** 此对象的标识 */
	private int id;
	
	/** 群组标识 */
	private int groupId;
	
	/** 资源标识 */
	private int resourceId;
	
	/** 发表资源的用户标识 */
	private int userId;
	
	/** 在群组中的资源分类标识 */
	private Integer groupCateId;
	
	/** 是否是群组中的精华 */
	private boolean isGroupBest;
	
	/** 发布到群组中的时间 */
	private Date pubDate = new Date();

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "GroupResource{id=" + this.id + 
			",groupId=" + this.groupId +
			",articleId=" + this.resourceId +
			",userId=" + this.userId +
			"}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(groupId, resourceId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof GroupResource)) return false;
		
		GroupResource other = (GroupResource)o;
		
		return this.groupId == other.groupId &&
			this.resourceId == other.resourceId;
	}
	
	/** 此对象的标识 */
	public int getId() {
		return id;
	}

	/** 此对象的标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 群组标识 */
	public int getGroupId() {
		return groupId;
	}

	/** 群组标识 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/** 资源标识 */
	public int getResourceId() {
		return resourceId;
	}

	/** 资源标识 */
	public void setResourceId(int articleId) {
		this.resourceId = articleId;
	}

	/** 发表资源的用户标识 */
	public int getUserId() {
		return userId;
	}

	/** 发表资源的用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 在群组中的资源分类标识 */
	public Integer getGroupCateId() {
		return this.groupCateId;
	}
	
	/** 在群组中的资源分类标识 */
	public void setGroupCateId(Integer groupCateId) {
		this.groupCateId = groupCateId;
	}

	/** 是否是群组中的精华 */
	public boolean getIsGroupBest() {
		return isGroupBest;
	}

	/** 是否是群组中的精华 */
	public void setIsGroupBest(boolean isGroupBest) {
		this.isGroupBest = isGroupBest;
	}

	/** 发布到群组中的时间 */
	public Date getPubDate() {
		return pubDate;
	}

	/** 发布到群组中的时间 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category groupCate;
	/** Hibernate 映射所需字段, 外面不需要访问 */
	Category getGroupCate() {
		return this.groupCate;
	}
	/** Hibernate 映射所需字段, 外面不需要访问 */
	void setGroupCate(Category groupCate) {
		// this.groupCate = groupCate;
	}
}
