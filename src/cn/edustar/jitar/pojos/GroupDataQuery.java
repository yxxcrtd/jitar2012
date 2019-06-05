package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * JitarGroupQuery entity. @author MyEclipse Persistence Tools
 */

public class GroupDataQuery implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 143544444444444444L;
	/**
	 * 
	 */
	
	private int id;
	private String objectGuid;
	private Date createDate = new Date();
	private String beginDate;
	private String endDate;
	private String loginName;
	private String trueName;
	private String unitName;
	private String unitTitle;
	private Integer metaSubjectId;
	private String metaSubjectTitle;
	private Integer gradeId;
	private String gradeTitle;
	private Integer articleCount;
	private Integer bestArticleCount;
	private Integer resourceCount;
	private Integer bestResourceCount;
	private Integer topicCount;
	private Integer replyCount;

	// Constructors

	/** default constructor */
	public GroupDataQuery() {
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObjectGuid() {
		return this.objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitTitle() {
		return this.unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}

	public Integer getMetaSubjectId() {
		return this.metaSubjectId;
	}

	public void setMetaSubjectId(Integer metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}

	public String getMetaSubjectTitle() {
		return this.metaSubjectTitle;
	}

	public void setMetaSubjectTitle(String metaSubjectTitle) {
		this.metaSubjectTitle = metaSubjectTitle;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeTitle() {
		return this.gradeTitle;
	}

	public void setGradeTitle(String gradeTitle) {
		this.gradeTitle = gradeTitle;
	}

	public Integer getArticleCount() {
		return this.articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public Integer getBestArticleCount() {
		return this.bestArticleCount;
	}

	public void setBestArticleCount(Integer bestArticleCount) {
		this.bestArticleCount = bestArticleCount;
	}

	public Integer getResourceCount() {
		return this.resourceCount;
	}

	public void setResourceCount(Integer resourceCount) {
		this.resourceCount = resourceCount;
	}

	public Integer getBestResourceCount() {
		return this.bestResourceCount;
	}

	public void setBestResourceCount(Integer bestResourceCount) {
		this.bestResourceCount = bestResourceCount;
	}

	public Integer getTopicCount() {
		return this.topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getReplyCount() {
		return this.replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

}