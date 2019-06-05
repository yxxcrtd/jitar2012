package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.TagRef;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.TagQueryParam;
import cn.edustar.jitar.service.UserQueryParam;

/**
 * 访问标签信息的数据库接口定义
 * 
 * @author Administrator
 */
public interface TagDao extends Dao {
	
	/**
	 * 得到指定标识的标签
	 * 
	 * @param id
	 * @return
	 */
	public Tag getTag(int id);

	/**
	 * 通过标签名字得到标签
	 * 
	 * @param tagName
	 * @return
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
	 * 
	 * @param objId 对象标识
	 * @param objType 对象类型
	 * @return 返回引用的标签列表
	 */
	public List<Tag> getRefTagList(int objId, ObjectType objType);

	/**
	 * 得到最新 count 个标签
	 * 
	 * @param count
	 * @return 返回标签集合
	 */
	public List<Tag> getNewTagList(int count);

	/**
	 * 得到使用标签(多个)的对象列表
	 * 
	 * @param tag_ids 标签标识数组
	 * @param obj_type 对象类型
	 * @return
	 */
	public List<Integer> getObjectListByTags(List<Integer> tag_ids, int obj_type);

	/**
	 * 得到使用标签(多个)的对象列表
	 * 
	 * @param tag_ids 标签标识数组
	 * @return 返回组的集合
	 * 
	 */
	public List<Group> getGroupListByTags(List<Integer> tag_ids);

	/**
	 * 得到使用指定标签的文章列表
	 * 
	 * @param tagId 标签标识
	 * @param order 排序方式
	 * @param pager 分页参数
	 * @return 返回为 List&lt;Article&gt; 集合
	 */
	public List<Article> getArticleListByTag(int tagId, int order, Pager pager);

	/**
	 * 得到使用指定标签的文章列表
	 * 
	 * @param tagId
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Article> getArticleListByTag(int tagId, ArticleQueryParam param, Pager pager);

	/**
	 * 得到使用指定标签的资源列表
	 * 
	 * @param tagId 标签标识
	 * @param param 查询参数
	 * @param pager 分页参数
	 * @return
	 */
	public List<Resource> getResourceListByTag(int tagId, ResourceQueryParam param, Pager pager);

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
	 * 创建一个新的标签. 属性必须在外部已经设置、检查好了
	 * 
	 * @param tag
	 */
	public void createTag(Tag tag);

	/**
	 * 创建一个新的标签引用. 属性必须在外部已经设置、检查好了
	 * 
	 * @param tag_ref
	 */
	public void createTagRef(TagRef tag_ref);

	/**
	 * 增加/减去指定标签的引用计数，inc_num 大于 0 为增加；小于 0 为减去； = 0 不操作
	 * 
	 * @param tag
	 * @param inc_num
	 * @return 返回更新的记录数，正常应 = 1。同时 tag 对象中的 refCount 也将被更新
	 */
	public int incTagRefCount(Tag tag, int inc_num);

	/**
	 * 增加/减去指定标签的访问计数, inc_num 大于 0 为增加；小于 0 为减去； = 0 不操作
	 * 
	 * @param tag
	 * @param inc_num
	 * @return 返回更新的记录数，正常应 = 1。同时 tag 对象中的 viewCount 也将被更新
	 */
	public int incTagViewCount(Tag tag, int inc_num);

	/**
	 * 删除指定标记标识、对象标识、对象类型的标记引用，在成功的时候同时减去标签引用计数
	 * 
	 * @param tagId 标记的标识
	 * @param objId 对象标识
	 * @param objType 对象类型
	 * @return 返回操作影响的纪录数量，1 应该是正常值；0 表示没有；超过 1 有问题
	 */
	public int deleteTagRef(int tagId, int objId, ObjectType objType);

	/**
	 * 得到指定对象的所有标签引用
	 * 
	 * @param objType
	 * @param objId
	 * @return
	 */
	public List<TagRef> getTagRefByObject(ObjectType objType, int objId);

	/**
	 * 删除指定对象的所有标签引用, 并且更新相应标签的引用计数
	 * 
	 * @param objType 对象类型
	 * @param objId 对象标识
	 * @return 返回被删除的标签引用对象
	 */
	public List<TagRef> deleteTagRefByObject(ObjectType objType, int objId);
	
}
