package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 表示一个学段对象实体.
 *
 *
 */
/**
 * @author 孟宪会
 *
 */
public class Grade implements Serializable, Cloneable {
	/** serialVersionUID */
	private static final long serialVersionUID = -6650787735938804400L;

	/** 对象标识 */
	private int gradeId;
		
	/** 学段名称 */
	private String gradeName;
		
	/** true 代表学段, false 代表年级 */
	private boolean isGrade;
	
	// TODO: parent Grade
		
	/** Default Constructor */
	public Grade() {
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Grade{id=" + this.gradeId + ",name=" + this.gradeName + "}";
	}
	
	/** 对象标识 */
	public int getGradeId() {
		return this.gradeId;
	}

	/** 对象标识 */
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	/** 学段名称 */
	public String getGradeName() {
		return this.gradeName;
	}

	/** 学段名称 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	/** true 代表学段, false 代表年级 */
	public boolean getIsGrade() {
		return this.isGrade;
	}

	/** true 代表学段, false 代表年级 */
	public void setIsGrade(boolean isGrade) {
		this.isGrade = isGrade;
	}

	/**
	 * 得到此学段的开始位置, 一个学段可以表示为 [startId, endId) 这个范围.
	 * 例如小学 = 3000, 其范围为 [3000, 4000).
	 * 更复杂的范围由外部程序实现.
	 * <br/>实际上当前实现下 startId == gradeId.
	 * @return 
	 */
	public int getStartId() {
		return this.gradeId;
	}
	
	/**
	 * 得到此学段的结束区域, 一个学段可以表示为 [startId, endId) 这个范围.
	 * 例如小学 = 3000, 其范围为 [3000, 4000).
	 * 更复杂的范围由外部程序实现.
	 * @return
	 */
	public int getEndId() {
		if ((this.gradeId % 10000) == 0)
			return this.gradeId + 10000;
		if ((this.gradeId % 1000) == 0)
			return this.gradeId + 1000;
		if ((this.gradeId % 100) == 0)
			return this.gradeId + 100;
		if ((this.gradeId % 10) == 0)
			return this.gradeId + 10;
		return this.gradeId + 1;
	}
}
