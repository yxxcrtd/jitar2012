package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 用户文章列表(也支持分类列表)模块.
 *
 *
 */
public class UserArticleListModule extends AbstractModuleWithTP {
	/** 文章服务. */
	private ArticleService art_svc;
	
	/** 分类服务 */
	@SuppressWarnings("unused")
	private CategoryService cat_svc;
	
	/**
	 * 构造.
	 */
	public UserArticleListModule() {
		super("user_articles", "用户文章列表");
	}
	
	/** 文章服务. */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}
	
	/** 用户分类服务 */
	public void setCategoryService(CategoryService cat_svc) {
		this.cat_svc = cat_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户数据.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 得到当前访问的分类.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		Integer cid = param_util.getIntParamZeroAsNull("cid");
		
		// 得到用户该分类下的文章.
		Pager pager = new Pager();
		pager.setCurrentPage(param_util.safeGetIntParam("page", 1));
		pager.setPageSize(40);
		
		ArticleQueryParam param = new ArticleQueryParam();
		param.userCateId = cid;
		param.userId = user.getUserId();
		
		List<ArticleModelEx> list = art_svc.getArticleList(param, pager);
		
		// 合成并输出结果.
		outputHtml(user, list, pager, response);
	}

	/** 合并输出 */
	private void outputHtml(User user, List<ArticleModelEx> article_list, 
			Pager pager, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("article_list", article_list);
		root_map.put("pager", pager);
		
		String template_name = "/WEB-INF/user/default/user_articles.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
