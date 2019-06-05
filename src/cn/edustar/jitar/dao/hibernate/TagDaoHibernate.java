package cn.edustar.jitar.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.TagDao;
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
import cn.edustar.jitar.util.CommonUtil;

/**
 * Tag 数据库访问的实际实现
 * 
 *
 */
public class TagDaoHibernate extends BaseDaoHibernate implements TagDao {
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#getTag(int)
	 */
	public Tag getTag(int id) {
		return (Tag)getSession().get(Tag.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#getTagByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Tag getTagByName(String tagName) {
		String queryString = "FROM Tag WHERE tagName = ?";
		List list = getSession().createQuery(queryString).setString(0, tagName).list();
		if (list == null || list.size() == 0) return null;
		return (Tag)list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getTagList(cn.edustar.jitar.service.TagQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Tag> getTagList(TagQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(getSession(), -1, param.count);
		else
			return query.queryData(getSession(), pager);
	}
	
	/*
	 * 从两个表联合查询，只获取 Tag 表内容，但是条件、排序来自于  TagRef 表。Hibernate 必须使用别名！下面的 HQL 可能有问题。
	 * 
	 * @see cn.edustar.jitar.dao.iface.TagDao#getRefTagList(int, cn.edustar.jitar.model.ObjectType)
	 */
	@SuppressWarnings("unchecked")
	public List<Tag> getRefTagList(int objId, ObjectType objType) {
		String queryString = "SELECT t FROM Tag AS t, TagRef AS r WHERE t.tagId = r.tagId AND r.objectId = " + objId + " AND r.objectType = " + objType.getTypeId() + " ORDER BY r.orderNum";		
		return getSession().createQuery(queryString).list();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getNewTagList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Tag> getNewTagList(int count) {
		String hql = "FROM Tag ORDER BY tagId DESC";
		//return getSession().createQuery(hql).setFirstResult(count).list();
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return getSession().createQuery(hql).setFirstResult(0).setMaxResults(count).list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#getObjectListByTags(java.util.List, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getObjectListByTags(List<Integer> tag_ids, int obj_type) {
		String queryString = "SELECT DISTINCT r.objectId " +
			" FROM TagRef AS r " +
			" WHERE r.tagId IN " + CommonUtil.toSqlInString(tag_ids) +
			"    AND r.objectType = " + obj_type + 
			" ORDER BY r.objectId DESC ";
		return getSession().createQuery(queryString).list();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getArticleListByTag(int, int, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Article> getArticleListByTag(int tagId, int order, Pager pager) {
		// 构造查询.
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT a ";
		query.fromClause = " FROM TagRef tr, Article a ";
		query.addAndWhere("tr.objectType = :objectType");
		query.setInteger("objectType", ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
		query.addAndWhere("tr.objectId = a.articleId");
		query.addAndWhere("tr.tagId = :tagId");
		query.setInteger("tagId", tagId);
		query.addAndWhere("a.auditState = " + Article.AUDIT_STATE_OK);	// 审核通过.
		query.addAndWhere("a.draftState = false");		// 非草稿.
		query.addAndWhere("a.delState = false");		// 非删除.
		
		query.orderClause = "ORDER BY a.createDate DESC";
		
		// 产生结果.
		if (pager == null)
			return query.queryData(getSession());
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getArticleListByTag(int, cn.edustar.jitar.service.ArticleQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Article> getArticleListByTag(int tagId, ArticleQueryParam param, Pager pager) {
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT a ";
		query.fromClause = " FROM TagRef tr, Article a ";
		query.addAndWhere("tr.objectType = " + ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
		query.addAndWhere("tr.objectId = a.articleId");
		query.addAndWhere("tr.tagId = " + tagId);
		
		// 产生结果.
		if (pager == null)
			return query.queryData(getSession());
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getResourceListByTag(int, cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getResourceListByTag(int tagId, ResourceQueryParam param, Pager pager) {
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT r ";
		query.fromClause = " FROM TagRef tr, Resource r ";
		query.addAndWhere("tr.objectType = " + ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
		query.addAndWhere("tr.objectId = r.resourceId");
		query.addAndWhere("tr.tagId = " + tagId);
		
		// 产生结果.
		if (pager == null)
			return query.queryData(getSession());
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getGroupListByTags(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getGroupListByTags(List<Integer> tag_ids) {
		// 例子: select g.GroupName from S_TagRef tr,g_group g where(tr.objectId=g.groupId and tr.Tagid in (1,2))
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT g ";  
		query.fromClause = " FROM TagRef tr, Group g"; //  
		query.addAndWhere("tr.objectType = :objectType");
		query.setInteger("objectType", ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		query.addAndWhere("tr.objectId = g.groupId");
		query.addAndWhere("tr.tagId in " + CommonUtil.toSqlInString(tag_ids));
		query.addAndWhere("g.groupState = " + Group.GROUP_STATE_NORMAL); //正常状态.
		
		query.orderClause = "ORDER BY g.createDate DESC";  //按创建时间排序.

		
		return query.queryData(getSession());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getGroupListByTag(int, cn.edustar.jitar.service.GroupQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getGroupListByTag(int tagId, GroupQueryParam param, Pager pager) {
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT g ";
		query.fromClause = " FROM TagRef tr, Group g ";
		query.addAndWhere("tr.objectType = " + ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		query.addAndWhere("tr.objectId = g.groupId");
		query.addAndWhere("tr.tagId = " + tagId);
		
		// 产生结果.
		if (pager == null)
			return query.queryData(getSession());
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#getUserListByTag(int, cn.edustar.jitar.service.UserQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserListByTag(int tagId, UserQueryParam param, Pager pager) {
		// 构造查询.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT u ";
		query.fromClause = " FROM TagRef tr, User u ";
		query.addAndWhere("tr.objectType = " + ObjectType.OBJECT_TYPE_USER.getTypeId());
		query.addAndWhere("tr.objectId = u.userId");
		query.addAndWhere("tr.tagId = " + tagId);
		
		// 产生结果.
		if (pager == null)
			return query.queryData(getSession());
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#createTag(cn.edustar.jitar.pojos.Tag)
	 */
	public void createTag(Tag tag) {
		if (tag == null) throw new IllegalArgumentException("tag == null");
		getSession().save(tag);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#createTagRef(cn.edustar.jitar.pojos.TagRef)
	 */
	public void createTagRef(TagRef tag_ref) {
		if (tag_ref == null) throw new IllegalArgumentException("tag_ref == null");
		getSession().save(tag_ref);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#incTagRefCount(cn.edustar.jitar.pojos.Tag, int)
	 */
	public int incTagRefCount(Tag tag, int inc_num) {
		if (tag == null) throw new IllegalArgumentException("tag == null");
		if (inc_num == 0) return 0;
		
		String update_string = "UPDATE Tag SET refCount = refCount + (" + inc_num + 
			") WHERE tagId = " + tag.getTagId();
		int update_num = getSession().createQuery(update_string).executeUpdate();
		if (update_num == 1) {
			tag.setRefCount(tag.getRefCount() + inc_num);
		}
		return update_num;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#incTagViewCount(cn.edustar.jitar.pojos.Tag, int)
	 */
	public int incTagViewCount(Tag tag, int inc_num) {
		if (tag == null) throw new IllegalArgumentException("tag == null");
		if (inc_num == 0) return 0;
		
		String update_string = "UPDATE Tag SET viewCount = viewCount + (" + inc_num + 
			") WHERE tagId = " + tag.getTagId();
		int update_num = getSession().createQuery(update_string).executeUpdate();
		if (update_num == 1) {
			tag.setViewCount(tag.getViewCount() + inc_num);
		}
		return update_num;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.TagDao#deleteTagRef(int, int, int)
	 */
	public int deleteTagRef(int tagId, int objId, ObjectType objType) {
		String queryString = "DELETE TagRef WHERE tagId = " + tagId + 
			" AND objectId = " + objId +
			" AND objectType = " + objType.getTypeId();
		int delete_num = getSession().createQuery(queryString).executeUpdate();
		if (delete_num == 1) {
			queryString = "UPDATE Tag SET refCount = refCount - 1 WHERE tagId = " + tagId;
			getSession().createQuery(queryString).executeUpdate();	// 返回的更新数被忽略了，有点不好.

		}
		return delete_num;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.TagDao#deleteTagRefByObject(cn.edustar.jitar.model.ObjectType, int)
	 */
	public List<TagRef> deleteTagRefByObject(ObjectType objType, int objId) {
		// 1. 得到所有要删除的引用对象.
		List<TagRef> tagref_list = getTagRefByObject(objType, objId);
		if (tagref_list == null || tagref_list.size() == 0) return tagref_list;
		
		// 2. 删除这些标签引用.
		/// getSession().deleteAll(tagref_list);
		String delete_hql = "DELETE FROM TagRef " +
			" WHERE objectType = " + objType.getTypeId() +
			"   AND objectId = " + objId;
		getSession().createQuery(delete_hql).executeUpdate();
		
		// 3. 计算涉及到的标签的引用计数.
		List<Integer> tagIds = internalGetTagIds(tagref_list);
		String hql = "SELECT tagId, COUNT(tagId) " +
			" FROM TagRef " +
			" GROUP BY tagId " +
			" HAVING tagId IN " + CommonUtil.toSqlInString(tagIds);
		// 返回的为 List<Object[tagId, COUNT(tagId)]> 集合.
		@SuppressWarnings("unchecked")
		List<Object[]> refc_list = getSession().createQuery(hql).list();
		
		// 更新引用数.
		internalUpdateTagRefCount(refc_list);
		
		return tagref_list;
	}
	
	private void internalUpdateTagRefCount(List<Object[]> refc_list) {
		for (Object[] tagid_count : refc_list) {
			Integer tagId = CommonUtil.safeXtransHiberInteger(tagid_count[0]);
			Integer count = CommonUtil.safeXtransHiberInteger(tagid_count[1]);
			if (count == null) count = 0;
			if (tagId != null) {	// 理论上一定不会为 null.
				String hql = "UPDATE Tag SET refCount = " + count + 
					" WHERE tagId = " + tagId;
				getSession().createQuery(hql).executeUpdate();
			}
		}
	}
	
	/** 得到 List<TagRef> 中所有标签的标识集合. */
	private List<Integer> internalGetTagIds(List<TagRef> tagref_list) {
		List<Integer> ids = new ArrayList<Integer>();
		for (TagRef tagref : tagref_list) {
			ids.add(tagref.getTagId());
		}
		return ids;
	}
	
	@SuppressWarnings("unchecked")
	public List<TagRef> getTagRefByObject(ObjectType objType, int objId) {
		String hql = "FROM TagRef WHERE objectType = " + objType.getTypeId() + 
			" AND objectId = " + objId;
		return (List<TagRef>)getSession().createQuery(hql).list();
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}
