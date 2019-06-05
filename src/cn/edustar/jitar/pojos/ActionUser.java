package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 
 * 教研活动的参与用户
 * @author 孟宪会
 *
 */
public class ActionUser implements Serializable, Cloneable  {

	/**
	 * 序列化唯一标识符
	 */
	private static final long serialVersionUID = 5955544833177925265L;
	
	/** 活动参与人员表标识 */
	private int actionUserId;
	
	/** 活动标识 */
	private int actionId;
	
	/** 活动参与用户标识 */
	private int userId;
	
	/** 活动参与人员所携带的人数总数 */
	private int attendUserCount;
	
	/** 活动参与人员的说明 */
	private String description;
	
	/** 邀请参与人员者的标识 */
	private Integer inviteUserId;
	
	/** 活动参与人员是否通过审核 */
	private int isApprove;
	
	/** 活动参与人员的状态  0:未回复,1:已参加,2:已退出,3:已请假 */
	private int status;	
	
	public int getActionUserId() {
		return actionUserId;
	}
	public void setActionUserId(int actionUserId) {
		this.actionUserId = actionUserId;
	}
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAttendUserCount() {
		return attendUserCount;
	}
	public void setAttendUserCount(int attendUserCount) {
		this.attendUserCount = attendUserCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	public int getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(int isApprove) {
		this.isApprove = isApprove;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
