package cn.edustar.jitar;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 在 filter 中设置此 RequestContext 绑定到当前线程
 * 
 * 
 */
public class JitarRequestContext {

    /** 绑定到当前线程的 RequestContext */
    private static final ThreadLocal<JitarRequestContext> request_ctxt = new ThreadLocal<JitarRequestContext>();

    /**
     * 获得绑定到当前线程的请求环境
     * 
     * @return
     */
    public static JitarRequestContext getRequestContext() {
        return request_ctxt.get();
    }

    /**
     * 设置绑定到当前线程的请求环境
     * 
     * @param val
     */
    public static void setRequestContext(JitarRequestContext val) {
        request_ctxt.set(val);
    }

    /**
     * 构造
     * 
     * @param servlet_ctxt
     * @param request
     * @param response
     */
    public JitarRequestContext(ServletContext servlet_ctxt, ServletRequest request, ServletResponse response, JitarContext jtar_ctxt, String siteUrl) {
        this.servlt_ctxt = servlet_ctxt;
        this.request = request;
        this.response = response;
        this.jtar_ctxt = jtar_ctxt;
        this.siteUrl = siteUrl;
    }

    /** Web 应用环境 */
    protected ServletContext servlt_ctxt;

    /** 当前线程的请求 */
    protected ServletRequest request;

    /** 当前线程的响应 */
    protected ServletResponse response;

    /** 当前线程的 JitarContext */
    protected JitarContext jtar_ctxt;

    /** 当前请求的 SiteUrl */
    protected String siteUrl;

    /**
     * Web 应用环境
     * 
     * @return
     */
    public ServletContext getServletContext() {
        return this.servlt_ctxt;
    }

    /**
     * Web 应用环境
     * 
     * @param servlet_ctxt
     */
    public void setServletContext(ServletContext servlet_ctxt) {
        this.servlt_ctxt = servlet_ctxt;
    }

    /**
     * 当前请求
     * 
     * @return
     */
    public ServletRequest getRequest() {
        return this.request;
    }

    /**
     * 设置当前 request, 并返回原 request
     * 
     * @param request
     * @return
     */
    public ServletRequest setRequest(ServletRequest request) {
        ServletRequest old = this.request;
        this.request = request;
        return old;
    }

    /**
     * 当前响应
     * 
     * @return
     */
    public ServletResponse getResponse() {
        return this.response;
    }

    /**
     * 设置当前 response 对象, 并返回原 response 对象
     * 
     * @param response
     * @return
     */
    public ServletResponse setResponse(ServletResponse response) {
        ServletResponse old = this.response;
        this.response = response;
        return old;
    }

    /**
     * 当前线程的 JitarContext
     * 
     * @return
     */
    public JitarContext getJitarContext() {
        return this.jtar_ctxt;
    }

    /**
     * 当前线程的 JitarContext
     * 
     * @param jtar_ctxt
     */
    public void setJitarContext(JitarContext jtar_ctxt) {
        this.jtar_ctxt = jtar_ctxt;
    }

    /**
     * 当前请求的 SiteUrl
     * 
     * @return
     */
    public String getSiteUrl() {
        return this.siteUrl;
    }

    /**
     * 当前请求的 SiteUrl
     * 
     * @param siteUrl
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void destroy() {
        request_ctxt.set(null);
        request_ctxt.remove();
    }
}
