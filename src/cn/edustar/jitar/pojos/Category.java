package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.edustar.jitar.service.CategoryHelper;

/**
 * 系统分类. 可以被应用在用户博客网站分类、博文分类、相册分类、群组分类等.
 * 
 * 
 * @remark 以 name, parentId, itemType 做为业务键.
 * @remark 关于 itemType 以字符串表示，缺省 = 'default' 表示是系统缺省分类. 其它对象使用分类的时候，使用
 *         ObjectType_id 的方式做为 itemType 例如 'User_1', 'Group_7' 等.
 */
public class Category implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = -2175529968754715877L;

    /** 分类的标识主键，数据库自动生成. */
    private int categoryId;

    /** 分类的全局唯一标识. */
    private String objectUuid = UUID.randomUUID().toString().toUpperCase();

    /** 分类的名字，可以使用中文和空格等特殊字符. */
    private String name = "";

    /** 分类中项目类型，也代表该分类应用给哪种对象. 参见类的说明. */
    private String itemType = "default";

    /** 父分类标识. */
    private Integer parentId = null;

    /** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
    private String parentPath = "/";

    /** 分类在该层的排序值. */
    private int orderNum;

    /** 子分类数量. */
    private int childNum;

    /** 分类的详细描述. */
    private String description;

    /** 此分类下内容数量. 对于博文分类指该分类下博文数量. 其它项目对象可以不统计（不使用此字段） */
    private int itemNum;

    /** 是否是系统分类 */
    private boolean isSystem = false;
    // Constructors

    /**
     * 扩展的对象 因为 Category将被CategoryModel对象使用，CategoryModel对象又被
     * CategoryTreeModel对象使用 CategoryTreeModel 是为了创建分类树,在前端展示
     * 这里在Category中增加extendedObject扩展对象，是为了支持其他的分类能使用CategoryTreeModel这个对象。
     */
    private Object extendedObject = null;

    /** 下级子分类，这里暂时只用在Tree显示用 */
    private List<Category> childCategoryList = new ArrayList<Category>();
    
    /** default constructor */
    public Category() {
    }

    /**
     * 构造函数
     * 
     * @param categoryId
     * @param name
     * @param parentId
     */
    public Category(Integer categoryId, String name, Integer parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
    }

    /**
     * 构造函数
     * 
     * @param categoryId
     * @param name
     * @param parentId
     * @param extendedObject
     */
    public Category(Integer categoryId, String name, Integer parentId, Object extendedObject) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.extendedObject = extendedObject;
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer strbuf = new StringBuffer(128);
        strbuf.append("Category{id=").append(this.categoryId).append(", parent=").append(this.parentId).append(", name=").append(this.name)
                .append(", path=").append(this.parentPath).append(", order=").append(this.orderNum).append(", itemNum=").append(this.itemNum)
                .append("}");
        return strbuf.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return PojoHelper.hashCode(name, parentId, itemType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || !(o instanceof Category))
            return false;
        Category other = (Category) o;
        return PojoHelper.equals(name, other.name) && PojoHelper.equals(parentId, other.parentId) && PojoHelper.equals(itemType, other.itemType);
    }

    // Property accessors

    /** 分类的标识主键，数据库自动生成. */
    public int getId() {
        return this.categoryId;
    }

    /** 分类的标识主键，数据库自动生成. */
    public int getCategoryId() {
        return this.categoryId;
    }

    /** 分类的标识主键，数据库自动生成. */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /** 分类的全局唯一标识. */
    public String getObjectUuid() {
        return this.objectUuid;
    }

    /** 分类的全局唯一标识. */
    public void setObjectUuid(String objectUuid) {
        this.objectUuid = objectUuid;
    }

    /** 分类的名字，可以使用中文和空格等特殊字符. */
    public String getName() {
        return this.name;
    }

    /** 分类的名字，可以使用中文和空格等特殊字符. */
    public void setName(String name) {
        this.name = name;
    }

    /** 分类中项目类型，也代表该分类应用给哪种对象. 参见类的说明. */
    public String getItemType() {
        return this.itemType;
    }

    /** 分类中项目类型，也代表该分类应用给哪种对象. 参见类的说明. */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /** 父分类标识. */
    public Integer getParentId() {
        return this.parentId;
    }

    /** 父分类标识. */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 得到分类自己的路径 = parentPath + id(36进制) + '/'
     * 
     * @return
     */
    public String getCategoryPath() {
        return this.parentPath + CategoryHelper.toPathString(this.categoryId) + "/";
    }

    /** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
    public String getParentPath() {
        return this.parentPath;
    }

    /** 父分类的整路径. 如/1/3/8/，指父分类为1，3，8. */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /** 分类在该层的排序值. */
    public int getOrderNum() {
        return this.orderNum;
    }

    /** 分类在该层的排序值. */
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /** 子分类数量. */
    public int getChildNum() {
        return this.childNum;
    }

    /** 子分类数量. */
    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    /** 分类的详细描述. */
    public String getDescription() {
        return this.description;
    }

    /** 分类的详细描述. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 此分类下内容数量. 对于博文分类指该分类下博文数量. 其它项目对象可以不统计（不使用此字段） */
    public int getItemNum() {
        return this.itemNum;
    }

    /** 此分类下内容数量. 对于博文分类指该分类下博文数量. 其它项目对象可以不统计（不使用此字段） */
    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Object getExtendedObject() {
        return extendedObject;
    }

    public void setExtendedObject(Object extendedObject) {
        this.extendedObject = extendedObject;
    }

    public void setChildCategoryList(List<Category> childCategoryList) {
        this.childCategoryList = childCategoryList;
    }
    
    public List<Category> getChildCategoryList() {
        return childCategoryList;
    }
}