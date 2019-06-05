package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ProductConfig implements Serializable, Cloneable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 939268162167794751L;

	/** 对象标识 */
	private int id;

	/** 产品编号 */
	private String productID;

	/** 标题 */
	private String productName;

	
	/** 协作组数量 */
	private String installDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public ProductConfig clone() {
		ProductConfig other = new ProductConfig();
		other.id = this.id;
		other.productID = this.productID;
		other.productName = this.productName;
		other.installDate = this.installDate;
		return other;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

}
