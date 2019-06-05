package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * PPlugInTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 *
 */
public class PlugInTopic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2675903442357548601L;
	// Fields

	private Integer plugInTopicId;
	private String title;
	private Date createDate;
	private Integer createUserId;
	private String createUserName;
	private String topicContent;
	private String addIp;
	private String parentGuid;
	private String parentObjectType;

	// Constructors

	/** default constructor */
	public PlugInTopic() {
	}

	/** minimal constructor */
	public PlugInTopic(String title, Date createDate, Integer createUserId,
			String createUserName) {
		this.title = title;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
	}

	/** full constructor */
	public PlugInTopic(String title, Date createDate, Integer createUserId,
			String createUserName, String topicContent, String addIp,
			String parentGuid, String parentObjectType) {
		this.title = title;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.topicContent = topicContent;
		this.addIp = addIp;
		this.parentGuid = parentGuid;
		this.parentObjectType = parentObjectType;
	}

	// Property accessors

	public Integer getPlugInTopicId() {
		return this.plugInTopicId;
	}

	public void setPlugInTopicId(Integer plugInTopicId) {
		this.plugInTopicId = plugInTopicId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getTopicContent() {
		return this.topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public String getAddIp() {
		return this.addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getParentGuid() {
		return this.parentGuid;
	}

	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}

	public String getParentObjectType() {
		return this.parentObjectType;
	}

	public void setParentObjectType(String parentObjectType) {
		this.parentObjectType = parentObjectType;
	}

}