package cn.edustar.jitar.pojos;
import java.io.Serializable;
public class VPrepareCourseEdit implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4722636065906336226L;
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
