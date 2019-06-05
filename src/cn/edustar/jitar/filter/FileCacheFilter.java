package cn.edustar.jitar.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 孟宪会
 * 
 */
public class FileCacheFilter implements Filter {

	// TOMCAT 7 可以通过配置文件实现缓存了，无需再使用下面的方法。
	// 参见 http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html
	private static int CACHE_DAYS = 30;// 30天
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String m = req.getHeader("If-Modified-Since");
		if (m != null && m.length() > 0) {
			try {
				Calendar now = Calendar.getInstance();
				long d = now.getTimeInMillis() - req.getDateHeader("If-Modified-Since");
				d = d / 1000 / 60 / 60 / 24;
				if (d > CACHE_DAYS) {
					httpResponse.setDateHeader("Last-Modified", now.getTimeInMillis());
					long expires = CACHE_DAYS * 24 * 60 * 60; // 30天
					httpResponse.setDateHeader("Expires", System.currentTimeMillis() + (1000 * expires));
					httpResponse.setHeader("Cache-Control", "public, max-age=" + expires);
				}
				else {
					httpResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return;
				}
			}
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		else {
			long expires = CACHE_DAYS * 24 * 60 * 60; 
			httpResponse.setDateHeader("Expires", System.currentTimeMillis() + (1000 * expires));
			httpResponse.setHeader("Cache-Control", "public, max-age=" + expires);
		}
		chain.doFilter(request, httpResponse);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}
