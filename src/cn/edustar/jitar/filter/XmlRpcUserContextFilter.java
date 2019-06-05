package cn.edustar.jitar.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.edustar.jitar.context.UserContext;

/**
 * @author Yang XinXin
 * @version 2.0.0, 2010-09-17 15:53:23
 */
public class XmlRpcUserContextFilter implements Filter {
	private FilterConfig config;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		UserContext.setCurrentUserContext((UserContext) config
				.getServletContext().getAttribute(
						UserContext.USER_CONTEXT_KEY_NAME));
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
