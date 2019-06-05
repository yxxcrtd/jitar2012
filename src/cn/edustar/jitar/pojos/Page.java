package cn.edustar.jitar.pojos;

import java.io.Serializable;
import java.util.Date;

import cn.edustar.jitar.model.ObjectType;

/**
 * Page entity.
 * 
 * @author 孟宪会，暂时未注释. */
public class Page implements Serializable, Cloneable {
	// Fields

	/** serialVersionUID */
	private static final long serialVersionUID = 5592027505263923098L;
	
	/** 页面标识，主键，自动产生. */
	private int pageId;
	
	/** 页面的名字，特殊名字如 'index' 用于主页. 必须符合 java变量名规则. */
	private String name;
	
	/** 页面标题. */
	private String title;
	
	/** 拥有此页面的对象类型，参见 ObjectType 定义. */
	private int objType;
	
	/** 拥有此页面的对象标识，可能是用户、群组、学科等 */
	private int objId;
	
	/** 页面描述. */
	private String description;
	
	/** 创建时间 **/
	private Date createDate;
	
	/** 显示顺序 **/
	private short itemOrder;
	
	/** 布局, 是一个编号, 定位到 '/WEB-INF/layout/layout_$layoutId.ftl' **/
	private Integer layoutId;
	
	/** 页面显示主题 */
	private String skin = "default";
	
	/** 用户自定义配置，JSON格式 */
	private String customSkin;
	// Constructors

	/** default constructor */
	public Page() {
	}

	protected Page(Page src) {
		this.pageId = src.pageId;
		this.name = src.name;
		this.title = src.title;
		this.objType = src.objType;
		this.objId = src.objId;
		this.description = src.description;
		this.createDate = src.createDate;
		this.itemOrder = src.itemOrder;
		this.layoutId = src.layoutId;
		this.skin = src.skin;	
		this.customSkin = src.customSkin;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Page{id=" + this.pageId + ",name=" + this.name + 
			",objId=" + this.objId + ",objType=" + this.objType + "}";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Page clone() {
		return new Page(this);
	}
	
	/**
	 * 兼容 PageModel.
	 * @return
	 */
	public Page _getPageObject() {
		return this;
	}
	
	// Property accessors

	/** 页面标识，主键，自动产生. */
	public int getId() {
		return pageId;
	}
	
	/** 页面标识，主键，自动产生. */
	public int getPageId() {
		return this.pageId;
	}

	/** 页面标识，主键，自动产生. */
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	/** 页面的名字，特殊名字如 'index' 用于主页. 必须符合 java变量名规则. */
	public String getName() {
		return this.name;
	}

	/** 页面的名字，特殊名字如 'index' 用于主页. 必须符合 java变量名规则. */
	public void setName(String name) {
		this.name = name;
	}

	/** 页面标题. */
	public String getTitle() {
		return this.title;
	}

	/** 页面标题. */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 拥有此页面的对象类型，参见 ObjectType 定义. */
	public int getObjType() {
		return this.objType;
	}
	
	/** 拥有此页面的对象类型，参见 ObjectType 定义. */
	public void setObjType(int objType) {
		this.objType = objType;
	}

	/** 拥有此页面的对象标识，可能是用户、群组、学科等 */
	public int getObjId() {
		return this.objId;
	}

	/** 拥有此页面的对象标识，可能是用户、群组、学科等 */
	public void setObjId(int objId) {
		this.objId = objId;
	}

	/** 页面描述. */
	public String getDescription() {
		return this.description;
	}

	/** 页面描述. */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 创建时间 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 创建时间 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 显示顺序 **/
	public short getItemOrder() {
		return itemOrder;
	}

	/** 显示顺序 **/
	public void setItemOrder(short itemOrder) {
		this.itemOrder = itemOrder;
	}

	/** 布局 **/
	public Integer getLayoutId() {
		return layoutId;
	}

	/** 布局 **/
	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}

	/** 页面显示主题 */
	public String getSkin() {
		return this.skin;
	}

	/** 页面显示主题 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 是否是一个系统页面.
	 * @return
	 */
	public boolean getIsSystemPage() {
		return this.objType == ObjectType.OBJECT_TYPE_SYSTEM.getTypeId();
	}

	public String getCustomSkin() {
		return customSkin;
	}

	public void setCustomSkin(String customSkin) {
		this.customSkin = customSkin;
	}
}