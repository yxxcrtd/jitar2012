package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 群组论坛回复查询参数对象.
 *
 *
 */
public class BbsReplyQueryParam implements QueryParam {
	/** 回复数量, 缺省 = 10; -1 表示不限制 */
	public int count = 10;
	
	/** 要查询的版面标识, 缺省 = null 表示不限制 */
	public Integer groupId = null;
	
	/** 要查询的主题标识, 缺省 = null 表示不限制 */
	public Integer topicId = null;
	
	/** 发贴的用户表示, 缺省 = null 表示不限制 */
	public Integer userId = null;
	
	/** 是否精华,缺省为 null 表示所有回复; = true 表示获取精华的; = false 表示获取非精华的. */
	public Boolean bestType = null;
	
	/** 删除状态, == null 表示不区分, 缺省 = false 查询未删除的. */
	public Boolean delState = Boolean.FALSE;
	
	/** 要选择的字段, 只在 getReplyDataTable() 方法中生效 */
	public String selectFields = BbsService.GET_REPLY_DATATABLE;
	
	/** 
     * 排序方式 
	 * 排序方法，为1按发表时间，为2按点击数，为3按回复数. 缺省 = 1
	 */
	public int orderType = 1;
	/** 排序方法，为1按发表时间 逆序排列. */
	public static final int ORDER_TYPE_CREATEDATE_DESC = 1;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Reply R";
		
		// 版面限定.
		if (this.groupId != null)
			query.addAndWhere("R.groupId = " + this.groupId);
		
		// 主题限定.
		if (this.topicId != null)
			query.addAndWhere("R.topicId = " + this.topicId);
		
		// 发表回复者限定.
		if (this.userId != null)
			query.addAndWhere("R.userId = " + this.userId);
		// 是否精华.
		if(this.bestType != null) {
			query.addAndWhere("R.isBest = " + this.bestType);
		}
		// 是否删除.
		if(this.delState != null) {
			query.addAndWhere("R.isDeleted = " + this.delState);
		}
		
		// 排序.
		switch (this.orderType) {
		case BbsReplyQueryParam.ORDER_TYPE_CREATEDATE_DESC:
			query.addOrder("R.createDate DESC");
			break;
		}
		return query;
	}

}
