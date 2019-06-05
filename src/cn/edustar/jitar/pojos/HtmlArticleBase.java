package cn.edustar.jitar.pojos;

import java.util.Date;
import java.util.UUID;

public class HtmlArticleBase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6630231411071163147L;
	private int articleId;
	private String articleGuid = UUID.randomUUID().toString().toUpperCase();
	private String title;
	private int userId;
	private String loginName;
	private Date createDate = new Date();
	private int viewCount=0;
	private int commentCount = 0;
	private Integer userCateId =  null;
	private Integer sysCateId = null;
	private short hideState = 0;
	private short auditState = 0;
	private boolean draftState = false;
	private boolean delState = false;
	private boolean typeState = false;
	private boolean recommendState = false;
	private boolean bestState = false;	
	private String tableName = "Jitar_Article";

	public HtmlArticleBase() {
		
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getUserCateId() {
		return userCateId;
	}

	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
	}

	public Integer getSysCateId() {
		return sysCateId;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public short getHideState() {
		return hideState;
	}

	public void setHideState(short hideState) {
		this.hideState = hideState;
	}

	public short getAuditState() {
		return auditState;
	}

	public void setAuditState(short auditState) {
		this.auditState = auditState;
	}

	public boolean getDraftState() {
		return draftState;
	}

	public void setDraftState(boolean draftState) {
		this.draftState = draftState;
	}

	public boolean getDelState() {
		return delState;
	}

	public void setDelState(boolean delState) {
		this.delState = delState;
	}

	public boolean getTypeState() {
		return typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	public boolean getRecommendState() {
		return recommendState;
	}

	public void setRecommendState(boolean recommendState) {
		this.recommendState = recommendState;
	}

	public boolean getBestState() {
		return bestState;
	}

	public void setBestState(boolean bestState) {
		this.bestState = bestState;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
