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

/**
 * 系统服务 servlet.
 *   也添加了一些通用 ajax 请求的入口地址(不知道这样好不好)
 * 
 *
 * 
 * <ul>
 *  <li>简单数据获取服务: '/s/sdapi/$methodName?param'
 * </ul> 
 */
public class EntityPageServlet extends HttpServlet {
	/** logger */
	private static final Log logger = LogFactory.getLog(EntityPageServlet.class);

	/** serialVersionUID */
	private static final long serialVersionUID = 0L;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
		// no-op
	}

	/** 支持 '/s/module/$modname' AJAX 请求的处理 bean */
	private String moduleBeanName = "sysModAjax";
	
	/** 支持 '/s/sdapi/$method' 简单数据请求服务 bean */
	private String sdapiBeanName = "simpleDataApi";
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean log_debug = logger.isDebugEnabled();
		// 得到访问路径.
		String path_info = request.getPathInfo();
		if (log_debug) {
			logger.debug("EntityPageServlet path_info = " + path_info);
		}
		
		// 解析 path_info.
		if (path_info == null) path_info = "";
		String[] parts = path_info.split("/");	// ['', 'xxx', ...]
		if (parts.length < 2) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
			return;
		}
		
		// parts[1] 是 context 部分.
		String bean_name = null;			// 处理 bean 的名字.
		if ("sdapi".equals(parts[1]))
			bean_name = this.sdapiBeanName;
		else if ("module".equals(parts[1]))
			bean_name = this.moduleBeanName;
		else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 从 spring factory 中获取 处理用的 bean.
		WebApplicationContext app_ctxt = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		
		try {
			ServletHandler handler = (ServletHandler)app_ctxt.getBean(bean_name);
			handler.handleRequest(request, response);
		} catch (BeansException ex) {
			logger.error("试图获取 bean " + bean_name + " 的时候发生异常，请检查配置文件是否正确", ex);
			throw new ServletException(ex);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		// no-op
	}
}
