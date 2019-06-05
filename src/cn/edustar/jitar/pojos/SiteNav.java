package cn.edustar.jitar.pojos;

/**
 * SiteNav entity. @author MyEclipse Persistence Tools
 */

public class SiteNav implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6940053031204782580L;
	private int siteNavId;
	private String siteNavName;
	private String siteNavUrl;
	private String currentNav;
	private Boolean siteNavIsShow;
	private Integer siteNavItemOrder;
	private Boolean isExternalLink;
	private int ownerType;
	private int ownerId;
	
	/** 默认是整站的导航:0整站 1：机构 2：学科 */
	public final static int SITENAV_OWNERTYPE_DEFAULT = 0;
	public final static int SITENAV_OWNERTYPE_UNIT = 1;
	public final static int SITENAV_OWNERTYPE_SUBJECT = 2;
	public final static int SITENAV_OWNERTYPE_CHANNEL = 3;

	// Constructors

	/** default constructor */
	public SiteNav() {
	}
	// Property accessors

	public Boolean getIsExternalLink() {
		return isExternalLink;
	}

	public void setIsExternalLink(Boolean isExternalLink) {
		this.isExternalLink = isExternalLink;
	}

	public int getSiteNavId() {
		return this.siteNavId;
	}

	public void setSiteNavId(int siteNavId) {
		this.siteNavId = siteNavId;
	}

	public String getSiteNavName() {
		return this.siteNavName;
	}

	public void setSiteNavName(String siteNavName) {
		this.siteNavName = siteNavName;
	}

	public String getSiteNavUrl() {
		return this.siteNavUrl;
	}

	public void setSiteNavUrl(String siteNavUrl) {
		this.siteNavUrl = siteNavUrl;
	}

	public Boolean getSiteNavIsShow() {
		return this.siteNavIsShow;
	}

	public void setSiteNavIsShow(Boolean siteNavIsShow) {
		this.siteNavIsShow = siteNavIsShow;
	}

	public Integer getSiteNavItemOrder() {
		return this.siteNavItemOrder;
	}

	public void setSiteNavItemOrder(Integer siteNavItemOrder) {
		this.siteNavItemOrder = siteNavItemOrder;
	}
	public String getCurrentNav() {
		return currentNav;
	}

	public void setCurrentNav(String currentNav) {
		this.currentNav = currentNav;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
}