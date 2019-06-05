package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 表示一个标签。使用 tagName 做为业务键来比较是否相同。equals.
 * 
 *
 */
public class Tag implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -1931825298296033579L;

	/** 标签标识，数据库产生的唯一标识、主键。 */
	private int tagId;
	
	/** 标签全局唯一标识。约束为唯一。 */
	private String tagUuid = UUID.randomUUID().toString().toUpperCase();
	
	/** 标签标题，25汉字以内。有索引，不能重复，所以做为业务键。 */
	private String tagName;
	
	/** 标签创建时间。 */
	private Date createDate;
	
	/** 该标签被引用的总次数。有索引。 */
	private int refCount;
	
	/** 点击数 */
	private int viewCount;
	
	/** 该标签类别。(可能并不使用，缺省给0) */
	private int tagType;
	
	/** 是否禁用。某些标签可能由于法律原因被禁用，如：'法轮功'。 */
	private boolean disabled;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Tag{id=" + tagId + ",name=" + tagName + ",ref=" + refCount + "}";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Tag)) return false;
		
		return compareString(this.tagName, ((Tag)o).tagName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (tagName == null) return 0;
		return tagName.hashCode();
	}
	
	/**
	 * 比较两个字符串是否相等。a == null && b == null 认为相等。否则 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean compareString(String a, String b) {
		if (a == null)
			return (b == null);
		else 
			return a.equals(b);
	}
	
	/** 标签标识，数据库产生的唯一标识、主键。 */
	public int getTagId() {
		return tagId;
	}

	/** 标签标识，数据库产生的唯一标识、主键。 */
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	/** 标签全局唯一标识。约束为唯一。 */
	public String getTagUuid() {
		return tagUuid;
	}

	/** 标签全局唯一标识。约束为唯一。 */
	public void setTagUuid(String tagUuid) {
		this.tagUuid = tagUuid;
	}

	/** 标签标题，25汉字以内。有索引，不能重复，所以做为业务键。 */
	public String getTagName() {
		return tagName;
	}

	/** 标签标题，25汉字以内。有索引，不能重复，所以做为业务键。 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/** 标签创建时间。 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 标签创建时间。 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 该标签被引用的总次数。有索引。 */
	public int getRefCount() {
		return refCount;
	}

	/** 该标签被引用的总次数。有索引。 */
	public void setRefCount(int refCount) {
		this.refCount = refCount;
	}
	
	/** 点击数 */
	public int getViewCount() {
		return this.viewCount;
	}
	
	/** 点击数 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/** 该标签类别。(可能并不使用，缺省给0) */
	public int getTagType() {
		return tagType;
	}

	/** 该标签类别。(可能并不使用，缺省给0) */
	public void setTagType(int tagType) {
		this.tagType = tagType;
	}

	/** 是否禁用。某些标签可能由于法律原因被禁用。 */
	public boolean getDisabled() {
		return disabled;
	}

	/** 是否禁用。某些标签可能由于法律原因被禁用。 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	
}
