package cn.edustar.jitar.jython;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.JitarRequestContext;

/**
 * 解释 '.py' 的 servlet, 我们在原 Jython 的 JythonServlet 基础上进行了增强, 使得其能够更便利的写出 python 的 action
 * 
 *
 */
public class JythonServlet extends HttpServlet {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 150266651154745623L;

	/** 日志记录器 */
	private static final Log log = LogFactory.getLog(JythonServlet.class);

	/** Spring IOC 容器 */
	private ApplicationContext spring_ctxt;

	/*
	 * Jython Servlet Initialize
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() {
		this.spring_ctxt = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		if (this.spring_ctxt == null) {
			log.warn("The spring_ctxt can't be find !");
		}
	}

	/**
	 * Implementation of the HttpServlet main method.
	 * 
	 * @param request the request parameter.
	 * @param response the response parameter.
	 * @exception ServletException
	 * @exception IOException
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		/*
		 * HttpServletRequest http_req = (HttpServletRequest)request; // DEBUG
		 * System.out.println("=========== JythonServlet =============");
		 * System.out.println(" request = " + request);
		 * System.out.println(" response = " + response); 
		 * System.out.println(" request.requestURI = " + http_req.getRequestURI()); 
		 * System.out.println(" request.requestURL = " + http_req.getRequestURL()); 
		 * System.out.println(" request.servletPath = " + http_req.getServletPath()); 
		 * System.out.println(" request.pathInfo = " + http_req.getPathInfo()); 
		 * 
		 * Enumeration enumer = request.getAttributeNames(); 
		 * while (enumer.hasMoreElements()) { 
		 * Object key = enumer.nextElement(); 
		 * System.out.println(" request.attribute[" + key + "] = " + request.getAttribute(String.valueOf(key))); }
		 */

		JitarRequestContext ctx = JitarRequestContext.getRequestContext();
		
		if (ctx != null) {		
			ServletRequest old_request = ctx.setRequest(request);
			ServletResponse old_response = ctx.setResponse(response);
			
			try {
				request.setAttribute("pyservlet_ex", this);
	
				// 目标: 在 py 脚本中能够方便的处理上传.
				// 问题: 当使用 Struts 的时候, request 会被封装成为 struts MultiPartRequestWrapper.
				// 不使用 Struts 的时候, request 是原始的 tomcat RequestFacade.
				// 解决: 当有 struts 的时候, 我们从 req(MultiPartRequestWrapper) 中得到 ...
				// 其解析出来的文件等参数, 包装为我们的 Request.
				// 当不使用 Struts 的时候我们用 apache FileUpload 模块自己解析.
				String content_type = request.getContentType();
				if (content_type != null && content_type.indexOf("multipart/form-data") != -1) {
					ServletRequest origin_request = JitarRequestContext.getRequestContext().getRequest();
					// 带有文件上传部分.
					MultiPartRequestImpl req_wrapper = MultiPartRequestImpl.createWrapper((HttpServletRequest) request, (HttpServletRequest) origin_request);
					ctx.setRequest(req_wrapper);
				}
	
				// 从 spring 中构造 JythonServletHandler 对象.
				JythonServletHandler handler = (JythonServletHandler) spring_ctxt.getBean("jythonServletHandler");
	
				// 注入 app, req, resp 对象.
				handler.setServletContext(this.getServletContext());
				handler.setServletRequest(request);
				handler.setServletResponse(response);
	
				// 执行服务.
				handler.serivce();
	
				response.flushBuffer();
			} finally {
				serviceCleanup(request);
				ctx.setResponse(old_response);
				ctx.setRequest(old_request);
			}
		}
	}

	/**
	 * 清理可能的临时上载文件
	 * 
	 * @param request
	 */
	private void serviceCleanup(ServletRequest request) {
		if (request == null || !(request instanceof MultiPartRequestImpl))
			return;

		try {
			((MultiPartRequestImpl) request).close();
		} catch (Throwable ex) {
			log.error("exception when cleanup, ignored.", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		
	}
	
}
