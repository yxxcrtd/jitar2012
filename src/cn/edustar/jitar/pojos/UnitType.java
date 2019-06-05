package cn.edustar.jitar.pojos;

/**
 * JitarUnit entity. @author MyEclipse Persistence Tools
 */

public class UnitType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7124582110399726974L;
	private int Id;
	private String unitTypeGuid = java.util.UUID.randomUUID().toString().toLowerCase();
	private String unitTypeName;
	private int orderNo;

	/** default constructor */
	public UnitType() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUnitTypeGuid() {
		return unitTypeGuid;
	}

	public void setUnitTypeGuid(String unitTypeGuid) {
		this.unitTypeGuid = unitTypeGuid;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	}