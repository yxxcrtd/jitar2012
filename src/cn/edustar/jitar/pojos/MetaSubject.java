package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 元学科实体, Subject(显示学科) 由元学科+元学段组合而成.
 * <p>
 *  元学科包括 '语文', '数学', '英语' 等，元学段包括 '高中', '初中', '小学', '幼教'等.
 *  通过组合产生出显示学科如 '初中语文', '高中化学', '幼儿英语' 等.
 * </p>
 *
 *
 */
public class MetaSubject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8159538014675738055L;

	/** 元学科标识. */
	private int msubjId;
	
	/** 元学科名字, 如 '语文', '数学'. */
	private String msubjName = "";

	/** 元学科标准代码, 当前可能不使用. */
	private String msubjCode = "";

	/** 排序号. */
	private int orderNum;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MetaSubject{id=" + this.msubjId + ", name=" + this.msubjName + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.msubjName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof MetaSubject)) return false;
		MetaSubject other = (MetaSubject)o;
		return PojoHelper.equals(this.msubjName, other.msubjName);
	}
	
	/** 元学科标识. */
	public int getMsubjId() {
		return msubjId;
	}

	/** 元学科标识. */
	public void setMsubjId(int msubjId) {
		this.msubjId = msubjId;
	}

	/** 元学科名字, 如 '语文', '数学'. */
	public String getMsubjName() {
		return msubjName;
	}

	/** 元学科名字, 如 '语文', '数学'. */
	public void setMsubjName(String msubjName) {
		this.msubjName = msubjName;
	}

	/** 元学科标准代码, 当前可能不使用. */
	public String getMsubjCode() {
		return msubjCode;
	}

	/** 元学科标准代码, 当前可能不使用. */
	public void setMsubjCode(String msubjCode) {
		this.msubjCode = msubjCode;
	}
	

	/** 排序号. */
	public int getOrderNum() {
		return orderNum;
	}
	

	/** 排序号. */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
}
