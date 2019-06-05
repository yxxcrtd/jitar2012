package cn.edustar.jitar.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.dao.CategoryDao;
import cn.edustar.jitar.ex.CategoryException;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.EventManager;

/**
 * 系统分类服务接口的实现类.
 * 
 * @author yxx
 */
public class CategoryServiceImpl implements CategoryService {
	/** 日志记录器. */
	private static final Log logger = LogFactory.getLog(CategoryServiceImpl.class);
	
	/** Category DAO */
	private CategoryDao cate_dao;
	
	/** 事件服务. */
	private EventManager evt_mgr;
	
	/** 缓存服务. */
	private CacheService cache_svc;

	/** Category DAO */
	public void setCategoryDao(CategoryDao cate_dao) {
		this.cate_dao = cate_dao;
	}
	
	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 缓存服务 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

	public Category getCategory(int categoryId) {
		return cate_dao.getCategory(categoryId);
	}

	public Category getCategory(String uuId){
		return cate_dao.getCategory(uuId);
	}
	
	public Category getCategory(String cateName,String itemType,boolean isSystem)
	{
		return cate_dao.getCategory(cateName,itemType,isSystem);
	}

	public void createCategory(Category category) {
		if (category == null) 
			throw new IllegalArgumentException("category == null");
		boolean success = false;
		try {
			if (category.getCategoryId() != 0)
				throw new CategoryException("updateCategory for categoryId != 0");
			Integer parentId = category.getParentId();
			Category parent_category = null;
			if (parentId != null) {
				parent_category = cate_dao.getCategory(parentId);
			}
			
			// 逻辑验证.
			validateCreateCategory(category, parent_category);
			
			// 发布将要创建事件.
			evt_mgr.publishEvent(EVENT_CATEGORY_CREATING, this, category);
			
			// 实际创建分类对象.
			cate_dao.createCategory(category, parent_category);
			flushCacheForCategory(category);
			
			// 发布分类创建完成事件.
			evt_mgr.publishEvent(EVENT_CATEGORY_CREATED, this, category);
			
			success = true;
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("创建分类 category=" + category + ", success=" + success);
			}
		}
	}


	public void updateCategory(Category category) {
		// 简单参数验证.
		if (category == null) 
			throw new IllegalArgumentException("category == null");
		if (category.getCategoryId() == 0)
			throw new CategoryException("没有分类标识的分类对象, 应调用 createCategory() 方法");
		
		// 得到原始分类对象.
		Category origin_category = cate_dao.getCategory(category.getId());
		if (origin_category == null)
			throw new CategoryException("指定标识的分类不存在, id=" + category.getId());
		cate_dao.evict(origin_category);
		
		// 设置一些不需要外部更新的属性.
		setNotUpdateProperty(category, origin_category);

		// 更新, 可能带有移动操作.
		cate_dao.updateCategory(category, origin_category);
		cate_dao.flush();
		
		// 更新缓存.
		flushCacheForCategory(category);
	}
	
	// 设置一些不需要外部更新的属性.
	private void setNotUpdateProperty(Category category, Category origin_category) {
		category.setObjectUuid(origin_category.getObjectUuid());
		category.setItemType(origin_category.getItemType());
		category.setOrderNum(origin_category.getOrderNum());
		category.setChildNum(origin_category.getChildNum());
		category.setItemNum(origin_category.getItemNum());
		category.setParentPath(origin_category.getParentPath());
	}

	
	public void deleteCategory(Category category) {
		if (category == null) 
			throw new IllegalArgumentException("category == null");

		// 逻辑验证.
		validateDeleteCategory(category);

		// 发布将要创建事件.
		evt_mgr.publishEvent(EVENT_CATEGORY_DELETING, this, category);
		
		// 实际创建分类对象.
		cate_dao.deleteCategory(category);
		cate_dao.flush();
		flushCacheForCategory(category);
		
		// 发布分类创建完成事件.
		evt_mgr.publishEvent(EVENT_CATEGORY_DELETED, this, category);
		
		if (logger.isDebugEnabled()) {
			logger.debug("删除了分类 " + category);
		}
	}

	
	public void deleteCategoryTree(String itemType) {
		cate_dao.deleteCategoryByItemType(itemType);
		
		// 刷新缓存.
		String cache_key = cacheKeyForItemType(itemType);
		cache_svc.remove(cache_key);
	}

	// 验证是否能删除分类.
	private void validateDeleteCategory(Category category) {
		if (cate_dao.getChildrenCount(category.getCategoryId()) > 0)
			throw new CategoryException("不能删除带有子分类的分类");
	}

	// 在分类名中不能使用的非法字符.
	private String invalid_name_char = " )(*&^%$#@!~`=\\|/?<>;:\'\"{}[]";
	
	/**
	 * 对将要创建的分类进行验证.
	 * @param category
	 */
	private void validateCreateCategory(Category category, Category parent_category) throws CategoryException {
		// 验证分类名.
		String name = category.getName();
		if (name == null || name.length() == 0)
			throw new CategoryException("未给出分类名");
		for (int i = 0; i < name.length(); ++i) {
			if (invalid_name_char.indexOf(name.charAt(i)) >= 0)
				throw new CategoryException("分类名中包含有非法字符，例如 '*&>/' 等字符");
		}
		
		// 验证 itemType.
		String itemType = category.getItemType();
		if (itemType == null || itemType.length() == 0)
			throw new CategoryException("invalid itemTpe 未给出正确的分类项目类型(一般是系统内部错)");
		
		// 验证父分类是否存在.
		Integer parentId = category.getParentId();
		if (parentId != null) {
			if (parent_category == null)
				throw new CategoryException("parent Category does not exist itemTpe 要创建的分类的父分类不存在");
			
			// 验证父分类 itemType == itemType
			if (!itemType.equals(parent_category.getItemType()))
				throw new CategoryException("invalid parent and self itemTpe 父分类与当前分类类型不匹配");
		}
	}
	
	/** 得到指定项目类型的缓存键 */
	private static String cacheKeyForItemType(String item_type) {
		return "ctree_" + item_type;
	}
	
	private void flushCacheForCategory(Category category) {
		String cache_key = cacheKeyForItemType(category.getItemType());
		cache_svc.remove(cache_key);
	}
	
	
	public CategoryTreeModel getCategoryTree(String item_type) {
		return getCategoryTree(item_type, true);
	}

	/**
	 * 得到一个分类以及其子子孙孙的下级分类树
	 */
	public CategoryTreeModel getCategoryTree(String item_type,int rootCateId){
		
		// 得到该分类类型的所有分类.
		List<Category> cate_list = cate_dao.getChildCategoriesOnAndOn(item_type,rootCateId);
		
		// 从结果中创建分类树.
		CategoryTreeModel tree = CategoryTreeModel.createTree(item_type, cate_list.iterator());
		//cache_svc.put(cache_key, tree);
		return tree;		
	}
	
	public CategoryTreeModel getCategoryTree(String item_type, boolean fromCache) {
		// 尝试从缓存中获取.		
	    /**
	     * 
	     *  注意：Memcached 缓存有点问题！！！！！暂时不进行缓存	     *  
	     *  
	     */
	    fromCache = false;
		String cache_key = cacheKeyForItemType(item_type);
		//cache_svc.remove(cache_key);
		if (fromCache) {
			CategoryTreeModel tree = (CategoryTreeModel)cache_svc.get(cache_key);
			//System.out.println("cate_list1=" + tree);
			if (tree != null) return tree;
		}
		
		// 得到该分类类型的所有分类.
		List<Category> cate_list = cate_dao.getCategoryList(item_type);
		
		// 从结果中创建分类树.
		CategoryTreeModel tree = CategoryTreeModel.createTree(item_type, cate_list.iterator());
		
		//System.out.println("cate_list2=" + tree);
		cache_svc.put(cache_key, tree);
		return tree;
	}
	
	/**
	 * 将List<Category>转换为CategoryTreeModel
	 * @param cate_list
	 * @return
	 */
	public CategoryTreeModel getCategoryTree(List<Category> cate_list){
		CategoryTreeModel tree = CategoryTreeModel.createTree("default", cate_list.iterator());
		return tree;
	}
	
	
	public List<Category> getChildCategories(String item_type, Integer parentId) {
		return cate_dao.getChildCategories(item_type, parentId);
	}

	/**
	 * 得到指定类型的所有字分类
	 * @param item_type
	 * @return
	 */
	public List<Category> getCategories(String item_type){
		return cate_dao.getCategories(item_type);
	}
	
	public int getChildrenCount(int categoryId) {
		return cate_dao.getChildrenCount(categoryId);
	}
	

	public void getCategoryByMap(Map<Integer, Category> map) {
		cate_dao.getCategoryByMap(map);
	}
	
	/**
	 * 
	 */
	public void setCategoryOrder(int categoryId,int orderNo)
	{
		cate_dao.updateCategoryOrderNum(categoryId,orderNo);
	}
	
	public List<Integer> getCategoryIds(int categoryId){
		return cate_dao.getCategoryIds(categoryId);
	}
	
	
	public List<Category> showTree(String item_type){
	    return this.cate_dao.showTree(item_type);
	}
}
