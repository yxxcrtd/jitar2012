package cn.edustar.jitar.pojos;

import java.util.UUID;

/**
 * JitarUnit entity. @author MyEclipse Persistence Tools
 */

public class Unit implements java.io.Serializable {

	// Fields


	private static final long serialVersionUID = -642025104257016062L;
	private int unitId;
	private String unitGuid = UUID.randomUUID().toString().toLowerCase();
	private String unitName;
	private String unitTitle;
	private String siteTitle;
	private int parentId;
	private String unitPathInfo;
	private Boolean hasChild = false;
	private int userCount = 0;
	private int articleCount = 0;
	private int historyArticleCount = 0;
	private int recommendArticleCount = 0;
	private int resourceCount = 0;
	private int recommendResourceCount = 0;
	private int photoCount = 0;
	private int videoCount = 0;
	private int totalScore = 0;
	private int itemIndex = 0;
	private String unitType="";
	private String unitPhoto="";
	private String unitInfo;
	
	/** 删除状态：
	 * false - 正常
	 * true - 已删除（在回收站中）。 */
	private boolean delState;
	
	/**
	 * 模板名称
	 */
	private String templateName = "template1";
	
    /**
	 * 样式名称
	 */
	private String themeName = "theme1";
	private String unitLogo;
	private String headerContent;
	private String footerContent;
	private String shortcutTarget;
	private int rank;
	private Double aveScore = 0d;

	// Constructors

	/** default constructor */
	public Unit() {
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitGuid() {
		return unitGuid;
	}

	public void setUnitGuid(String unitGuid) {
		this.unitGuid = unitGuid;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSiteTitle() {
		return siteTitle;
	}

	public String getUnitTitle() {
		return unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}

	public void setSiteTitle(String siteTitle) {
		this.siteTitle = siteTitle;
	}


	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUnitPathInfo() {
		return unitPathInfo;
	}

	public void setUnitPathInfo(String unitPathInfo) {
		this.unitPathInfo = unitPathInfo;
	}

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public int getHistoryArticleCount() {
		return historyArticleCount;
	}

	public void setHistoryArticleCount(int historyArticleCount) {
		this.historyArticleCount = historyArticleCount;
	}

	public int getRecommendArticleCount() {
		return recommendArticleCount;
	}

	public void setRecommendArticleCount(int recommendArticleCount) {
		this.recommendArticleCount = recommendArticleCount;
	}

	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public int getRecommendResourceCount() {
		return recommendResourceCount;
	}

	public void setRecommendResourceCount(int recommendResourceCount) {
		this.recommendResourceCount = recommendResourceCount;
	}

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getUnitLogo() {
		return unitLogo;
	}

	public void setUnitLogo(String unitLogo) {
		this.unitLogo = unitLogo;
	}

	public String getHeaderContent() {
		return headerContent;
	}

	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}

	public String getFooterContent() {
		return footerContent;
	}

	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}

	public String getShortcutTarget() {
		return shortcutTarget;
	}

	public void setShortcutTarget(String shortcutTarget) {
		this.shortcutTarget = shortcutTarget;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Double getAveScore() {
		return aveScore;
	}

	public void setAveScore(Double aveScore) {
		this.aveScore = aveScore;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitPhoto() {
		return unitPhoto;
	}

	public void setUnitPhoto(String unitPhoto) {
		this.unitPhoto = unitPhoto;
	}

	public String getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(String unitInfo) {
		this.unitInfo = unitInfo;
	}
	
	public boolean getDelState() {
		return this.delState;
	}

	public void setDelState(boolean delState) {
		this.delState = delState;
	}

	@Override
    public String toString() {
        return "Unit [unitId=" + unitId + ", unitGuid=" + unitGuid + ", unitName=" + unitName + ", unitTitle=" + unitTitle + ", siteTitle="
                + siteTitle + ", parentId=" + parentId + ", unitPathInfo=" + unitPathInfo + ", hasChild=" + hasChild + ", unitType=" + unitType + "]";
    }

}