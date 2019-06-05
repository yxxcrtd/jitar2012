package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class EvaluationPlanTemplate implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881975975253288060L;
	private int id;
	private int evaluationPlanId;
	private int evaluationTemplateId;
	private EvaluationPlan evaluationPlan;
	private EvaluationTemplate evaluationTemplate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public EvaluationPlan getEvaluationPlan() {
		return evaluationPlan;
	}
	public void setEvaluationPlan(EvaluationPlan evaluationPlan) {
	}
	public EvaluationTemplate getEvaluationTemplate() {
		return evaluationTemplate;
	}
	public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
	}
	
	
}
