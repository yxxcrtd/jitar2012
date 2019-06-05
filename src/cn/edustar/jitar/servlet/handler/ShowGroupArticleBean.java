package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.ArticleModel;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 显示发布到群组中的文章.
 *   URL: 'http://www.domain.com/ctxt/g/$groupname/article/$articleId.html'
 *   PathInfo: '/$groupname/article/$articleId.html'
 *
 *
 */
public class ShowGroupArticleBean extends ServletBeanBase {
	/** 文章记录器 */
	@SuppressWarnings("deprecation")
	private static final Log logger = LogFactory.getLog(ShowEntryBean.class);
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 用户服务 */
	private UserService user_svc;
	
	/** 模板合成服务 */
	private TemplateProcessor t_proc;

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 页面服务 */
	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}

	/** 模板合成服务 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}

	/** 群组名字 */
	private String group_name;
	
	/** 文章标识 */
	private int article_id;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		if (parsePathInfo() == false) {
			not_found();
			return;
		}
		
		// 得到群组.
		Group group = group_svc.getGroupMayCached(group_name);
		if (group == null) {
			not_found();
			return;
		}
		
		// 得到文章.
		Tuple<Article, GroupArticle> tuple = group_svc.getGroupArticleByArticleId(group._getGroupObject(), article_id);
		if (tuple == null || tuple.getKey() == null) {
			not_found();
			return;
		}
		
		// 得到文章作者.
		User user = user_svc.getUserById(tuple.getKey().getUserId());
		
		// 得到页面.
		Page page = page_svc.getPageByKey(PageKey.SYSTEM_GROUP_ARTICLE_INDEX);
		if (page == null) {
			logger.error("没有找到系统页面: SYSTEM_GROUP_ARTICLE_INDEX ");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "System page unexist");
			return;
		}
		
		// 得到该页面所有 widget
		List<Widget> widget_list = page_svc.getPageWidgets(page.getPageId());
		
		// 合成模板.
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("article", ArticleModel.wrap(tuple.getKey()));
		root_map.put("page", page);
		root_map.put("widget_list", widget_list);
		
		// 使用位于 WEB-INF 下面的模板.
		String template_name = "/WEB-INF/group/default/group_article_index.ftl";

		response.setContentType("text/html; charset=UTF-8");
		processTemplate(t_proc, root_map, template_name);
	}
	

	/** 解析 pathinfo 中的参数, 并检查参数格式合法性 */
	private boolean parsePathInfo() {
		String path_info = request.getPathInfo();	// '/$groupname/article/$aid.html'
		if (path_info == null) return false;
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);		// '$groupname/article/$aid.html'
		
		String[] o = path_info.split("/");		// [$groupname, article, $aid.html]
		if (o.length != 3) return false;
		this.group_name = o[0];
		if (this.group_name == null || this.group_name.length() == 0) 
			return false;
		if ("article".equals(o[1]) == false)
			return false;
		if (o[2].endsWith(".html") == false)
			return false;
		o[2] = o[2].substring(0, o[2].length() - ".html".length());  // get rid of '.html'
		// now o[0] is $username, o[2] is $aid
		if (!ParamUtil.isInteger(o[2])) return false;
		this.article_id = Integer.parseInt(o[2]);
		
		// OK 了，这种解析工作也许应该放在外面的类里面完成 ??, 或者搞个模板.
		return true;
	}
}
