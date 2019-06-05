package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class EvaluationContent implements Serializable{

	private static final long serialVersionUID = 5318864866309553487L;
	private int evaluationContentId;
	private int publishUserId;
	private String publishUserName;
	private Date createDate = new Date();
	private int evaluationPlanId;
	private int evaluationTemplateId;
	private String publishContent;
	private EvaluationTemplate evaluationTemplate;
	public EvaluationContent(){}

	public int getEvaluationContentId() {
		return evaluationContentId;
	}

	public void setEvaluationContentId(int evaluationContentId) {
		this.evaluationContentId = evaluationContentId;
	}


	public int getPublishUserId() {
		return publishUserId;
	}

	public void setPublishUserId(int publishUserId) {
		this.publishUserId = publishUserId;
	}

	public String getPublishUserName() {
		return publishUserName;
	}

	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getEvaluationPlanId() {
		return evaluationPlanId;
	}

	public void setEvaluationPlanId(int evaluationPlanId) {
		this.evaluationPlanId = evaluationPlanId;
	}

	public int getEvaluationTemplateId() {
		return evaluationTemplateId;
	}

	public void setEvaluationTemplateId(int evaluationTemplateId) {
		this.evaluationTemplateId = evaluationTemplateId;
	}

	public String getPublishContent() {
		return publishContent;
	}

	public void setPublishContent(String publishContent) {
		this.publishContent = publishContent;
	}

	public EvaluationTemplate getEvaluationTemplate() {
		return evaluationTemplate;
	}

	public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
		
	}


}
