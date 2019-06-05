package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 赞的实体类。
 * 
 * @author mxh
 * 
 */
public class Praise implements Serializable {

    /**
     * 序列化版本标识
     */
    private static final long serialVersionUID = -8502057060858983004L;

    /** 赞表标识 */
    private int praiseId;

    /** 赞类型, 参见 ObjectType.java */
    private int objType;

    /** 赞对象标识， 参见 ObjectType.java */
    private int objId;

    /** 赞用户标识 */
    private int userId;

    public Praise() {
    }

    public int getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(int praiseId) {
        this.praiseId = praiseId;
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

    @Override
    public String toString() {
        return "Praise [praiseId=" + praiseId + ", objType=" + objType + ", objId=" + objId + ", userId=" + userId + "]";
    }

}
