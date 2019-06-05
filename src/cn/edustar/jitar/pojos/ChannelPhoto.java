package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ChannelPhoto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2761917811782035428L;
	private int channelPhotoId;
	private int channelId;
	private int photoId;
	private int userId;
	private int unitId;
	private int viewCount;
	private Integer channelCateId;
	private String channelCate;
	private Photo photo;
	private User user;
	private Unit unit;
	
	public ChannelPhoto(){}

	public int getChannelPhotoId() {
		return channelPhotoId;
	}

	public void setChannelPhotoId(int channelPhotoId) {
		this.channelPhotoId = channelPhotoId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		//this.photo = photo;
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
