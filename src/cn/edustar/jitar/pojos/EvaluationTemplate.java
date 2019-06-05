package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class EvaluationTemplate implements Serializable {
	
	private static final long serialVersionUID = 5835146524082836372L;
	private int evaluationTemplateId;
	private String evaluationTemplateName;
	private boolean enabled;
	private String templateFile;

	public EvaluationTemplate() {
	}

	public int getEvaluationTemplateId() {
		return evaluationTemplateId;
	}

	public void setEvaluationTemplateId(int evaluationTemplateId) {
		this.evaluationTemplateId = evaluationTemplateId;
	}

	public String getEvaluationTemplateName() {
		return evaluationTemplateName;
	}

	public void setEvaluationTemplateName(String evaluationTemplateName) {
		this.evaluationTemplateName = evaluationTemplateName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

}
