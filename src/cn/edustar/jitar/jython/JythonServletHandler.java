package cn.edustar.jitar.jython;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.python.core.Py;
import org.python.core.PyClass;
import org.python.core.PyInstance;
import org.python.core.PyObject;
import org.python.core.PyString;

import cn.edustar.jitar.service.TemplateProcessor;

/**
 * PythonServlet 请求实际处理器.
 *
 *
 */
public class JythonServletHandler {
	/** Web 系统环境 */
	protected ServletContext servlet_ctxt;

	/** Python 脚本支持对象 */
	protected JythonScriptManager jython_mgr;

	/** Spring 中的配置 freemarker processor 对象 */
	protected TemplateProcessor t_proc;
	
	/** servlet 请求对象 */
	protected ServletRequest request;

	/** servlet 响应对象 */
	protected ServletResponse response;

	/** 实际执行请求的 python instance 对象 */
	protected PyInstance py_instance;

	/**
	 * 构造.
	 */
	public JythonServletHandler() {
	}

	/** Web 系统环境 */
	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}

	/** Python 脚本支持对象 */
	public void setJythonScriptManager(JythonScriptManager jython_mgr) {
		this.jython_mgr = jython_mgr;
	}

	/** Spring 中的配置 freemarker processor 对象 */
	public void setTemplateProcessor(TemplateProcessor val) {
		this.t_proc = val;
	}
	
	/** Web 系统环境 */
	public void setServletContext(ServletContext servlet_ctxt) {
		this.servlet_ctxt = servlet_ctxt;
	}

	/** servlet 请求对象 */
	public void setServletRequest(ServletRequest request) {
		this.request = request;
	}

	/** servlet 响应对象 */
	public void setServletResponse(ServletResponse response) {
		this.response = response;
	}

	/**
	 * 执行 servlet 服务请求.
	 */
	public void serivce() throws ServletException, IOException {
		// 我们假定缺省内容返回为 text/html, 编码为 UTF-8.
		response.setContentType("text/html; charset=UTF-8");
		
		// 得到 py 脚本文件.
		String spath = (String)request.getAttribute("javax.servlet.include.servlet_path");		
		
		if (spath == null) {
			spath = ((HttpServletRequest) request).getServletPath();
			if (spath == null || spath.length() == 0) {
				// Servlet 2.1 puts the path of an extension-matched
				// servlet in PathInfo.
				spath = ((HttpServletRequest) request).getPathInfo();
			}
		}
		//System.out.println("spath = " + spath );
		
		String rpath = getServletContext().getRealPath(spath);
		File f = new File(rpath);
		if(!f.exists()) 
		{
			f = null;
			response.getWriter().write("文件路径不存在。 ");
			response.getWriter().close();
			return;
		}
		//System.out.println("rpath = " + rpath );
		// 产生该 python 的一个实例, 并调用其 execute() 方法.
		try {
			// 加载 python 类.
			PyObject pycls = jython_mgr.getPythonClass(rpath);
			if(pycls == null)
			{
				response.getWriter().print("类不存在。可能是文件名大小写导致的。");
				response.getWriter().close();
				return;
			}

			PyObject result = executeScript(pycls);

			// 执行结果.
			processResult(result);
		} catch (Throwable ex) {
			if (response.isCommitted()) {
				PrintWriter writer = response.getWriter();
				writer.println("'</option>\"</script></select></style><pre>");
				ex.printStackTrace(writer);
				writer.println("</pre>");
			} else
			{
				//HttpServletRequest req =(HttpServletRequest)request;
				//System.out.println("下面错误的调用页面可能是：" + req.getHeader("Referer"));
				throw new ServletException(ex);
			}
		}
	}

	/**
	 * 产生指定 python 类的一个实例, 并执行其 execute() 方法.
	 * 
	 * @param cls
	 */
	protected PyObject executeScript(PyObject cls) {
		// 产生该类的一个新实例.
		//this.py_instance = (PyInstance) cls.__call__();

		// 调用该对象的 'execute()' 方法.
		PyObject result = cls.__call__().invoke("execute".intern());

		// 返回.
		return result;
	}

	/**
	 * 根据 script 返回结果执行下一步.
	 * @param result
	 */
	protected void processResult(Object result) throws IOException, ServletException {
		// 不执行任何操作.
		if (result == null)
			return;
		if (result == Py.None)
			return;
		if (result instanceof PyString || result instanceof String) {
			processStringResult(result.toString());
			return;
		}
		// TODO: process python tuple, list result
	}

	protected void processStringResult(String result) throws IOException, ServletException {
		if (result == null || result.length() == 0 || "none".equalsIgnoreCase(result))
			return;
		if (result.endsWith("ftl")) {
			processFreemarkerResult(result);
		} else {
			// .jsp ? .py? 总是转过去.
			RequestDispatcher dispatcher = servlet_ctxt.getRequestDispatcher(result);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * 执行下一个 freemarker 请求.
	 * 
	 * @param ftl
	 */
	protected void processFreemarkerResult(String ftl) throws IOException {
		// 执行 freemarker 模板合成.
		Object root_map = t_proc.createRootMap((HttpServletRequest)request, null);
		t_proc.processTemplate(root_map, response.getWriter(), ftl, null);
		root_map = null;
	}
	
}
