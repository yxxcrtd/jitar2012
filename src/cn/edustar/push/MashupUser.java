package cn.edustar.push;

import java.util.Date;



/**
 * @author 孟宪会
 *
 */
public class MashupUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015667609496760337L;
	private Integer mashupUserId;
	private String mashupUserGuid;
	private Date lastUpdated = new Date();
	private String fromUrl;
	private String title;
	private String unitName;
	private String unitTitle;
	// Constructors

	/** default constructor */
	public MashupUser() {
	}

	/** full constructor */
	public MashupUser(String mashupUserGuid, Date lastUpdated) {
		this.mashupUserGuid = mashupUserGuid;
		this.lastUpdated = lastUpdated;
	}

	// Property accessors

	public Integer getMashupUserId() {
		return this.mashupUserId;
	}

	public void setMashupUserId(Integer mashupUserId) {
		this.mashupUserId = mashupUserId;
	}

	public String getMashupUserGuid() {
		return this.mashupUserGuid;
	}

	public void setMashupUserGuid(String mashupUserGuid) {
		this.mashupUserGuid = mashupUserGuid;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitTitle() {
		return unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}
}