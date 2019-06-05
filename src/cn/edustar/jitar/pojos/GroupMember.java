package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 表示群组和其成员的关系对象。
 *  
 *   groupId, userId 联合做为业务键。
 *   
 *
 */
public class GroupMember implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1595696563772772396L;

	/** 该成员在本群组中的角色 普通组员 member = 0 */
	public static final int GROUP_ROLE_MEMBER = 0;
	/** 该成员在本群组中的角色 资深成员 vip = 500 (当前未使用) */
	public static final int GROUP_ROLE_VIP = 500;
	/** 该成员在本群组中的角色 副管理员 vice_manager = 800 */
	public static final int GROUP_ROLE_VICE_MANAGER = 800;
	/** 该成员在本群组中的角色 管理员 manager = 1000 */
	public static final int GROUP_ROLE_MANAGER = 1000;
	
	/** 该成员在本群组内的状态 正常可用状态 normal = 0 */
	public static final int STATUS_NORMAL = 0;
	/** 该成员在本群组内的状态 申请后待审核 wait_audit = 1 */
	public static final int STATUS_WAIT_AUDIT = 1;
	/** 该成员在本群组内的状态 待删除 deleting = 2 */
	public static final int STATUS_DELETING = 2;
	/** 该成员在本群组内的状态 锁定 locked = 3 */
	public static final int STATUS_LOCKED = 3;
	/** 该成员在本群组内的状态 邀请未回应 inviting = 4 */
	public static final int STATUS_INVITING = 4;
	
	/** 标识 */
	private int id;
	
	/** 群组标识 */
	private int groupId;
	
	/** 成员标识 */
	private int userId;
	
	/** 该成员在本群组内的状态：0 – 正常可用状态；1 – 申请后待审核； 2 – 待删除；3 – 锁定，4 – 邀请未回应； */
	private int status = STATUS_NORMAL;
	
	/** 该成员在本群组中的角色：普通组员；副管理员；管理员； */
	private int groupRole = GROUP_ROLE_MEMBER;
	
	/** 加入群组时间 */
	private Date joinDate = new Date();
	
	/** 该成员在本群组内发表的文章数量 */
	private int articleCount=0;
	
	/** 该成员在本群组内发表的资源数量 */
	private int resourceCount=0;
	
	/** 该成员在本群组内发表的网课数量, 版本2将实现 */
	private int courseCount=0;
	
	/** 该成员在本群组内发表的主题数量 */
	private int topicCount=0;
	
	/** 该成员在本群组内回复主题的数量 */
	private int replyCount=0;
	
	/** 该成员在本群组内发起的活动数量, 版本2将实现 */
	private int actionCount=0;
	
	/** 发送邀请的用户标识, 可能为 null */
	private Integer inviterId;
	
	/** 单位 */
	private String teacherUnit;

	/** 专业职务 */
	private String teacherZYZW;

	/** 学历 */
	private String teacherXL;

	/** 学位 */
	private String teacherXW;
	
	/** 研究专长 */
	private String teacherYJZC;
	
	/** 映射对象 2009-5-6 增加 */
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * 
	 */
	public GroupMember() {
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupMember{id=" + this.id + 
				", group=" + this.groupId + 
				", user=" + this.userId +
				", status=" + this.status + "}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if ((o instanceof GroupMember)) return false;
		
		GroupMember other = (GroupMember)o;
		return (this.userId == other.userId && this.groupId == other.groupId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.userId, this.groupId);
	}
	
	/** 标识 */
	public int getId() {
		return this.id;
	}

	/** 标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 群组标识 */
	public int getGroupId() {
		return this.groupId;
	}

	/** 群组标识 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/** 成员标识 */
	public int getUserId() {
		return this.userId;
	}

	/** 成员标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 该成员在本群组内的状态： 0－正常可用状态；1－申请后待审核；2－待删除；3－锁定；4－邀请未回应。 */
	public int getStatus() {
		return this.status;
	}

	/** 该成员在本群组内的状态： 0－正常可用状态；1－申请后待审核；2－待删除；3－锁定；4－邀请未回应。 */
	public void setStatus(int status) {
		this.status = status;
	}

	/** 该成员在本群组中的角色 */
	public int getGroupRole() {
		return this.groupRole;
	}
	
	/** 该成员在本群组中的角色 */
	public void setGroupRole(int groupRole) {
		this.groupRole = groupRole;
	}

	/** 加入群组时间 */
	public Date getJoinDate() {
		return this.joinDate;
	}

	/** 加入群组时间 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/** 该成员在本群组内发表的博文数量 */
	public int getArticleCount() {
		return this.articleCount;
	}

	/** 该成员在本群组内发表的博文数量 */
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/** 该成员在本群组内发表的资源数量 */
	public int getResourceCount() {
		return resourceCount;
	}

	/** 该成员在本群组内发表的资源数量 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	/** 该成员在本群组内发表的网课数量, 版本2将实现 */
	public int getCourseCount() {
		return courseCount;
	}

	/** 该成员在本群组内发表的网课数量, 版本2将实现 */
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	/** 该成员在本群组内发表的主题数量 */
	public int getTopicCount() {
		return this.topicCount;
	}

	/** 该成员在本群组内发表的主题数量 */
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	/** 该成员在本群组内回复主题的数量 */
	public int getReplyCount() {
		return this.replyCount;
	}

	/** 该成员在本群组内回复主题的数量 */
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	/** 该成员在本群组内发起的活动数量, 版本2将实现 */
	public int getActionCount() {
		return this.actionCount;
	}

	/** 该成员在本群组内发起的活动数量, 版本2将实现 */
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	/** 发送邀请的用户标识, 可能为 null */
	public Integer getInviterId() {
		return this.inviterId;
	}

	/** 发送邀请的用户标识, 可能为 null */
	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}
	
	public String getTeacherUnit() {
		return teacherUnit;
	}

	public void setTeacherUnit(String teacherUnit) {
		this.teacherUnit = teacherUnit;
	}

	public String getTeacherZYZW() {
		return teacherZYZW;
	}

	public void setTeacherZYZW(String teacherZYZW) {
		this.teacherZYZW = teacherZYZW;
	}

	public String getTeacherXL() {
		return teacherXL;
	}

	public void setTeacherXL(String teacherXL) {
		this.teacherXL = teacherXL;
	}

	public String getTeacherXW() {
		return teacherXW;
	}

	public void setTeacherXW(String teacherXW) {
		this.teacherXW = teacherXW;
	}

	public String getTeacherYJZC() {
		return teacherYJZC;
	}

	public void setTeacherYJZC(String teacherYJZC) {
		this.teacherYJZC = teacherYJZC;
	}	
}
