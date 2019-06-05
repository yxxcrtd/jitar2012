package cn.edustar.jitar.pojos;

/**
 * UnitLinks entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitLinks implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5353854638810014713L;
	private Integer linkId;
	private Integer unitId;
	private String linkName;
	private String linkAddress;
	private String linkIcon;

	// Constructors

	/** default constructor */
	public UnitLinks() {
	}

	/** minimal constructor */
	public UnitLinks(Integer unitId, String linkName, String linkAddress) {
		this.unitId = unitId;
		this.linkName = linkName;
		this.linkAddress = linkAddress;
	}

	/** full constructor */
	public UnitLinks(Integer unitId, String linkName, String linkAddress,
			String linkIcon) {
		this.unitId = unitId;
		this.linkName = linkName;
		this.linkAddress = linkAddress;
		this.linkIcon = linkIcon;
	}

	// Property accessors

	public Integer getLinkId() {
		return this.linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkAddress() {
		return this.linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getLinkIcon() {
		return this.linkIcon;
	}

	public void setLinkIcon(String linkIcon) {
		this.linkIcon = linkIcon;
	}

}