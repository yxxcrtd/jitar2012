package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 表示一个学科实体
 * 
 *
 * 
 * @remark 由于学科数量比较少且很少很少修改, 所以内存将完全缓存一份学科信息.
 */
public class Subject implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -5816069760831889390L;

	/** 标识, 数据库自动生成 */
	private int subjectId;
	
	/** 所属元学科. */
	private MetaSubject metaSubject;

	/** 所属学段, 只能是 Grade.isGrade == true 的学段, 而不是年级. */
	private Grade metaGrade;
	
	/** 学科名字, 不重复, 作为业务键 */
	private String subjectName;

	/** 用于显示时候的排序 */
	private int orderNum;

	/** 学科国家标准编码，用于和其它系统交互. */
	private String subjectCode;

	/** 该学科用户/博客/工作室数量 */
	private int userCount;
	
	/** 该学科协作组数量 */
	private int groupCount;
	
	/** 该学科文章数 */
	private int articleCount;
	
	/** 该学科资源数 */
	private int resourceCount;
	
	/** 对象资源库分类ID */
	private int reslibCId;
	
	/** 今日发表的文章数量 */
	private int todayArticleCount;
	
	/** 昨日发表的文章数量 */
	private int yesterdayArticleCount;
	
	/** 今日发布的资源数量 */
	private int todayResourceCount;
	
	/** 昨日发布的资源数量 */
	private int yesterdayResourceCount;
	
	private int historyArticleCount = 0;
	
	
	//private int photoCount = 0;
	private int videoCount = 0;
	private int prepareCourseCount = 0;
	private int actionCount = 0;
	
	private String subjectGuid = java.util.UUID.randomUUID().toString().toUpperCase();
	private String headerContent;
	private String footerContent;
	private String templateName;
	private String themeName;
	private String logo;
	private String custormTemplate;
	private String shortcutTarget;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Subject{id=" + this.subjectId + ", name=" + this.subjectName + "}";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.subjectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || !(o instanceof Subject))
			return false;

		Subject other = (Subject) o;
		return PojoHelper.equals(this.subjectName, other.subjectName);
	}

	// Get and set
	
	/** 标识, 数据库自动生成 */
	public int getSubjectId() {
		return subjectId;
	}

	/** 标识, 数据库自动生成 */
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	/** 所属元学科. */
	public MetaSubject getMetaSubject() {
		return this.metaSubject;
	}

	/** 所属元学科. */
	public void setMetaSubject(MetaSubject metaSubject) {
		this.metaSubject = metaSubject;
	}

	/** 学科名字, 不重复, 作为业务键 */
	public String getSubjectName() {
		return subjectName;
	}

	/** 学科名字, 不重复, 作为业务键 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/** 用于显示时候的排序 */
	public int getOrderNum() {
		return orderNum;
	}

	/** 用于显示时候的排序 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/** 学科国家标准编码，用于和其它系统交互. */
	public String getSubjectCode() {
		return subjectCode;
	}

	/** 学科国家标准编码，用于和其它系统交互. */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/** 该学科用户/博客/工作室数量 */
	public int getUserCount() {
		return userCount;
	}

	/** 该学科用户/博客/工作室数量 */
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	/** 该学科协作组数量 */
	public int getGroupCount() {
		return groupCount;
	}

	/** 该学科协作组数量 */
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	/** 该学科文章数 */
	public int getArticleCount() {
		return articleCount;
	}

	/** 该学科文章数 */
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/** 该学科资源数 */
	public int getResourceCount() {
		return resourceCount;
	}

	/** 该学科资源数 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	/** 所属学段, 只能是 Grade.isGrade == true 的学段, 而不是年级. */
	public Grade getMetaGrade() {
		return metaGrade;
	}

	/** 所属学段, 只能是 Grade.isGrade == true 的学段, 而不是年级. */
	public void setMetaGrade(Grade metaGrade) {
		this.metaGrade = metaGrade;
	}

	public int getReslibCId() {
		return reslibCId;
	}

	public void setReslibCId(int reslibCId) {
		this.reslibCId = reslibCId;
	}
	
	public int getTodayArticleCount() {
		return todayArticleCount;
	}

	public void setTodayArticleCount(int todayArticleCount) {
		this.todayArticleCount = todayArticleCount;
	}

	public int getYesterdayArticleCount() {
		return yesterdayArticleCount;
	}

	public void setYesterdayArticleCount(int yesterdayArticleCount) {
		this.yesterdayArticleCount = yesterdayArticleCount;
	}

	public int getTodayResourceCount() {
		return todayResourceCount;
	}

	public void setTodayResourceCount(int todayResourceCount) {
		this.todayResourceCount = todayResourceCount;
	}

	public int getYesterdayResourceCount() {
		return yesterdayResourceCount;
	}

	public void setYesterdayResourceCount(int yesterdayResourceCount) {
		this.yesterdayResourceCount = yesterdayResourceCount;
	}

	public int getHistoryArticleCount() {
		return historyArticleCount;
	}

	public void setHistoryArticleCount(int historyArticleCount) {
		this.historyArticleCount = historyArticleCount;
	}

	/*public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}*/

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public int getPrepareCourseCount() {
		return prepareCourseCount;
	}

	public void setPrepareCourseCount(int prepareCourseCount) {
		this.prepareCourseCount = prepareCourseCount;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public String getSubjectGuid() {
		return subjectGuid;
	}

	public void setSubjectGuid(String subjectGuid) {
		this.subjectGuid = subjectGuid;
	}

	public String getHeaderContent() {
		return headerContent;
	}

	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}

	public String getFooterContent() {
		return footerContent;
	}

	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}



	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCustormTemplate() {
		return custormTemplate;
	}

	public void setCustormTemplate(String custormTemplate) {
		this.custormTemplate = custormTemplate;
	}

	public String getShortcutTarget() {
		return shortcutTarget;
	}

	public void setShortcutTarget(String shortcutTarget) {
		this.shortcutTarget = shortcutTarget;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	
}
