package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 群组/协作组
 * 
 *
 * @version 1.0.0 Mar 4, 2009 11:05:56 AM
 * @remark groupName 必须不重复，用作业务键，数据库中有约束；groupTags必须以规格方式保存，并且和Tag表保持一致，使用TagService进行操作
 */
public class Group implements Serializable, Cloneable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -6046737686247387580L;
	
	/** 群组状态：0 - 正常 */
	public static final short GROUP_STATE_NORMAL = 0;
	/** 群组状态：1 - 待审核, 不可访问, 不可向其发布内容, 不可加入新成员*/
	public static final short GROUP_STATE_WAIT_AUDIT = 1;
	/** 群组状态：2 - 锁定, 可访问, 不可向其发布内容, 不可加入新成员. */
	public static final short GROUP_STATE_LOCKED = 2;
	/** 群组状态：3 - 已删除, 不可访问, 不可向其发布内容, 不可加入新成员. */
	public static final short GROUP_STATE_DELETED = 3;
	/** 群组状态：4 - 隐藏, 成员可访问, 成员可向其发布内容, 可以邀请加入成员, 主页等处不显示. */
	public static final short GROUP_STATE_HIDED = 4;
	
	/** 群组标识. */
	private int groupId;
	
	/** 群组全局唯一标识，是一个 Uuid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	private String groupGuid = java.util.UUID.randomUUID().toString().toUpperCase();
	
	/** 
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母)，不能重复。URL写成 /Groups/g/$groupname 即可访问到该群组，等同于 /Groups/group.jsp?id=$groupid 
	 */
	private String groupName;
	
	/** 群组中文名称，中文限定在1-50长度之间 */
	private String groupTitle = "";
	
	/** 群组所属分类，为NULL表示未设定该群组所属分类. 系统配置可以强制要求选择一个分类. */
	private Integer categoryId;
	
	/** 群组创建时间 */
	private Date createDate = new Date();
	
	/** 群组创建者标识 */
	private int createUserId;
	
	/** 群组头像，存放(位置待定)，允许使用站外URL */
	private String groupIcon;
	
	/** 此群组的所有标签，以 ',' 分隔的标准格式保存 */
	private String groupTags;
	
	/** 群组介绍 */
	private String groupIntroduce;
	
	/** 申请说明 */
	private String requisition;
	
	/** 是否是优秀团队，false - 普通，true - 优秀团队. */
	private boolean isBestGroup;
	
	/** 是否推荐协作组, false - 普通, true - 推荐协作组. */
	private boolean isRecommend;
	
	/** 群组状态：0 - 正常，1 - 待审核，2 - 锁定，3 - 待删除，4 - 隐藏 (使用 GROUP_STATE_XXX 常量) */
	private short groupState;
	
	/** 该群组加入的用户数 */
	private int userCount;
	
	/** 该群组的文章数 */
	private int articleCount;
	
	/** 该群组发表的主题（话题）数 */
	private int topicCount;
	
	/** 该群组发表的主题(话题)数的所有'讨论数' */
	private int discussCount;
	
	/** 本群组举行的活动数 */
	private int actionCount;
	
	/** 该群组的资源数 */
	private int resourceCount;
	
	/** 该群组的课程数 */
	private int courseCount;
	
	/** 该群组访问数(浏览量) */
	private int visitCount;

	/** 加入该群组所需要的条件：0 - 任意加入 */
	public static final int JOIN_LIMIT_NOLIMIT = 0;
	/** 加入该群组所需要的条件：1 - 加入需审核 */
	public static final int JOIN_LIMIT_NEEDAUTID = 1;
	/** 加入该群组所需要的条件：2 - 只能邀请 */
	public static final int JOIN_LIMIT_ONLYINVITE = 2;
	
	/** 加入该群组所需要的条件： 0－任意加入，1－加入需审核，2－只能邀请，其它含义待定 */
	private int joinLimit;
	
	/** 如果需要积分才能加入，则设置需要的积分数N */
	private int joinScore;

	/** 群组对象扩展属性，保存为 JSON 格式 */
	private String attributes;

	/** 群组所属学科 */
	private Integer subjectId;

	/** 群组所属学段 */
	private Integer gradeId;
	

	/** 精华文章数量 */
	private Integer bestArticleCount = 0;

	/** 精华资源数量 */
	private Integer bestResourceCount = 0;
	
	/** 推送状态：1，已经推送；2：待推送；0：没有设置 */	
	private int pushState = 0;	
	private Integer pushUserId;

	/**课题组立项号*/
	private String ktNo;
	/**课题组课题级别*/
	private String ktLevel;
	/**课题开始时间*/
	private Date ktStartDate;
	/**课题结束时间*/
	private Date ktEndDate;
	/**父Id*/
	private int parentId=0;
	
	private int photoCount=0;
	
	private int videoCount=0;
	
	/*学科学段 gradeid/subjectid  ,多个之间用逗号分开*/
	private String XKXDId="";
	/** 学科学段 grade/subject  ,多个之间用逗号分开*/
	private String XKXDName="";	
	
	public String getXKXDId() {
		return XKXDId;
	}

	public String GetXKXDIdEx() {
		//得到的时候，去掉前后逗号?
		String ids= XKXDId;
		if(ids!=null){
			if(ids.length()>0){
				if(ids.startsWith(",")){
					ids=ids.substring(1);
				}
				if(ids.endsWith(",")){
					ids=ids.substring(0,ids.length()-1);
				}
			}		
		}
		return ids;
	}
	
	public void setXKXDId(String xKXDId) {
		//设置学科学段ID的时候，前后加上逗号 ，为了方便查询
		if(xKXDId!=null){
			if(xKXDId.length()>0){
				if(!xKXDId.startsWith(",")){
					xKXDId=","+xKXDId;
				}
				if(!xKXDId.endsWith(",")){
					xKXDId=xKXDId+",";
				}
			}
		}
		this.XKXDId = xKXDId;
	}

	public String getXKXDName() {
		return XKXDName;
	}
	
	public String GetXKXDNameEx() {
		//得到的时候，再去掉前后逗号?
		String names=XKXDName;
		if(names!=null){
			if(names.length()>0){
				if(names.startsWith(",")){
					names=names.substring(1);
				}
				if(names.endsWith(",")){
					names=names.substring(0,names.length()-1);
				}
			}
		}
		return names;
	}
	
	public void setXKXDName(String xKXDName) {
		this.XKXDName = xKXDName;
	}

	
	public int getPushState() {
		return pushState;
	}
	
	

	public void setPushState(int pushState) {
		this.pushState = pushState;
	}

	public Integer getPushUserId() {
		return pushUserId;
	}

	public void setPushUserId(Integer pushUserId) {
		this.pushUserId = pushUserId;
	}

	/**
	 * 构造
	 */
	public Group() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Group{id=" + this.groupId + ", title=" + this.groupTitle + ", name=" + this.groupName + "}";
	}

	/**
	 * 得到显示用字符串
	 * 
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.groupTitle) + "'(id=" + this.groupId + ")";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Group)) return false;
		Group other = (Group)o;
		if (PojoHelper.equals(this.groupName, other.groupName))
			return true;
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return PojoHelper.hashCode(this.groupName);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Group clone() throws CloneNotSupportedException {
		return (Group)super.clone();
	}
	
	/**
	 * 返回自己, 兼容 GroupModel 中的方法用
	 * 
	 * @return
	 */
	public Group _getGroupObject() {
		return this;
	}
	
	/** 群组标识. */
	public int getGroupId() {
		return this.groupId;
	}

	/** 群组标识. */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/** 群组全局唯一标识，是一个 Uuid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public String getGroupGuid() {
		return this.groupGuid;
	}
	
	/** 群组全局唯一标识，是一个 Uuid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	
	/** 
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母), 不能重复.	 * URL写成 /Groups/g/$groupname 即可访问到该群组, 
	 * 等同于 /Groups/group.jsp?id=$groupid 
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/** 
	 * 群组英文名称，只能使用字母和数字（首字母必须是字母), 不能重复.	 * URL写成 /Groups/g/$groupname 即可访问到该群组, 
	 * 等同于 /Groups/group.jsp?id=$groupid 
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/** 群组中文名称，中文限定在1-50长度之间 */
	public String getGroupTitle() {
		return this.groupTitle;
	}

	/** 群组中文名称，中文限定在1-50长度之间 */
	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	/** 群组所属分类，为NULL表示未设定该群组所属分类. 系统配置可以强制要求选择一个分类. */
	public Integer getCategoryId() {
		return this.categoryId;
	}

	/** 群组所属分类，为NULL表示未设定该群组所属分类. 系统配置可以强制要求选择一个分类. */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/** 群组创建时间 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 群组创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 群组创建者标识 */
	public int getCreateUserId() {
		return this.createUserId;
	}

	/** 群组创建者标识 */
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	/** 群组头像，存放(位置待定)，允许使用站外URL */
	public String getGroupIcon() {
		return this.groupIcon;
	}

	/** 群组头像，存放(位置待定)，允许使用站外URL */
	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	/** 此群组的所有标签，以 ',' 分隔的标准格式保存 */
	public String getGroupTags() {
		return this.groupTags;
	}

	/** 此群组的所有标签，以 ',' 分隔的标准格式保存 */
	public void setGroupTags(String groupTags) {
		this.groupTags = groupTags;
	}

	/** 群组介绍 */
	public String getGroupIntroduce() {
		return this.groupIntroduce;
	}

	/** 群组介绍 */
	public void setGroupIntroduce(String groupIntroduce) {
		this.groupIntroduce = groupIntroduce;
	}

	/** 申请说明 */
	public String getRequisition() {
		return this.requisition;
	}
	
	/** 申请说明 */
	public void setRequisition(String requisition) {
		this.requisition = requisition;
	}
	
	/** 是否是优秀团队，false - 普通，true - 优秀团队. */
	public boolean getIsBestGroup() {
		return this.isBestGroup;
	}

	/** 是否是优秀团队，false - 普通，true - 优秀团队. */
	public void setIsBestGroup(boolean isBestGroup) {
		this.isBestGroup = isBestGroup;
	}
	
	/** 是否推荐协作组, false - 普通, true - 推荐协作组. */
	public boolean getIsRecommend() {
		return this.isRecommend;
	}
	
	/** 是否推荐协作组, false - 普通, true - 推荐协作组. */
	public void setIsRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	/** 群组状态：0正常，1待审核，2锁定，3待删除，4隐藏 (使用 GROUP_STATE_XXX 常量) */
	public short getGroupState() {
		return this.groupState;
	}

	/** 群组状态：0正常，1待审核，2锁定，3待删除，4隐藏 (使用 GROUP_STATE_XXX 常量) */
	public void setGroupState(short groupState) {
		this.groupState = groupState;
	}

	/** 该群组加入的用户数 */
	public int getUserCount() {
		return this.userCount;
	}

	/** 该群组加入的用户数 */
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
	/** 该群组的文章数 */
	public int getArticleCount() {
		return this.articleCount;
	}
	
	/** 该群组的文章数 */
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/** 该群组发表的主题(话题)数 */
	public int getTopicCount() {
		return this.topicCount;
	}

	/** 该群组发表的主题(话题)数 */
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	/** 该群组发表的主题(话题)数的所有'讨论数' */
	public int getDiscussCount() {
		return discussCount;
	}

	/** 该群组发表的主题(话题)数的所有'讨论数' */
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}

	/** 本群组举行的活动数 */
	public int getActionCount() {
		return this.actionCount;
	}

	/** 本群组举行的活动数 */
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	/** 该群组的资源数 */
	public int getResourceCount() {
		return this.resourceCount;
	}
	
	/** 该群组的资源数 */
	public void setResourceCount(int resourceCount) {		this.resourceCount = resourceCount;
	}
	
	/** 该群组的课程数 */
	public int getCourseCount() {
		return this.courseCount;
	}
	
	/** 该群组的课程数 */
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	/** 该群组访问数(浏览量) */
	public int getVisitCount() {
		return this.visitCount;
	}

	/** 该群组访问数(浏览量) */
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	/** 加入该群组所需要的条件： 0－任意加入，1－加入需审核，2－只能邀请，其它含义待定 */
	public int getJoinLimit() {
		return this.joinLimit;
	}

	/** 加入该群组所需要的条件：0 - 任意加入, 1 - 加入需审核, 2 - 只能邀请, 其它含义待定 */
	public void setJoinLimit(int joinLimit) {
		this.joinLimit = joinLimit;
	}

	/** 如果需要积分才能加入，则设置需要的积分数N */
	public int getJoinScore() {
		return this.joinScore;
	}

	/** 如果需要积分才能加入，则设置需要的积分数N */
	public void setJoinScore(int joinScore) {
		this.joinScore = joinScore;
	}

	/** 群组对象扩展属性，保存为 JSON 格式 */
	public String getAttributes() {
		return this.attributes;
	}
	
	/** 群组对象扩展属性，保存为 JSON 格式 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	/** 群组所属学科 */
	public Integer getSubjectId() {
		return this.subjectId;
	}

	/** 群组所属学科 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	/** 群组所属学段 */
	public Integer getGradeId() {
		return gradeId;
	}

	/** 群组所属学段 */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Subject subject;
	Subject getSubject() {
		return this.subject;
	}
	void setSubject(Subject subject) {
		// this.subject = subject;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Grade grade;
	Grade getGrade() {
		return this.grade;
	}
	void setGrade(Grade grade) {
		// this.grade = grade;
	}
		
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category sysCate;
	Category getSysCate() {
		return this.sysCate;
	}
	void setSysCate(Category sysCate) {
		// this.sysCate = sysCate;
	}

	public Integer getBestArticleCount() {
		return bestArticleCount;
	}

	public void setBestArticleCount(Integer bestArticleCount) {
		this.bestArticleCount = bestArticleCount;
	}

	public Integer getBestResourceCount() {
		return bestResourceCount;
	}

	public void setBestResourceCount(Integer bestResourceCount) {
		this.bestResourceCount = bestResourceCount;
	}


	
	public String getKtNo() {
		return ktNo;
	}



	public void setKtNo(String ktNo) {
		this.ktNo = ktNo;
	}



	public String getKtLevel() {
		return ktLevel;
	}



	public void setKtLevel(String ktLevel) {
		this.ktLevel = ktLevel;
	}



	public Date getKtStartDate() {
		return ktStartDate;
	}



	public void setKtStartDate(Date ktStartDate) {
		this.ktStartDate = ktStartDate;
	}



	public Date getKtEndDate() {
		return ktEndDate;
	}



	public void setKtEndDate(Date ktEndDate) {
		this.ktEndDate = ktEndDate;
	}



	public int getParentId() {
		return parentId;
	}



	public void setParentId(int parentId) {
		this.parentId = parentId;
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
