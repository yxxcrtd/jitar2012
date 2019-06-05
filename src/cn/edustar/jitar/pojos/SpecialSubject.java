package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * SSpecialSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 * 
 */
public class SpecialSubject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 421912087163519822L;

	/**
	 * 专题标识
	 */
	/**
	 * 
	 */
	private Integer specialSubjectId;
	
	/**
	 * 专题全球标识符
	 */
	private String objectGuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/**
	 * 专题名称
	 */
	private String title;
	
	/**
	 * 专题 Logo
	 */
	private String logo;
	
	/**
	 * 专题导读
	 */
	private String description;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 创建人标识
	 */
	private int createUserId;
	
	/**
	 * 有效期限日期
	 */
	private Date expiresDate;
	
	/**
	 * 专题所属对象，如总站的，学科的等，为 system 则表示是总站的
	 */
	private String objectType;
	
	/**
	 * 专题所属对象的标识，为 null 则表示是总站的
	 */
	private Integer objectId;
	
	/** 简单获得该专题是否过期。只读属性 */
	private boolean expired = false;

	// Constructors

	/** default constructor */
	public SpecialSubject() {
	}

	public SpecialSubject( String objectGuid,
			String title, String logo, String description, Date createDate,
			int createUserId, Date expiresDate, String objectType, Integer objectId) {
		this.objectGuid = objectGuid;
		this.title = title;
		this.logo = logo;
		this.description = description;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.expiresDate = expiresDate;
		this.objectType = objectType;
		this.objectId = objectId;
	}

	public Integer getSpecialSubjectId() {
		return specialSubjectId;
	}

	public void setSpecialSubjectId(Integer specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public String getObjectGuid() {
		return objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Date getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(Date expiresDate) {
		this.expiresDate = expiresDate;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	
	public boolean getExpired(){
		Date now = new Date();
		return now.after(this.expiresDate);
	}

}