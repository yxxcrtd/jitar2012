package cn.edustar.jitar.pojos;
import java.io.Serializable;
public class VPrepareCourseMember implements Serializable, Cloneable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3994690162676699863L;
	private Integer prepareCourseId;
	private Integer cc;
	public Integer getPrepareCourseId() {
		return prepareCourseId;
	}
	public void setPrepareCourseId(Integer prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}
	public Integer getCc() {
		return cc;
	}
	public void setCc(Integer cc) {
		this.cc = cc;
	}
}
