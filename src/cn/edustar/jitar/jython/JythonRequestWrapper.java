package cn.edustar.jitar.jython;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cn.edustar.jitar.JitarRequestContext;

/**
 * 提供给 python 使用的当前 request 对象包装, 其从当前线程中 RequestContext 的 ServletRequest
 * 直接调用原方法.
 * 
 * 注意: 此对象由于是全局使用的, 其在构造的时候并无 request 对象存在, 因此无法使用 Wrapper 基类. 其通过特定方法让 python
 * 脚本访问, 所以实现起来略有特殊.
 * 
 *
 * 
 */
public class JythonRequestWrapper implements HttpServletRequest {
	private HttpServletRequest req() {
		return (HttpServletRequest) JitarRequestContext.getRequestContext().getRequest();
	}

	public String getAuthType() {
		return req().getAuthType();
	}

	public String getContextPath() {
		return req().getContextPath();
	}

	public Cookie[] getCookies() {
		return req().getCookies();
	}

	public long getDateHeader(String name) {
		return req().getDateHeader(name);
	}

	public String getHeader(String name) {
		return req().getHeader(name);
	}

	public Enumeration<String> getHeaderNames() {
		return req().getHeaderNames();
	}

	public Enumeration<String> getHeaders(String name) {
		return req().getHeaders(name);
	}

	public int getIntHeader(String name) {
		return req().getIntHeader(name);
	}

	public String getMethod() {
		return req().getMethod();
	}

	public String getPathInfo() {
		return req().getPathInfo();
	}

	public String getPathTranslated() {
		return req().getPathTranslated();
	}

	public String getQueryString() {
		return req().getQueryString();
	}

	public String getRemoteUser() {
		return req().getRemoteUser();
	}

	public String getRequestURI() {
		return req().getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return req().getRequestURL();
	}

	public String getRequestedSessionId() {
		return req().getRequestedSessionId();
	}

	public String getServletPath() {
		return req().getServletPath();
	}

	public HttpSession getSession() {
		return req().getSession();
	}

	public HttpSession getSession(boolean create) {
		return req().getSession(create);
	}

	public Principal getUserPrincipal() {
		return req().getUserPrincipal();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return req().isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL() {
		return req().isRequestedSessionIdFromURL();
	}

	@SuppressWarnings("deprecation")
	public boolean isRequestedSessionIdFromUrl() {
		return req().isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdValid() {
		return req().isRequestedSessionIdValid();
	}

	public boolean isUserInRole(String role) {
		return req().isUserInRole(role);
	}

	public Object getAttribute(String arg0) {
		return req().getAttribute(arg0);
	}

	public Enumeration<String> getAttributeNames() {
		return req().getAttributeNames();
	}

	public String getCharacterEncoding() {
		return req().getCharacterEncoding();
	}

	public int getContentLength() {
		return req().getContentLength();
	}

	public String getContentType() {
		return req().getContentType();
	}

	public ServletInputStream getInputStream() throws IOException {
		return req().getInputStream();
	}

	public String getLocalAddr() {
		return req().getLocalAddr();
	}

	public String getLocalName() {
		return req().getLocalName();
	}

	public int getLocalPort() {
		return req().getLocalPort();
	}

	public Locale getLocale() {
		return req().getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return req().getLocales();
	}

	public String getParameter(String arg0) {
		return req().getParameter(arg0);
	}

	public Map<String, String[]> getParameterMap() {
		return req().getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return req().getParameterNames();
	}

	public String[] getParameterValues(String arg0) {
		return req().getParameterValues(arg0);
	}

	public String getProtocol() {
		return req().getProtocol();
	}

	public BufferedReader getReader() throws IOException {
		return req().getReader();
	}

	@SuppressWarnings("deprecation")
	public String getRealPath(String arg0) {
		return req().getRealPath(arg0);
	}

	public String getRemoteAddr() {
		return req().getRemoteAddr();
	}

	public String getRemoteHost() {
		return req().getRemoteHost();
	}

	public int getRemotePort() {
		return req().getRemotePort();
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		return req().getRequestDispatcher(arg0);
	}

	public String getScheme() {
		return req().getScheme();
	}

	public String getServerName() {
		return req().getServerName();
	}

	public int getServerPort() {
		return req().getServerPort();
	}

	public boolean isSecure() {
		return req().isSecure();
	}

	public void removeAttribute(String arg0) {
		req().removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		req().setAttribute(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		req().setCharacterEncoding(arg0);
	}

	public ServletContext getServletContext() {
		return req().getServletContext();
	}

	public AsyncContext startAsync() {
		return req().startAsync();
	}

	public AsyncContext startAsync(ServletRequest paramServletRequest, ServletResponse paramServletResponse) {
		return req().startAsync(paramServletRequest, paramServletResponse);
	}

	public boolean isAsyncStarted() {
		return req().isAsyncStarted();
	}

	public boolean isAsyncSupported() {
		return req().isAsyncSupported();
	}

	public AsyncContext getAsyncContext() {
		return req().getAsyncContext();
	}

	public DispatcherType getDispatcherType() {
		return req().getDispatcherType();
	}

	public boolean authenticate(HttpServletResponse paramHttpServletResponse) throws IOException, ServletException {
		return req().authenticate(paramHttpServletResponse);
	}

	public void login(String paramString1, String paramString2) throws ServletException {
		req().login(paramString1, paramString2);
	}

	public void logout() throws ServletException {
		req().logout();
	}

	public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
		return req().getParts();
	}

	public Part getPart(String paramString) throws IOException, IllegalStateException, ServletException {
		return req().getPart(paramString);
	}

}
