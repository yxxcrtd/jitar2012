package cn.edustar.jitar.pojos;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户的好友对象
 * 
 *
 */
public class Friend implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -3435584794449629489L;

	/**
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母), 不能重复。
	 * 
	 * URL写成 /Groups/g/$groupname 即可访问到该群组, 等同于 /Groups/group.jsp?id=$groupid
	 */
	private String groupName;

	/** 对象标识。 */
	private int id;

	/** 用户标识。 */
	private int userId;

	/** 朋友的标识。 */
	private int friendId;

	/** 添加此好友的时间。 */
	private Date addTime = new Date();

	/** 好友的分类标识， null 表示未分类。 */
	private Integer typeId;

	/** 用户给此好友添加的额外说明。 */
	private String remark;

	/** 是否是黑名单，这是特殊分类。为 0(false)好友; 为1(true)是黑名单 */
	private boolean isBlack;

	/**
	 * 无参构造函数
	 */
	public Friend() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		// 先自己与自己比较
		if (this == obj)
			return true;

		// 对象是否为空，并且检测对象的类型
		if (obj == null || !(obj instanceof Message))
			return false;

		Friend other = (Friend) obj;
		if (PojoHelper.equals(this.groupName, other.groupName))
			return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Friend{id = " + this.id + ", userId = " + this.userId
				+ ", friendId = " + this.friendId + ", addTime=" + this.addTime
				+ "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.id, this.userId, this.friendId);
	}

	/** Get and Set */
	/** 对象标识。 */
	public int getId() {
		return this.id;
	}

	/** 对象标识。 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母), 不能重复。
	 * 
	 * URL写成 /Groups/g/$groupname 即可访问到该群组, 等同于 /Groups/group.jsp?id=$groupid
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母), 不能重复。
	 * 
	 * URL写成 /Groups/g/$groupname 即可访问到该群组, 等同于 /Groups/group.jsp?id=$groupid
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/** 用户标识。 */
	public int getUserId() {
		return userId;
	}

	/** 用户标识。 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 朋友的标识。 */
	public int getFriendId() {
		return friendId;
	}

	/** 朋友的标识。 */
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	/** 添加此好友的时间。 */
	public Date getAddTime() {
		return addTime;
	}

	/** 添加此好友的时间。 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/** 好友的分类标识， null 表示未分类。 */
	public Integer getTypeId() {
		return typeId;
	}

	/** 好友的分类标识， null 表示未分类。 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/** 用户给此好友添加的额外说明。 */
	public String getRemark() {
		return remark;
	}

	/** 用户给此好友添加的额外说明。 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 是否是黑名单，这是特殊分类。为 0(false)好友; 为1(true)是黑名单 */
	public boolean isBlack() {
		return isBlack;
	}

	/** 是否是黑名单，这是特殊分类。为 0(false)好友; 为1(true)是黑名单 */
	public boolean getIsBlack() {
		return isBlack;
	}

	/** 是否是黑名单，这是特殊分类。为 0(false)好友; 为1(true)是黑名单 */
	public void setBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}

	/** 是否是黑名单，这是特殊分类。为 0(false)好友; 为1(true)是黑名单 */
	public void setIsBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
}
