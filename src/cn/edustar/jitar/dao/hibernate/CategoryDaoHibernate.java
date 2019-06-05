package cn.edustar.jitar.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.dao.CategoryDao;
import cn.edustar.jitar.ex.CategoryException;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.service.CategoryHelper;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 系统分类使用 Hibernate 的数据访问对象的实现
 *
 * @author Yang Xinxin
 * @version 1.0.0 Aug 6, 2008 8:47:10 AM
 */
public class CategoryDaoHibernate extends BaseDaoHibernate implements CategoryDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.CategoryDao#getCategory(int)
	 */
	public Category getCategory(int categoryId) {
		//return (Category) getSession().get(Category.class, categoryId);
		String hql="FROM Category WHERE categoryId=?";
		List list = this.getSession().createQuery(hql).setInteger(0, categoryId).list();
		if (list == null || list.size() == 0)
			return null;
		return (Category) list.get(0);
		
	}

	public Category getCategory(String categoryUuid) {
		String hql="FROM Category WHERE objectUuid=?";
		List list = this.getSession().createQuery(hql).setString(0, categoryUuid).list(); 
		if (list == null || list.size() == 0)
			return null;
		return (Category) list.get(0);
		
	}	
	
	public Category getCategory(String cateName,String itemType,boolean isSystem){
		String hql="FROM Category WHERE name=:name and itemType=:itemType and isSystem=:isSystem";
		List list =  this.getSession().createQuery(hql).setString("name", cateName).setString("itemType", itemType).setBoolean("isSystem", isSystem).list();
		if (list == null || list.size() == 0)
			return null;
		return (Category) list.get(0);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.CategoryDao#getCategoryNames(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getCategoryNames(List<Integer> cate_ids) {
		// 参数判断.
		Map<Integer, String> result = new java.util.HashMap<Integer, String>();
		if (cate_ids == null || cate_ids.size() == 0)
			return result;

		// 组装 HQL IN (xxx) 子句中的 xxx.
		String id_hql = CommonUtil.toSqlInString(cate_ids);

		// 查找出结果.
		String hql = "SELECT categoryId, name FROM Category WHERE categoryId IN " + id_hql;
		List<Object[]> list = (List<Object[]>) this.getSession().createQuery(hql).list();

		// 组装结果为 Map<Integer, String> 格式.  ? 也许组装可以让外面做?
		for (int i = 0; i < list.size(); ++i) {
			Object[] row = list.get(i);
			result.put((Integer) row[0], (String) row[1]);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.CategoryDao#getCategoryList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategoryList(String itemType) {
		
		String hql;
		if(itemType.equals(CategoryService.RESOURCE_TYPE_TYPE))
		{
			hql = "select new cn.edustar.jitar.pojos.Category(tcId,tcTitle,tcParent) FROM ResType " +
			" ORDER BY tcSort ";
			return this.getSession().createQuery(hql).list();
		}
		else
		{
			hql = " FROM Category " +
			" WHERE itemType = :itemType " +
			" ORDER BY parentPath, orderNum, categoryId ";
			//System.out.println("hql="+hql); 
			return this.getSession().createQuery(hql).setString("itemType",itemType).list();
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.CategoryDao#getChildCategories(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getChildCategories(String item_type, Integer cate_id) {
		String hql = "";
		if (cate_id == null) {
			hql = "FROM Category WHERE itemType = :itemType AND parentId IS NULL ORDER BY orderNum, categoryId";
		} else {
			hql = "FROM Category WHERE itemType = :itemType AND parentId = " + cate_id + " ORDER BY orderNum, categoryId";
		}
		return this.getSession().createQuery(hql).setString("itemType",item_type).list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategories(String item_type){
		String hql = "";
		hql = "FROM Category WHERE itemType = :itemType ORDER BY orderNum, categoryId";
		return this.getSession().createQuery(hql).setString("itemType",item_type).list();		
	}
	
	/**
	 * 得到分类，包含了cate_id的子子孙孙的分类
	 * @param item_type
	 * @param cate_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getChildCategoriesOnAndOn(String item_type, Integer cate_id) {
		String hql = "";
		//System.out.println("getChildCategoriesOnAndOn"+hql);
		if (cate_id == null) {
			hql = "FROM Category WHERE itemType = :itemType ORDER BY parentId,orderNum,categoryId";
		} else {
			String catePath = "/";
			catePath = CategoryHelper.toPathString(cate_id);
			hql = "FROM Category WHERE itemType = :itemType AND (categoryId = " + cate_id + " OR ParentPath LIKE '%/" + catePath + "/%') ORDER BY parentId,orderNum, categoryId";
			//System.out.println(""+hql);
		}
		return this.getSession().createQuery(hql).setString("itemType",item_type).list();
	}
	
	/**
	 * 计算指定分类类型下 一级分类的下一个排序号.
	 * @param item_type
	 * @return
	 */
	private int nextOrderNum(String item_type) {
		String hql = "SELECT MAX(orderNum) FROM Category WHERE itemType = :itemType";
		Object o = this.getSession().createQuery(hql).setString("itemType",item_type).uniqueResult();
		if(o == null) return 1;
		return 1 + Integer.valueOf(o.toString()).intValue();
	}
	
	/**return ;
	 * 计算指定分类类型下 指定父分类的下一个排序号.
	 * @param item_type
	 * @return
	 */
	private int nextOrderNum(String item_type, int parentId) {
		String hql = "SELECT MAX(orderNum) FROM Category WHERE itemType = :itemType AND parentId = " + parentId;
		Object o = this.getSession().createQuery(hql).setString("itemType",item_type).uniqueResult();
		if(o == null) return 1;
		return 1 + Integer.valueOf(o.toString()).intValue();
	}
	
	/**
	 * 计算指定分类的分类路径. 分类路径 = 父分类路径 + c.id(36进制) + "/" .
	 * @param c
	 * @return 为方便使用，全部转换为大写.
	 */
	public static final String calcCategoryPath(Category c) {
		return (c.getParentPath() + Integer.toString(c.getCategoryId(), 36) + "/").toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.CategoryDao#createCategory(cn.edustar.jitar.pojos.Category, cn.edustar.jitar.pojos.Category)
	 */
	public void createCategory(Category category, Category parent_category) {
		// 新增, 则 categoryId, childNum, itemNum 都 = 0
		category.setCategoryId(0);
		category.setChildNum(0);
		category.setItemNum(0);
		
		// 更新父分类子分类数量，如果有父分类的话
		Integer parentId = category.getParentId();
		if (parentId == null) {
			// 父分类没有，则
			category.setParentPath("/");	// 一级分类一定如此
			category.setOrderNum(nextOrderNum(category.getItemType()));
		} else {
			// 有父分类，则验证必须有参数、分类标识符合、分类类型符合
			if (parent_category == null)
				throw new IllegalArgumentException("parent_category == null");
			if (parent_category.getCategoryId() != parentId.intValue())
				throw new CategoryException("不正确的父分类");
			if (!parent_category.getItemType().equals(category.getItemType()))
				throw new CategoryException("分类与其父分类的分类类型不符合");
			
			// 设置正确的 parentPath, orderNum
			category.setParentPath(calcCategoryPath(parent_category));
			category.setOrderNum(nextOrderNum(parent_category.getItemType(), parent_category.getCategoryId()));
		}		
		// 实际保存分类对象
		this.getSession().save(category);
		
		// 如果有父分类，更新父分类的子分类数量
		if (parentId != null)
			updateParentChildCount(parentId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#updateCategory(cn.edustar.jitar.pojos.Category, cn.edustar.jitar.pojos.Category)
	 */
	public void updateCategory(Category category, Category origin_category) {
		// 简单更新，不涉及移动.
		if (CommonUtil.equals(category.getParentId(), origin_category.getParentId()) ) {
			this.getSession().update(category);
			return;
		}
		
		// 得到新父分类.
		Category new_parent = null;
		if (category.getParentId() != null) {
			new_parent = getCategory(category.getParentId());
			if (new_parent != null)
				this.evict(new_parent);
		}
		
		// 判断是否可以移动, 例如不能移动到自己的子分类下面.
		checkCanMove(category);
		
		// 计算新的父分类下的排序值.
		int orderNum = 1 + getLastOrderNum(category.getItemType(), category.getParentId());
		String parentPath = CategoryHelper.calcCategoryPath(new_parent);
		
		// 更新子孙的 parentPath
		updateAllChildrenParentPath(origin_category, parentPath);
		
		// 更新自己
		category.setOrderNum(orderNum);
		category.setParentPath(parentPath);
		this.getSession().update(category);
		
		// 更新原父分类子分类数量.
		if (origin_category.getParentId() != null)
			updateParentChildCount(origin_category.getParentId());
		
		// 更新新父分类子分类数量.
		if (category.getParentId() != null)
			updateParentChildCount(category.getParentId());
	}
	
	/**
	 * 检测是否可以将分类移动到它现在指定的父分类下.
	 * @return
	 */
	private void checkCanMove(Category category) {
		// 肯定能够移动到根分类下的.
		if (category.getParentId() == null)
			return;
		
		// 自己不能移动到自己下面.
		if (category.getCategoryId() == category.getParentId())
			throw new RuntimeException("不能将分类移动成为自己的子分类");
		
		// 遍历其所有父分类, 如果有一个是自己, 则不能移动.
		Integer parentId = category.getParentId();
		while (parentId != null) {
			if (parentId == category.getCategoryId())
				throw new RuntimeException("不能把分类移动到自己的子分类下面.");
				
			Category parent_category = this.getCategory(parentId);
			if (parent_category == null)
				throw new RuntimeException("父分类不存在, 可能数据库记录不合法.");
			this.evict(parent_category);
			
			// 继续验证父分类的父分类.
			parentId = parent_category.getParentId();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#deleteCategory(cn.edustar.jitar.pojos.Category)
	 */
	public void deleteCategory(Category category) {
		/**系统分类不允许删除*/	
		if(category.getIsSystem()==true){
			return;
		}
		Integer parentId = category.getParentId();
		
		// 根据 category.itemType 设置相应表中的引用.
		if (CategoryService.BLOG_CATEGORY_TYPE.equalsIgnoreCase(category.getItemType()))
			setBlogCategoryToNull(category);
		else if (CategoryService.ARTICLE_CATEGORY_TYPE.equalsIgnoreCase(category.getItemType()))
			setArticleCategoryToNull(category);
		else if (CategoryService.GROUP_CATEGORY_TYPE.equalsIgnoreCase(category.getItemType()))
			setGroupCategoryToNull(category);
		else if (CategoryService.RESOURCE_CATEGORY_TYPE.equalsIgnoreCase(category.getItemType()))
			setResourceCategoryToNull(category);
		else if (CategoryService.PHOTO_CATEGORY_TYPE.equalsIgnoreCase(category.getItemType()))
			setPhotoCategoryToNull(category);
		
		// 删除分类本身.
		this.getSession().delete(category);
		
		// 如果有父分类, 重新计算父分类的子分类数量, 并更新该数量.
		if (parentId != null)
			updateParentChildCount(parentId);
	}

	// 设置引用了该分类的工作室、文章的分类属性为 null.
	private void setBlogCategoryToNull(Category category) {
		// 消除所有工作室对该分类的引用.
		String hql = "UPDATE User SET categoryId = NULL WHERE categoryId = :categoryId";
		int count1 = this.getSession().createQuery(hql).setInteger("categoryId",  category.getCategoryId()).executeUpdate();
		//int count1 = getSession().bulkUpdate(hql,category.getCategoryId());
		
	}
	
	// 设置引用了该分类的文章的分类属性为 null.
	private void setArticleCategoryToNull(Category category) {
		// 消除文章对该分类的引用.
		String hql = "UPDATE Article SET sysCateId = NULL WHERE sysCateId = :sysCateId";
		int count2 = this.getSession().createQuery(hql).setInteger("sysCateId",  category.getCategoryId()).executeUpdate();
		
	}
	
	// 设置引用了该分类的协作组的分类属性为 null.
	private void setGroupCategoryToNull(Category category) {
		// 消除协作组对该分类的引用.
		String hql = "UPDATE Group SET categoryId = NULL WHERE categoryId = :categoryId";
		int count = this.getSession().createQuery(hql).setInteger("categoryId", category.getCategoryId()).executeUpdate();
		
	}

	// 设置引用了该分类的资源的分类属性为 null.
	private void setResourceCategoryToNull(Category category) {
		// 消除资源对该分类的引用.
		String hql = "UPDATE Resource SET sysCateId = NULL WHERE sysCateId = :sysCateId";
		int count = this.getSession().createQuery(hql).setInteger("sysCateId",  category.getCategoryId()).executeUpdate();
		
		
	}

	// 设置引用了该分类的图片的分类属性为 null.
	private void setPhotoCategoryToNull(Category category) {
		// 消除图片对该分类的引用.
		String hql = "UPDATE Photo SET sysCateId = NULL WHERE sysCateId = :sysCateId";
		int count = this.getSession().createQuery(hql).setInteger("sysCateId",  category.getCategoryId()).executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#deleteCategoryByItemType(java.lang.String)
	 */
	public void deleteCategoryByItemType(String itemType) {
		if (itemType == null) itemType = "";
		/**不能删除系统分类！*/
		String hql = "DELETE FROM Category WHERE isSystem=false and itemType = :itemType";
		int count = this.getSession().createQuery(hql).setString("itemType", itemType).executeUpdate();
		
	}

	
	/**
	 * 重新计算指定父分类的子分类数量, 并更新该数量.
	 * @param parentId
	 */
	private void updateParentChildCount(int parentId) {
		// 计算子分类数量.
		int childNum = getChildrenCount(parentId);
		
		// 更新父分类的子分类数量
		String update_hql = "UPDATE Category SET childNum = :childNum WHERE categoryId = :categoryId";
		int result = this.getSession().createQuery(update_hql).setInteger("childNum", childNum).setInteger("categoryId", parentId).executeUpdate();// getSession().bulkUpdate(update_hql, new Object[] {childNum, parentId} );
		if (result != 1)
			throw new CategoryException("期待更新一条记录，但返回的更新数 = " + result);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#getCategoryByMap(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void getCategoryByMap(Map<Integer, Category> map) {
		if (map == null || map.size() == 0) return;
		
		// 组装 SQL IN 子句.
		StringBuffer strbuf = new StringBuffer();
		for (Integer cid : map.keySet()) {
			strbuf.append(cid).append(",");
		}
		strbuf.setLength(strbuf.length() - 1);	// 去掉最后一个 ','
		String in_str = strbuf.toString();
		
		String hql = "FROM Category WHERE categoryId IN (" + in_str + ")";
		List<Category> list = this.getSession().createQuery(hql).list();
		
		for (int i = 0; i < list.size(); ++i) {
			Category category = list.get(i);
			Integer cid = category.getCategoryId();
			map.put(cid, category);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#getChildrenCount(int)
	 */
	public int getChildrenCount(int categoryId) {
		String hql = "SELECT COUNT(*) FROM Category WHERE parentId = :parentId";
		Object o = this.getSession().createQuery(hql).setInteger("parentId",categoryId).uniqueResult();
		if(o == null) return 0;
		return Integer.valueOf(o.toString()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.CategoryDao#getLastOrderNum(cn.edustar.jitar.pojos.Category)
	 */
	private int getLastOrderNum(String itemType, Integer parentId) {
		if (parentId == null) {
			String hql = "SELECT MAX(orderNum) FROM Category WHERE itemType = :itemType AND parentId IS NULL";
			Object o = this.getSession().createQuery(hql).setString("itemType",itemType).uniqueResult();
			if(o == null) return 0;
			return Integer.valueOf(o.toString()).intValue();
			
		} else {
			String hql = "SELECT MAX(orderNum) FROM Category WHERE itemType = :itemType AND parentId = :parentId";
			Object o = this.getSession().createQuery(hql).setString("itemType",itemType).setInteger("parentId", parentId).uniqueResult();
			if(o == null) return 0;
			return Integer.valueOf(o.toString()).intValue();
		}
	}

	/**
	 * 更新 category 之下所有子孙节点的 parentPath, 前面部分替换为所给参数 parentPath.
	 * @param category
	 * @param parentPath
	 */
	private void updateAllChildrenParentPath(Category category, String parentPath) {
		// 示例: category = 12(/1/), 其子孙节点有  13(/1/C/), 14(/1/C), 15(/1/C/D/)
		//   新 parentPath = '/3/', 也即将 category 移动到新的父节点 3(/3/) 下面
		// 
		// 节点标识  原父路径   新父路径
		//  12     /1/      /3/
		//   13    /1/C/	/3/C/
		//   14    /1/C/    /3/C/
		//   15    /1/C/D/  /3/C/D/
		
		// 计算原 category 路径前缀 (如: /1/)
		String old_path = category.getParentPath();
		
		// 得到所有子孙节点.
		List<Object[]> all_child = internalGetAllChildren(category);
		for (Object[] o : all_child) {
			// o[0] 分类标识, o[1] 分类 parentPath
			Integer categoryId = (Integer)o[0];
			String path = (String)o[1];
			
			// 新路径 = '/3/' + '/1/C/'(去掉 '/1/') 部分. 
			String new_path = parentPath + path.substring(old_path.length());
			
			// 更新数据库.
			String hql = "UPDATE Category SET parentPath = :parentPath WHERE categoryId = :categoryId";
			this.getSession().createQuery(hql).setString("parentPath", new_path).setInteger("categoryId", categoryId);
			//getSession().bulkUpdate(hql, new Object[]{new_path, categoryId});
		}
	}
	
	/**
	 * 得到指定分类的所有子孙分类.
	 * @param category
	 * @return 返回 List&lt;Object[categoryId, parentPath]&gt;
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> internalGetAllChildren(Category category) {
		String like = category.getCategoryPath() + "%";
		String hql = "SELECT categoryId, parentPath " +
			" FROM Category " +
			" WHERE itemType = :itemType " +
			"   AND parentPath LIKE :parentPath";
		return this.getSession().createQuery(hql).setString("itemType", category.getItemType()).setString("parentPath", like).list();
		//return getSession().find(hql, new Object[]{category.getItemType(), like});
	}
	
	/**
	 * 设置分类的排序号码.
	 * @param Id
	 */
	public void updateCategoryOrderNum(int categoryId,int orderNo) {
		// 更新
		String update_hql = "UPDATE Category SET orderNum = :orderNum WHERE categoryId = :categoryId";
		int result = this.getSession().createQuery(update_hql).setInteger("orderNum", orderNo).setInteger("categoryId", categoryId).executeUpdate();
				
		if (result != 1)
			throw new CategoryException("期待更新一条记录，但返回的更新数 = " + result);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getCategoryIds(int categoryId){
		Category category=getCategory(categoryId);
		List<Integer> list=null;
		if(category==null){
			list=new ArrayList<Integer>();
			list.add(categoryId);
		}else{
			String like = category.getCategoryPath() + "%";
			String hql = "SELECT categoryId " +
				" FROM Category " +
				" WHERE itemType = :itemType " +
				"   AND parentPath LIKE :parentPath";
			list=this.getSession().createQuery(hql).setString("itemType", category.getItemType()).setString("parentPath", like).list();
			list.add(0, categoryId);
		}
		return list;
	}
	
	@Override
	public void evict(Object object) {
		this.getSession().evict(object);		
	}

	@Override
	public void flush() {
		this.getSession().flush();
	}	
	
	
	/**
     * 得到某分类的树形数据结构。
     * @param item_type
     * @return
     */ 
	public List<Category> showTree(String item_type){	  
        List<Category> list = (List<Category>) this.getChildCategories(item_type,null);  
        for (Category t : list) {  
            if (t.getChildNum() > 0) {  
                t.setChildCategoryList(this.tree(item_type, t.getCategoryId()));               
            }
            else
            {
                t.setChildCategoryList(null);
            }                
        }  
        return list;  	    
	}
    private List<Category> tree(String item_type, Integer parentCategoryId){
        List<Category> list = (List<Category>) this.getChildCategories(item_type, parentCategoryId);  
        for (Category t : list) {  
            if (t.getChildNum() > 0) {  
                t.setChildCategoryList(this.tree(item_type, t.getCategoryId()));               
            }
            else
            {
                t.setChildCategoryList(null);
            }
        }  
        return list;  
    }  
}
