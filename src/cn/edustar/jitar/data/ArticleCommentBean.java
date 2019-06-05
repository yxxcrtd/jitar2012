package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.service.ArticleCommentQueryParam;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 文章评论 数据访问, 缺省 varName = 'comment_list'.
 *
 *
 */
public class ArticleCommentBean extends ArticleBean {
	/** 评论服务 */
	private CommentService cmt_svc;
	
	/** 评论查询参数 */
	private CommentQueryParam commentQueryParam = new CommentQueryParam();

	/**
	 * 构造.
	 */
	public ArticleCommentBean() {
		super.setVarName("comment_list");
		this.cmt_svc = JitarContext.getCurrentJitarContext().getCommentService();
	}

	/** 发表评论的用户标识, 缺省 = null 表示不限定 */
	public void setCommentUserId(String userId) {
		commentQueryParam.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}
	
	/** 被评论的文章/资源的所属用户的标识, 缺省 = null 表示不限定 */
	public void setCommentAboutUserId(String aboutUserId) {
		commentQueryParam.aboutUserId = ParamUtil.safeParseIntegerWithNull(aboutUserId, null);
	}
	
	/** 被评论的对象标识, 缺省 = null 表示不限定 */
	public void setArticleId(String articleId) {
		commentQueryParam.objectId = ParamUtil.safeParseIntegerWithNull(articleId, null);
	}
	
	/** 审核标志的; 缺省 = true 表示获取审核通过的; = false 表示获取未审核通过的; = null 表示不限定 */
	public void setCommentAudit(String audit) {
		commentQueryParam.audit = ParamUtil.safeParseBooleanWithNull(audit, null);
	}
	
	/** 排序方式, 缺省 = 0 按照 id 逆序排列 */
	public void setOrderType(int orderType) {
		commentQueryParam.orderType = orderType;
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 构造查询参数.
		ArticleCommentQueryParam param = new ArticleCommentQueryParam();
		param.articleQueryParam = super.getQueryParam(host);
		param.commentQueryParam = this.commentQueryParam;
		
		List<Comment> comment_list = cmt_svc.getArticleCommentList(param, null);
		
		// 设置数据.
		host.setData(getVarName(), comment_list);
	}
}
