package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/***
 * 讨论室
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public class ChatRoom implements Serializable {

	/** serialVersionUID*/
	private static final long serialVersionUID = 3112183080654269713L;
	
	/** 房间标识 */
	private int roomId;
	
	/** 群组标识 */
	private int groupId;
	
	/** 备课标识 */
	private int prepareCourseId;
	
	/** 房间名称 */
	private String roomName;
	
	/** 创建者的名称 */
	private String createrName;
	
	/** 创建房间的时间 */
	private Date createDate = new Date();
	
	/**房间的信息*/
	private String roomInfo;
	
	/** 是否关闭 */	
	private boolean isclosed;
	
	/** 群组标识 */
	public int getGroupId() {
		return groupId;
	}

	/** 群组标识 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/** 备课标识 */
	public int getPrepareCourseId() {
		return prepareCourseId;
	}

	/** 备课标识 */
	public void setPrepareCourseId(int prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}
	
	/** 房间标识 */
	public int getRoomId() {
		return roomId;
	}
	
	/** 房间标识 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	/** 房间名称 */
	public String getRoomName() {
		return roomName;
	}

	/** 房间名称 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/** 创建者的名称 */
	public String getCreaterName() {
		return createrName;
	}

	/** 创建者的名称 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	/** 创建房间的时间 */
	public Date getCreateDate() {
		return createDate;
	}
	/** 创建房间的时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**房间的信息*/
	public String getRoomInfo() {
		return roomInfo;
	}
	
	/**房间的信息*/
	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	
	/** 是否关闭 */	
	public boolean getIsClosed() {
		return isclosed;
	}

	/** 是否关闭 */	
	public void setIsClosed(boolean isclosed) {
		this.isclosed = isclosed;
	}
	
}
