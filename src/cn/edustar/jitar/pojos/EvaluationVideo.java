package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class EvaluationVideo implements Serializable, Cloneable{
	private static final long serialVersionUID = 3080952707302197533L;
	private int id;
	private int evaluationPlanId;
	private int videoId;
	private String videoTitle;
	private String flvThumbNailHref;
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
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	private EvaluationPlan evaluationPlan;
	public EvaluationPlan getEvaluationPlan() {
		return evaluationPlan;
	}
	public void setEvaluationPlan(EvaluationPlan evaluationPlan) {
	}

	private Video video;
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getFlvThumbNailHref() {
		return flvThumbNailHref;
	}
	public void setFlvThumbNailHref(String flvThumbNailHref) {
		this.flvThumbNailHref = flvThumbNailHref;
	}	
}
