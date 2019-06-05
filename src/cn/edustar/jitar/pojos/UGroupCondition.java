package cn.edustar.jitar.pojos;
// default package

/**
 * UGroupCondition entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UGroupCondition implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2431907347604841138L;
	private Integer conditionId;
	private String tableName;

	// Constructors

	/** default constructor */
	public UGroupCondition() {
	}

	/** minimal constructor */
	public UGroupCondition(Integer conditionId) {
		this.conditionId = conditionId;
	}

	/** full constructor */
	public UGroupCondition(Integer conditionId, String tableName) {
		this.conditionId = conditionId;
		this.tableName = tableName;
	}

	// Property accessors

	public Integer getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}