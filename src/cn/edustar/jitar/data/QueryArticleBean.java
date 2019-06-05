package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;

/**
 * 查询文章的 bean, 缺省 varName = 'article_list', 缺省支持分页.
 *
 *
 */
public class QueryArticleBean extends ArticleBean {
	/**
	 * 构造.
	 */
	public QueryArticleBean() {
		super.setVarName("article_list");
		super.setUsePager(true);
		super.setPageSize(20);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 如果有学科对象则设置学科条件.
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null) {
			param.useSubjectId = true;
			param.subjectId = subject.getSubjectId();
		}
		
		// 如果有用户对象则设置用户条件.
		User user = (User)host.getContextObject("user");
		if (user != null) {
			param.userId = user.getUserId();
		}
		
		// 如果有分类对象则设置系统分类条件.
		Category category = (Category)host.getContextObject("category");
		if (category != null) {
			param.sysCateId = category.getCategoryId();
		}

		String list_type = "所有文章";
		
		// 类型.
		String type = host.getParameters().safeGetStringParam("type", "new");
		if (type == null || type.length() == 0) type = "new";
		
		if (type == null || type.length() == 0 || "new".equals(type))
			list_type = "最新文章";		// 最新, 缺省按照时间排序.
		else if ("hot".equals(type)) {	// 热门, 按照点击数排序.
			param.orderType = ArticleQueryParam.ORDER_TYPE_VIEWCOUNT_DESC;
			list_type = "热门文章";
		} else if ("best".equals(type))	{	// 精华.
			param.bestType = Boolean.TRUE;
			list_type = "精华文章";
		} else if ("rcmd".equals(type))	{	// 推荐.
			param.recommendState = Boolean.TRUE;
			list_type = "推荐文章";
		} else if ("cmt".equals(type)) {	// 评论最多.
			param.orderType = ArticleQueryParam.ORDER_TYPE_COMMENTS_DESC;
			list_type = "评论最多文章";
		}
		
		// 有关键字则添加关键字条件.
		String k = host.getParameters().safeGetStringParam("k", null);
		if (k != null && k.length() > 0) {
			param.k = k;
			list_type = "查询 " + k + " 的文章";
		}
		
		host.setData("list_type", list_type);
		host.setData("type", type);
		super.doPrepareData(host);
	}
}
