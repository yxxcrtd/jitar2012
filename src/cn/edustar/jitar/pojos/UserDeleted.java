package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.usermgr.UserConst;

/**
 * UserDeleted
 * 
 * 已经被删除的用户
 *
 */
public class UserDeleted implements Serializable, Cloneable, UserConst {
	
	private static final long serialVersionUID = 8264674969867084137L;

	/** 用户缺省头像 */
	public static final String DEFAULT_USER_ICON = "images/default.gif";

	
	/** 用户标识，数据库自动生成. */
	private int Id;
	
	
	private int userId;

	/** 用户全局唯一标识，是一个 Uuid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	private String userGuid = UUID.randomUUID().toString().toUpperCase();

	/**
	 * 用户登录名，限制条件：只能是数字、字母的组合, 第一位必须是字母, 长度在3-50位之间,
	 * 不区分大小写，不能是系统名（需要从配置文件或者配置数据库读取），如www,admin,system,system32等.
	 */
	private String loginName;

	/** 用户真实姓名，考虑到国际和少数民族，不必只限定为中文 */
	private String trueName = "";

	/** 用户昵称，用于在界面上用户显示该用户，长度限制在1-20位长度之间（长度可配置） */
	private String nickName = "";

	/** 用户的电子邮件，需要符合电子邮件格式. 电子邮件是否唯一可以配置. */
	private String email = "";

	/** 注册日期，缺省为当前时间. */
	private Date createDate = new Date();

	/**
	 * 用户文件所在的虚拟目录，缺省 = 'u'，为了文件存储扩充使用，需要符合操作系统的文件夹规则,
	 * 如不能有\等特殊字母，为方便起见，现在只限定在字母范围内，且不分大小写.
	 */
	private String virtualDirectory = "u";

	/**
	 * 用户物理文件夹，包括静态文件、上载的文件等. 目录规则待定【按申请年月日; 按字母顺序或者由管理员自由配置等，这个路径会出现在用户的URL中】.
	 * 
	 * 博文等静态文件按年份放置，暂定规则：LoginName的第一个字母 '/LoginName/'
	 */
	private String userFileFolder = "";

	/** 用户性别：0 - 女，1 - 男，3 - 未知或未填 */
	private short gender = 1;

	/** 用户所在的机构, 依据配置或使用用户不同，该值可能有不同业务需要. */
	private Integer unitId;	
	private String unitPathInfo;
	
	/** 用户头衔字段，如名师、教研员、推荐工作室、学科带头人等 */
	private String userType;

	/** 工作室名称，注册时缺省取值 '用户昵称' + 的工作室 */
	private String blogName = "";

	/** 工作室介绍 */
	private String blogIntroduce = "";

	/** 用户状态, 
	 * 0 - 正常；
	 * 1 - 待审核； 
	 * 2 - 待删除； 
	 * 3 - 已锁定 */
	private int userStatus = 0;

	/** 用户所在的用户组，用户组进行该组用户的统一设置 */
	private int userGroupId;

	/** 该用户博客站点总浏览量 */
	private int visitCount;

	/** 文章访问量 */
	private int visitArticleCount;

	/** 资源访问量 */
	private int visitResourceCount;

	/** 原创文章数 */
	private int myArticleCount;

	private int articleCount;

	/** 转载文章数 */
	private int otherArticleCount;
	
	/** 推荐的文章数 */
	private int recommendArticleCount;
	
	/** 我的文章被评论数 */
	private int articleCommentCount;
	
	/** 我评论的文章数 */
	private int ArticleICommentCount;

	/** 上传的资源总数量, 不包含删除和未审核的 */
	private int resourceCount;
	
	/** 推荐的资源数 */
	private int recommendResourceCount;
	
	/** 我的资源被评论数 */
	private int resourceCommentCount;
	
	/** 我评论的资源数 */
	private int resourceICommentCount;
	
	/** 资源下载数 */
	private int resourceDownloadCount;
	
	/** 创建协作组数 */
	private int createGroupCount;
	
	/** 加入协作组数 */
	private int jionGroupCount;

	/** 发表的图片数量. */
	private int photoCount;

	/** 视频数 */
	private int videoCount;

	/** 上传的网课数量. */
	private int courseCount;

	/** 发表的主题数量. */
	private int topicCount;

	/** 该用户发表的评论数量，这是一个统计数字. */
	private int commentCount;
	
	private int usedFileSize;

	/**用户的主备数*/
	private int prepareCourseCount;
	
	private int historyMyArticleCount;
	private int historyOtherArticleCount;
	
	/** 对 Guid 加密后的 EncryptedGuid */	
	//private String encryptedGuid;
	
	/**浙大统一用户ID**/
	private String accountId;
	
	/**
	 * 用户头像, 如果上传到本地, 则使用相对路径如 'images/headImg/yang_333.gif', 如果使用外网地址, 
	 * 则必须如 'http://domain/...' 格式.
	 */
	private String userIcon = DEFAULT_USER_ICON;

	/** 用户积分:积分规则待定. */
	private int userScore;

	/** 工作室分类 */
	private Integer categoryId;

	/** 角色常量 = 0 缺省 教师 */
	public static final int ROLE_TEACHER = 0;
	/** 角色常量 = 1 教研员 */
	public static final int ROLE_JYY = 1;
	/** 角色常量 = 50 教育行政人员 */
	public static final int ROLE_GOVERNMENT = 50;
	/** 角色常量 = 100 学生 */
	public static final int ROLE_STUDENT = 100;
	/** 角色常量 = 200 其他 */
	public static final int ROLE_OTHER = 200;

	/** 角色ID： 0-49 为教师，50-99 为教育行政人员, 100-199 为学生, 200 其他 */
	private int positionId;

	/** 组策略属性的更新序列号 Update Serial Number */
	private int usn;

	/** 学科Id */
	private Integer subjectId;

	/** 学段Id */
	private Integer gradeId;
	
	/** 用户标签 */
	private String userTags = "";
	
	/** 用户身份证 */
	private String idCard = "";
	/** 用户QQ */
	private String qq = "";
	
	/**资源,文章,评论积分 */
	private int articleScore=0;
	private int resourceScore=0;
	private int photoScore = 0;
	private int videoScore = 0;
	private int commentScore=0;

	/**资源,文章,评论罚分 */
	private int articlePunishScore=0;
	private int resourcePunishScore=0;
	private int commentPunishScore=0;
	private int photoPunishScore=0;
	private int videoPunishScore = 0;

	/** 推送状态：1，已经推送；2：待推送；0：没有设置 */	
	private int pushState = 0;	
	private Integer pushUserId;
	
	/** 用户的版本号码，默认为1，每修改一次加1，可以和用户服务器的用户Version对比，以确定是否更新 */
	private int version = 1;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public int getUsedFileSize() {
		return usedFileSize;
	}

	public void setUsedFileSize(int usedFileSize) {
		this.usedFileSize = usedFileSize;
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

	// Constructors
	public UserDeleted() {
		// 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "User{id=" + this.userId + ",loginName=" + this.loginName + "}";
	}
	
	/**
	 * 得到显示在 log, msg 中的用户表示.
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.nickName) + "(id=" + this.userId + ",login=" + this.loginName + ")'";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.loginName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || !(o instanceof UserDeleted))
			return false;

		return PojoHelper.equals(this.loginName, ((UserDeleted) o).loginName);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public UserDeleted clone() throws CloneNotSupportedException  {
		return (UserDeleted)super.clone();
	}
	
	/**
	 * 得到原始用户 POJO 对象，一般用于程序内部非模板获取用户属性.
	 * 为了兼容 UserModel 中同名方法, 当然自己返回自己即可.
	 * @return
	 */
	public UserDeleted _getUserObject() {
		return this;
	}

	public int getId() {
		return this.Id;
	}

	/** 用户标识，数据库自动生成. */
	public void setId(int Id) {
		this.Id = Id;
	}
	
	/** 用户标识，数据库自动生成. */
	public int getUserId() {
		return this.userId;
	}

	/** 用户标识，数据库自动生成. */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 用户全局唯一标识，是一个 Guid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public String getUserGuid() {
		return this.userGuid;
	}

	/** 用户全局唯一标识，是一个 Guid 表示的字符串，如 'F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3' */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	/**
	 * 用户登录名，限制条件：只能是数字、字母的组合，第一位必须是字母，长度在3-50位之间，
	 * 不区分大小写，不能是系统名（需要从配置文件或者配置数据库读取），如www,admin,system,system32等
	 */
	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * 用户登录名，限制条件：只能是数字、字母的组合，第一位必须是字母，长度在3-50位之间，
	 * 不区分大小写，不能是系统名（需要从配置文件或者配置数据库读取），如www,admin,system,system32等
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 用户真实姓名，考虑到国际和少数民族，不必只限定为中文 */
	public String getTrueName() {
		return this.trueName;
	}

	/** 用户真实姓名，考虑到国际和少数民族，不必只限定为中文 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/** 用户昵称，用于在界面上用户显示该用户，长度限制在1-20位长度之间（长度可配置） */
	public String getNickName() {
		return this.nickName;
	}

	/** 用户昵称，用于在界面上用户显示该用户，长度限制在1-20位长度之间（长度可配置） */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/** 用户的电子邮件，需要符合电子邮件格式. 电子邮件是否唯一可以配置. */
	public String getEmail() {
		return this.email;
	}

	/** 用户的电子邮件，需要符合电子邮件格式. 电子邮件是否唯一可以配置.  */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 用户文件所在的虚拟目录，缺省 =
	 * 'u'，为了文件存储扩充使用，需要符合操作系统的文件夹规则，如不能有\等特殊字母，为方便起见，现在只限定在字母范围内，且不分大小写.
	 */
	public String getVirtualDirectory() {
		return this.virtualDirectory;
	}

	/**
	 * 用户文件所在的虚拟目录，缺省 =
	 * 'u'，为了文件存储扩充使用，需要符合操作系统的文件夹规则，如不能有\等特殊字母，为方便起见，现在只限定在字母范围内，且不分大小写.
	 */
	public void setVirtualDirectory(String virtualDirectory) {
		this.virtualDirectory = virtualDirectory;
	}

	/**
	 * 用户物理文件夹，包括静态文件、上载的文件等. 目录规则待定【按申请年月日；按字母顺序或者由管理员自由配置等，这个路径会出现在用户的URL中】
	 * 
	 * 博文等静态文件按年份放置，暂定规则：LoginName的第一个字母/LoginName/
	 */
	public String getUserFileFolder() {
		return this.userFileFolder;
	}

	/**
	 * 用户物理文件夹，包括静态文件、上载的文件等. 目录规则待定【按申请年月日；按字母顺序或者由管理员自由配置等，这个路径会出现在用户的URL中】
	 * 
	 * 博文等静态文件按年份放置，暂定规则：LoginName的第一个字母/LoginName/
	 */
	public void setUserFileFolder(String userFileFolder) {
		this.userFileFolder = userFileFolder;
	}

	/** 用户性别：0 - 女，1 - 男，3 - 未知或未填 */
	public short getGender() {
		return this.gender;
	}

	/** 用户性别：0 - 女，1 - 男，3 - 未知或未填 */
	public void setGender(short gender) {
		this.gender = gender;
	}

	/** 用户所在的机构, 依据配置或使用用户不同，该值可能有不同业务需要. */
	public Integer getUnitId() {
		return this.unitId;
	}

	/** 用户所在的机构, 依据配置或使用用户不同，该值可能有不同业务需要. */
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitPathInfo() {
		return unitPathInfo;
	}
	public void setUnitPathInfo(String unitPathInfo) {
		if(unitPathInfo == null) unitPathInfo = "";
		this.unitPathInfo = unitPathInfo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/** 博客名称，注册时缺省取值 '用户昵称' + 的博客 */
	public String getBlogName() {
		return this.blogName;
	}

	/** 博客名称，注册时缺省取值 '用户昵称' + 的博客 */
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	/** 个人博客介绍 */
	public String getBlogIntroduce() {
		return this.blogIntroduce;
	}

	/** 个人博客介绍 */
	public void setBlogIntroduce(String blogIntroduce) {
		this.blogIntroduce = blogIntroduce;
	}

	/** 用户状态, 0 - 正常；1 - 待审核； 2 - 待删除； 3 - 已锁定 */
	public int getUserStatus() {
		return this.userStatus;
	}

	/** 用户状态, 0 - 正常；1 - 待审核； 2 - 待删除； 3 - 已锁定 */
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	/** 用户所在的用户组，用户组进行该组用户的统一设置 */
	public int getUserGroupId() {
		return this.userGroupId;
	}

	/** 用户所在的用户组，用户组进行该组用户的统一设置 */
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	/** 该用户博客站点总浏览量 */
	public int getVisitCount() {
		return this.visitCount;
	}

	/** 该用户博客站点总浏览量 */
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	/** 原创文章数 */
	public int getMyArticleCount() {
		return myArticleCount;
	}

	/** 原创文章数 */
	public void setMyArticleCount(int myArticleCount) {
		this.myArticleCount = myArticleCount;
	}

	/** 转载文章数 */
	public int getOtherArticleCount() {
		return otherArticleCount;
	}

	/** 转载文章数 */
	public void setOtherArticleCount(int otherArticleCount) {
		this.otherArticleCount = otherArticleCount;
	}

	/** 上传的资源总数量, 不包含删除和未审核的 */
	public int getResourceCount() {
		return this.resourceCount;
	}

	/** 上传的资源总数量, 不包含删除和未审核的 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	/** 发表的图片数量. */
	public int getPhotoCount() {
		return this.photoCount;
	}

	/** 发表的图片数量. */
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	/** 上传的网课数量. */
	public int getCourseCount() {
		return this.courseCount;
	}

	/** 上传的网课数量. */
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	/** 该用户发表的评论数量，这是一个统计数字. */
	public int getCommentCount() {
		return this.commentCount;
	}

	/** 该用户发表的评论数量，这是一个统计数字. */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/** 发表的主题数量. */
	public int getTopicCount() {
		return this.topicCount;
	}

	/** 发表的主题数量. */
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	/**
	 * 用户头像, 如果上传到本地, 则使用相对路径如 'images/headImg/yang_333.gif', 如果使用外网地址, 则必须如
	 * 'http://xxx' 格式
	 */
	public String getUserIcon() {
		return this.userIcon;
	}

	/**
	 * 用户头像, 如果上传到本地, 则使用相对路径如 'images/headImg/yang_333.gif', 如果使用外网地址, 则必须如
	 * 'http://xxx' 格式
	 */
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	/** 用户积分:积分规则待定. */
	public Integer getUserScore() {
		return userScore;  //this.articleScore+this.resourceScore+this.commentScore + this.articlePunishScore + this.resourcePunishScore + this.commentPunishScore + this.photoPunishScore;
	}

	/** 用户积分:积分规则待定. 不使用set方法*/
	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}
	
	/** 工作室分类 */
	public Integer getCategoryId() {
		return this.categoryId;
	}

	/** 工作室分类 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/** 角色ID： 0-49 为教师，50-99 为教育行政人员, 100-199 为学生, 200 其他 */
	public int getPositionId() {
		return this.positionId;
	}

	/** 角色ID： 0-49 为教师，50-99 为教育行政人员, 100-199 为学生, 200 其他 */
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	/** 组策略属性的更新序列号 Update Serial Number */
	public int getUsn() {
		return usn;
	}

	/** 组策略属性的更新序列号 Update Serial Number */
	public void setUsn(int usn) {
		this.usn = usn;
	}

	/** 学科Id */
	public Integer getSubjectId() {
		return this.subjectId;
	}

	/** 学科Id */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	/** 学段Id */
	public Integer getGradeId() {
		return gradeId;
	}

	/** 学段Id */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	/** 注册日期，缺省为当前时间. */
	public Date getCreateDate() {
		return createDate;
	}

	/** 注册日期，缺省为当前时间. */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	private Category sysCate; // categoryId
	Category getSysCate() {
		return this.sysCate;
	}
	void setSysCate(Category sysCate) {
		// this.sysCate = sysCate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Grade grade; // categoryId
	Grade getGrade() {
		return this.grade;
	}
	void setGrade(Grade grade) {
		// this.sysCate = sysCate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Unit unit; // categoryId
	Unit getUnit() {
		return this.unit;
	}
	void setUnit(Unit unit) {
		// this.sysCate = sysCate;
	}

	/** 用户标签 */
	public String getUserTags() {
		return this.userTags;
	}

	/** 用户标签 */
	public void setUserTags(String userTags) {
		this.userTags = userTags == null ? "" : userTags;
	}

	/** 用户身份证 */
	public String getIdCard() {
		return this.idCard;
	}

	/** 用户身份证 */
	public void setIdCard(String idCard) {
		this.idCard = idCard == null ? "" : idCard;
	}	
	
	/** 用户QQ */
	public String getQq() {
		return this.qq;
	}

	/** 用户QQ */
	public void setQq(String qq) {
		this.qq = qq == null ? "" : qq;
	}	
	
	/** 推荐的文章数 */
	public int getRecommendArticleCount() {
		return recommendArticleCount;
	}

	/** 推荐的文章数 */
	public void setRecommendArticleCount(int recommendArticleCount) {
		this.recommendArticleCount = recommendArticleCount;
	}
	
	/** 我的文章被评论数 */
	public int getArticleCommentCount() {
		return articleCommentCount;
	}
	
	/** 我的文章被评论数 */
	public void setArticleCommentCount(int articleCommentCount) {
		this.articleCommentCount = articleCommentCount;
	}
	
	/** 我评论别人的文章数 */
	public int getArticleICommentCount() {
		return ArticleICommentCount;
	}
	
	/** 我评论别人的文章数 */
	public void setArticleICommentCount(int articleICommentCount) {
		ArticleICommentCount = articleICommentCount;
	}

	/** 推荐的资源数 */
	public int getRecommendResourceCount() {
		return recommendResourceCount;
	}

	/** 推荐的资源数 */
	public void setRecommendResourceCount(int recommendResourceCount) {
		this.recommendResourceCount = recommendResourceCount;
	}
	
	/** 我的资源被评论数 */
	public int getResourceCommentCount() {
		return resourceCommentCount;
	}
	
	/** 我的资源被评论数 */
	public void setResourceCommentCount(int resourceCommentCount) {
		this.resourceCommentCount = resourceCommentCount;
	}
	
	/** 我评论别人的资源数 */
	public int getResourceICommentCount() {
		return resourceICommentCount;
	}
	
	/** 我评论别人的资源数 */
	public void setResourceICommentCount(int resourceICommentCount) {
		this.resourceICommentCount = resourceICommentCount;
	}

	/** 资源下载数 */
	public int getResourceDownloadCount() {
		return resourceDownloadCount;
	}

	/** 资源下载数 */
	public void setResourceDownloadCount(int resourceDownloadCount) {
		this.resourceDownloadCount = resourceDownloadCount;
	}

	/** 创建协作组数 */
	public int getCreateGroupCount() {
		return createGroupCount;
	}

	/** 创建协作组数 */
	public void setCreateGroupCount(int createGroupCount) {
		this.createGroupCount = createGroupCount;
	}

	/** 加入协作组数 */
	public int getJionGroupCount() {
		return jionGroupCount;
	}

	/** 加入协作组数 */
	public void setJionGroupCount(int jionGroupCount) {
		this.jionGroupCount = jionGroupCount;
	}

	/** 文章积分 */
	public int getArticleScore() {
		return articleScore;
	}
	public void setArticleScore(int articleScore) {
		this.articleScore = articleScore;
	}

	/** 文章罚分 */
	public int getArticlePunishScore() {
		return articlePunishScore;
	}
	public void setArticlePunishScore(int articlePunishScore) {
		this.articlePunishScore = articlePunishScore;
	}
	
	/** 资源积分 */
	public int getResourceScore() {
		return resourceScore;
	}

	public void setResourceScore(int resourceScore) {
		this.resourceScore = resourceScore;
	}
	
	public int getPhotoScore() {
		return photoScore;
	}

	public void setPhotoScore(int photoScore) {
		this.photoScore = photoScore;
	}

	public int getVideoScore() {
		return videoScore;
	}

	public void setVideoScore(int videoScore) {
		this.videoScore = videoScore;
	}

	/** 资源罚分 */
	public int getResourcePunishScore() {
		return resourcePunishScore;
	}

	public void setResourcePunishScore(int resourcePunishScore) {
		this.resourcePunishScore = resourcePunishScore;
	}
	
	/** 评论积分 */
	public int getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(int commentScore) {
		this.commentScore = commentScore;
	}

	/** 评论罚分 */
	public int getCommentPunishScore() {
		return commentPunishScore;
	}
	public void setCommentPunishScore(int commentPunishScore) {
		this.commentPunishScore = commentPunishScore;
	}
	/** 图片罚分 */
	public int getPhotoPunishScore() {
		return photoPunishScore;
	}
	public void setPhotoPunishScore(int photoPunishScore) {
		this.photoPunishScore = photoPunishScore;
	}
	
	public int getVideoPunishScore() {
		return videoPunishScore;
	}

	public void setVideoPunishScore(int videoPunishScore) {
		this.videoPunishScore = videoPunishScore;
	}
	
	/** 文章访问量 */
	public int getVisitArticleCount() {
		return visitArticleCount;
	}

	/** 文章访问量 */
	public void setVisitArticleCount(int visitArticleCount) {
		this.visitArticleCount = visitArticleCount;
	}

	/** 资源访问量 */
	public int getVisitResourceCount() {
		return visitResourceCount;
	}

	/** 资源访问量 */
	public void setVisitResourceCount(int visitResourceCount) {
		this.visitResourceCount = visitResourceCount;
	}

	public int getArticleCount() {
		return this.articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public int getPrepareCourseCount() {
		return this.prepareCourseCount;
	}

	public void setPrepareCourseCount(int prepareCourseCount) {
		this.prepareCourseCount = prepareCourseCount;
	}
	public int getHistoryMyArticleCount() {
		return historyMyArticleCount;
	}
	public void setHistoryMyArticleCount(int historyMyArticleCount) {
		this.historyMyArticleCount = historyMyArticleCount;
	}
	public int getHistoryOtherArticleCount() {
		return historyOtherArticleCount;
	}
	public void setHistoryOtherArticleCount(int historyOtherArticleCount) {
		this.historyOtherArticleCount = historyOtherArticleCount;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
