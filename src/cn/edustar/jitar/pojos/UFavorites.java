package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class UFavorites implements Serializable, Cloneable  {
	
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1095245525388140224L;
	private Integer favId;
	private Integer favUser;
	private Integer objectType;
	private String objectUuid;
	private Integer objectId;
	private Date favDate;
	private String favTitle;
	private String favInfo;
	private Integer favTypeId;
	private String favHref;

	// Constructors

	/** default constructor */
	public UFavorites() {
	}

	/** minimal constructor */
	public UFavorites(Date favDate) {
		this.favDate = favDate;
	}

	/** full constructor */
	public UFavorites(Integer favUser, Integer objectType, String objectUuid,Integer objectId,
			Date favDate, String favTitle, String favInfo, Integer favTypeId,
			String favHref) {
		this.favUser = favUser;
		this.objectType = objectType;
		this.objectUuid = objectUuid;
		this.favDate = favDate;
		this.favTitle = favTitle;
		this.favInfo = favInfo;
		this.favTypeId = favTypeId;
		this.favHref = favHref;
		this.objectId=objectId;
	}

	// Property accessors

	public Integer getFavId() {
		return this.favId;
	}

	public void setFavId(Integer favId) {
		this.favId = favId;
	}

	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	
	public Integer getFavUser() {
		return this.favUser;
	}

	public void setFavUser(Integer favUser) {
		this.favUser = favUser;
	}

	public Integer getObjectType() {
		return this.objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public String getObjectUuid() {
		return this.objectUuid;
	}

	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
	}

	public Date getFavDate() {
		return this.favDate;
	}

	public void setFavDate(Date favDate) {
		this.favDate = favDate;
	}

	public String getFavTitle() {
		return this.favTitle;
	}

	public void setFavTitle(String favTitle) {
		this.favTitle = favTitle;
	}

	public String getFavInfo() {
		return this.favInfo;
	}

	public void setFavInfo(String favInfo) {
		this.favInfo = favInfo;
	}

	public Integer getFavTypeId() {
		return this.favTypeId;
	}

	public void setFavTypeId(Integer favTypeId) {
		this.favTypeId = favTypeId;
	}

	public String getFavHref() {
		return this.favHref;
	}

	public void setFavHref(String favHref) {
		this.favHref = favHref;
	}


}
