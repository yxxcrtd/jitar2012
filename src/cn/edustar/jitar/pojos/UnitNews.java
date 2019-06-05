package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * UnitNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitNews implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1037946571565180263L;
	private Integer unitNewsId;
	private String title;
	private Integer createUserId;
	private Integer unitId;
	private Date createDate;
	private Integer viewCount;
	private String content;
	private String picture;
	private Integer itemType;

	// Constructors

	/** default constructor */
	public UnitNews() {
	}

	/** minimal constructor */
	public UnitNews(String title, Integer createUserId, Integer unitId,
			Date createDate, Integer viewCount, String content, Integer itemType) {
		this.title = title;
		this.createUserId = createUserId;
		this.unitId = unitId;
		this.createDate = createDate;
		this.viewCount = viewCount;
		this.content = content;
		this.itemType = itemType;
	}

	/** full constructor */
	public UnitNews(String title, Integer createUserId, Integer unitId,
			Date createDate, Integer viewCount, String content, String picture,
			Integer itemType) {
		this.title = title;
		this.createUserId = createUserId;
		this.unitId = unitId;
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

	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
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