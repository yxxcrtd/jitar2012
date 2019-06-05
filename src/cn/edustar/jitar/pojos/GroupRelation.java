package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 表示群组与群组之间的关系.
 *   业务键 srcGroup, dstGroup, relation
 *
 *
 * @remark
 * 群组关系用语言描述为 srcGroup relation dstGroup, 如 
 *   '东城区群组' 包括 '三中群组'
 *   
 * <p>群组间的关系影响群组业务逻辑, 群组间可以建立多种关联, 关联可以由业务决定吧.
 * </p>
 */
public class GroupRelation implements Serializable {
	/** 群组关系: 包含关系, a contains b 则 b 的内容自动聚合到 a */
	public static final String RELATION_CONTAINS = "contains";
	
	/** 群组关系: 引用关系, a reference b 则 b referenced-by a  */
	public static final String RELATION_REFERENCE = "reference";
	
	/** 群组关系: 前序关系, a previous b 则 b next a  */
	public static final String RELATION_PREVIOUS = "previous";
	
	/** 群组关系: 后序关系, a next b 则 b previous a  */
	public static final String RELATION_NEXT = "next";
	
	/** serialVersionUID */
	private static final long serialVersionUID = -424991219760549344L;

	/** 对象标识 */
	private int id;
	
	/** 源群组标识 */
	private int srcGroup;
	
	/** 关系字符串，值为 'contains', 'next', 'previous', etc. */
	private String relation;
	
	/** 目标群组标识 */
	private int dstGroup;

	/** 此关系的创建时间 */
	private Date createDate;
	
	/** 源群组审核通过 */
	private boolean srcAudit;
	
	/** 目标群组审核通过 */
	private boolean dstAudit;

	/** 源群组删除了此关系 */
	private boolean srcDelete;
	
	/** 目标群组删除了此关系 */
	private boolean dstDelete;
	
	/** 关系的更多属性 */
	private String attribute;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "GroupRelation{id=" + this.id + 
			",srcGroup=" + srcGroup +
			",relation=" + relation +
			",dstGroup=" + dstGroup + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(srcGroup, relation, dstGroup);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof GroupRelation)) return false;
		
		GroupRelation other = (GroupRelation)o;
		return PojoHelper.equals(srcGroup, other.srcGroup) &&
			PojoHelper.equals(relation, other.relation) &&
			PojoHelper.equals(dstGroup, other.dstGroup);
	}
	
	/** 对象标识 */
	public int getId() {
		return id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 源群组标识 */
	public int getSrcGroup() {
		return srcGroup;
	}

	/** 源群组标识 */
	public void setSrcGroup(int srcGroup) {
		this.srcGroup = srcGroup;
	}

	/** 关系字符串，值为 'contains', 'next', 'previous', etc. */
	public String getRelation() {
		return relation;
	}

	/** 关系字符串，值为 'contains', 'next', 'previous', etc. */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/** 目标群组标识 */
	public int getDstGroup() {
		return dstGroup;
	}

	/** 目标群组标识 */
	public void setDstGroup(int dstGroup) {
		this.dstGroup = dstGroup;
	}

	/** 此关系的创建时间 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 此关系的创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 源群组审核通过 */
	public boolean getSrcAudit() {
		return srcAudit;
	}

	/** 源群组审核通过 */
	public void setSrcAudit(boolean srcAudit) {
		this.srcAudit = srcAudit;
	}

	/** 目标群组审核通过 */
	public boolean getDstAudit() {
		return dstAudit;
	}

	/** 目标群组审核通过 */
	public void setDstAudit(boolean dstAudit) {
		this.dstAudit = dstAudit;
	}

	/** 源群组删除了此关系 */
	public boolean getSrcDelete() {
		return srcDelete;
	}

	/** 源群组删除了此关系 */
	public void setSrcDelete(boolean srcDelete) {
		this.srcDelete = srcDelete;
	}

	/** 目标群组删除了此关系 */
	public boolean getDstDelete() {
		return dstDelete;
	}

	/** 目标群组删除了此关系 */
	public void setDstDelete(boolean dstDelete) {
		this.dstDelete = dstDelete;
	}

	/** 关系的更多属性 */
	public String getAttribute() {
		return attribute;
	}

	/** 关系的更多属性 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
