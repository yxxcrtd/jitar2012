package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 查询标签的参数对象.
 *
 *
 */
public class TagQueryParam implements QueryParam {
	/** 查询数量, 当未指定分页参数时候生效 */
	public int count = 10;

	/** 是否限制标签标识 */
	public Integer tagId = null;
	
	/** 是否限制标签名字 */
	public String tagName = null;
	
	/** 是否获取被禁用的, 缺省 = false 表示获取非禁用的; null 表示不限定; true 表示获取被禁用的 */
	public Boolean disabled = Boolean.FALSE;
	
	/** 排序方式, 缺省 = 0 为按照 id 逆序排列, = 1 按照 refCount 逆序排列, =2 按照 viewCount 逆序排列 */
	public int orderType = 0;
	
	/** 排序方式 = 0 按照 id 逆序排列 */
	public static final int ORDER_TYPE_ID_DESC = 0;
	/** 排序方式 = 1 按照 refCount 逆序排列 */
	public static final int ORDER_TYPE_REF_DESC = 1;
	/** 排序方式 = 2 按照 viewCount 逆序排列 */
	public static final int ORDER_TYPE_VIEW_DESC = 2;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Tag tg ";
		
		// 标签标识限定.
		if (tagId != null) {
			query.addAndWhere("tg.tagId = " + this.tagId);
		}
		
		// 标签名字限定.
		if (tagName != null) {
			query.addAndWhere("tg.tagName = :tagName");
			query.setString("tagName", this.tagName);
		}
		
		// disable
		if (this.disabled != null) {
			query.addAndWhere("tg.disabled = " + this.disabled);
		}
		
		// 排序.
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			query.addOrder("tg.tagId DESC");
			break;
		case ORDER_TYPE_REF_DESC:
			query.addOrder("tg.refCount DESC");
			break;
		case ORDER_TYPE_VIEW_DESC:
			query.addOrder("tg.viewCount DESC");
			break;
		}
		
		return query;
	}
}
