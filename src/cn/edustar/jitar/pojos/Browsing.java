package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容浏览记录。
 * 
 * @author mxh
 * 
 */
public class Browsing implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5491257081171413490L;

    /** 标识字段 */
    private int browsingId;

    /** 内容对象类型 ：文章、资源、视频等 */
    private int objType;

    /** 内容对象标识 */
    private int objId;

    /** 访问用户标识 */
    private int userId;

    /** 最后访问时间 */
    private Date browsingDate = new Date();

    public Browsing() {
    }
    
    public Browsing(int objType, int objId, int userId) {
        this.objType = objType;
        this.objId = objId;
        this.userId = userId;
    }
    public int getBrowsingId() {
        return browsingId;
    }
    public void setBrowsingId(int browsingId) {
        this.browsingId = browsingId;
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
    public Date getBrowsingDate() {
        return browsingDate;
    }
    public void setBrowsingDate(Date browsingDate) {
        this.browsingDate = browsingDate;
    }
    @Override
    public String toString() {
        return "Browsing [browsingId=" + browsingId + ", objType=" + objType + ", objId=" + objId + ", userId=" + userId + "]";
    }
}
