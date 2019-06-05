package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

public class ViewCount implements Serializable {

    private static final long serialVersionUID = 6496551456952543271L;
    private int id;
    private int objType;
    private int objId;
    private Date viewDate = new Date();
    private int viewCount = 0;
    private int deled = 0;

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

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeled() {
        return deled;
    }

    public void setDeled(int deled) {
        this.deled = deled;
    }

}
