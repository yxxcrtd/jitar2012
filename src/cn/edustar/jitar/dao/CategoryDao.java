package cn.edustar.jitar.dao;

import java.util.Map;
import java.util.List;

import cn.edustar.jitar.pojos.Category;

/**
 * 系统分类 DAO 接口.
 * 
 * @author yxx
 */
public interface CategoryDao extends Dao {
	/**
	 * 得到指定标识的分类.	 * 
	 * @param categoryId分类标识.
	 * @return 如果分类存在则返回 Category 对象，否则返回 null.	 */
	public Category getCategory(int categoryId);
	public Category getCategory(String uuid);
	public Category getCategory(String cateName,String itemType,boolean isSystem);
	/**
	 * 创建一个新的分类.
	 * 
	 * @param category - 要创建的分类.
	 * @param parent_category - 父分类，如果没有，则给出为 null (实现中会验证)
	 */
	public void createCategory(Category category, Category parent_category);

	/**
	 * 更新/移动一个分类.
	 * @param category - 分类对象.
	 * @param origin_category - 原分类对象.
	 */
	public void updateCategory(Category category, Category origin_category);
	
	/**
	 * 删除一个分类.
	 * 注意: 如果删除的是 'default', 'group', 'resource', 'photo' 类型的分类, 
	 *   则会把对应的工作室、文章等对象的引用分类设置为 null, 称之为消除引用.
	 * TODO: 当前将消除引用放到 DAO 里面完成有点不太合适, 但是为了能够解决事务处理,
	 *   暂时先这样实现.
	 * @param category
	 */
	public void deleteCategory(Category category);

	/**
	 * 删除指定分类类型的所有分类.
	 * @param itemType
	 */
	public void deleteCategoryByItemType(String itemType);
	
	/**
	 * 得到指定的一组标识的分类的名字，注意：分类标识可能有重复及空.	 * 
	 * @param cate_ids - 分类标识的数组.	 * @return 返回为分类标识、名字的映射Map&lt;Integer,String&gt;Integer的Key为分类标识，String的Value为分类名称. 可能为null.	 */
	public Map<Integer, String> getCategoryNames(List<Integer> cate_ids);

	/**
	 * 得到指定项目类型的分类树状表示的数据.	 *   注意顺序是按照 parentPath, orderNum 来排序的，用于构造 CategoryTreeModel.
	 *   
	 * @param item_type - 项目类型，参见 Category 类说明.	 * @return 返回为一个 List，其中每项是一个 Category.
	 */
	public List<Category> getCategoryList(String item_type);

	/**
	 * 得到指定分类的子分类. 按照其 orderNum 排序
	 * 	 * @param item_type - 分类类型	 * @param cate_id - 父分类标识	 * @return 返回子分类集合	 */
	public List<Category> getChildCategories(String item_type, Integer cate_id);

	/**
	 * 得到指定类型的所有字分类
	 * @param item_type
	 * @return
	 */
	public List<Category> getCategories(String item_type);
	
	/**
	 * 得到指定分类以及其子子孙孙的下级分类. 
	 * @param item_type
	 * @param cate_id
	 * @return
	 */
	public List<Category> getChildCategoriesOnAndOn(String item_type, Integer cate_id);
	
	/**
	 * 通过给出一个 map 来获得多个分类的信息，得到的分类也放在该 map 中.
	 *   此方法相当于调用多次 getCategory(cid) 然后放在 map 中.
	 * @param map
	 */
	public void getCategoryByMap(Map<Integer, Category> map);

	/**
	 * 得到指定分类的子分类数量.
	 * @param categoryId - 分类标识.
	 * @return 返回该分类的子分类的数量.
	 */
	public int getChildrenCount(int categoryId);
	
	/**
	 * 更新分类的排序
	 * @param categoryId
	 * @param orderNo
	 */
	public void updateCategoryOrderNum(int categoryId,int orderNo);
	/**
	 * 得到分类Id以及下级分类Id
	 * @param categoryId
	 * @return
	 */
	public List<Integer> getCategoryIds(int categoryId);
	
	/**
     * 得到某分类的树形数据结构。
     * @param item_type
     * @return
     */ 
	public List<Category> showTree(String item_type);
}
