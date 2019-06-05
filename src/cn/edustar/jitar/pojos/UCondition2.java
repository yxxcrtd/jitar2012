package cn.edustar.jitar.pojos;
// default package

/**
 * UCondition2 entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UCondition2 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1384198014951922596L;
	private Integer id;
	private Integer groupId;
	private String teacherType;
	private String teacherTypeKeyword;
	private String sqlCondition;

	// Constructors

	/** default constructor */
	public UCondition2() {
	}

	/** minimal constructor */
	public UCondition2(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public UCondition2(Integer id, Integer groupId, String teacherType,String teacherTypeKeyword,
			String sqlCondition) {
		this.id = id;
		this.groupId = groupId;
		this.teacherType = teacherType;
		this.teacherTypeKeyword = teacherTypeKeyword;
		this.sqlCondition = sqlCondition;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getTeacherType() {
		return this.teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}
	public String getTeacherTypeKeyword() {
		return this.teacherTypeKeyword;
	}

	public void setTeacherTypeKeyword(String teacherTypeKeyword) {
		this.teacherTypeKeyword = teacherTypeKeyword;
	}
	public String getSqlCondition() {
		return this.sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

}