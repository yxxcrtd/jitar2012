package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class EvaluationPlan implements Serializable, Cloneable {

	private static final long serialVersionUID = -6417504280509922461L;
	private int evaluationPlanId;
	private Integer metaSubjectId;
	private Integer metaGradeId;
	private Date startDate;
	private Date endDate;
	private String evaluationCaption; 
	private int teacherId;
	private String teacherName;
	private int createrId;
	private String createrName;
	private boolean enabled = true;
	private Date createDate = new Date();
	private Date teachDate;
	private String fdTeacher;
	
	public EvaluationPlan(){}

	public String getFdTeacher() {
		return fdTeacher;
	}

	public void setFdTeacher(String fdTeacher) {
		this.fdTeacher = fdTeacher;
	}
	
	public int getEvaluationPlanId() {
		return evaluationPlanId;
	}

	public void setEvaluationPlanId(int evaluationPlanId) {
		this.evaluationPlanId = evaluationPlanId;
	}
	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	
	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}	
	
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	public String getEvaluationCaption() {
		return evaluationCaption;
	}

	public void setEvaluationCaption(String evaluationCaption) {
		this.evaluationCaption = evaluationCaption;
	}

	public Integer getMetaSubjectId() {
		return metaSubjectId;
	}

	public void setMetaSubjectId(Integer metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}

	public Integer getMetaGradeId() {
		return metaGradeId;
	}

	public void setMetaGradeId(Integer metaGradeId) {
		this.metaGradeId = metaGradeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	private MetaSubject metaSubject;
	public MetaSubject getMetaSubject() {
		return this.metaSubject;
	}
	
	public void setMetaSubject(MetaSubject metaSubject) {
	}

	public void setGrade(Grade grade) {
	}

	private Grade grade;
	public Grade getGrade() {
		return this.grade;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getTeachDate() {
		return teachDate;
	}

	public void setTeachDate(Date teachDate) {
		this.teachDate = teachDate;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}	

}
