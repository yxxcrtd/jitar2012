package cn.edustar.jitar.data;

import javax.servlet.ServletRequest;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.util.ParamUtil;

/**
 * DataBean 容器缺省实现.
 *
 *
 */
public class DataHostImpl implements DataHost {
	private ServletRequest request;
	private ParamUtil param;
	
	/**
	 * 缺省构造, 将从 RequestContext 中获取 request.
	 */
	public DataHostImpl() {
		this(JitarRequestContext.getRequestContext().getRequest());
	}
	
	/**
	 * 使用指定 request 构造.
	 * @param request
	 */
	public DataHostImpl(ServletRequest request) {
		this.request = request;
		this.param = new ParamUtil(request.getParameterMap());
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataHost#getContextObject(java.lang.String)
	 */
	public Object getContextObject(String name) {
		return request.getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataHost#getParameters()
	 */
	public ParamUtil getParameters() {
		return param;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataHost#setData(java.lang.String, java.lang.Object)
	 */
	public void setData(String name, Object value) {
		request.setAttribute(name, value);
	}
}
