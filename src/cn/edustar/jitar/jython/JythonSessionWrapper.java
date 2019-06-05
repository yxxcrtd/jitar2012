package cn.edustar.jitar.jython;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edustar.jitar.JitarRequestContext;

/**
 * Python HttpSession 的包装.
 *
 *
 */
@SuppressWarnings({"unchecked", "deprecation"})
public class JythonSessionWrapper implements HttpSession {
	/** 得到当前 session */
	private HttpSession ses() {
		return ((HttpServletRequest)JitarRequestContext.getRequestContext().getRequest()).getSession();
	}
	
	public Object getAttribute(String name) {
		return ses().getAttribute(name);
	}

	public Enumeration getAttributeNames() {
		return ses().getAttributeNames();
	}

	public long getCreationTime() {
		return ses().getCreationTime();
	}

	public String getId() {
		return ses().getId();
	}

	public long getLastAccessedTime() {
		return ses().getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return ses().getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return ses().getServletContext();
	}

	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return ses().getSessionContext();
	}

	public Object getValue(String name) {
		return ses().getValue(name);
	}

	public String[] getValueNames() {
		return ses().getValueNames();
	}

	public void invalidate() {
		ses().invalidate();
	}

	public boolean isNew() {
		return ses().isNew();
	}

	public void putValue(String name, Object value) {
		ses().putValue(name, value);
	}

	public void removeAttribute(String name) {
		ses().removeAttribute(name);
	}

	public void removeValue(String name) {
		ses().removeValue(name);
	}

	public void setAttribute(String name, Object value) {
		ses().setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		ses().setMaxInactiveInterval(interval);
	}
}
