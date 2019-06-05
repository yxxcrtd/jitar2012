package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.TagDao;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.ResourceModelEx;
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
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserQueryParam;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 标签服务的实现类
 * 
 * @author Yang Xinxin
 */
public class TagServiceImpl implements TagService {
	/** 访问数据库的对象. */
	private TagDao tag_dao;

	/** 访问数据库的对象. */
	public TagDao getTagDao() {
		return this.tag_dao;
	}

	/** 访问数据库的对象. */
	public void setTagDao(TagDao o) {
		this.tag_dao = o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#parseTagList(java.lang.String)
	 */
	public String[] parseTagList(final String tagstr) {
		return CommonUtil.parseTagList(tagstr);
		// sep_string 也许可以被配置在外面. 下面的列表基本够用.
		// String sep_string = " ,;，。；|｜\'\"“”’‘`^\\r\n\t";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#getTag(int)
	 */
	public Tag getTag(int id) {
		return tag_dao.getTag(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#getTagByName(java.lang.String)
	 */
	public Tag getTagByName(String tagName) {
		return tag_dao.getTagByName(tagName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getTagList(cn.edustar.jitar.service.TagQueryParam, cn.edustar.data.Pager)
	 */
	public List<Tag> getTagList(TagQueryParam param, Pager pager) {
		return tag_dao.getTagList(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#getRefTagList(int, cn.edustar.jitar.model.ObjectType)
	 */
	public List<Tag> getRefTagList(int objId, ObjectType objType) {
		return tag_dao.getRefTagList(objId, objType);
	}

	private static final String[] EMPTY_TAGS = {};

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#createUpdateMultiTag(int, int, java.lang.String[], java.lang.String[])
	 */
	public void createUpdateMultiTag(int objId, ObjectType objType, String[] newTags, String[] oldTags) {
		if (objId == 0 || objType == null)
			throw new IllegalArgumentException("objId or objType is invalid");

		// 判定是否不需要更新
		if (newTags == null)
			newTags = EMPTY_TAGS;
		if (oldTags == null)
			oldTags = EMPTY_TAGS;
		if (tagsEquals(newTags, oldTags))
			return;

		// 表示需要添加的标签列表，并且顺序是 newTags 顺序
		List<String> add_list = new ArrayList<String>();

		// 表示需要删除的标签列表
		List<String> del_list = new ArrayList<String>();

		// 1. 遍历 newTags，如果插入则添加引用关系
		if (newTags.length > 0) {
			for (int i = 0; i < newTags.length; ++i) {
				String tag_name = newTags[i];
				if (!tagInList(tag_name, oldTags)) {
					// 需要添加
					if (!add_list.contains(tag_name))
						add_list.add(tag_name);
				}
			}
		}

		// 2. 遍历 delTags，如果删除则删除引用关系
		if (oldTags.length > 0) {
			for (int i = 0; i < oldTags.length; ++i) {
				String tag_name = oldTags[i];
				if (!tagInList(tag_name, newTags)) {
					// 需要删除
					if (!del_list.contains(tag_name))
						del_list.add(tag_name);
				}
			}
		}

		// 3. 得到该对象（含类型）当前引用的所有标签列表. 理论上 old_tags == cur_tags, 但实际可能不同
		List<Tag> cur_tags = tag_dao.getRefTagList(objId, objType);

		// 4. 执行上述积累下来的操作 - 删除
		for (int i = 0; i < del_list.size(); ++i) {
			String tag_name = del_list.get(i);
			int index = findTagIndex(tag_name, cur_tags);
			if (index == -1)
				continue; // 没找到则不用删除

			Tag tag_obj = cur_tags.get(index);

			// 删除对这个标记的引用
			tag_dao.deleteTagRef(tag_obj.getTagId(), objId, objType);
			cur_tags.remove(index);
		}

		// 5. 执行上述积累的操作 - 添加
		for (int i = 0; i < add_list.size(); ++i) {
			String tag_name = add_list.get(i);
			int index = findTagIndex(tag_name, cur_tags);
			if (index != -1)
				continue; // 如果已经有了，则不用添加

			Tag tag_obj = createOrUpdateTagAndTagRef(tag_name, objId, objType, cur_tags.size());
			cur_tags.add(tag_obj);
		}

		// 6. TODO:顺序问题
		tag_dao.flush();
	}

	/**
	 * 创建或更新一个标签. 如果标签已经存在，则增加引用计数；否则创建标签，并设置引用计数 = 1 返回找到或创建的标签
	 * 
	 * @param tag_name
	 * @param objId
	 * @param objType
	 * @param orderNum
	 * @return
	 */
	private Tag createOrUpdateTagAndTagRef(String tag_name, int objId, ObjectType objType, int orderNum) {
		// 查找或创建标签
		Tag tag_obj = tag_dao.getTagByName(tag_name);
		if (tag_obj == null) {
			// 创建一个新的标签
			tag_obj = new Tag();
			tag_obj.setTagName(tag_name);
			tag_obj.setRefCount(1);
			tag_obj.setCreateDate(new java.util.Date());
			tag_dao.createTag(tag_obj);
		} else {
			// 更新其引用计数
			tag_dao.incTagRefCount(tag_obj, 1);
		}

		// 创建引用对象
		TagRef tag_ref = new TagRef();
		tag_ref.setTagId(tag_obj.getTagId());
		tag_ref.setObjectId(objId);
		tag_ref.setObjectType(objType.getTypeId());
		tag_ref.setOrderNum(orderNum);

		tag_dao.createTagRef(tag_ref);

		return tag_obj;
	}

	/** 在 tag_list 中查找是否存在 tag_name 并返回索引，返回 -1 表示没有找到
	 * 
	 * @param tag_name
	 * @param tag_list
	 * @return
	 */
	private static int findTagIndex(String tag_name, List<Tag> tag_list) {
		for (int i = 0; i < tag_list.size(); ++i) {
			if (tag_name.equals(tag_list.get(i).getTagName()))
				return i;
		}
		return -1;
	}

	/** 判断新旧两个标签集合是否完全相同. 长度、顺序、内容必须全部相同才认为相同
	 * 
	 * @param newTags
	 * @param oldTags
	 * @return
	 */
	private static boolean tagsEquals(String[] newTags, String[] oldTags) {
		// 长度不同则认为不同
		if (newTags.length != oldTags.length)
			return false;
		for (int i = 0; i < newTags.length; ++i) {
			// 每个都匹配，顺序、内容都相同
			if (!newTags[i].equals(oldTags[i]))
				return false;
		}
		// 完全相同
		return true;
	}

	/**
	 * 判断 tag 是否出现在 tag_list 中
	 * 
	 * @param tag_name
	 * @param tag_list
	 * @return
	 */
	private static boolean tagInList(String tag_name, String[] tag_list) {
		for (int i = 0; i < tag_list.length; ++i) {
			if (tag_name.equals(tag_list[i]))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#getTagRef(int, int, cn.edustar.jitar.util.Pager)
	 */
	public Object getTagRef(int tagId, int objType, Pager pager) {
		throw new java.lang.UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.TagService#getArticleListByTag(int, int, cn.edustar.data.Pager)
	 */
	public List<ArticleModelEx> getArticleListByTag(int tagId, int order, Pager pager) {
		// 得到文章列表.
		List<Article> list = tag_dao.getArticleListByTag(tagId, order, pager);

		// 进行封装.
		List<ArticleModelEx> article_list = new ArrayList<ArticleModelEx>();
		for (Article article : list) {
			ArticleModelEx model = ArticleModelEx.wrap(article);
			article_list.add(model);
		}
		return article_list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.TagService#getObjectListByTags(java.util.List, cn.edustar.jitar.model.ObjectType)
	 */
	public List<Integer> getObjectListByTags(List<Integer> tag_ids, ObjectType obj_type) {
		if (tag_ids == null || tag_ids.size() == 0)
			return new ArrayList<Integer>();
		return tag_dao.getObjectListByTags(tag_ids, obj_type.getTypeId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getNewTagList(int)
	 */
	public List<Tag> getNewTagList(int count) {
		return tag_dao.getNewTagList(count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getGroupListByTags(java.util.List)
	 */
	public List<Group> getGroupListByTags(List<Integer> tag_ids) {
		return tag_dao.getGroupListByTags(tag_ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getArticleListByTag(int, cn.edustar.jitar.service.ArticleQueryParam, cn.edustar.data.Pager)
	 */
	public List<ArticleModelEx> getArticleListByTag(int tagId, ArticleQueryParam param, Pager pager) {
		// 得到文章列表.
		List<Article> list = tag_dao.getArticleListByTag(tagId, param, pager);

		// 包装返回.
		List<ArticleModelEx> article_list = new ArrayList<ArticleModelEx>();
		for (Article article : list)
			article_list.add(ArticleModelEx.wrap(article));
		return article_list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getResourceListByTag(int, cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	public List<ResourceModelEx> getResourceListByTag(int tagId, ResourceQueryParam param, Pager pager) {
		// 1. 得到资源列表.
		List<Resource> list = tag_dao.getResourceListByTag(tagId, param, pager);

		// 包装返回.
		List<ResourceModelEx> resource_list = new ArrayList<ResourceModelEx>();
		for (Resource resource : list) {
			resource_list.add(ResourceModelEx.wrap(resource));
		}
		return resource_list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getGroupListByTag(int, cn.edustar.jitar.service.GroupQueryParam, cn.edustar.data.Pager)
	 */
	public List<Group> getGroupListByTag(int tagId, GroupQueryParam param, Pager pager) {
		return tag_dao.getGroupListByTag(tagId, param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#getUserListByTag(int, cn.edustar.jitar.service.UserQueryParam, cn.edustar.data.Pager)
	 */
	public List<User> getUserListByTag(int tagId, UserQueryParam param, Pager pager) {
		return tag_dao.getUserListByTag(tagId, param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#incTagViewCount(cn.edustar.jitar.pojos.Tag, int)
	 */
	public void incTagViewCount(Tag tag, int inc_num) {
		tag_dao.incTagViewCount(tag, inc_num);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.TagService#deleteTagRefByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public void deleteTagRefByObject(ObjectType objType, int objId) {
		if (objType == null)
			throw new IllegalArgumentException("objType == null");
		tag_dao.deleteTagRefByObject(objType, objId);
	}
	
}
