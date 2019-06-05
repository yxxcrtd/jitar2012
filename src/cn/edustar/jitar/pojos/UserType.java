package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class UserType implements Serializable {

    /**
     * 用户类型定义表，如推荐、名师、教研员等等允许用户自定义
     */
    private static final long serialVersionUID = 578595141772656052L;

    private int typeId;
    private String typeName;
    private boolean isSystem;

    /*
     * 
     * 1 名师 2 推荐 3 学科带头人 4 教研员 5 研修之星 6 教师风采
     */
    /**
     * 1 名师
     */
    public static final int USERTYPE_FAMOUSE = 1;

    /**
     * 2 推荐
     */
    public static final int USERTYPE_RECOMMENDED = 2;

    /**
     * 3 学科带头人
     */
    public static final int USERTYPE_EXPERT = 3;

    /**
     * 4 教研员
     */
    public static final int USERTYPE_INSTRUCTOR = 4;

    /**
     * 5 研修之星
     */
    public static final int USERTYPE_STAR = 5;

    /**
     * 6 教师风采
     */
    public static final int USERTYPE_TEACHERMIEN = 6;

    public UserType() {
    }
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public boolean getIsSystem() {
        return isSystem;
    }
    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }
}
