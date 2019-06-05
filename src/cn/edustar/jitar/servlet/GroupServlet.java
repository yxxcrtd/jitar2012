package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 访问群组的 Servlet, 访问形式：
 *   访问群组首页:			'http://www.domain.com/ctxt/g/$groupName'
 *   访问 RSS: 		  		'http://www.domain.com/ctxt/g/$groupName/rss/article.xml', 'rss/hot_article.xml', 'rss/best_article.xml'
 *   访问群组文章:		 	'http://www.domain.com/ctxt/g/$groupName/article/$articleId.html'
 *   群组分类:         	'http://www.domain.com/ctxt/g/$groupName/category/$cid.html?para
 *   AJAX 请求群组模块: 	'http://www.domain.com/ctxt/g/$groupName/module/$mod_name?para
 *   (以后可能)群组论坛: 'http://www.domain.com/ctxt/g/$groupName/bbs/xxx
 * 
 *
 */
public class GroupServlet extends HttpServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 6639913560140220537L;
	
	/** ShowGroupBean 在 Spring 中配置的名字 */
	/// private String Show_Group_Bean = "showGroup";
	
	/** 获得群组最新文章 rss 聚合 */
	private String Show_Rss_Bean = "groupRss";
	
	/** 显示群组文章 */
	private String Show_Group_Article = "showGroupArticle";
	
	@SuppressWarnings("unused")
	private String Show_Group_Category_Bean = "showGroupCategory";

	private String Group_Module_Ajax_Bean = "groupModuleAjax";
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 得到访问地址，从地址里面看访问的是什么.
		String path_info = request.getPathInfo();		// '/$groupName...'
		if (path_info.startsWith("/"))
			path_info = path_info.substring(1);
		if (path_info.length() == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String bean_name = null;
		// path_info now is : '$groupName/...';
		String[] parts = path_info.split("/");
		if (parts.length == 1) {
			// bean_name = this.Show_Group_Bean;
			// 访问协作组首页.
			getServletContext().getRequestDispatcher("/WEB-INF/py/show_group_index.py").forward(request, response);
			return;
		} 
		
		if (parts.length == 2) {
			// 暂时没有这种 url.
		} else if (parts.length == 3) {		// ['manager', 'rescate', '22.html']
			if ("rescate".equals(parts[1])) {
				// 访问协作组资源分类.
				getServletContext().getRequestDispatcher("/WEB-INF/py/show_group_resource_category.py").forward(request, response);
				return;
			} else if ("artcate".equals(parts[1])) { 
				// 访问协作组文章分类.
				getServletContext().getRequestDispatcher("/WEB-INF/py/show_group_article_category.py").forward(request, response);
				return;
			} else if ("module".equals(parts[1])) {
				bean_name = this.Group_Module_Ajax_Bean;
			} else if ("py".equals(parts[1])) {
				String fileName = path_info.substring(path_info.lastIndexOf("/") + 1);
				request.setAttribute("groupName", parts[0]);
				this.getServletContext().getRequestDispatcher("/WEB-INF/py/" + fileName).forward(request, response);
				return;
			} else if ("article".equals(parts[1])) {
				bean_name = this.Show_Group_Article;
			} else if ("rss".equals(parts[1])) {
				bean_name = this.Show_Rss_Bean;
			}
		}

		if (bean_name == null) {
			// 不支持的都返回未找到
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
			return;
		}
		
		// 从 spring factory 中获取处理 bean
		WebApplicationContext app_ctxt = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ServletHandler bean = (ServletHandler)app_ctxt.getBean(bean_name);
		
		// 让该 bean 进行处理
		bean.handleRequest(request, response);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		
	}
	
}
