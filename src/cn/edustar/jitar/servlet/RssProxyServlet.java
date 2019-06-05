package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 实现 rss 请求的代理. 参见文档 'RSS模块实现'
 * 
 * Servlet 配置的处理地址：
 * 
 * '/manage/proxy/rss' '/s/rss'
 * 
 * 
 * 
 */
@WebServlet(name = "rssProxyServlet", urlPatterns = {"/manage/proxy/rss", "/s/rss"})
public class RssProxyServlet extends HttpServlet {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从 spring factory 中获取 show_blog bean.
        WebApplicationContext app_ctxt = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ServletHandler bean = (ServletHandler) app_ctxt.getBean("rssProxy");

        // 转发给 RssProxyBean.doGet.
        bean.handleRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
        // no-op
    }
}
