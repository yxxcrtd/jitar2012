package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.model.UserMgrClientModel;
import cn.edustar.jitar.model.UserMgrModel;

/**
 * @author 孟宪会
 *
 */
public class BlogServlet extends HttpServlet {

	private static final long serialVersionUID = -7268367655449721927L;

	private static final Log logger = LogFactory.getLog(BlogServlet.class);

	/** 为处理用户 module 回调请求的 bean */
	private String Ajax_Bean_Name = "userModuleAjax";

	/** 获取用户订阅信息的处理 bean */
	//private String Rss_Bean_Name = "userRssBean";

	public void init() {}

	public void destroy() {}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String userName = "";
		String bean_name = "";
		String context_path = request.getRequestURI();
		String virtualPath = request.getContextPath();
		//logger.info("BlogServlet 测试 virtualPath = " + virtualPath);	
		if(!virtualPath.endsWith("/"))
		{
			//兼容虚拟目录
			context_path = context_path.replaceFirst(virtualPath, "");
		}
		String trimedUrl = context_path.substring(3); //去掉 /u/三个字符
		
		//logger.info("测试 serverName = " + serverName);
		// 进入这个页面的 必然都是 /u/ 开头，否则，按照  JitarUrlRewrite 的规则，不会进入到本页面的
		//logger.info("BlogServlet 测试 context_path = " + context_path);		
		
		if(!context_path.startsWith("/u/"))		
		{
			// 进入这个页面的 必然都是 /u/ 开头，否则，按照  JitarUrlRewrite 的规则，不会进入到本页面的
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if(trimedUrl.endsWith("/")) trimedUrl = trimedUrl.substring(0,trimedUrl.length()-1);
		//提取用户登录名
		// admin,admin/xxxxxxx的格式
		userName = trimedUrl;
		if(userName.indexOf("/")>-1)
		{
			userName = userName.substring(0,userName.indexOf("/"));
		}
		//logger.info("测试userName = " + userName);
		if(request.getAttribute("UserName") == null)
		{
			request.setAttribute("UserName",userName);
		}
		if(request.getAttribute("loginName") == null)
		{
			request.setAttribute("loginName",userName);		
		}
		
		String UserSiteUrl = request.getContextPath();
		if( !UserSiteUrl.endsWith("/"))
		{
			UserSiteUrl+="/";
		}
		UserSiteUrl += userName + "/";
		request.setAttribute("UserSiteUrl",UserSiteUrl);	
		request.setAttribute("UserMgrClientUrl",UserMgrClientModel.getUserMgrClientUrl());
		request.setAttribute("UserMgrUrl",UserMgrModel.getUserMgrUrl());
		
		if(trimedUrl.indexOf("/") == -1 || context_path.endsWith("/index.py")){
			//logger.info("访问用户首页");
			this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_index.py").forward(request, response);
			return;
		}
		else
		{	
			//此时，trimedUrl 里 必然存在一个 /，
			String[] urlSlice = trimedUrl.split("/");
			String slice1 = urlSlice[1];
			
			//logger.info("slice1 = " +slice1);
			if(slice1.equals("category"))			{	
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_article_category.py").forward(request,response);
				return;
			}else if ("article".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_article.py").forward(request, response);
				return;
			}else if ("photocate".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_photo_category.py").forward(request, response);
				return;
			}else if ("rescate".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_resource_category.py").forward(request, response);
				return;
			}else if ("photo".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/user_photo_show.py").forward(request, response);
				return;
			}else if ("friend".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/user_friend_list.py").forward(request, response);
				return;
			}else if ("profile".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_profile.py").forward(request, response);
				return;
			}else if ("group".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/user_group_list.py").forward(request, response);
				return;
			}else if ("videocate".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_video_category.py").forward(request, response);
				return;
			}else if ("videocatelist".equals(slice1)) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/show_user_videos_category.py").forward(request, response);
				return;
			}else if(slice1.equals("py")){
				String fileName = trimedUrl.substring(trimedUrl.lastIndexOf("/") + 1);
				this.getServletContext().getRequestDispatcher("/WEB-INF/program/blog/" + fileName).forward(request, response);
				return;
			}else if(slice1.equals("html")){
				String fileName = trimedUrl.substring(trimedUrl.lastIndexOf("/") + 1);
				this.getServletContext().getRequestDispatcher("/html/user/" + userName + "/" + fileName).forward(request, response);
				return;
			}
			else if(slice1.equals("index.html")){
				this.getServletContext().getRequestDispatcher("/html/user/" + userName + "/index.html").forward(request, response);
				return;
			}
			else if(slice1.equals("module")){
				bean_name = this.Ajax_Bean_Name;			
			}else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			//this.getServletContext().getRequestDispatcher("/WEB-INF/py/_index.py").forward(request, response);
			// 从 spring factory 中获取 处理用的 bean.
			WebApplicationContext app_ctxt = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	
			try {				
				ServletHandler handler = (ServletHandler) app_ctxt.getBean(bean_name);
				handler.handleRequest(request, response);
			} catch (BeansException ex) {
				logger.error("试图获取 bean " + bean_name + " 的时候发生异常，请检查配置文件是否正确", ex);
				throw new ServletException(ex);
			}
		}		
	}
}
