package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 举报实体类。
 * 
 * @author mxh
 * 
 */
public class Report implements Serializable {

    /** 序列化标识 */
    private static final long serialVersionUID = 6827462296581223642L;

    /** 举报标识 */
    private int reportId;

    /** 举报类型, 参见 ObjectType.java */
    private int objType;

    /** 举报对象标识， 参见 ObjectType.java */
    private int objId;

    /** 举报者用户标识 */
    private int userId;

    /** 举报类型原因 */
    private String reportType;

    /** 举报时间 */
    private Date createDate = new Date();

    /** 举报对象的显示名称 */
    private String objTitle;

    /** 举报自定义内容 */
    private String reportContent;

    /** 是否已经处理 */
    private Boolean status = false;

    public Report() {
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getObjTitle() {
        return objTitle;
    }

    public void setObjTitle(String objTitle) {
        this.objTitle = objTitle;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Report [reportId=" + reportId + ", objType=" + objType + ", objId=" + objId + ", userId=" + userId + ", reportType=" + reportType
                + ", objTitle=" + objTitle + ", reportContent=" + reportContent + "]";
    }

}
