package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * PVote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 *	投票实体对象
 */
/**
 * @author 孟宪会
 *
 */
public class Vote implements java.io.Serializable {

	// Fields

	/**
	 * 序列化版本标识
	 */
	private static final long serialVersionUID = -529002858438830079L;
	
	/**
	 * 投票标识
	 */
	private Integer voteId;
	
	
	/**
	 * 全球统一标识符
	 */	
	private String objectGuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/**
	 * 投票名称
	 */
	private String title;
	
	/**
	 * 投票描述
	 */
	private String description;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 创建者标识
	 */
	private Integer createUserId;
	
	/**
	 * 创建者名称
	 */
	private String createUserName;
	
	/**
	 * 学科
	 */
	private Integer subjectId;
	
	/**
	 * 年级
	 */
	private Integer gradeId;
	
	/**
	 * 容器对象标识
	 */
	private String parentGuid;
	
	/**
	 * 容器对象类型
	 */
	private String parentObjectType;
	
	/**
	 * 投票截止时间
	 */
	private Date endDate;
	
	/** 投票所有权 */
	public static final String VOTE_OBJECTTYPE_SUBJECT = "subject";
	public static final String VOTE_OBJECTTYPE_USER = "user";
	public static final String VOTE_OBJECTTYPE_GROUP = "group";
	public static final String VOTE_OBJECTTYPE_PREPARECOURSE = "preparecourse";
	public static final String VOTE_OBJECTTYPE_NETCOURSE = "netcourse";
	public static final String VOTE_OBJECTTYPE_SYSTEM = "system"; /* 系统范围内 */
	
	// Constructors

	/** default constructor */
	public Vote() {
	}

	public Integer getVoteId() {
		return voteId;
	}

	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getParentGuid() {
		return parentGuid;
	}

	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}

	public String getParentObjectType() {
		return parentObjectType;
	}

	public void setParentObjectType(String parentObjectType) {
		this.parentObjectType = parentObjectType;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getObjectGuid() {
		return objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	// Property accessors

}