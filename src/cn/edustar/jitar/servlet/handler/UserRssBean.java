package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UserService;

/**
 * 访问用户订阅地址: '/u/$username/rss.xml'
 * 
 *
 *
 */
public class UserRssBean extends ServletBeanBase {
	/** 用户服务 */
	private UserService user_svc;
	
	/** 文章服务 */
	private ArticleService art_svc;
	
	/** 模板合成器 */
	private TemplateProcessor t_proc;
	
	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 文章服务 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/** 模板合成器 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}
	
	/** 当前被访问的用户的标识 */
	private String userLoginName;
	
	/** 用户对象 */
	private User user_model;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析访问路径.
		if (parsePathInfo() == false) {
			not_found();
			return;
		}
		
		// 获得用户.
		this.user_model = user_svc.getUserByLoginName(this.userLoginName);
		if (this.user_model == null) {
			// 用户不存在.
			not_found();
			return;
		}
		
		// 得到用户最新 N 篇文章.
		ArticleQueryParam qp = new ArticleQueryParam();
		qp.userId = user_model.getUserId();
		qp.count = 10;
		List<ArticleModelEx> article_list = art_svc.getArticleList(qp, null);
		
		// 合成模板.
		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user_model);
		root_map.put("article_list", article_list);

		String template_name = "/WEB-INF/user/default/user_rss.ftl";

		response.setContentType("application/xml; charset=utf-8");
		processTemplate(t_proc, root_map, template_name);
	}

	/**
	 * 解析访问地址.
	 * @return
	 */
	private boolean parsePathInfo() {
		String path_info = request.getPathInfo();	// '/$username/rss.xml'
		if (path_info == null) return false;
		
		// parts = ['', '$username', 'rss.xml']
		String[] parts = path_info.split("/");
		this.userLoginName = parts[1];
		if (this.userLoginName == null || this.userLoginName.length() == 0)
			return false;
		if ("rss.xml".equals(parts[2]) == false) return false;
		
		return true;
	}
}
