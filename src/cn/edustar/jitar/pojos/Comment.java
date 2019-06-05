package cn.edustar.jitar.pojos;

import java.util.Date;
import java.util.List;

/**
 * 评论实体对象。使用 userName, createDate 做为业务键.
 * 
 *
 */
public class Comment{

	/** 评论标识 */
	private int id;

	/** 被评论的对象类型，参见 ObjectType 定义 */
	private int objType;

	/** 被评论的对象标识 */
	private int objId;

	/** 评论标题 */
	private String title;

	/** 评论的内容 */
	private String content;

	/** 是否审核通过, true 表示通过; false 表示未通过审核. */
	private boolean audit = true;

	/** 评论的星级, 取值 0 - 5 */
	private int star = 0;

	/** 评论发表时间 */
	private Date createDate = new Date();

	/** 发表评论人的'用户Id' */
	private Integer userId;

	/** 评论人的名字 */
	private String userName;

	/** 发表评论时候的IP地址 */
	private String ip;

	/** 被评论对象的'用户Id' */
	private Integer aboutUserId;
	
	/** 2013年8月新加字段，显示回复_mxh  */
	private List<Comment> replyList = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Comment{id=" + this.id + ",title=" + this.title + ",userName="
				+ this.userName + "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(userName, createDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Comment))
			return false;
		Comment other = (Comment) o;
		return PojoHelper.equals(userName, other.userName) && PojoHelper.equals(createDate, other.createDate);
	}

	/** 评论标识 */
	public int getId() {
		return id;
	}

	/** 评论标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 被评论的对象类型，参见 ObjectType 定义 */
	public int getObjType() {
		return objType;
	}

	/** 被评论的对象类型，参见 ObjectType 定义 */
	public void setObjType(int objType) {
		this.objType = objType;
	}

	/** 被评论的对象标识 */
	public int getObjId() {
		return objId;
	}

	/** 被评论的对象标识 */
	public void setObjId(int objId) {
		this.objId = objId;
	}

	/** 评论标题 */
	public String getTitle() {
		return title;
	}

	/** 评论标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 评论的内容 */
	public String getContent() {
		return content;
	}

	/** 评论的内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 是否审核通过, true 表示通过; false 表示未通过审核. */
	public boolean getAudit() {
		return this.audit;
	}

	/** 是否审核通过, true 表示通过; false 表示未通过审核. */
	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	/** 评论的星级, 取值 0 - 5 */
	public int getStar() {
		return star;
	}

	/** 评论的星级, 取值 0 - 5 */
	public void setStar(int star) {
		this.star = star;
	}

	/** 评论发表时间 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 评论发表时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 发表评论的用户, 如果未登录/匿名评论则为 null */
	public Integer getUserId() {
		return userId;
	}

	/** 发表评论的用户, 如果未登录/匿名评论则为 null */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** 评论人的名字 */
	public String getUserName() {
		return userName;
	}

	/** 评论人的名字 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 发表评论时候的 ip 地址 */
	public String getIp() {
		return ip;
	}

	/** 发表评论时候的 ip 地址 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/** 冗余字段: 被评论的对象的所属用户 */
	public Integer getAboutUserId() {
		return this.aboutUserId;
	}

	/** 冗余字段: 被评论的对象的所属用户 */
	public void setAboutUserId(Integer aboutUserId) {
		this.aboutUserId = aboutUserId;
	}

    public List<Comment> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Comment> replyList) {
        this.replyList = replyList;
    }
	
}
