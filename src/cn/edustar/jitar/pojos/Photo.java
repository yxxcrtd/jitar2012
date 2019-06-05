package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 照片实体对象
 * 
 *
 */
public class Photo extends Document implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 3450700103206904257L;

	/** 为操作方便，加入用户昵称. */
	private String userNickName;

	/** 为操作方便，加入用户真实姓名. */
	private String userTrueName;

	/** 个人相册分类标识，可能为 null. */
	private Integer userStaple;

	/** 系统分类标识，可能为 null. */
	private Integer sysCateId;

	/** 链接地址: 此地址相对于网站根路径, 如 '/admin/files/photo/1234.gif', 使用时候前面要加上 ${SiteUrl} */
	private String href;

	/** 图片宽度. */
	private int width;

	/** 图片高度. */
	private int height;

	/** 图片所占空间大小, 以字节为单位. */
	private int size;

	/** 发布本文档时的IP来源。 */
	private String addIp;

	/** 照片的删除状态：false-正常，true-已删除（在回收站中） */
	private boolean delState;
	
	/** 是否只在自己的空间里显示。true-只显示在自己的空间里，false 可以显示在总站 */
	private boolean isPrivateShow;
	
	/**
	 * 专题
	 */
	private int specialSubjectId;
	
	
	private Integer unitId;
	
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	/**
	 * 构造.
	 */
	public Photo() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Photo{id=" + this.getId() + ",title=" + this.getTitle() + ",user=" + this.getUserId() + "}";
	}
	
	/**
	 * 得到友好显示的字符串.
	 * 
	 * @return
	 */
	public String toDisplayString() {
		return "'" + this.getTitle() + "'(id=" + this.getPhotoId() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getObjectUuid().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || !(o instanceof Photo))
			return false;

		Photo other = (Photo) o;
		return this.getObjectUuid().equalsIgnoreCase(other.getObjectUuid());
	}

	// Property accessors

	/** 图片标识 */
	public int getPhotoId() {
		return super.getId();
	}

	/** 图片标识 */
	public void setPhotoId(int id) {
		super.setId(id);
	}

	/** 为操作方便，加入用户昵称. */
	public String getUserNickName() {
		return userNickName;
	}

	/** 为操作方便，加入用户昵称. */
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	/** 为操作方便，加入用户真实姓名. */
	public String getUserTrueName() {
		return userTrueName;
	}

	/** 为操作方便，加入用户真实姓名. */
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	/** 个人相册分类标识，可能为 null. */
	public Integer getUserStaple() {
		return userStaple;
	}

	/** 个人相册分类标识，可能为 null. */
	public void setUserStaple(Integer userStaple) {
		this.userStaple = userStaple;
	}

	/** 系统分类标识，可能为 null. */
	public Integer getSysCateId() {
		return sysCateId;
	}

	/** 系统分类标识，可能为 null. */
	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	/** 链接地址: 此地址相对于网站根路径, 如 '/admin/files/photo/1234.gif', 使用时候前面要加上 ${SiteUrl} */
	public String getHref() {
		return this.href;
	}

	/** 链接地址: 此地址相对于网站根路径, 如 '/admin/files/photo/1234.gif', 使用时候前面要加上 ${SiteUrl} */
	public void setHref(String href) {
		this.href = href;
	}

	/** 图片宽度. */
	public int getWidth() {
		return width;
	}

	/** 图片宽度. */
	public void setWidth(int width) {
		this.width = width;
	}

	/** 图片高度. */
	public int getHeight() {
		return height;
	}

	/** 图片高度. */
	public void setHeight(int height) {
		this.height = height;
	}

	/** 图片所占空间大小, 以字节为单位. */
	public int getSize() {
		return size;
	}

	/** 图片所占空间大小, 以字节为单位. */
	public void setSize(int size) {
		this.size = size;
	}

	/** 发布本文档时的IP来源。 */
	public String getAddIp() {
		return addIp;
	}

	/** 发布本文档时的IP来源。 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/** 照片的删除状态：false-正常，true-已删除（在回收站中） */
	public boolean getDelState() {
		return this.delState;
	}

	/** 照片的删除状态：false-正常，true-已删除（在回收站中） */
	public void setDelState(boolean delState) {
		this.delState = delState;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category sysCate;
	/** Hibernate 映射所需字段, 外面不需要访问 */
	Category getSysCate() {
		return this.sysCate;
	}
	/** Hibernate 映射所需字段, 外面不需要访问 */
	void setSysCate(Category sysCate) {
		// this.sysCate = sysCate;
	}
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private PhotoStaple staple;
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	PhotoStaple getStaple() {
		return this.staple;
	}
	/** Hibernate 映射所需字段, 外面不需要访问 */
	void setStaple(PhotoStaple staple) {
		// this.staple = staple;
	}

	public boolean getIsPrivateShow() {
		return isPrivateShow;
	}

	public void setIsPrivateShow(boolean isPrivateShow) {
		this.isPrivateShow = isPrivateShow;
	}

	public int getSpecialSubjectId() {
		return specialSubjectId;
	}

	public void setSpecialSubjectId(int specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}
	
}
