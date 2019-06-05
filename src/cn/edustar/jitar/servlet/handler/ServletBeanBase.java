package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.servlet.ServletHandler;
import cn.edustar.jitar.util.WebUtil;

/**
 * 'ShowBlogBean,ShowArticleBean'等为'servlet'服务的'bean'的基类.
 * 
 *
 * @remark 注意:(ServletBean在'spring'中都必须配置为'singleton=false',其不支持多线程).
 */
public abstract class ServletBeanBase implements ServletHandler {
	
	/** 日志 */
	private static final Log log = LogFactory.getLog(ServletBeanBase.class);

	/** request */
	protected HttpServletRequest request;

	/** response */
	protected HttpServletResponse response;

	/** 构造方法 */
	protected ServletBeanBase() {

	}

	/** HTTP 请求对象 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/** HTTP 响应对象 */
	public HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * 实际处理请求。派生类实现 handleRequest 方法即可
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//log.info("Handle request URI = " + request.getRequestURI() + ", QueryString = " + request.getQueryString());
		this.response = response;
		this.request = request;
		handleRequst();
	}

	/**
	 * 处理
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract void handleRequst() throws IOException, ServletException;

	/**
	 * 请求失败
	 *
	 * @throws IOException
	 */
	protected final void bad_request() throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	}

	/**
	 * 向客户端返回 400 Bad Request 错误
	 * 
	 * @param text
	 * @throws IOException
	 */
	protected final void bad_request(String text) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, text);
	}

	/**
	 * 向客户端返回 404 Not Found 错误
	 * 
	 * @throws IOException
	 */
	protected final void not_found() throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getRequestURI());
	}

	/**
	 * 判定当前是否有用户登录了, 判定依据是 session.getAttribute(User.SESSION_LOGIN_USERID_KEY) 非空
	 * 
	 * @return
	 */
	public boolean isUserLogined() {
		// 统一用户登录部分放到了 UserVerifyFilter 中实现了
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String loginName = (String) session.getAttribute(User.SESSION_LOGIN_NAME_KEY);
		return (loginName != null) && (loginName.length() > 0);
	}

	/**
	 * 得到当前登录用户, 登录用户在 统一用户认证 Filter 里面被设置
	 * 
	 * @return 如果没有则返回 null
	 */
	public User getLoginUser() {
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return WebUtil.getLoginUser(session);
	}

	/**
	 * 使用指定的 map 做为根数据执行执行模板, 输出到 response 中
	 * 
	 * @param root_map
	 * @param ftl
	 */
	@SuppressWarnings("unchecked")
	public void processTemplate(TemplateProcessor t_proc, Map map, String ftl) throws IOException {
		Object root_map = t_proc.createRootMap(request, map);
		t_proc.processTemplate(root_map, response.getWriter(), ftl, null);
	}
	
}
