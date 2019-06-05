package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.pojos.GroupNews;

/**
 * 查询群组新闻的查询参数.
 *
 *
 */
public class GroupNewsQueryParam implements QueryParam {
	/** 查询条数, 一般在未指定 pager 的时候使用 */
	public int count = 10;
	
	/** 限定群组标识, 缺省 = null 表示不限制 */
	public Integer groupId = null;
	
	/** 发布用户标识, 缺省 = null 表示不限制 */
	public Integer userId = null;
	
	/** 要获取的对象状态, 缺省 = GroupNews.NEWS_STATUS_NORMAL 获取正常状态的; = null 则不限制 */
	public Integer status = GroupNews.NEWS_STATUS_NORMAL;
	
	/** 要获取的类型, 缺省 = null 表示不限制 */
	public Integer newsType = null;

	/** 是否要求有图片, 缺省 = null 表示不限制 */
	public Boolean hasPicture = null;
	
	/** 要查询的关键字, 缺省 = null 表示不限制 */
	public String k = null;
	
	/** 要获取的信息字段, 可能只在部分函数中生效, 且返回的 DataTable 依据此字段设置 Schema */
	public String selectFields = "gn.newsId, gn.groupId, gn.userId, gn.title, gn.status, gn.newsType, gn.createDate, gn.viewCount";
	
	/** 从哪里获取数据 */
	public String fromClause = "FROM GroupNews gn";
	
	/** 排序方式, 缺省 = 0 按照创建时间逆序排列 */
	public int orderType = 0;
	
	/** 排序方式, = 0 按照创建时间逆序排列 */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 0;
	/** 排序方式, = 1 按照点击数逆序排列 */
	public static final int ORDER_TYPE_VIEWCOUNT_DESC = 1;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + selectFields;
		query.fromClause = this.fromClause;
		
		// 限制条件.
		if (this.groupId != null)
			query.addAndWhere("gn.groupId = " + this.groupId);
		if (this.userId != null)
			query.addAndWhere("gn.userId = " + this.userId);
		if (this.status != null)
			query.addAndWhere("gn.status = " + this.status);
		if (this.newsType != null)
			query.addAndWhere("gn.newsType = " + this.newsType);
		
		// 图片限制.
		if (this.hasPicture != null) {
			if (this.hasPicture.booleanValue()) {
				// 有图片, 一般是限制有图片.
				query.addAndWhere("gn.picture <> '' AND NOT gn.picture IS NULL");
			} else {
				// 无图片.
				query.addAndWhere("gn.picture = '' OR gn.picture IS NULL");
			}
		}
		
		// 关键字.
		if (this.k != null && this.k.length() > 0) {
			query.addAndWhere("gn.title LIKE :titleKey");
			query.setString("titleKey", "%" + this.k + "%");
		}
		
		// 排序.
		switch (this.orderType) {
		case ORDER_TYPE_CREATEDATE_DESC:
			query.addOrder("gn.createDate DESC");
			break;
		case ORDER_TYPE_VIEWCOUNT_DESC:
			query.addOrder("gn.viewCount DESC");
			break;
		}
		
		return query;
	}
}
