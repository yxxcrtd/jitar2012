package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 群组资源查询参数.
 *
 *
 */
public class GroupResourceQueryParam implements QueryParam {
	/** 内部的资源查询对象 */
	public ResourceQueryParam resourceQueryParam = new ResourceQueryParam();

	/** 要查询的资源的所属群组, 缺省 = null 表示不限制 */
	public Integer groupId;
	
	/** 资源标识, 缺省 = null 表示不限制 */
	public Integer resourceId;

	/** 是否是群组精华资源, 缺省 = null 表示不限制 */
	public Boolean isGroupBest = null;

	/** 要查询的字段, 缺省只有少量字段 */
	public String selectFields = "r.resourceId, r.title, r.tags, r.userId, r.createDate, " +
		"r.viewCount, r.subjectId, r.fsize as fsize, " +
		"gr.groupId, gr.isGroupBest ";
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		// 先创建原来的查询对象, 原查询只获取 Resource r.
		QueryHelper query = resourceQueryParam.createQuery();
		
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM Resource r, GroupResource gr ";
		query.addAndWhere("r.resourceId = gr.resourceId");
		
		if (this.groupId != null)
			query.addAndWhere("gr.groupId = " + this.groupId);
		if (this.resourceId != null)
			query.addAndWhere("r.resourceId = " + this.resourceId);
		if (this.isGroupBest != null)
			query.addAndWhere("gr.isGroupBest = " + this.isGroupBest);
		
		return query;
	}

}
