package cn.edustar.jitar.pojos;
import java.io.Serializable;
import java.util.Date;
public class EvaluationResource implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506382747928487037L;
	private int id;
	private int evaluationPlanId;
	private int resourceId;
	private String resourceTitle;
	private String resourceHref;
	private Date createDate=new Date();
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
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	private EvaluationPlan evaluationPlan;
	EvaluationPlan getEvaluationPlan() {
		return evaluationPlan;
	}
	public void setEvaluationPlan(EvaluationPlan evaluationPlan) {
	}

	private Resource resource;
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
	}
	public String getResourceTitle() {
		return resourceTitle;
	}
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	public String getResourceHref() {
		return resourceHref;
	}
	public void setResourceHref(String resourceHref) {
		this.resourceHref = resourceHref;
	}
	
}
