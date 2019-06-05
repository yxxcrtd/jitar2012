package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ChannelUser implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8259007515988645274L;

	private int channelUserId;
	private int channelId;
	private int userId;
	private int unitId;
	private String unitTitle;
	
	public ChannelUser() {

	}

	public int getChannelUserId() {
		return channelUserId;
	}
	public void setChannelUserId(int channelUserId) {
		this.channelUserId = channelUserId;
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

	private User user;
	public User getUser() {
		return this.user;
	}
	void setUser(User user) {
		//只读属性
	}
	
	private Channel channel;
	public Channel getChannel() {
		return this.channel;
	}
	void setChannel(Channel channel) {
		//只读属性
	}
}
