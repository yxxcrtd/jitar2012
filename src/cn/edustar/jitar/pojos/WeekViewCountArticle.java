package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class WeekViewCountArticle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2021092674325810519L;
	private int articleId;
	private Date createDate;
	private String loginName;
	private String title;
	private String trueName;
	private boolean typeState;
	private String userIcon;
	private int userId;
	private int viewCount;

	public int getArticleId() {
		return articleId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getTitle() {
		return title;
	}

	public String getTrueName() {
		return trueName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public int getUserId() {
		return userId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public boolean isTypeState() {
		return typeState;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
}
