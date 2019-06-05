package cn.edustar.jitar.pojos;

public class BackYear implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8269221907365223197L;
	private int backYearId;
	private int backYear;
	private String backYearType;
	private int backYearCount = 0;
	
	public BackYear() {
	}

	public int getBackYearId() {
		return backYearId;
	}

	public void setBackYearId(int backYearId) {
		this.backYearId = backYearId;
	}

	public int getBackYear() {
		return backYear;
	}

	public void setBackYear(int backYear) {
		this.backYear = backYear;
	}

	public String getBackYearType() {
		return backYearType;
	}

	public void setBackYearType(String backYearType) {
		this.backYearType = backYearType;
	}

	public int getBackYearCount() {
		return backYearCount;
	}

	public void setBackYearCount(int backYearCount) {
		this.backYearCount = backYearCount;
	}

}
