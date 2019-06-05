package cn.edustar.jitar.util;

import java.io.Serializable;
import java.util.List;


public class SubjectNav implements Serializable, Cloneable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011901219865614902L;
	private String gradeName;
	private int gradeId;
	private List<Object> metaSubject;
	
	public SubjectNav(String gradeName, int gradeId,
			List<Object> metaSubject) {
		this.gradeName = gradeName;
		this.gradeId = gradeId;
		this.metaSubject = metaSubject;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public List<Object> getMetaSubject() {
		return metaSubject;
	}
	public void setMetaSubject(List<Object> metaSubject) {
		this.metaSubject = metaSubject;
	}
}
