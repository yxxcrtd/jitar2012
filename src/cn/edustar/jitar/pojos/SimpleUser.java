package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;


public class SimpleUser  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1308851374703611668L;
	private String accountId;
	private int articleCommentCount;
	private int articleCount;
	private int ArticleICommentCount;
	private int articlePunishScore=0;
	private int articleScore=0;
	private String blogIntroduce;
	private String blogName ;
	private Integer categoryId;
	private int commentCount;
	private int commentPunishScore=0;	
	private int commentScore=0;
	private int courseCount;
	private Date createDate ;
	private int createGroupCount;
	private String email;
	private short gender = 1;
	private Integer gradeId;
	private String idCard ;
	private int jionGroupCount;
	private String loginName;
	private int myArticleCount;
	private String nickName ;
	private int otherArticleCount;	
	private int photoCount;
	private int photoPunishScore=0;
	private int photoScore = 0;
	private int positionId;
	private int prepareCourseCount;
	private int pushState = 0;
	private Integer pushUserId;
	private String qq ;
	private int recommendArticleCount;
	private int recommendResourceCount;
	private int resourceCommentCount;
	private int resourceCount;
	private int resourceDownloadCount;
	private int resourceICommentCount;
	private int resourcePunishScore=0;
	private int resourceScore=0;
	private Integer subjectId;	
	private int topicCount;
	private String trueName ;
	private Integer unitId;
	private String unitPathInfo;
	private int usedFileSize;
	private String userFileFolder;
	private int userGroupId;	
	private String userGuid;
	private String userIcon ;
	private int userId;
	private int userScore;
	private int userStatus;
	private String userTags;
	private String userType;
	private int usn;
	private int videoCount;
	private int videoPunishScore = 0;
	private int videoScore = 0;
	private String virtualDirectory;
	private int visitArticleCount;
	private int visitCount;	
	private int visitResourceCount;

	public String getAccountId() {
		return accountId;
	}
	/** 我的文章被评论数 */
	public int getArticleCommentCount() {
		return articleCommentCount;
	}
	
	public int getArticleCount() {
		return this.articleCount;
	}

	/** 我评论别人的文章数 */
	public int getArticleICommentCount() {
		return ArticleICommentCount;
	}
	/** 文章罚分 */
	public int getArticlePunishScore() {
		return articlePunishScore;
	}

	/** 文章积分 */
	public int getArticleScore() {
		return articleScore;
	}

	/** 个人博客介绍 */
	public String getBlogIntroduce() {
		return this.blogIntroduce;
	}

	/** 博客名称，注册时缺省取值 '用户昵称' + 的博客 */
	public String getBlogName() {
		return this.blogName;
	}


	/** 工作室分类 */
	public Integer getCategoryId() {
		return this.categoryId;
	}

	// Property accessors

	/** 该用户发表的评论数量，这是一个统计数字. */
	public int getCommentCount() {
		return this.commentCount;
	}

	/** 评论罚分 */
	public int getCommentPunishScore() {
		return commentPunishScore;
	}

	/** 评论积分 */
	public int getCommentScore() {
		return commentScore;
	}

	/** 上传的网课数量. */
	public int getCourseCount() {
		return this.courseCount;
	}


	/** 注册日期，缺省为当前时间. */
	public Date getCreateDate() {
		return createDate;
	}


	/** 创建协作组数 */
	public int getCreateGroupCount() {
		return createGroupCount;
	}

	/** 用户的电子邮件，需要符合电子邮件格式. 电子邮件是否唯一可以配置. */
	public String getEmail() {
		return this.email;
	}

	/** 用户性别：0 - 女，1 - 男，3 - 未知或未填 */
	public short getGender() {
		return this.gender;
	}

	/** 学段Id */
	public Integer getGradeId() {
		return gradeId;
	}

	/** 用户身份证 */
	public String getIdCard() {
		return this.idCard;
	}

	/** 加入协作组数 */
	public int getJionGroupCount() {
		return jionGroupCount;
	}


	public String getLoginName() {
		return this.loginName;
	}

	/** 原创文章数 */
	public int getMyArticleCount() {
		return myArticleCount;
	}

	/** 用户昵称，用于在界面上用户显示该用户，长度限制在1-20位长度之间（长度可配置） */
	public String getNickName() {
		return this.nickName;
	}

	/** 转载文章数 */
	public int getOtherArticleCount() {
		return otherArticleCount;
	}

	/** 发表的图片数量. */
	public int getPhotoCount() {
		return this.photoCount;
	}

	/** 图片罚分 */
	public int getPhotoPunishScore() {
		return photoPunishScore;
	}
	public int getPhotoScore() {
		return photoScore;
	}
	/** 角色ID： 0-49 为教师，50-99 为教育行政人员, 100-199 为学生, 200 其他 */
	public int getPositionId() {
		return this.positionId;
	}

	public int getPrepareCourseCount() {
		return this.prepareCourseCount;
	}

	public int getPushState() {
		return pushState;
	}

	public Integer getPushUserId() {
		return pushUserId;
	}

	/** 用户QQ */
	public String getQq() {
		return this.qq;
	}

	/** 推荐的文章数 */
	public int getRecommendArticleCount() {
		return recommendArticleCount;
	}

	/** 推荐的资源数 */
	public int getRecommendResourceCount() {
		return recommendResourceCount;
	}

	/** 我的资源被评论数 */
	public int getResourceCommentCount() {
		return resourceCommentCount;
	}

	/** 上传的资源总数量, 不包含删除和未审核的 */
	public int getResourceCount() {
		return this.resourceCount;
	}

	/** 资源下载数 */
	public int getResourceDownloadCount() {
		return resourceDownloadCount;
	}

	/** 我评论别人的资源数 */
	public int getResourceICommentCount() {
		return resourceICommentCount;
	}

	/** 资源罚分 */
	public int getResourcePunishScore() {
		return resourcePunishScore;
	}

	/** 资源积分 */
	public int getResourceScore() {
		return resourceScore;
	}

	/** 学科Id */
	public Integer getSubjectId() {
		return this.subjectId;
	}

	/** 发表的主题数量. */
	public int getTopicCount() {
		return this.topicCount;
	}

	/** 用户真实姓名，考虑到国际和少数民族，不必只限定为中文 */
	public String getTrueName() {
		return this.trueName;
	}

	/** 用户所在的机构, 依据配置或使用用户不同，该值可能有不同业务需要. */
	public Integer getUnitId() {
		return this.unitId;
	}

	public String getUnitPathInfo() {
		return unitPathInfo;
	}

	public int getUsedFileSize() {
		return usedFileSize;
	}

	public String getUserFileFolder() {
		return this.userFileFolder;
	}

	/** 用户所在的用户组，用户组进行该组用户的统一设置 */
	public int getUserGroupId() {
		return this.userGroupId;
	}

	/** 用户全局唯一标识，是一个 Guid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public String getUserGuid() {
		return this.userGuid;
	}

	/**
	 * 用户头像, 如果上传到本地, 则使用相对路径如 'images/headImg/yang_333.gif', 如果使用外网地址, 则必须如
	 * 'http://xxx' 格式
	 */
	public String getUserIcon() {
		return this.userIcon;
	}

	/** 用户标识，数据库自动生成. */
	public int getUserId() {
		return this.userId;
	}

	/** 用户积分:积分规则待定. */
	public Integer getUserScore() {
		return userScore;  
	}

	/** 用户状态, 0 - 正常；1 - 待审核； 2 - 待删除； 3 - 已锁定 */
	public int getUserStatus() {
		return this.userStatus;
	}

	/** 用户标签 */
	public String getUserTags() {
		return this.userTags;
	}

	public String getUserType() {
		return userType;
	}

	/** 组策略属性的更新序列号 Update Serial Number */
	public int getUsn() {
		return usn;
	}

	public int getVideoCount() {
		return videoCount;
	}
	
	public int getVideoPunishScore() {
		return videoPunishScore;
	}

	public int getVideoScore() {
		return videoScore;
	}

	public String getVirtualDirectory() {
		return this.virtualDirectory;
	}

	/** 文章访问量 */
	public int getVisitArticleCount() {
		return visitArticleCount;
	}

	/** 该用户博客站点总浏览量 */
	public int getVisitCount() {
		return this.visitCount;
	}

	/** 资源访问量 */
	public int getVisitResourceCount() {
		return visitResourceCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.loginName);
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/** 我的文章被评论数 */
	public void setArticleCommentCount(int articleCommentCount) {
		this.articleCommentCount = articleCommentCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/** 我评论别人的文章数 */
	public void setArticleICommentCount(int articleICommentCount) {
		ArticleICommentCount = articleICommentCount;
	}

	public void setArticlePunishScore(int articlePunishScore) {
		this.articlePunishScore = articlePunishScore;
	}

	public void setArticleScore(int articleScore) {
		this.articleScore = articleScore;
	}

	/** 个人博客介绍 */
	public void setBlogIntroduce(String blogIntroduce) {
		this.blogIntroduce = blogIntroduce;
	}

	/** 博客名称，注册时缺省取值 '用户昵称' + 的博客 */
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	/** 工作室分类 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/** 该用户发表的评论数量，这是一个统计数字. */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public void setCommentPunishScore(int commentPunishScore) {
		this.commentPunishScore = commentPunishScore;
	}

	public void setCommentScore(int commentScore) {
		this.commentScore = commentScore;
	}

	/** 上传的网课数量. */
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	/** 注册日期，缺省为当前时间. */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 创建协作组数 */
	public void setCreateGroupCount(int createGroupCount) {
		this.createGroupCount = createGroupCount;
	}

	/** 用户的电子邮件，需要符合电子邮件格式. 电子邮件是否唯一可以配置.  */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 用户性别：0 - 女，1 - 男，3 - 未知或未填 */
	public void setGender(short gender) {
		this.gender = gender;
	}	
	
	/** 学段Id */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	/** 用户身份证 */
	public void setIdCard(String idCard) {
		this.idCard = idCard == null ? "" : idCard;
	}	
		
	/** 加入协作组数 */
	public void setJionGroupCount(int jionGroupCount) {
		this.jionGroupCount = jionGroupCount;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 原创文章数 */
	public void setMyArticleCount(int myArticleCount) {
		this.myArticleCount = myArticleCount;
	}

	/** 用户昵称，用于在界面上用户显示该用户，长度限制在1-20位长度之间（长度可配置） */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/** 转载文章数 */
	public void setOtherArticleCount(int otherArticleCount) {
		this.otherArticleCount = otherArticleCount;
	}
	
	/** 发表的图片数量. */
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}
	
	public void setPhotoPunishScore(int photoPunishScore) {
		this.photoPunishScore = photoPunishScore;
	}
	
	public void setPhotoScore(int photoScore) {
		this.photoScore = photoScore;
	}

	/** 角色ID： 0-49 为教师，50-99 为教育行政人员, 100-199 为学生, 200 其他 */
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public void setPrepareCourseCount(int prepareCourseCount) {
		this.prepareCourseCount = prepareCourseCount;
	}

	public void setPushState(int pushState) {
		this.pushState = pushState;
	}

	public void setPushUserId(Integer pushUserId) {
		this.pushUserId = pushUserId;
	}

	/** 用户QQ */
	public void setQq(String qq) {
		this.qq = qq == null ? "" : qq;
	}

	/** 推荐的文章数 */
	public void setRecommendArticleCount(int recommendArticleCount) {
		this.recommendArticleCount = recommendArticleCount;
	}

	/** 推荐的资源数 */
	public void setRecommendResourceCount(int recommendResourceCount) {
		this.recommendResourceCount = recommendResourceCount;
	}
	/** 我的资源被评论数 */
	public void setResourceCommentCount(int resourceCommentCount) {
		this.resourceCommentCount = resourceCommentCount;
	}

	/** 上传的资源总数量, 不包含删除和未审核的 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	/** 资源下载数 */
	public void setResourceDownloadCount(int resourceDownloadCount) {
		this.resourceDownloadCount = resourceDownloadCount;
	}
	
	/** 我评论别人的资源数 */
	public void setResourceICommentCount(int resourceICommentCount) {
		this.resourceICommentCount = resourceICommentCount;
	}

	public void setResourcePunishScore(int resourcePunishScore) {
		this.resourcePunishScore = resourcePunishScore;
	}
	
	public void setResourceScore(int resourceScore) {
		this.resourceScore = resourceScore;
	}

	/** 学科Id */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	/** 发表的主题数量. */
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	/** 用户真实姓名，考虑到国际和少数民族，不必只限定为中文 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/** 用户所在的机构, 依据配置或使用用户不同，该值可能有不同业务需要. */
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public void setUnitPathInfo(String unitPathInfo) {
		if(unitPathInfo == null) unitPathInfo = "";
		this.unitPathInfo = unitPathInfo;
	}
	
	public void setUsedFileSize(int usedFileSize) {
		this.usedFileSize = usedFileSize;
	}
	public void setUserFileFolder(String userFileFolder) {
		this.userFileFolder = userFileFolder;
	}

	/** 用户所在的用户组，用户组进行该组用户的统一设置 */
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}
	/** 用户全局唯一标识，是一个 Guid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	/**
	 * 用户头像, 如果上传到本地, 则使用相对路径如 'images/headImg/yang_333.gif', 如果使用外网地址, 则必须如
	 * 'http://xxx' 格式
	 */
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	/** 用户标识，数据库自动生成. */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/** 用户积分:积分规则待定. 不使用set方法*/
	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	/** 用户状态, 0 - 正常；1 - 待审核； 2 - 待删除； 3 - 已锁定 */
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	/** 用户标签 */
	public void setUserTags(String userTags) {
		this.userTags = userTags == null ? "" : userTags;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/** 组策略属性的更新序列号 Update Serial Number */
	public void setUsn(int usn) {
		this.usn = usn;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public void setVideoPunishScore(int videoPunishScore) {
		this.videoPunishScore = videoPunishScore;
	}

	public void setVideoScore(int videoScore) {
		this.videoScore = videoScore;
	}
	public void setVirtualDirectory(String virtualDirectory) {
		this.virtualDirectory = virtualDirectory;
	}

	/** 文章访问量 */
	public void setVisitArticleCount(int visitArticleCount) {
		this.visitArticleCount = visitArticleCount;
	}	
	/** 该用户博客站点总浏览量 */
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	/** 资源访问量 */
	public void setVisitResourceCount(int visitResourceCount) {
		this.visitResourceCount = visitResourceCount;
	}	
}
