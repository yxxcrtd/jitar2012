package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.ArticleModel;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 显示一个博文的 bean, 博文
 *   URL: 'http://www.domain.com/groups/u/$userName/article/$articleid.html'
 *   PathInfo: '/$userName/article/$articleid.html'
 * 
 *
 * @deprecated 被 py/show_article.py 代替
 */
public class ShowEntryBean extends BaseShowUser {
	
	/** 文章记录器 */
	private static final Log logger = LogFactory.getLog(ShowEntryBean.class);
	
	/** 用户标识 */
	private String _user_loginName;
	
	/** 文章标识 */
	private int _article_id;
	
	/** 文章服务 */
	private ArticleService art_svc;
	
	/** 文章服务 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}
	
	/** 用户对象 */
	private User user_model;
	
	/** 博文对象 */
	private ArticleModel article_model;
	
	/** 页面对象 */
	private Page entry_page;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析请求 uri, 得到要访问的博文所述用户和博文标识.
		if (parseUri() == false) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 获得用户.
		this.user_model = getUserService().getUserByLoginName(this._user_loginName);
		if (this.user_model == null) {
			// 用户不存在.
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 获得博文, ?? cache.
		Article article = art_svc.getArticle(this._article_id);
		if (article == null || article.getUserId() != user_model.getUserId()) {
			// 文章不存在, 或不属于该用户.
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		this.article_model = ArticleModel.wrap(article);
		
		// 得到此文章的 page. 当前先实现使用系统缺省的.
		this.entry_page = getSystemPageWithUserSkin(PageKey.SYSTEM_USER_ENTRY, user_model._getUserObject());
		
		if (this.entry_page == null) {
			logger.error("没有找到系统页面: SYSTEM_USER_ENTRY ");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "System entry page unexist");
			return;
		}
		
		// 得到该页面所有 widget
		List<Widget> webpart_list = getPageService().getPageWidgets(this.entry_page.getPageId());
		
		// 合成模板
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user_model);
		root_map.put("article", article_model);
		root_map.put("page", this.entry_page);
		root_map.put("widgets", webpart_list);
		
		// 使用位于 WEB-INF 下面的用户缺省首页模板.
		String template_name = "/WEB-INF/user/default/user_entry.ftl";

		response.setContentType("text/html; charset=UTF-8");
		processTemplate(getTemplateProcessor(), root_map, template_name);
		
		art_svc.increaseViewCount(article_model.getArticleId(), 1);
	}
	
	// 解析请求 uri, 从里面得到用户标识和文章标识.
	// 从 BlogServlet 中调用的时候，path_info = '/$userName/article/$articleid.html';
	private boolean parseUri() {
		String path_info = request.getPathInfo();	// '/$username/article/$aid.html'
		if (path_info == null) return false;
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);		// '$username/article/$aid.html'
		
		String[] o = path_info.split("/");		// [$username, article, $aid.html]
		if (o.length != 3) return false;
		this._user_loginName = o[0];
		if ("article".equals(o[1]) == false)
			return false;
		if (o[2].endsWith(".html") == false)
			return false;
		o[2] = o[2].substring(0, o[2].length() - ".html".length());  // get rid of '.html'
		// now o[0] is $username, o[2] is $aid
		if (!ParamUtil.isInteger(o[2])) return false;
		this._article_id = Integer.parseInt(o[2]);
		
		// OK 了，这种解析工作也许应该放在外面的类里面完成 ??, 或者搞个模板.
		return true;
	}
}
