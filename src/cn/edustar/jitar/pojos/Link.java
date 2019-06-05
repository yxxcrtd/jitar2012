package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import cn.edustar.jitar.util.CommonUtil;

/***
 * 友情链接, 提供给协作组、个人、站点用.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public class Link implements Serializable, Cloneable {
	/** serialVersionUID */
	private static final long serialVersionUID = 7179124647918957250L;
	
	/** 对象标识 */
	private int linkId;
	
	/** 所属对象类型 */
	private int objectType;
	
	/** 所属对象标识 */
	private int objectId;
	
	/** 链接标题 */
	private String title;
	
	/** 链接地址 */
	private String linkAddress;
	
	/** 链接类型  1 表示外部链接; 2 表示内部链接*/
	private int linkType;
	
	/** 链接简介 */
	private String description;
	
	/** 链接创建时间 */
	private Date createDate = new Date();
	
	/** 作为链接的图片地址 */
	private String linkIcon;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// 先与自己比较. 
		if (this == obj) return true;

		// 对象是否为空, 并判断对象类型.		if (obj == null || !(obj instanceof Link))
			return false;
		
		Link other = (Link)obj;
		return PojoHelper.equals(this.objectType, other.objectType) &&
			PojoHelper.equals(this.objectId, other.objectId) &&
			PojoHelper.equals(this.getLinkAddress(), other.getLinkAddress());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Link{linkId=" + this.getLinkId() +
				",LinkAddress=" + this.getLinkAddress() + "}";
	}

	/**
	 * 得到友好显示字符串.
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.title) + "'(id=" + this.linkId + ")"; 
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.linkAddress, this.objectType, this.objectId);
	}
	
	/** 对象标识 */
	public int getLinkId() {
		return linkId;
	}
	
	/** 对象标识 */
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	
	/** 链接名称 */
	public String getTitle() {
		return title;
	}
	
	/** 链接名称 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 链接地址 */
	public String getLinkAddress() {
		return linkAddress;
	}
	
	/** 链接地址 */
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	
	/** 链接类型 */
	public int getLinkType() {
		return linkType;
	}
	
	/** 链接类型 */
	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
	/** 链接简介 */
	public String getDescription() {
		return description;
	}
	
	/** 链接简介 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 链接创建时间 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/** 链接创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/** 作为链接的图片地址 */
	public String getLinkIcon() {
		return linkIcon;
	}
	
	/** 作为链接的图片地址 */
	public void setLinkIcon(String linkIcon) {
		this.linkIcon = linkIcon;
	}

	
	/** 所属对象类型 */
	public int getObjectType() {
		return objectType;
	}
	

	/** 所属对象类型 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}


	/** 所属对象标识 */
	public int getObjectId() {
		return objectId;
	}
	

	/** 所属对象标识 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
}
