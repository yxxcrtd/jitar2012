package cn.edustar.jitar.service;

import java.util.Date;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.util.DateUtil;

/**
 * 获取文章的参数包装对象.
 * 	 用于 ArticleService.getArticleDataTable() 方法.
 *   用于 ArticleService.getArticleList() 方法.
 *   用于 GroupService.getGroupArticleList() 方法.
 * 
 *
 */
public class ArticleQueryParam extends DocumentQueryParam implements QueryParam {
	/** 获得文章条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public int count = 10;
	
	/** 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的 */
	public Boolean bestType = null;
	
	/** 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的。 */
	public Integer hideState = 0;
	
	/** 查询草稿状态，== null 表示不区分，缺省 = false 查询非草稿的。 */
	public Boolean draftState = Boolean.FALSE;
	
	/** 查询推荐状态, 缺省 = null 表示不区分.  */
	public Boolean recommendState = null;
	
	/** 删除状态，== null 表示不区分，缺省 = false 查询未删除的。  */
	public Boolean delState = Boolean.FALSE;
	

	/** 是否提取系统分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public boolean retrieveSystemCategory = false;
	
	/** 是否提取用户分类信息; 缺省 = false 表示不提取; true 表示提取 */
	public boolean retrieveUserCategory = false;
	
	/** 所属学科标识, 缺省 = null. 必须设置 useSubjectId 才生效. */
	public Integer subjectId = null;
	
	/** 是否限定所属学科, 缺省 = false 不限定. */
	public boolean useSubjectId = false;
	
	/** 从创建开始算起到到现在的天数在指定范围内的, 单位: 天；缺省 = null 表示不限制. */
	public Double daysFromCreate = null;

	/** 对文章标题进行 LIKE 限定的关键字, 缺省 = null 表示不限定 */
	public String k = null;
	
	public Integer groupCateId = null;
	
	/**
	 * 根据条件创建查询对象.
	 * @return
	 */
	public QueryHelper createQuery() {
		QueryHelper query = new QueryHelper();
		query.fromClause = "FROM Article a";
		// 用户.
		if (this.userId != null) {
			query.addAndWhere("a.userId = " + this.userId);
		}

		// 是否精华.
		if (this.bestType != null) {
			query.addAndWhere("a.bestState = " + this.bestType);
		}
		
		// 所属学科.
		if (this.useSubjectId) {
			if (this.subjectId == null)
				query.addAndWhere("a.subjectId IS NULL");
			else
				query.addAndWhere("a.subjectId = " + this.subjectId);
		}

		// 系统分类，个人分类.
		if (this.sysCateId != null)
			query.addAndWhere("a.sysCateId = " + this.sysCateId);
		if (this.userCateId != null)
			query.addAndWhere("a.userCateId = " + this.userCateId);

		// 审核状态.
		if (this.auditState != null)
			query.addAndWhere("a.auditState = " + this.auditState);
		// 隐藏状态.
		if (this.hideState != null)
			query.addAndWhere("a.hideState = " + this.hideState);
		// 草稿状态.
		if (this.draftState != null)
			query.addAndWhere("a.draftState = " + this.draftState);
		// 推荐状态.
		if (this.recommendState != null)
			query.addAndWhere("a.recommendState = " + this.recommendState);
		// 删除状态.
		if (this.delState != null)
			query.addAndWhere("a.delState = " + this.delState);
		
		

		// 创建日期.
		if (this.daysFromCreate != null) {
			query.addAndWhere("a.createDate >= :createDate");
			Date fromCreate = DateUtil.addDays(DateUtil.getNow(), -daysFromCreate);
			query.setDate("createDate", fromCreate);
		}
		if (this.k != null && this.k.length() > 0) {
			query.addAndWhere("a.title LIKE :likeTitle");
			query.setString("likeTitle", "%" + this.k + "%");
		}
		
		if (this.groupCateId != null) {
			query.addAndWhere("ga.groupCateId = " + this.groupCateId);
		}

		// 排序部分.
		String order_hql = "";
		switch (this.orderType) {
		case ORDER_TYPE_ID_DESC:
			order_hql = "a.articleId DESC";
			break;
		case ORDER_TYPE_CREATEDATE_DESC:
			order_hql = "a.createDate DESC, a.articleId DESC";
			break;
		case ORDER_TYPE_VIEWCOUNT_DESC:
			order_hql = "a.viewCount DESC, a.articleId DESC";
			break;
		case ORDER_TYPE_COMMENTS_DESC:
			order_hql = "a.commentCount DESC, a.articleId DESC";
			break;
		}
		query.addOrder(order_hql);

		return query;
	}
}
