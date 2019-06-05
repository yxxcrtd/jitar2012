package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class UnitCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 467318928997870798L;

	private int countId;
	private int unitId;

	private int selfArticleCount;
	private int childArticleCount;
	private int selfHistoryArticleCount;
	private int childHistoryArticleCount;
	private int selfResourceCount;
	private int selfPhotoCount;
	private int selfVideoCount;
	private int selfUserCount;
	private int childResourceCount;
	private int childPhotoCount;
	private int childVideoCount;
	private int childUserCount;

	public UnitCount() {
	}

	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getSelfArticleCount() {
		return selfArticleCount;
	}

	public void setSelfArticleCount(int selfArticleCount) {
		this.selfArticleCount = selfArticleCount;
	}

	public int getChildArticleCount() {
		return childArticleCount;
	}

	public void setChildArticleCount(int childArticleCount) {
		this.childArticleCount = childArticleCount;
	}

	public int getSelfHistoryArticleCount() {
		return selfHistoryArticleCount;
	}

	public void setSelfHistoryArticleCount(int selfHistoryArticleCount) {
		this.selfHistoryArticleCount = selfHistoryArticleCount;
	}

	public int getChildHistoryArticleCount() {
		return childHistoryArticleCount;
	}

	public void setChildHistoryArticleCount(int childHistoryArticleCount) {
		this.childHistoryArticleCount = childHistoryArticleCount;
	}

	public int getSelfResourceCount() {
		return selfResourceCount;
	}

	public void setSelfResourceCount(int selfResourceCount) {
		this.selfResourceCount = selfResourceCount;
	}

	public int getSelfPhotoCount() {
		return selfPhotoCount;
	}

	public void setSelfPhotoCount(int selfPhotoCount) {
		this.selfPhotoCount = selfPhotoCount;
	}

	public int getSelfVideoCount() {
		return selfVideoCount;
	}

	public void setSelfVideoCount(int selfVideoCount) {
		this.selfVideoCount = selfVideoCount;
	}

	public int getSelfUserCount() {
		return selfUserCount;
	}

	public void setSelfUserCount(int selfUserCount) {
		this.selfUserCount = selfUserCount;
	}

	public int getChildResourceCount() {
		return childResourceCount;
	}

	public void setChildResourceCount(int childResourceCount) {
		this.childResourceCount = childResourceCount;
	}

	public int getChildPhotoCount() {
		return childPhotoCount;
	}

	public void setChildPhotoCount(int childPhotoCount) {
		this.childPhotoCount = childPhotoCount;
	}

	public int getChildVideoCount() {
		return childVideoCount;
	}

	public void setChildVideoCount(int childVideoCount) {
		this.childVideoCount = childVideoCount;
	}

	public int getChildUserCount() {
		return childUserCount;
	}

	public void setChildUserCount(int childUserCount) {
		this.childUserCount = childUserCount;
	}
}
