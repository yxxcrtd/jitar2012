package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * 一般性新闻对象.
 *
 *
 */
public class News {
	/** 新闻状态 = 0 表示正常 */
	public static final int NEWS_STATUS_NORMAL = 0;
	/** 新闻状态 = 1 表示待审核 */
	public static final int NEWS_STATUS_WAIT_AUTID = 1;
	/** 新闻状态 = 2 表示被删除 */
	public static final int NEWS_STATUS_DELETED = 2;
	
	/** 新闻标识 */
	private int newsId;
	
	/** 发表人标识 */
	private int userId;
	
	/** 新闻标题 */
	private String title = "";
	
	/** 新闻内容 */
	private String content;
	
	/** 是否有图片, 有则表示图片地址 */
	private String picture;
	
	/** 新闻状态, = 0 表示正常状态, = 1 表示待审核, = 2 表示被删除 */
	private int status;
	
	/** 新闻类型 */
	private int newsType;
	
	/** 新闻创建时间 */
	private Date createDate = new Date();
	
	/** 点击次数 */
	private int viewCount;

	/** 新闻标识 */
	public int getNewsId() {
		return newsId;
	}

	/** 新闻标识 */
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	/** 发表人标识 */
	public int getUserId() {
		return userId;
	}

	/** 发表人标识 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/** 文章标题 */
	public String getTitle() {
		return title;
	}

	/** 文章标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 文章内容 */
	public String getContent() {
		return content;
	}

	/** 文章内容 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 是否有图片, 有则表示图片地址 */
	public String getPicture() {
		return picture;
	}

	/** 是否有图片, 有则表示图片地址 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/** 新闻状态, = 0 表示正常状态, = 1 表示待审核, = 2 表示被删除 */
	public int getStatus() {
		return status;
	}

	/** 新闻状态, = 0 表示正常状态, = 1 表示待审核, = 2 表示被删除 */
	public void setStatus(int status) {
		this.status = status;
	}

	/** 新闻类型 */
	public int getNewsType() {
		return newsType;
	}

	/** 新闻类型 */
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	/** 新闻创建时间 */
	public Date getCreateDate() {
		return createDate;
	}

	/** 新闻创建时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 点击次数 */
	public int getViewCount() {
		return viewCount;
	}

	/** 点击次数 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
}