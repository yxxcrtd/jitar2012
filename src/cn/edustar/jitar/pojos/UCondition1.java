package cn.edustar.jitar.pojos;
// default package

/**
 * UCondition1 entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UCondition1 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6309285998768383939L;
	private Integer id;
	private Integer groupId;
	private Integer score1;
	private Integer score2;
	private Integer conditionType;
	// Constructors

	/** default constructor */
	public UCondition1() {
	}

	/** minimal constructor */
	public UCondition1(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public UCondition1(Integer id, Integer groupId, Integer score1,
			Integer score2) {
		this.id = id;
		this.groupId = groupId;
		this.score1 = score1;
		this.score2 = score2;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConditionType() {
		return this.conditionType;
	}

	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}
	
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getScore1() {
		return this.score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Integer getScore2() {
		return this.score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

}