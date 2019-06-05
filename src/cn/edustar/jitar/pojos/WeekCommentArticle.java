package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class WeekCommentArticle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 154499796510500719L;
	private int articleId;
	private String title;
	private Date createDate;
	private boolean typeState;
	private int userId;
	private String userIcon;
	private String trueName;
	private String loginName;
	private int commentCount;
	
	public WeekCommentArticle() {
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isTypeState() {
		return typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	
}