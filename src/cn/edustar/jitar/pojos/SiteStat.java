package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 网站级的统计信息.
 * 
 *
 */
public class SiteStat implements Serializable, Cloneable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 939268160167794553L;

	/** 对象标识 */
	private int id;
	
	/** 总访问量(主页点击数) */
	private int visitCount;
	
	/** 注册用户数量 */
	private int userCount;
	
	/** 协作组数量 */
	private int groupCount;
	
	/** 发表的文章数量 */
	private int articleCount;
	
	/** 资源数量 */
	private int resourceCount;

	/** 主题数量 */
	private int topicCount;

	/** 相片数量 */
	private int photoCount;
	
	/** 今日发表的文章数量 */
	private int todayArticleCount;
	
	/** 昨日发表的文章数量 */
	private int yesterdayArticleCount;
	
	/** 今日发布的资源数量 */
	private int todayResourceCount;
	
	/** 昨日发布的资源数量 */
	private int yesterdayResourceCount;
	
	/** 评论数 */
	private int commentCount;

	private int historyArticleCount;
	
	private String dateCount;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public SiteStat clone() {
		SiteStat other = new SiteStat();
		other.id = this.id;
		other.visitCount = this.visitCount;
		other.userCount = this.userCount;
		other.groupCount = this.groupCount;
		other.articleCount = this.articleCount;
		other.resourceCount = this.resourceCount;
		other.photoCount = this.photoCount;
		other.todayArticleCount = this.todayArticleCount;
		other.yesterdayArticleCount = this.yesterdayArticleCount;
		other.todayResourceCount = this.todayResourceCount;
		other.yesterdayResourceCount = this.yesterdayResourceCount;
		other.dateCount=this.dateCount;
		other.commentCount = this.commentCount;
		other.historyArticleCount = this.historyArticleCount;
		return other;
	}
	
	/** 对象标识 */
	public int getId() {
		return id;
	}

	/** 对象标识 */
	public void setId(int id) {
		this.id = id;
	}

	/** 总访问量(主页点击数) */
	public int getVisitCount() {
		return visitCount;
	}

	/** 总访问量(主页点击数) */
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	/** 注册用户数量 */
	public int getUserCount() {
		return userCount;
	}

	/** 注册用户数量 */
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	/** 协作组数量 */
	public int getGroupCount() {
		return groupCount;
	}

	/** 协作组数量 */
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	/** 发表的文章数量 */
	public int getArticleCount() {
		return articleCount;
	}

	/** 发表的文章数量 */
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/** 资源数量 */
	public int getResourceCount() {
		return resourceCount;
	}

	/** 资源数量 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	/** 相片数量 */
	public int getPhotoCount() {
		return photoCount;
	}

	/** 相片数量 */
	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	/** 主题数量 */
	public int getTopicCount() {
		return topicCount;
	}

	/** 主题数量 */
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
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

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getDateCount() {
		return dateCount;
	}

	public void setDateCount(String dateCount) {
		this.dateCount = dateCount;
	}

	public int getHistoryArticleCount() {
		return historyArticleCount;
	}

	public void setHistoryArticleCount(int historyArticleCount) {
		this.historyArticleCount = historyArticleCount;
	}
	
}
