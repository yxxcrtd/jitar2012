package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 
 * 一般为了查询指定群组下的视频列表，将限定条件 groupId.
 */
public class GroupVideoQueryParam implements QueryParam {
	public int count = 10;
	public String k = null;
	public Integer groupCateId = null;
	public int orderType = 1;
	
	/** 要查询的视频的所属群组, 缺省 = null 表示不限制 */
	public Integer groupId;
	
	/** 视频标识, 缺省 = null 表示不限制 */
	public Integer videoId;

	/** 是否是群组精华视频, 缺省 = null 表示不限制 */
	public Boolean isGroupBest = null;

	/** 要查询的字段列表, 缺省有视频的部分属性和群组视频部分属性 */
	public String selectFields = "v.videoId, v.title, v.tags, v.userId, v.createDate, " +
			"v.viewCount, v.gradeId, v.subjectId,v.href,v.flvHref,v.flvThumbNailHref, " +
			"gv.groupId, gv.isGroupBest ";	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		// 先创建原来的查询对象, 原查询只获取 Article a.
		QueryHelper query = new QueryHelper();		
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM Video v,GroupVideo gv ";
		query.addAndWhere("v.videoId = gv.videoId");
		
		if (this.groupId != null)
			query.addAndWhere("gv.groupId = " + this.groupId);
		if (this.isGroupBest != null)
			query.addAndWhere("gv.isGroupBest = " + this.isGroupBest);
		if(this.groupCateId != null)
		{
			query.addAndWhere("gv.groupCateId = " + this.groupCateId);
		}
		if (this.k != null && this.k.length() > 0) {
			query.addAndWhere("v.title LIKE :likeTitle");
			query.setString("likeTitle", "%" + this.k + "%");
		}
		
		String order_hql = "";
		switch (this.orderType) {
		case 1:
			order_hql = "gv.videoId DESC";
			break;
		default:
			order_hql = "gv.id DESC";
			break;
		}
		query.addOrder(order_hql);
		return query;
	}
}
