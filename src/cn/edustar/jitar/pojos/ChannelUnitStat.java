package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ChannelUnitStat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632035155459517999L;
	private int id;
	private int channelId;
	private String statGuid;
	private int unitId;
	private String unitTitle;
	private int userCount;
	private int articleCount;
	private int resourceCount;
	private int photoCount;
	private int videoCount;
	public ChannelUnitStat() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getStatGuid() {
		return statGuid;
	}
	public void setStatGuid(String statGuid) {
		this.statGuid = statGuid;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getUnitTitle() {
		return unitTitle;
	}
	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	public int getPhotoCount() {
		return photoCount;
	}
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}
	public int getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
}
