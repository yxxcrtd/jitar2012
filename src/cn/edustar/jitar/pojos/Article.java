package cn.edustar.jitar.pojos;

import java.io.Serializable;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 文章对象。以 objectUuid 为业务键。(备选的是 userId + createDate)
 * 
 * @author Yang xinxin
 * @version 1.0.0 Mar 30, 2008 5:04:35 PM
 */
public class Article extends Document implements Serializable, Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7253313645831868480L;

	/** 为操作方便，加入用户昵称 */
	private String loginName;

	/** 为操作方便，加入用户真实姓名 */
	private String userTrueName;

	/** 博客文章内容 */
	private String articleContent;

	/** 文章摘要 */
	private String articleAbstract;

	/** 学段Id */
	private Integer gradeId;

	/** 文章个人分类标识，可能为 null */
	private Integer userCateId;

	/** 文章系统分类标识，可能为 null */
	private Integer sysCateId;

	/** 顶的数量 */
	private int digg;

	/** 踩的数量 */
	private int trample;

	/** 总的星级 */
	private int starCount;

	/** 文章是否是隐藏状态, 0 - 表示不隐藏，1 - 表示隐藏 */
	private short hideState;
	
	/** 文章置顶状态：false - 不置顶，true - 置顶 */
	private boolean topState;

	/** 文章精华状态：false - 普通，true - 精华 */
	private boolean bestState;

	/** 文章草稿状态：false - 正常，true - 草稿 */
	private boolean draftState;

	/** 文章删除状态：false - 正常，true - 已删除(在回收站中) */
	private boolean delState;

	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	private boolean recommendState;

	/** 文章允许评论状态：false - 不允许评论，true - 允许评论，缺省为 true */
	private boolean commentState = true;
	
	/** 推荐文章的范围 */
	private String rcmdPathInfo;	
	
	/** 多级审核状态 */
	private String approvedPathInfo;
	
	
	/** 发布本文章时的IP来源 */
	private String addIp;

	/** 文章的类型，0 原创；1 转载 */
	private boolean typeState;

	/** 组织机构属性 */
	private Integer unitId;	
	private String unitPathInfo;	
	private String orginPath;

	/** 推送状态：1，已经推送；2：待推送；0：没有设置 */
	private int pushState;
	private Integer pushUserId;
	
	/**文章格式：1==Word  0==网页  默认是网页*/
	private Integer articleFormat=0;
	/** 文章的Word文档是否允许下载，0不允许；1允许 */
	private boolean wordDownload=false;
	private String wordHref ="";
	
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	// Default Constructor
	public Article() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Article{id=" + this.getId() + ",title=" + this.getTitle()
				+ ",user=" + this.getUserId() + "}";
	}

	/**
	 * 返回 'title'(id=nnn) 的友好显示字符串, 一般用于显示在浏览器中, 因此对 title 进行了 html 编码
	 * 
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.getTitle()) + "'(id="
				+ this.getId() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.getObjectUuid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || !(o instanceof Article))
			return false;

		Article other = (Article) o;
		return this.getObjectUuid().equalsIgnoreCase(other.getObjectUuid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Article clone() throws CloneNotSupportedException {
		return (Article) super.clone();
	}

	// Property accessors

	/** 文章标识 */
	public int getArticleId() {
		return super.getId();
	}

	/** 文章标识 */
	public void setArticleId(int articleId) {
		super.setId(articleId);
	}

	/** 为操作方便，加入用户真实姓名 */
	public String getUserTrueName() {
		return this.userTrueName;
	}

	/** 为操作方便，加入用户真实姓名 */
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 博客文章内容 */
	public String getArticleContent() {
		return this.articleContent;
	}

	/** 博客文章内容 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	/** 文章个人分类标识，可能为 null */
	public Integer getUserCateId() {
		return this.userCateId;
	}

	/** 文章个人分类标识，可能为 null */
	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
	}

	/** 文章系统分类标识，可能为 null */
	public Integer getSysCateId() {
		return this.sysCateId;
	}

	public int getTrample() {
		return this.trample;
	}

	public int getDigg() {
		return this.digg;
	}

	public void setDigg(int digg) {
		this.digg = digg;
	}

	public void setTrample(int trample) {
		this.trample = trample;
	}

	/** 文章系统分类标识，可能为 null */
	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	/** 文章是否是隐藏状态, = 0 表示不隐藏，= 1 表示隐藏 */
	public short getHideState() {
		return this.hideState;
	}

	/** 文章是否是隐藏状态, = 0 表示不隐藏，= 1 表示隐藏 */
	public void setHideState(short hideState) {
		this.hideState = hideState;
	}

	/** 文章置顶状态：false - 不置顶，true - 置顶 */
	public boolean getTopState() {
		return this.topState;
	}

	/** 文章置顶状态：false - 不置顶，true - 置顶 */
	public void setTopState(boolean topState) {
		this.topState = topState;
	}

	/** 文章精华状态：false - 普通，true - 精华 */
	public boolean getBestState() {
		return this.bestState;
	}

	/** 文章精华状态：false - 普通，true - 精华 */
	public void setBestState(boolean bestState) {
		this.bestState = bestState;
	}

	/** 文章草稿状态：false - 正常，true - 草稿 */
	public boolean getDraftState() {
		return this.draftState;
	}

	/** 文章草稿状态：false - 正常，true - 草稿 */
	public void setDraftState(boolean draftState) {
		this.draftState = draftState;
	}

	/** 文章删除状态：false - 正常，true - 已删除(在回收站中) */
	public boolean getDelState() {
		return this.delState;
	}

	/** 文章删除状态：false - 正常，true - 已删除(在回收站中) */
	public void setDelState(boolean delState) {
		this.delState = delState;
	}


	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	public boolean getRecommendState() {
		return this.recommendState;
	}

	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	public void setRecommendState(boolean recommendState) {
		this.recommendState = recommendState;
	}

	/** 文章允许评论状态：false - 不允许评论，true - 允许评论，缺省为 true */
	public boolean getCommentState() {
		return this.commentState;
	}

	/** 文章允许评论状态：false - 不允许评论，true - 允许评论，缺省为 true */
	public void setCommentState(boolean commentState) {
		this.commentState = commentState;
	}

	

	/** 发布本文章时的IP来源 */
	public String getAddIp() {
		return this.addIp;
	}

	/** 发布本文章时的IP来源 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/** 文章摘要 */
	public String getArticleAbstract() {
		return articleAbstract;
	}

	/** 文章摘要 */
	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	/** 此文章的标签 */
	public String getArticleTags() {
		return this.getTags();
	}

	/** 此文章的标签 */
	public void setArticleTags(String articleTags) {
		this.setTags(articleTags);
	}

	/** 学段Id */
	public Integer getGradeId() {
		return this.gradeId;
	}

	/** 学段Id */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
		this.setExtGradeId(gradeId);
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Subject subject;

	/** Hibernate 映射所需字段, 外面不需要访问 */
	Subject getSubject() {
		return this.subject;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	void setSubject(Subject subject) {
		// this.subject = subject;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category sysCate;

	/** Hibernate 映射所需字段, 外面不需要访问 */
	Category getSysCate() {
		return this.sysCate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	void setSysCate(Category sysCate) {
		// this.sysCate = sysCate;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public boolean getTypeState() {
		return this.typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
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

	public String getUnitPathInfo() {
		return unitPathInfo;
	}

	public void setUnitPathInfo(String unitPathInfo) {
		this.unitPathInfo = unitPathInfo;
	}

	public String getOrginPath() {
		return orginPath;
	}

	public void setOrginPath(String orginPath) {
		this.orginPath = orginPath;
	}

	public String getApprovedPathInfo() {
		return approvedPathInfo;
	}

	public void setApprovedPathInfo(String approvedPathInfo) {
		this.approvedPathInfo = approvedPathInfo;
	}

	public String getRcmdPathInfo() {
		return rcmdPathInfo;
	}

	public void setRcmdPathInfo(String rcmdPathInfo) {
		this.rcmdPathInfo = rcmdPathInfo;
	}

	public Integer getArticleFormat() {
		return articleFormat;
	}

	public void setArticleFormat(Integer articleFormat) {
		this.articleFormat = articleFormat;
	}

	public boolean getWordDownload() {
		return wordDownload;
	}

	public void setWordDownload(boolean wordDownload) {
		this.wordDownload = wordDownload;
	}

	public String getWordHref() {
		return wordHref;
	}

	public void setWordHref(String wordHref) {
		this.wordHref = wordHref;
	}

	

}
