package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class UserSubjectGrade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8222187480195576080L;
	private int userSubjectGradeId;
	private int userId;
	private Integer subjectId;
	private Integer gradeId;
	
	public UserSubjectGrade(){}
	
	public int getUserSubjectGradeId() {
		return userSubjectGradeId;
	}
	public void setUserSubjectGradeId(int userSubjectGradeId) {
		this.userSubjectGradeId = userSubjectGradeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
}
