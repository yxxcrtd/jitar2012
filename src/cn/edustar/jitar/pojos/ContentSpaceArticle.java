package cn.edustar.jitar.pojos;

import java.util.Date;


public class ContentSpaceArticle implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -2706040198590939811L;
	private int contentSpaceArticleId;
	private String title;
	private Date createDate = new Date();
	private int createUserId = 0;
	private String createUserLoginName;	
	private int contentSpaceId = 0;	
	private String content;
	private String pictureUrl;
	
	/** 文章所属类型：整站，学科，机构 */
	private int ownerType = 0;
	
	/** 文章所属类型的标识，0表示整站 */
	private int ownerId = 0;
	private int viewCount = 0;

	public ContentSpaceArticle() {
	}

	public int getContentSpaceArticleId() {
		return contentSpaceArticleId;
	}

	public void setContentSpaceArticleId(int contentSpaceArticleId) {
		this.contentSpaceArticleId = contentSpaceArticleId;
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

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getCreateUserLoginName() {
		return createUserLoginName;
	}

	public void setCreateUserLoginName(String createUserLoginName) {
		this.createUserLoginName = createUserLoginName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public int getContentSpaceId() {
		return contentSpaceId;
	}

	public void setContentSpaceId(int contentSpaceId) {
		this.contentSpaceId = contentSpaceId;
	}


}