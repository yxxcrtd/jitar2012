package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ArticleModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 显示文章评论的模块.
 * 
 *
 */
public class ArticleCommentsModule extends AbstractModuleWithTP {
	/** 模块名 */
	public static final String MODULE_NAME = "article_comments";
	
	/** 模块标题 */
	public static final String MODULE_TITLE = "文章评论";

	/** 文章服务 */
	private ArticleService art_svc;

	/** 评论服务 */
	private CommentService cmt_svc;

	/**
	 * 构造.
	 */
	public ArticleCommentsModule() {
		super(MODULE_NAME, MODULE_TITLE);
	}
	
	/** 文章服务 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 得到用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 得到文章对象.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		int article_id = param_util.safeGetIntParam("aid");
		ArticleModel article = ArticleModel.wrap(art_svc.getArticle(article_id));
		if (article == null) {
			response.getOut().write("未找到文章");
			return;
		}

		// 构造分页.
		Pager pager = new Pager();
		pager.setCurrentPage(param_util.safeGetIntParam("page", 1));
		pager.setPageSize(20);
		pager.setItemNameAndUnit("评论", "条");

		// 得到文章的评论.
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_ARTICLE;
		param.objectId = article.getArticleId();
		param.audit = true;
		List<Comment> comment_list = cmt_svc.getCommentList(param, pager);

		// 合成.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("article", article);
		root_map.put("comment_list", comment_list);
		root_map.put("pager", pager);

		// ??? 好像对 IE 或者 Firefox 不生效 ??
		noResponseCache(response);

		response.setContentType(TEXT_HTML_UTF_8);
		String template_name = "/WEB-INF/user/default/article_comments.ftl";
		processTemplate(root_map, response.getOut(), template_name);
	}
}
