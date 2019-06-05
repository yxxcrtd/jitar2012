package cn.edustar.jitar.pojos;

/**
 * JitarUnitSubject entity. @author MyEclipse Persistence Tools
 */

public class UnitSubject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1240000916000089L;
	private int unitSubjectId;
	private int unitId;
	private int subjectId;
	private int metaSubjectId;
	private int metaGradeId;
	private Subject subject;
	private Unit unit;
	private String displayName;


	// Constructors

	/** default constructor */
	public UnitSubject() {
	}


	public int getUnitSubjectId() {
		return unitSubjectId;
	}


	public void setUnitSubjectId(int unitSubjectId) {
		this.unitSubjectId = unitSubjectId;
	}


	public int getUnitId() {
		return unitId;
	}


	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}


	public int getSubjectId() {
		return subjectId;
	}


	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}


	public int getMetaSubjectId() {
		return metaSubjectId;
	}


	public void setMetaSubjectId(int metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}


	public int getMetaGradeId() {
		return metaGradeId;
	}


	public void setMetaGradeId(int metaGradeId) {
		this.metaGradeId = metaGradeId;
	}


	public Subject getSubject() {
		return this.subject;
	}

	public Unit getUnit() {
		return this.unit;
	}


	public void setSubject(Subject subject) {
		//this.subject = subject;
	}


	public void setUnit(Unit unit) {
		//this.unit = unit;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


}