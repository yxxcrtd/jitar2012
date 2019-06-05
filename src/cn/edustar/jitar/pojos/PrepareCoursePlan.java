package cn.edustar.jitar.pojos;

// default package

import java.util.Date;

/**
 * PrepareCoursePlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrepareCoursePlan implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3504939897477553446L;
	private Integer prepareCoursePlanId;
	private String title;
	private Date startDate;
	private Date endDate;
	private Integer groupId;
	private Integer gradeId;
	private Integer subjectId;
	private String planContent;
	private Boolean defaultPlan;
	private Date createDate;
	private Integer createUserId;

	// Constructors

	/** default constructor */
	public PrepareCoursePlan() {
	}

	// Property accessors

	public Integer getPrepareCoursePlanId() {
		return this.prepareCoursePlanId;
	}

	public void setPrepareCoursePlanId(Integer prepareCoursePlanId) {
		this.prepareCoursePlanId = prepareCoursePlanId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getPlanContent() {
		return this.planContent;
	}

	public void setPlanContent(String planContent) {
		this.planContent = planContent;
	}

	public Boolean getDefaultPlan() {
		return this.defaultPlan;
	}

	public void setDefaultPlan(Boolean defaultPlan) {
		this.defaultPlan = defaultPlan;
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

}