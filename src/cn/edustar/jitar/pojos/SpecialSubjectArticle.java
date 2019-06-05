package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * SSpecialSubjectArticle entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SpecialSubjectArticle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298227833990707064L;
	// Fields

	private Integer specialSubjectArticleId;
	private int articleId;
	private String title;
	private String articleGuid;
	private int specialSubjectId;
	private Date createDate;
	private int userId;
	private String loginName;
	private String userTrueName;
	private boolean articleState = true;
	// 这里的typeState是：Word格式（1）和网页格式（0）
	private boolean typeState = false;
	
	public SpecialSubjectArticle() {
	}

	public Integer getSpecialSubjectArticleId() {
		return specialSubjectArticleId;
	}

	public void setSpecialSubjectArticleId(Integer specialSubjectArticleId) {
		this.specialSubjectArticleId = specialSubjectArticleId;
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

	public String getArticleGuid() {
		return articleGuid;
	}

	public void setArticleGuid(String articleGuid) {
		this.articleGuid = articleGuid;
	}

	
	public int getSpecialSubjectId() {
		return specialSubjectId;
	}

	public void setSpecialSubjectId(int specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserTrueName() {
		return userTrueName;
	}

	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public boolean isArticleState() {
		return articleState;
	}

	public void setArticleState(boolean articleState) {
		this.articleState = articleState;
	}

	public boolean getTypeState() {
		return typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}
}