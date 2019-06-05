package cn.edustar.jitar.action;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 分类管理辅助类, 一般用于嵌入到别的类而非做为基类
 */
public class CategoryActionHelper {
	/** 分类服务对象, 必须从外部注入 */
	private CategoryService cate_svc;
	
	/** 分类服务对象, 必须从外部注入 */
	public CategoryService getCategoryService() {
		return cate_svc;
	}
	
	/** 分类服务对象, 必须从外部注入 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}

	/**
	 * 新建一个分类对象.
	 * @return
	 */
	public Category newCategory() {
		Category category = new Category();
		category.setCategoryId(0);
		category.setName("");
		category.setOrderNum(0);
		category.setItemType("");
		return category;
	}
	
	/**
	 * 从提交的数据中收集数据.
	 * 
	 * @param category
	 */
	public Category collectCategoryObject(ParamUtil param_util) {
		Category category = new Category();
		category.setCategoryId(param_util.getIntParam("categoryId"));
		category.setName(param_util.safeGetStringParam("name"));
		category.setItemType(param_util.safeGetStringParam("itemType"));
		category.setParentId(param_util.getIntParamZeroAsNull("parentId"));
		category.setDescription(param_util.safeGetStringParam("description"));
		return category;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.CategoryService#getCategory(int)
	 */
	public Category getCategory(int cateId) {
		return cate_svc.getCategory(cateId);
	}

	public CategoryTreeModel getCategoryTreeCached(String item_type) {
		return cate_svc.getCategoryTree(item_type);
	}

	public CategoryTreeModel getCategoryTreeUncached(String item_type) {
		return cate_svc.getCategoryTree(item_type, false);
	}
}
