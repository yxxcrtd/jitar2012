package cn.edustar.jitar.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.edustar.jitar.ex.CategoryException;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;

/**
 * 系统分类服务接口定义
 *
 * @author Yang Xinxin
 * @version 1.0.0 Aug 6, 2008 8:58:58 AM
 */
public interface CategoryService {
	/** 系统分类的 itemType = 'default' */
	// private static final String SYSTEM_CATEGORY_TYPE = "default";

	/** 用户/博客/工作室的系统分类 itemType = 'blog' */
	public static final String BLOG_CATEGORY_TYPE = "blog";

	/** 文章的系统分类 itemType = 'default' */
	public static final String ARTICLE_CATEGORY_TYPE = "default";
	
	/** 协作组的系统分类 itemType = 'group' */
	public static final String GROUP_CATEGORY_TYPE = "group";
	
	/** 频道图片分类 itemType = 'channel_photo_' */
	public static final String CHANNEL_PHOTO_PREFIX = "channel_photo_";
	
	/** 频道文章分类 itemType = 'channel_article_' */
	public static final String CHANNEL_ARTICLE_PREFIX = "channel_article_";
	
	/** 频道资源分类 itemType = 'channel_resource_' */
	public static final String CHANNEL_RESOURCE_PREFIX = "channel_resource_";
	
	/** 频道视频分类 itemType = 'channel_video_' */
	public static final String CHANNEL_VIDEO_PREFIX = "channel_video_";

	/** 集体备课组的分类 GUID */ 
	public static final String GROUP_CATEGORY_GUID_JTBK = "5E7B6405-68C5-4BCA-ABB6-ED274976EC4E";
	/** 课题研究组的分类 GUID */
	public static final String GROUP_CATEGORY_GUID_KTYJ = "A76BDA40-FC6C-4CA1-BA6D-57151BB71567";
	/** 普通协助组的分类 GUID */
	public static final String GROUP_CATEGORY_GUID_COMMON = "E42EAD2F-73A2-4D29-8CC5-7B37818F125C";

	
	/** 资源的系统分类 itemType = 'resource' */
	public static final String RESOURCE_CATEGORY_TYPE = "resource";

	/** 视频的系统分类 itemType = video */
	public static final String VIDEO_CATEGORY_TYPE = "video";
	
	/** 资源类型 */
	public static final String RESOURCE_TYPE_TYPE = "restype";
	
	/** 图片/相册的系统分类 itemType = 'photo' */
	public static final String PHOTO_CATEGORY_TYPE = "photo";
	
	/** 事件：将要创建分类；此时分类还未写入到数据库中. 事件对象为要创建的分类对象 */
	public static final String EVENT_CATEGORY_CREATING = "jitar.category.creating";

	/** 事件：分类创建完成；此时分类写入到数据库中，但事务未提交. 事件对象为要创建的分类对象 */
	public static final String EVENT_CATEGORY_CREATED = "jitar.category.created";

	/** 事件：将要删除分类；事件对象为要删除的分类对象 */
	public static final String EVENT_CATEGORY_DELETING = "jitar.category.deleting";

	/** 事件：分类已经删除；事件对象为要删除的分类对象 */
	public static final String EVENT_CATEGORY_DELETED = "jitar.category.deleted";

	/**
	 * 根据'分类标识'得到分类对象
	 * 
	 * @param cateId 分类标识.
	 * @return
	 */
	public Category getCategory(int cateId);
	
	public Category getCategory(String uuId);
	
	public Category getCategory(String cateName,String itemType,boolean isSystem);
	
	/**
	 * 创建一个新分类.
	 * 
	 * @param category
	 * @event 在创建之前发布 'jitar.category.creating' 事件，
	 * 
	 * 在创建之后发布 'jitar.category.created' 事件
	 * @exception CategoryException 验证失败
	 * @exception DataAccessException Spring 包装的数据库访问错误
	 */
	public void createCategory(Category category);

	
	/**
	 * 更新/移动一个分类.
	 * 
	 * @param category
	 */
	public void updateCategory(Category category);

	/**
	 * 删除一个分类, 该删除不操作相关的其它表.
	 * 注意：实际删除中将根据 itemType 对 user,group,resource,photo 相应分类属性做出设置.
	 * @param category
	 * @event 删除之前发布 'jitar.category.deleting' 事件 删除之后发布 'jitar.category.deleted' 事件
	 */
	public void deleteCategory(Category category);
	
	/**
	 * 得到指定系统分类项目类型的分类树.
	 * 
	 * @param item_type - 项目类型.
	 * @return
	 */
	public CategoryTreeModel getCategoryTree(String item_type);

	/**
	 * 得到指定系统分类项目类型的分类树.
	 * 得到包含了rootCateId的以及其子孙分类
	 * 
	 * @param item_type
	 * @param rootCateId 
	 * @return
	 */
	public CategoryTreeModel getCategoryTree(String item_type,int rootCateId);
	/**
	 * 得到指定系统分类项目类型的分类树.
	 * 
	 * @param item_type - 项目类型.
	 * @param fromCache - true 表示可以从缓存中获取; false 表示不从缓存中获取.
	 * @return
	 */
	public CategoryTreeModel getCategoryTree(String item_type, boolean fromCache);

	/**
	 * 删除指定类型的分类树, 如 itemType = 'group_1' 一般是协作组等对象将被删除的时候调用.
	 * @param itemType
	 */
	public void deleteCategoryTree(String itemType);
	
	/**
	 * 得到指定类型、指定分类标识的所有字分类
	 * 
	 * @param item_type 分类类型，参见 Category 定义
	 * @param parentId 父分类标识，= null 表示获取根级分类
	 * @return 返回分类对象列表
	 */
	public List<Category> getChildCategories(String item_type, Integer parentId);

	/**
	 * 得到指定类型的所有字分类
	 * @param item_type
	 * @return
	 */
	public List<Category> getCategories(String item_type);
	
	/**
	 * 得到指定标识的分类的子分类数量
	 * 
	 * @param categoryId - 分类标识.
	 * @return 子分类数量.
	 */
	public int getChildrenCount(int categoryId);

	/**
	 * 通过给出一个 map 来获得多个分类的信息，得到的分类也放在该 map 中. 
	 * 此方法相当于调用多次 getCategory(cid) 然后放在map 中, 
	 *   然而效率比单个调用要好一些.
	 * @param map
	 */
	public void getCategoryByMap(Map<Integer, Category> map);
	
	/**
	 * 设置分类排序
	 * @param categoryId
	 * @param orderNo
	 */
	public void setCategoryOrder(int categoryId,int orderNo);
	
	/**
	 * 得到分类Id以及下级分类Id
	 */
	public List<Integer> getCategoryIds(int categoryId);
	
	/**
	 * 将List<Category>转换为CategoryTreeModel
	 * @param cate_list
	 * @return
	 */
	public CategoryTreeModel getCategoryTree(List<Category> cate_list);	
	
	/**
	 * 得到某分类的树形数据结构。
	 * @param item_type
	 * @return
	 */	
	public List<Category> showTree(String item_type);
}
