package cn.edustar.jitar.module;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

/**
 * ModuleResponse 的一个简单实现，使用 StringWriter 作为输出缓存。
 * 
 *
 */
public class ModuleResponseImpl implements ModuleResponse {
	
	/** ServletResponse */
	private HttpServletResponse response;

	/**
	 * 构造一个 ModuleResponseImpl 的新实例。
	 * 
	 * 提供给派生类使用。
	 * 
	 */
	protected ModuleResponseImpl() {

	}

	/**
	 * 使用 ServletResponse 构造一个 ModuleResponseImpl
	 * 
	 * @param response
	 * @throws IOException
	 */
	public ModuleResponseImpl(HttpServletResponse response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.ModuleResponse#getOut()
	 */
	public Writer getOut() throws IOException {
		return response.getWriter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.ModuleResponse#setContentType(java.lang.String)
	 */
	public void setContentType(String contentType) {
		response.setContentType(contentType);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.module.ModuleResponse#setHeaderNoCache(java.lang.String)
	 */
	public void setHeader(String name, String value) throws IOException {
		response.setHeader(name, value);
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.module.ModuleResponse#setDateHeader(java.lang.String, long)
	 */
	public void setDateHeader(String name, long date) throws IOException {
		response.setDateHeader(name, date);
	}
}
