package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 简单对象，供存储过程使用
 * @author admin
 *
 */
public final class Article2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 275585857865912022L;
	private int articleId;
	private String objectUuid;
	private String title;
	private int userId;
	private Date createDate;
	private Date lastModified;
	private String articleTags;
	private int viewCount;
	private int commentCount;
	private short auditState;
	private Integer subjectId;
	private String userTrueName;
	private Integer gradeId;
	private Integer userCateId;
	private Integer sysCateId;
	private int digg;
	private int trample;
	private int starCount;
	private short hideState;
	private boolean topState;
	private boolean bestState;
	private boolean draftState;
	private boolean delState;
	private boolean recommendState;
	private boolean commentState;
	private String rcmdPathInfo;
	private String approvedPathInfo;
	private String addIp;
	private boolean typeState;
	private Integer unitId;
	private String unitPathInfo;
	private String orginPath;
	private int pushState;
	private Integer pushUserId;
	private String loginName;
	
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getObjectUuid() {
		return objectUuid;
	}

	public void setObjectUuid(String objectUuid) {
		this.objectUuid = objectUuid;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getArticleTags() {
		return articleTags;
	}

	public void setArticleTags(String articleTags) {
		this.articleTags = articleTags;
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

	public short getAuditState() {
		return auditState;
	}

	public void setAuditState(short auditState) {
		this.auditState = auditState;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getUserTrueName() {
		return userTrueName;
	}

	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
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

	public int getDigg() {
		return digg;
	}

	public void setDigg(int digg) {
		this.digg = digg;
	}

	public int getTrample() {
		return trample;
	}

	public void setTrample(int trample) {
		this.trample = trample;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public short getHideState() {
		return hideState;
	}

	public void setHideState(short hideState) {
		this.hideState = hideState;
	}

	public boolean isTopState() {
		return topState;
	}

	public void setTopState(boolean topState) {
		this.topState = topState;
	}

	public boolean isBestState() {
		return bestState;
	}

	public void setBestState(boolean bestState) {
		this.bestState = bestState;
	}

	public boolean isDraftState() {
		return draftState;
	}

	public void setDraftState(boolean draftState) {
		this.draftState = draftState;
	}

	public boolean isDelState() {
		return delState;
	}

	public void setDelState(boolean delState) {
		this.delState = delState;
	}

	public boolean isRecommendState() {
		return recommendState;
	}

	public void setRecommendState(boolean recommendState) {
		this.recommendState = recommendState;
	}

	public boolean isCommentState() {
		return commentState;
	}

	public void setCommentState(boolean commentState) {
		this.commentState = commentState;
	}

	public String getRcmdPathInfo() {
		return rcmdPathInfo;
	}

	public void setRcmdPathInfo(String rcmdPathInfo) {
		this.rcmdPathInfo = rcmdPathInfo;
	}

	public String getApprovedPathInfo() {
		return approvedPathInfo;
	}

	public void setApprovedPathInfo(String approvedPathInfo) {
		this.approvedPathInfo = approvedPathInfo;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public boolean isTypeState() {
		return typeState;
	}

	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitPathInfo() {
		return unitPathInfo;
	}

	public void setUnitPathInfo(String unitPathInfo) {
		this.unitPathInfo = unitPathInfo;
	}

	public String getOrginPath() {
		return orginPath;
	}

	public void setOrginPath(String orginPath) {
		this.orginPath = orginPath;
	}

	public int getPushState() {
		return pushState;
	}

	public void setPushState(int pushState) {
		this.pushState = pushState;
	}

	public Integer getPushUserId() {
		return pushUserId;
	}

	public void setPushUserId(Integer pushUserId) {
		this.pushUserId = pushUserId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	

}
