package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ChannelVideo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1537406958712249056L;

	private int channelVideoId;
	private int channelId;
	private int videoId;
	private int userId;
	private int unitId;
	private int viewCount = 0;
	private Integer channelCateId;
	private String channelCate;
	private Video video;
	private User user;
	private Unit unit;
	
	public ChannelVideo() {}

	public int getChannelVideoId() {
		return channelVideoId;
	}

	public void setChannelVideoId(int channelVideoId) {
		this.channelVideoId = channelVideoId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getChannelCateId() {
		return channelCateId;
	}

	public void setChannelCateId(Integer channelCateId) {
		this.channelCateId = channelCateId;
	}

	public String getChannelCate() {
		return channelCate;
	}

	public void setChannelCate(String channelCate) {
		this.channelCate = channelCate;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		//this.video = video;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		//this.user = user;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		//this.unit = unit;
	}

}
