package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.User;

/**
 * 标签服务接口
 * 
 *
 */
public interface TagService {
	
	/** 引用次数降序排列 = 0 */
	public static final int ORDER_BY_REFCOUNT_DESC = 0;
	
	/** 创建日期降序排列 = 1 */
	public static final int ORDER_BY_CREATEDATE_DESC = 1;
	
	/**
	 * 分解一个用户输入的标签字符串，变成内部使用的字符串数组格式
	 * 	 * @param tagstr
	 * @return 返回被分解之后的字符串。将去掉非法字符、重复等
	 * @remark 识别：'', ',', ';'等分隔字符(也可是中文)
	 */
	public String[] parseTagList(String tagstr);
	
	/**
	 * 得到指定标识的标签
	 * 	 * @param id - 标签标识	 * @return 返回标签对象，如果没有则返回：null
	 */
	public Tag getTag(int id);
	
	/**
	 * 通过标签名字得到标签
	 * 	 * @param tagName - 标签名字	 * @return 返回标签对象，如果没有则返回：null
	 */
	public Tag getTagByName(String tagName);
	
	/**
	 * 得到指定条件和分页选项下的标签列表
	 * 
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Tag> getTagList(TagQueryParam param, Pager pager);
	
	/**
	 * 得到指定类型的指定对象的所有引用的标签列表。按照标签引用序排列
	 * 	 * @param objId - 对象标识	 * @param objType - 对象类型	 * @return 返回引用的标签列表	 */
	public List<Tag> getRefTagList(int objId, ObjectType objType);
	
	/**
	 * 创建/更新一组标签。如果指定标签不存在，则创建并设置引用为 0，否则更新标签引用
	 * 	 * @param objId - 引用这些标签的对象标识	 * @param objType - 引用这些标签的对象类型。定义为常量	 * @param newTags - 新标签列表。 要创建/增加引用的标签列表	 * @param oldTags - 旧标签列表。 要减去引用的标签列表	 * @remark 如果一个标签即出现在 newTags, 又出现在 oldTags 则不创建/更新标签引用数
	 * 			如果一个标签仅出现在 newTags, 则创建/增加标签引用	 *   			如果一个标签仅出现在 oldTags, 则减掉标签引用	 *   			在增加/更新博文文章的时候，调用此函数创建/更新标签及其引用关系	 */
	public void createUpdateMultiTag(int objId, ObjectType objType, String[] newTags, String[] oldTags);

	/**
	 * 得到指定对象类型引用的指定标识的标签的对象标识列表。可能带有分页要求
	 * 	 * @param tagId - 标签标识	 * @param objType - 对象类型标识。参见 model.ObjectType 对象说明	 * @param pager - 分页参数	 * @return
	 * @see cn.edustar.jitar.model.ObjectType
	 */
	// public Object getTagRef(int tagId, int objType, Pager pager);

	/**
	 * 得到使用指定标签的文章列表
	 * 
	 * @param tagId - 标签标识
	 * @param order - 排序方式
	 * @param pager - 分页参数
	 * @return
	 */
	public List<ArticleModelEx> getArticleListByTag(int tagId, int order, Pager pager);
	
	/**
	 * 得到使用标签(多个)的对象列表。此方法提供给 UserTagsFeedModule 使用
	 * 	 * @param tag_ids - 标签标识数组
	 * @param obj_type - 对象类型
	 * @return
	 */
	public List<Integer> getObjectListByTags(List<Integer> tag_ids, ObjectType obj_type);

	/**
	 * 得到最新 count 个标签
	 * 
	 * @param count
	 * @return
	 */
	public List<Tag> getNewTagList(int count);
	
	/**
	 * 得到使用此标签的组
	 * 	 * @param tag_ids 标签标识数组
	 * @author Yang XinXin
	 */
	public List<Group> getGroupListByTags(List<Integer> tag_ids);
	
	/**
	 * 得到使用指定标签的文章列表
	 * 
	 * @param tagId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<ArticleModelEx> getArticleListByTag(int tagId, ArticleQueryParam param, Pager pager);
	
	/**
	 * 得到使用指定标签的资源列表
	 * 
	 * @param tagId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<ResourceModelEx> getResourceListByTag(int tagId, ResourceQueryParam param, Pager pager);

	/**
	 * 得到使用指定标签的群组列表
	 * 
	 * @param tagId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Group> getGroupListByTag(int tagId, GroupQueryParam param, Pager pager);

	/**
	 * 得到使用指定标签的用户列表
	 * 
	 * @param tagId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<User> getUserListByTag(int tagId, UserQueryParam param, Pager pager);

	/**
	 * 增加/减少指定标签的点击数
	 * 
	 * @param tag
	 * @param inc_num
	 */
	public void incTagViewCount(Tag tag, int inc_num);

	/**
	 * 删除指定对象所有的标签引用, 一般是要删除这个对象之前调用此方法删除其标签引用
	 * 
	 * @param objType - 对象类型
	 * @param objectId - 对象标识
	 */
	public void deleteTagRefByObject(ObjectType objType, int objectId);
	
}
