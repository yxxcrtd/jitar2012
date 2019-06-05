package cn.edustar.jitar.model;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.Resource;

/**
 * 添加了扩展属性的 ResourceModel.
 *
 * 里面的对象能否访问取决于 获得此对象的方法是否设置了对应的值, 否则初始化为 null.
 */
public class ResourceModelEx extends ResourceModel {
	/**
	 * 包装给定的 resource 对象为 ResourceModelEx
	 * @param resource
	 * @return
	 */
	public static final ResourceModelEx wrap(Resource resource) {
		if (resource == null) return null;
		return new ResourceModelEx(resource);
	}
	
	public static final ResourceModelEx wrap(ResourceModel resource) {
		if (resource == null) return null;
		return new ResourceModelEx(resource._getResourceObject());
	}
	
	protected ResourceModelEx(Resource resource) {
		super(resource);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ResourceModelEx{resource=" + document +
			",sys_cate=" + sys_cate +
			",user_cate=" + user_cate +
			",grp_res=" + group_resource +
			"}";
	}
	
	/** 系统分类对象 */
	private Category sys_cate;
	
	/** 系统分类对象 */
	public void setSystemCategory(Category category) {
		this.sys_cate = category;
	}
	
	/** 系统分类对象 */
	public Category getSystemCategory() {
		return this.sys_cate;
	}
	
	/** 用户分类 */
	private Category user_cate;
	
	/** 用户分类 */
	public void setUserCategory(Category user_cate) {
		this.user_cate = user_cate;
	}
	
	/** 用户分类 */
	public Category getUserCategory() {
		return this.user_cate;
	}

	/** GroupResource 对象 */
	private GroupResource group_resource;
	
	/** GroupResource 对象 */
	public void setGroupResource(GroupResource group_resource) {
		this.group_resource = group_resource;
	}
	
	/** GroupResource 对象 */
	public GroupResource getGroupResource() {
		return this.group_resource;
	}

	/** 获得是否群组中最佳资源 */
	public boolean getIsGroupBest() {
		return group_resource.getIsGroupBest();
	}

	/** 资源在群组中的分类 */
	private Category group_cate;
	
	/** 资源在群组中的分类 */
	public Category getGroupCategory() {
		return group_cate;
	}
	
	/** 资源在群组中的分类 */
	public void setGroupCategory(Category cate) {
		this.group_cate = cate;
	}
}
