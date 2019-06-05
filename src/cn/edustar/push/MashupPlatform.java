package cn.edustar.push;


/**
 * @author 孟宪会
 *
 */
public class MashupPlatform implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6877703002333375846L;
	private Integer mashupPlatformId;
	private String platformGuid;
	private String platformName;
	private String platformHref;
	
	//区县平台的状态,0:正常，1：锁定，2：删除
	private Integer platformState;

	// Constructors

	/** default constructor */
	public MashupPlatform() {
	}

	/** full constructor */
	public MashupPlatform(String platformGuid, String platformName,
			String platformHref, Integer platformState) {
		this.platformGuid = platformGuid;
		this.platformName = platformName;
		this.platformHref = platformHref;
		this.platformState = platformState;
	}

	// Property accessors

	public Integer getMashupPlatformId() {
		return this.mashupPlatformId;
	}

	public void setMashupPlatformId(Integer mashupPlatformId) {
		this.mashupPlatformId = mashupPlatformId;
	}

	public String getPlatformGuid() {
		return this.platformGuid;
	}

	public void setPlatformGuid(String platformGuid) {
		this.platformGuid = platformGuid;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformHref() {
		return this.platformHref;
	}

	public void setPlatformHref(String platformHref) {
		this.platformHref = platformHref;
	}

	public Integer getPlatformState() {
		return this.platformState;
	}

	public void setPlatformState(Integer platformState) {
		this.platformState = platformState;
	}

}