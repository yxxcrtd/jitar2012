package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * Q&A 实体类. 
 * @author 孟宪会
 */

public class Question implements java.io.Serializable {

	// Fields

	/**
	 * 成员属性
	 */
	private static final long serialVersionUID = 2405564804488183051L;
	
	/** 问题标识 */
	private Integer questionId;
	
	/** 问题的全球唯一标识符 */
	private String objectGuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/** 调用问题对象的全球唯一标识符 */
	private String parentGuid;
	
	/** 对象类型 */
	private String parentObjectType;	
	
	/** 问题标题 */
	private String topic;
	
	/** 问题创建时间 */
	private Date createDate;
	
	/** 提问者用户标识 */
	private Integer createUserId;
	
	/** 创建者用户显示名 */
	private String createUserName;
	
	/** 提问内容 */
	private String questionContent;	
	
	/** 操作者的IP */
	private String addIp;	
	
	// 构造器

	/** 默认的构造器 */
	public Question() {}

	// 属性访问器

	/** 获取问题的标识 */
	public Integer getQuestionId() {
		return this.questionId;
	}

	/** 设置问题的标识 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	/** 获取问题的标题 */
	public String getTopic() {
		return this.topic;
	}

	/** 设置问题的标题 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/** 获取问题的创建时间 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 设置问题的创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/** 获取问题创建者的标识 */
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	/** 设置问题创建者的标识 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	/** 获取问题的创建者的显示名 */
	public String getCreateUserName() {
		return this.createUserName;
	}

	/** 设置问题创建者的显示名 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/** 获取问题的内容描述 */
	public String getQuestionContent() {
		return this.questionContent;
	}

	/** 设置问题的内容描述 */
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	/** 设置问题操作者的 ip */
	public String getAddIp() {
		return this.addIp;
	}

	/** 设置问题操作者的 ip 标识 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/** 获取问题的全球唯一标识符 */
	public String getObjectGuid() {
		return objectGuid;
	}

	/** 设置问题的全球唯一标识符 */
	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	/** 获取调用问题对象的全球唯一标识符 */
	public String getParentGuid() {
		return parentGuid;
	}

	/** 设置调用问题对象的全球唯一标识符 */
	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}

	public String getParentObjectType() {
		return parentObjectType;
	}

	public void setParentObjectType(String parentObjectType) {
		this.parentObjectType = parentObjectType;
	}
}