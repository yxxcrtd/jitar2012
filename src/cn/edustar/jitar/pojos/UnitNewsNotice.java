package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * UnitNewsNotice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitNewsNotice implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6523468763373596534L;
	
	/** 新闻公告标识 */
	private Integer unitNewsId;
	
	/** 新闻公告标题 */
	private String title;
	
	/** 创建者标识 */
	private int createUserId;
	
	/** 机构标识 */
	private int organizationUnitId;
	
	/** 创建时间 */
	private Date createDate;
	
	/** 浏览次数 */
	private int viewCount;
	
	/** 内容 */
	private String content;
	
	/** 图片地址 */
	private String picture;
	
	/** 0：图片新闻，1：公告，2：动态（无图片新闻） */
	private int itemType;

	// Constructors

	/** default constructor */
	public UnitNewsNotice() {
	}

	/** minimal constructor */
	public UnitNewsNotice(String title, Integer createUserId,
			Integer organizationUnitId, Date createDate, Integer viewCount,
			String content, Integer itemType) {
		this.title = title;
		this.createUserId = createUserId;
		this.organizationUnitId = organizationUnitId;
		this.createDate = createDate;
		this.viewCount = viewCount;
		this.content = content;
		this.itemType = itemType;
	}

	/** full constructor */
	public UnitNewsNotice(String title, Integer createUserId,
			Integer organizationUnitId, Date createDate, Integer viewCount,
			String content, String picture, Integer itemType) {
		this.title = title;
		this.createUserId = createUserId;
		this.organizationUnitId = organizationUnitId;
		this.createDate = createDate;
		this.viewCount = viewCount;
		this.content = content;
		this.picture = picture;
		this.itemType = itemType;
	}

	// Property accessors

	public Integer getUnitNewsId() {
		return this.unitNewsId;
	}

	public void setUnitNewsId(Integer unitNewsId) {
		this.unitNewsId = unitNewsId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getOrganizationUnitId() {
		return this.organizationUnitId;
	}

	public void setOrganizationUnitId(Integer organizationUnitId) {
		this.organizationUnitId = organizationUnitId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getItemType() {
		return this.itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

}