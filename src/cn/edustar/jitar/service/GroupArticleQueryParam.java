package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;

/**
 * 
 * 一般为了查询指定群组下的文章列表，将限定条件 groupId.
 */
public class GroupArticleQueryParam implements QueryParam {
	public int count = 10;
	public String k = null;
	public Integer groupCateId = null;
	public int orderType = 1;
	
	/** 要查询的文章的所属群组, 缺省 = null 表示不限制 */
	public Integer groupId;
	
	/** 文章标识, 缺省 = null 表示不限制 */
	public Integer articleId;

	/** 是否是群组精华文章, 缺省 = null 表示不限制 */
	public Boolean isGroupBest = null;

	/** 要查询的字段列表, 缺省有文章的部分属性和群组文章部分属性 */
	public String selectFields = "ga.articleId, ga.groupId, ga.userId, ga.loginName, ga.userTrueName, ga.title, ga.createDate, ga.isGroupBest, ga.pubDate, ga.groupCateId, ga.typeState ";
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.QueryParam#createQuery()
	 */
	public QueryHelper createQuery() {
		// 先创建原来的查询对象, 原查询只获取 Article a.
		QueryHelper query = new QueryHelper();		
		query.selectClause = "SELECT " + this.selectFields;
		query.fromClause = "FROM GroupArticle ga ";
		query.addAndWhere("articleState=1");
		
		if (this.groupId != null)
			query.addAndWhere("ga.groupId = " + this.groupId);
		if (this.isGroupBest != null)
			query.addAndWhere("ga.isGroupBest = " + this.isGroupBest);
		if(this.groupCateId != null)
		{
			query.addAndWhere("ga.groupCateId = " + this.groupCateId);
		}
		if (this.k != null && this.k.length() > 0) {
			query.addAndWhere("ga.title LIKE :likeTitle");
			query.setString("likeTitle", "%" + this.k + "%");
		}
		
		String order_hql = "";
		switch (this.orderType) {
		case 1:
			order_hql = "ga.articleId DESC";
			break;
		default:
			order_hql = "ga.id DESC";
			break;
		}
		query.addOrder(order_hql);
		return query;
	}
}
