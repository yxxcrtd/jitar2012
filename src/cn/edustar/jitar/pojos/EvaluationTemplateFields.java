package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class EvaluationTemplateFields implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4139179390139578673L;
	private int fieldsId;
	private int evaluationTemplateId;
	private String fieldsCaption;
	private String fieldsName;
	
	public EvaluationTemplateFields(){}
	
	public int getFieldsId() {
		return fieldsId;
	}
	public void setFieldsId(int fieldsId) {
		this.fieldsId = fieldsId;
	}
	public int getEvaluationTemplateId() {
		return evaluationTemplateId;
	}
	public void setEvaluationTemplateId(int evaluationTemplateId) {
		this.evaluationTemplateId = evaluationTemplateId;
	}
	public String getFieldsCaption() {
		return fieldsCaption;
	}
	public void setFieldsCaption(String fieldsCaption) {
		this.fieldsCaption = fieldsCaption;
	}
	public String getFieldsName() {
		return fieldsName;
	}
	public void setFieldsName(String fieldsName) {
		this.fieldsName = fieldsName;
	}
	
	private EvaluationTemplate evaluationTemplate;
	EvaluationTemplate getEvaluationTemplate() {
		return evaluationTemplate;
	}
	public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
	}

	
	
}
