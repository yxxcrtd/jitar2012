package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 表示标签、对象之间的引用关系。业务键为 {tagId, objectId, objectType}
 * 
 *
 *
 */
public class TagRef implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -8972862273831514392L;

	/** 引用的标识。 */
	private int id;
	
	/** 标签标识。 */
	private int tagId;
	
	/** 对象标识。 */
	private int objectId;
	
	/** 对象类型，参见 model.ObjectType 定义的对象类型编码。 */
	private int objectType;
	
	/** 同一对象引用序，可能不一定需要，先保留并使用它。 */
	private int orderNum;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "TagRef{id=" + id + ",tagId=" + tagId + ",objId=" + objectId +
			",objType=" + objectType + ",order=" + orderNum + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return tagId*1987 + objectId*119 + objectType;	// ? overflow
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == null || !(o instanceof TagRef)) return false;
		TagRef o2 = (TagRef)o;
		return (tagId == o2.tagId) && (objectId == o2.objectId) && (objectType == o2.objectType);
	}
	
	/** 引用的标识。 */
	public int getId() {
		return id;
	}

	/** 引用的标识。 */
	public void setId(int id) {
		this.id = id;
	}

	/** 标签标识。 */
	public int getTagId() {
		return tagId;
	}

	/** 标签标识。 */
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	/** 对象标识。 */
	public int getObjectId() {
		return objectId;
	}

	/** 对象标识。 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	/** 对象类型，参见 model.ObjectType 定义的对象类型编码。 */
	public int getObjectType() {
		return objectType;
	}

	/** 对象类型，参见 model.ObjectType 定义的对象类型编码。 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	/** 同一对象引用序，可能不一定需要，先保留并使用它。 */
	public int getOrderNum() {
		return orderNum;
	}

	/** 同一对象引用序，可能不一定需要，先保留并使用它。 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
}
