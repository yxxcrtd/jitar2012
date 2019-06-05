package cn.edustar.jitar.pojos;

import java.util.Date;

public class ChannelArticle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1419810368714443339L;
	private int channelArticleId;
	private int channelId;
	private int articleId;
	private String articleGuid;
	private String title;
	private int userId;
	private String loginName;
	private String userTrueName;
	private Date createDate;
	private String channelCate;
	private Integer channelCateId;
	/** articleState为1:显示，为0：不显示  */
	private boolean articleState = true;
	private boolean typeState = false;

	public ChannelArticle() {
	}

	public int getChannelArticleId() {
		return channelArticleId;
	}

	public void setChannelArticleId(int channelArticleId) {
		this.channelArticleId = channelArticleId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleGuid() {
		return articleGuid;
	}

	public void setArticleGuid(String articleGuid) {
		this.articleGuid = articleGuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getChannelCate() {
		return channelCate;
	}

	public void setChannelCate(String channelCate) {
		this.channelCate = channelCate;
	}

	public Integer getChannelCateId() {
		return channelCateId;
	}

	public void setChannelCateId(Integer channelCateId) {
		this.channelCateId = channelCateId;
	}

	public boolean getArticleState() {
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
