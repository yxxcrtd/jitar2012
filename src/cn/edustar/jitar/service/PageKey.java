package cn.edustar.jitar.service;

import cn.edustar.jitar.model.ObjectType;

/**
 * 用于表示页面服务查找一个页面时候的键的组合
 * 
 *
 * 查找一个唯一页面的键为 ObjectType, ObjectId, PageName 的组合
 */
public class PageKey {
	/** 系统级的 用户首页模板页面(用于给其它用户复制一份初始化的页面) */
	public static final PageKey SYSTEM_USER_INDEX = 
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.index");	
		
	/** 系统级的 用户博文模板页面 */
	public static final PageKey SYSTEM_USER_ENTRY = 
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.entry");
	
	/** 系统级的 用户分类模板页面 */
	public static final PageKey SYSTEM_USER_CATEGORY =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.category");
	
	/** 系统级的 用户资源分类页面 */
	public static final PageKey SYSTEM_USER_RESCATE = 
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.rescate");

	/** 系统级的 用户视频分类页面 */
	public static final PageKey SYSTEM_USER_VIDEOCATE = 
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.videocate");
	
	/** 系统级的 用户档案页面. */
	public static final PageKey SYSTEM_USER_PROFILE =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "user.profile");
	
	/** 系统级的 群组首页模板页面(用于给其它新建群组复制一份初始化的页面) */
	public static final PageKey SYSTEM_GROUP_INDEX =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "group.index");
	
	/** 系统级的 群组首页模板页面(用于给其它新建群组复制一份初始化的页面) */
	public static final PageKey SYSTEM_GROUPKT_INDEX =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "groupkt.index");
	
	/** (未实现) 系统级的 协作组文章页面 */
	public static final PageKey SYSTEM_GROUP_ARTICLE_INDEX = 
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "group.showart");
	
	/** 系统级的 协作组文章分类页面 */
	public static final PageKey SYSTEM_GROUP_ARTICLE_CATEGORY =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "@group.artcate");
	
	/** 系统级的 协作组资源分类页面 */
	public static final PageKey SYSTEM_GROUP_RESOURCE_CATEGORY =
		new PageKey(ObjectType.OBJECT_TYPE_SYSTEM, 0, "@group.rescate");
	
	/** 对象类型，必须给出 */
	private final ObjectType obj_type;
	
	/** 对象标识 */
	private final int obj_id;
	
	/** 页面名称 */
	private final String page_name;
	
	/**
	 * 使用指定的对象类型、对象标识、页面名称构造一个 PageKey 的新实例
	 * @param obj_type
	 * @param obj_id
	 * @param page_name
	 */
	public PageKey(ObjectType obj_type, int obj_id, String page_name) {
		this.obj_type = obj_type;
		this.obj_id = obj_id;
		this.page_name = page_name;
	}
	
	/**
	 * 使用指定的对象类型、对象标识、页面名称构造一个 PageKey 的新实例
	 * @param obj_type_id
	 * @param obj_id
	 * @param page_name
	 */
	public PageKey(int obj_type_id, int obj_id, String page_name) {
		this.obj_type = ObjectType.fromTypeId(obj_type_id);
		this.obj_id = obj_id;
		this.page_name = page_name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "PageKey{objType=" + this.obj_type + ",objId=" + this.obj_id +
			",pageName=" + this.page_name + "}";
	}
	
	/**
	 * 得到对象类型
	 * @return
	 */
	public ObjectType getObjectType() {
		return this.obj_type;
	}
	
	/**
	 * 得到对象标识
	 * @return
	 */
	public int getObjectId() {
		return this.obj_id;
	}
	
	/**
	 * 得到页面名称
	 * @return
	 */
	public String getPageName() {
		return this.page_name;
	}
}
