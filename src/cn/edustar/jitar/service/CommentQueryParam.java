package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ObjectType;

/**
 * 评论查询参数对象
 * 
 *
 */
public class CommentQueryParam implements QueryParam {

	/** 数量 */
	public int count = 20;

	/** 发表评论的用户标识, 缺省 = null 表示不限定 */
	public Integer userId;

	/** 被评论的文章/资源的所属用户的标识, 缺省 = null 表示不限定 */
	public Integer aboutUserId;

	/** 被评论的对象类型, 缺省 = null 表示不限定; 一般和 objectId 一起使用 */
	public ObjectType objectType;

	/** 被评论的对象标识, 缺省 = null 表示不限定 */
	public Integer objectId;

	/** 审核标志的; 缺省 = true 表示获取审核通过的; = false 表示获取未审核通过的; = null 表示不限定 */
	public Boolean audit = Boolean.TRUE;

	/** 排序方式, 缺省 = 0 按照 id 逆序排列 */
	public int orderType = 0;
	
	/** ajax排序使用 */
	public Integer lastId = null;

	/** 排序方式, 按照 id 逆序排列 */
	public static final int ORDER_TYPE_ID_DESC = 0;
	/** 排序方式, 按照 id 正序排列 */
	public static final int ORDER_TYPE_ID_ASC = 1;

	private int searchType=0;
	/**
	 * 将条件合并到指定查询中, 函数假定 Comment 对象简称为 c
	 * 
	 * @param query
	 * @return
	 */
	public QueryHelper combineQuery(QueryHelper query) {
		// 发表用户条件
		if (this.userId != null) {
			query.addAndWhere("c.userId = " + this.userId);
		}
		// 被评论用户条件
		if (this.aboutUserId != null) {
			query.addAndWhere("c.aboutUserId = " + this.aboutUserId);
		}
		// 评论对象条件
		if (this.objectType != null) {
			query.addAndWhere("c.objType = " + this.objectType.getTypeId());
		}
		
		if (this.objectId != null) {
			query.addAndWhere("c.objId = " + this.objectId);
		}
		// 审核条件
		if (this.audit != null) {
			query.addAndWhere("c.audit = " + this.audit);
		}
		
		//ajax排序使用，参见文章详情页
		if(this.lastId != null){
		    query.addAndWhere("c.id < " + this.lastId);
		}

		if(searchType==1){
			query.addAndWhere("c.objId = v.videoId");
		}
			
		// 排序
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("c.id DESC");
			break;
		case ORDER_TYPE_ID_ASC:
			query.addOrder("c.id ASC");
			break;
		}

		return query;
	}

	/**
	 * 根据条件创建查询对象
	 * 
	 * @return
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Comment c ";
		searchType=0;
		return combineQuery(query);
	}

	public QueryHelper createQueryEx() {
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Comment c, Video v ";
		searchType=1;
		return combineQuery(query);
	}

}
