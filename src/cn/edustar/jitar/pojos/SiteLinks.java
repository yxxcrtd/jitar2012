package cn.edustar.jitar.pojos;

/**
 * SiteLinks entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SiteLinks implements java.io.Serializable {

	// Fields

	/**
	 * 友情链接的公共实体类
	 */
	private static final long serialVersionUID = 1373686336612657415L;
	private Integer linkId;
	/**
	 * 'subject','site',等字符类的
	 */
	private String objectType;
	private Integer objectId;
	private String linkHref;
	private String linkTitle;
	private String linkIcon;

	// Constructors

	/** default constructor */
	public SiteLinks() {
	}

	/** minimal constructor */
	public SiteLinks(String objectType, Integer objectId, String linkHref,
			String linkTitle) {
		this.objectType = objectType;
		this.objectId = objectId;
		this.linkHref = linkHref;
		this.linkTitle = linkTitle;
	}

	/** full constructor */
	public SiteLinks(String objectType, Integer objectId, String linkHref,
			String linkTitle, String linkIcon) {
		this.objectType = objectType;
		this.objectId = objectId;
		this.linkHref = linkHref;
		this.linkTitle = linkTitle;
		this.linkIcon = linkIcon;
	}

	// Property accessors

	public Integer getLinkId() {
		return this.linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getLinkHref() {
		return this.linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	public String getLinkTitle() {
		return this.linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getLinkIcon() {
		return this.linkIcon;
	}

	public void setLinkIcon(String linkIcon) {
		this.linkIcon = linkIcon;
	}

}