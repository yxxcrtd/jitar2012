package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 群组文章关联.
 * 
 *
 *
 *  使用 groupId, articleId 做为业务键.
 */
public class GroupArticle implements Serializable {
	/** */
	private static final long serialVersionUID = -4233928663630335728L;

	/** 此对象的标识 */
	private int id;
	
	/** 群组标识 */
	private int groupId;
	
	/** 文章标识 */
	private int articleId;
	
	/** 发表文章的用户标识 */
	private int userId;
	
	/** 是否是群组中的精华 */
	private boolean isGroupBest;
	
	/** 发布到群组中的时间 */
	private Date pubDate = new Date();
	
	/** 在群组中的文章分类标识 */
	private Integer groupCateId;
	
	private String title;
	private String loginName;
	private String userTrueName;
	private Date createDate;
	private boolean articleState = true;
	private boolean typeState = false;
	

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "GroupArticle{id=" + this.id + 
			",groupId=" + this.groupId +
			",articleId=" + this.articleId +
			",userId=" + this.userId +
			"}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(groupId, articleId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof GroupArticle)) return false;
		
		GroupArticle other = (GroupArticle)o;
		
		return this.groupId == other.groupId &&
			this.articleId == other.articleId;
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

	/** 文章标识 */
	public int getArticleId() {
		return articleId;
	}

	/** 文章标识 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/** 发表文章的用户标识 */
	public int getUserId() {
		return userId;
	}

	/** 发表文章的用户标识 */
	public void setUserId(int userId) {
		this.userId = userId;
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

	public Integer getGroupCateId() {
		return groupCateId;
	}

	public void setGroupCateId(Integer groupCateId) {
		this.groupCateId = groupCateId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserTrueName() {
		return userTrueName;
	}

	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean getArticleState() {
		return articleState;
	}

	public void setArticleState(boolean articleState) {
		this.articleState = articleState;
	}

	public boolean getTypeState() {
		return typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}
}
