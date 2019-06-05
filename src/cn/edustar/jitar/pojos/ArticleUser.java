package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class ArticleUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4180466901320917629L;

	private int articleId;
	private Date createDate;
	private String objectUuid;
	private String title;
	private int userId;
		
	public ArticleUser() {
	}
	
	public int getArticleId() {
		return articleId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getObjectUuid() {
		return objectUuid;
	}
	public String getTitle() {
		return title;
	}
	public int getUserId() {
		return userId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
