package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 教研活动的对象
 * @author 孟宪会
 *
 */
public class Action implements Serializable, Cloneable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6530165023991769371L;

	/** 标识 */
	@SuppressWarnings("unused")
	private int id;
	
	/** 活动标识 */
	private int actionId;
	
	/** 活动标题 */
	private String title;
	
	/** 活动对象所有者类型 */
	private String ownerType;	

	/** 活动所属于的对象 */
	private Integer ownerId;
	
	/** 活动创建日期 */
	private Date createDate = new Date();
	
	/** 活动创建人 */
	private int createUserId;
	
	/** 活动类型：0，不限制（任意参加）；1，只能组内人员参加；2，只能邀请 */
	private int actionType;
	
	/** 活动描述 */
	private String description;
	
	/** 参与人数限制：0,不限制 */
	private int userLimit;
	
	/** 活动开始时间 */
	private Date startDateTime = new Date();
	
	/** 活动结束时间 */
	private Date finishDateTime = new Date();
	
	/** 活动报名截止时间 */
	private Date attendLimitDateTime = new Date();
	
	/** 活动地点 */
	private String place;
	
	/** 活动参加人数 */
	private int attendCount;
	
	/** 活动确认参加人数 */
	private int attendSuccessCount;
	
	/** 
	 * 活动确认不参加人数
	 */
	private int attendQuitCount;	
	
	/** 活动没有回应人数 */
	private int AttendFailCount;
	
	/** 活动状态 
	 * -1：待删除（回收站）
	 * 0：正常
	 * 1：待审批
	 * 2：已经关闭
	 * 3：锁定
	 */	
	private int status;
	
	/** 活动方式 */
	private int visibility;
	
	/** 是否锁定 */
	private int isLock;
	
	/** 是否锁定：0表示胃锁定，1表示锁定 */
	public int getIsLock() {
		return isLock;
	}
	
	/** 是否锁定：0表示胃锁定，1表示锁定 */
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	
	/** 活动是否公开：0公开，1非公开，只有参与人员可见 */
	public int getVisibility() {
		return visibility;
	}
	
	/** 活动是否公开：0公开，1非公开，只有参与人员可见 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	
	/** 活动创建时间 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/** 活动创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/** 活动标识 */
	public int getId() {
		return this.actionId;
	}
	
	/** 活动标识 */
	public void setId(int id) {
		this.actionId = id;
	}
	
	/** 活动标识 */
	public int getActionId() {
		return actionId;
	}
	
	/** 活动标识 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	/** 活动标题 */
	public String getTitle() {
		return title;
	}
	
	/** 活动标题 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 活动所属对象的标识 */
	public Integer getOwnerId() {
		return ownerId;
	}
	
	/** 活动所属对象的标识 */
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	/** 活动创建者标识 */
	public int getCreateUserId() {
		return createUserId;
	}
	
	/** 活动创建者标识 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	
	/** 活动类型：0，不限制（任意参加）；1，只能组内人员参加；2，只能邀请 */
	public int getActionType() {
		return actionType;
	}
	
	/** 活动类型：0，不限制（任意参加）；1，只能组内人员参加；2，只能邀请 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
	/** 活动描述 */
	public String getDescription() {
		return description;
	}
	
	/** 活动描述 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 活动参与用户数量限制 */
	public int getUserLimit() {
		return userLimit;
	}
	
	/** 活动参与用户数量限制 */
	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}
	
	/** 活动开始时间 */
	public Date getStartDateTime() {
		return startDateTime;
	}
	
	/** 活动开始时间 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	/** 活动结束时间 */
	public Date getFinishDateTime() {
		return finishDateTime;
	}
	
	/** 活动结束时间 */
	public void setFinishDateTime(Date finishDateTime) {
		this.finishDateTime = finishDateTime;
	}
	
	/** 活动报名截止时间 */
	public Date getAttendLimitDateTime() {
		return attendLimitDateTime;
	}
	
	/** 活动报名截止时间 */
	public void setAttendLimitDateTime(Date attendLimitDateTime) {
		this.attendLimitDateTime = attendLimitDateTime;
	}
	
	/** 活动地点 */
	public String getPlace() {
		return place;
	}
	
	/** 活动地点 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	/** 活动已经参与人数 */
	public int getAttendCount() {
		return attendCount;
	}
	
	/** 活动已经参与人数 */
	public void setAttendCount(int attendCount) {
		this.attendCount = attendCount;
	}
	
	/** 活动确认参与人数 */
	public int getAttendSuccessCount() {
		return attendSuccessCount;
	}
	
	/** 活动确认参与人数 */
	public void setAttendSuccessCount(int attendSuccessCount) {
		this.attendSuccessCount = attendSuccessCount;
	}
	
	/** 活动确认不参与人数 */
	public int getAttendQuitCount() {
		return attendQuitCount;
	}
	
	/** 活动确认不参与人数 */
	public void setAttendQuitCount(int attendQuitCount) {
		this.attendQuitCount = attendQuitCount;
	}
	
	/** 活动状态 
	 * -1：待删除（回收站）
	 * 0：正常
	 * 1：待审批
	 * 2：已经关闭
	 * 3：锁定
	 */	
	public int getStatus() {
		return status;
	}
	
	/** 活动状态 
	 * -1：待删除（回收站）
	 * 0：正常
	 * 1：待审批
	 * 2：已经关闭
	 * 3：锁定
	 */	
	public void setStatus(int status) {
		this.status = status;
	}
	
	/** 活动确认不参与人数 */
	public int getAttendFailCount() {
		return AttendFailCount;
	}
	
	/** 活动确认不参与人数 */
	public void setAttendFailCount(int attendFailCount) {
		AttendFailCount = attendFailCount;
	}
	
	/** 活动所属的对象类型：目前有user,group,course */
	public String getOwnerType() {
		return ownerType;
	}
	
	/** 活动所属的对象类型：目前有user,group,course */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

    @Override
    public String toString() {
        return "Action [id=" + id + ", actionId=" + actionId + ", title=" + title + ", ownerType=" + ownerType + ", ownerId=" + ownerId
                + ", createDate=" + createDate + ", createUserId=" + createUserId + ", actionType=" + actionType + ", userLimit=" + userLimit
                + ", startDateTime=" + startDateTime + ", finishDateTime=" + finishDateTime + ", attendLimitDateTime=" + attendLimitDateTime
                + ", attendCount=" + attendCount + ", attendSuccessCount=" + attendSuccessCount + ", attendQuitCount=" + attendQuitCount
                + ", AttendFailCount=" + AttendFailCount + ", status=" + status + ", visibility=" + visibility + ", isLock=" + isLock + "]";
    }    
}