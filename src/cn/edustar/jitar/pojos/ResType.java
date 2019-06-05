package cn.edustar.jitar.pojos;

/**
 * ResType entity.
 * 
 * @author gusheng
 */
public class ResType implements java.io.Serializable {

	// Fields

	/** serialVersionUID */
	private static final long serialVersionUID = -1442683173158604422L;
	
	private int tcId;
	private String tcTitle = "";
	private String tcCode = "";
	private Integer tcParent;
	private int tcSort;
	private String tcComments;

	// Constructors

	/** default constructor */
	public ResType() {
	}

	/** minimal constructor */
	public ResType(String tcTitle, Integer tcParent) {
		this.tcTitle = tcTitle;
		this.tcParent = tcParent;
	}

	/** full constructor */
	public ResType(String tcTitle, String tcCode, Integer tcParent,
			int tcSort, String tcComments) {
		this.tcTitle = tcTitle;
		this.tcCode = tcCode;
		this.tcParent = tcParent;
		this.tcSort = tcSort;
		this.tcComments = tcComments;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResType{id=" + this.tcId + ",title=" + this.tcTitle + "}"; 
	}

	// Property accessors

	public int getTcId() {
		return this.tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public String getTcTitle() {
		return this.tcTitle;
	}

	public void setTcTitle(String tcTitle) {
		this.tcTitle = tcTitle;
	}

	public String getTcCode() {
		return this.tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

	public Integer getTcParent() {
		return this.tcParent;
	}

	public void setTcParent(Integer tcParent) {
		this.tcParent = tcParent;
	}

	public int getTcSort() {
		return this.tcSort;
	}

	public void setTcSort(int tcSort) {
		this.tcSort = tcSort;
	}

	public String getTcComments() {
		return this.tcComments;
	}

	public void setTcComments(String tcComments) {
		this.tcComments = tcComments;
	}
}
