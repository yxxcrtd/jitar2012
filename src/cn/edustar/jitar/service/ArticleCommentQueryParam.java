package cn.edustar.jitar.service;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ObjectType;

/**
 * 结合了 ArticleQueryParam 和 CommentQueryParam 的查询对象.
 *
 *
 */
public class ArticleCommentQueryParam implements QueryParam {
	/** 要查询的记录数量, 其覆盖 articleQueryParam, commentQueryParam 中的对等设置 */
	public int count = 10;
	
	/** 文章查询条件 */
	public ArticleQueryParam articleQueryParam = new ArticleQueryParam();
	
	/** 评论查询条件 */
	public CommentQueryParam commentQueryParam = new CommentQueryParam();
	
	/**
	 * 缺省构造.
	 */
	public ArticleCommentQueryParam() {
		this.articleQueryParam.orderType = -1;	// 缺省去掉文章排序.
		this.commentQueryParam.objectType = ObjectType.OBJECT_TYPE_ARTICLE;
	}
	
	/**
	 * 根据查询条件构造查询对象.
	 * @return
	 */
	public QueryHelper createQuery() {
		QueryHelper query = this.articleQueryParam.createQuery();
		commentQueryParam.combineQuery(query);
		query.selectClause = "SELECT c ";
		query.fromClause = "FROM Article a, Comment c ";
		query.addAndWhere("c.objId = a.articleId");
		
		return query;
	}
}
