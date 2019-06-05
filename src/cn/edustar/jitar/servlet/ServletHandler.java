package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义处理 Servlet 请求的 bean 必须要实现的接口。
 *
 *
 */
public interface ServletHandler {
	/** Status code (404) indicating that the requested resource is not available. */
	public static final int SC_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;
	
	/**
	 * 实际处理请求。
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException;
}
