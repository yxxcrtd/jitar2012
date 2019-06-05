package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 论坛查询参数对象.
 *
 *
 */  
public class BbsTopicQueryParam implements QueryParam {
	/** 要查询的主题数量, 缺省 = 10; -1 表示不限制, 只在未提供 pager 参数时候生效. */
	public int count = 10;
	
	/** 限制的所属群组标识参数, 缺省 = null 表示不限定. */
	public Integer groupId = null;

	/** 发贴的用户表示, 缺省 = null 表示不限制. */
	public Integer userId = null;
	
	/** 是否精华,缺省为 null 表示所有主题; = true 表示获取精华的; = false 表示获取非精华的. */
	public Boolean bestType = null;
	
	/** 删除状态, == null 表示不区分, 缺省 = false 查询未删除的. */
	public Boolean delState = Boolean.FALSE;
	
	/** 置顶状态, == null 表示不区分; = true 表示获取置顶的; = false 表示获取非置顶的. */
	public Boolean topState = Boolean.FALSE;
	
	/** 要获取的字段列表, 缺省在 BbsService.GET_TOPIC_DATATABLE 中给出. */
	public String selectFields = BbsService.GET_TOPIC_DATATABLE;
	
    /** 
     * 排序方式 
	 * 排序方法，为1按发表时间，为2按点击数，为3按回复数. 缺省 = 1
	 */
	public int orderType = 1;
	/** 排序方法，为1按发表时间 逆序排列. */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	/** 排序方法，为2按点击数 逆序排列. */
	public static final int ORDER_TYPE_VIEWCOUNT_DESC = 2;
	/** 排序方法，为3按回复数 逆序排列. */
	public static final int ORDER_TYPE_REPLYCOUNT_DESC = 3; 
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Topic T";
		
		// 群组.
		if (groupId != null) {
			query.addAndWhere("T.groupId = " + this.groupId);
		}
		// 用户.
		if (userId != null) {
			query.addAndWhere("T.userId = " + this.userId);
		}
		// 是否精华.
		if (this.bestType != null) {
			query.addAndWhere("T.isBest = " + this.bestType);
		}
		// 是否删除.
		if (this.delState != null) {
			query.addAndWhere("T.isDeleted = " + this.delState);
		}
		// 是否置顶.
		if (this.topState != null) {
			query.addAndWhere("T.isTop =" + this.topState);
		}

		//排序.
		String order_hql = "";
		switch (this.orderType) {
		case BbsTopicQueryParam.ORDER_TYPE_CREATEDATE_DESC:
			order_hql = "T.createDate DESC";
			break;
		case BbsTopicQueryParam.ORDER_TYPE_VIEWCOUNT_DESC:
			order_hql = "T.viewCount DESC";
			break;
		case BbsTopicQueryParam.ORDER_TYPE_REPLYCOUNT_DESC:
			order_hql = "T.replyCount DESC";
		}
		query.addOrder(order_hql);
		
		return query;
	}
}
