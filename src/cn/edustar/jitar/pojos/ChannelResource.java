package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class ChannelResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6525386386538350508L;

	private int channelResourceId;
	private int channelId;
	private int userId;
	private int resourceId;
	private int unitId;
	private int viewCount = 0;
	private Integer channelCateId;
	private String channelCate;
	private Resource resource;
	private User user;
	private Unit unit;
	public ChannelResource() {}

	public int getChannelResourceId() {
		return channelResourceId;
	}

	public void setChannelResourceId(int channelResourceId) {
		this.channelResourceId = channelResourceId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		//this.resource = resource;
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
