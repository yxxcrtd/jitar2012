package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 每天的机构统计
 * 
 * @author admin
 * 
 */
public class UnitDayCount implements Serializable {

    private static final long serialVersionUID = 3423884143116660028L;

    private int id;
    private int unitId;
    private int articleCount = 0;
    private int resourceCount = 0;
    private int photoCount = 0;
    private int videoCount = 0;
    private int userCount = 0;
    private int historyArticleCount = 0;
    private String unitPathInfo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    public int getArticleCount() {
        return articleCount;
    }
    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }
    public int getResourceCount() {
        return resourceCount;
    }
    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
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
    public int getHistoryArticleCount() {
        return historyArticleCount;
    }
    public void setHistoryArticleCount(int historyArticleCount) {
        this.historyArticleCount = historyArticleCount;
    }
    public String getUnitPathInfo() {
        return unitPathInfo;
    }
    public void setUnitPathInfo(String unitPathInfo) {
        this.unitPathInfo = unitPathInfo;
    }
    public int getUserCount() {
        return userCount;
    }
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}
