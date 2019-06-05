package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * <p>
 * 定时统计站点基础数据。
 * </p>
 * 
 * @author mxh
 * 
 */
public class TimerCount implements Serializable {
    private static final long serialVersionUID = -5412529649712548950L;
    public static final int COUNT_TYPE_SITE = 1;

    private int countId = 1; // 整站数据
    private int userCount = 0;
    private int groupCount = 0;
    private int totalArticleCount = 0;
    private int totalResourceCount = 0;
    private int commentCount = 0;
    private int photoCount = 0;
    private int videoCount = 0;
    private int prepareCourseCount = 0;
    private int todayArticleCount = 0;
    private int yesterdayArticleCount = 0;
    private int historyArticleCount = 0;
    private int todayResourceCount = 0;
    private int yesterdayResourceCount = 0;
    private String countDate = "";

    /**
     * <p>
     * 无参数的构造器。
     * </p>
     */
    public TimerCount() {
    }

    public int getCountId() {
        return countId;
    }
    public void setCountId(int countId) {
        this.countId = countId;
    }
    public int getUserCount() {
        return userCount;
    }
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    public int getGroupCount() {
        return groupCount;
    }
    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }
    public int getTotalArticleCount() {
        return totalArticleCount;
    }
    public void setTotalArticleCount(int totalArticleCount) {
        this.totalArticleCount = totalArticleCount;
    }
    public int getTotalResourceCount() {
        return totalResourceCount;
    }
    public void setTotalResourceCount(int totalResourceCount) {
        this.totalResourceCount = totalResourceCount;
    }
    public int getCommentCount() {
        return commentCount;
    }
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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
    public int getPrepareCourseCount() {
        return prepareCourseCount;
    }

    public void setPrepareCourseCount(int prepareCourseCount) {
        this.prepareCourseCount = prepareCourseCount;
    }

    public int getTodayArticleCount() {
        return todayArticleCount;
    }
    public void setTodayArticleCount(int todayArticleCount) {
        this.todayArticleCount = todayArticleCount;
    }
    public int getYesterdayArticleCount() {
        return yesterdayArticleCount;
    }
    public void setYesterdayArticleCount(int yesterdayArticleCount) {
        this.yesterdayArticleCount = yesterdayArticleCount;
    }
    public int getHistoryArticleCount() {
        return historyArticleCount;
    }
    public void setHistoryArticleCount(int historyArticleCount) {
        this.historyArticleCount = historyArticleCount;
    }
    public int getTodayResourceCount() {
        return todayResourceCount;
    }
    public void setTodayResourceCount(int todayResourceCount) {
        this.todayResourceCount = todayResourceCount;
    }
    public int getYesterdayResourceCount() {
        return yesterdayResourceCount;
    }
    public void setYesterdayResourceCount(int yesterdayResourceCount) {
        this.yesterdayResourceCount = yesterdayResourceCount;
    }
    public String getCountDate() {
        return countDate;
    }
    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    /**
     * 友好显示的方法。
     */
    @Override
    public String toString() {
        return "TimerCount [countId=" + countId + ", userCount=" + userCount + ", groupCount=" + groupCount
                + ", totalArticleCount=" + totalArticleCount + ", totalResourceCount=" + totalResourceCount
                + ", commentCount=" + commentCount + ", photoCount=" + photoCount + ", videoCount=" + videoCount
                + ", prepareCourseCount=" + prepareCourseCount + ", todayArticleCount=" + todayArticleCount
                + ", yesterdayArticleCount=" + yesterdayArticleCount + ", historyArticleCount=" + historyArticleCount
                + ", todayResourceCount=" + todayResourceCount + ", yesterdayResourceCount=" + yesterdayResourceCount
                + ", countDate=" + countDate + "]";
    }
}
