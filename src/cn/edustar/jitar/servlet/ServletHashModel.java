package cn.edustar.jitar.servlet;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 支持从 map -> request -> session -> application 获取数据的 freemarker hash 模型实现.
 *
 *
 */
public class ServletHashModel implements TemplateHashModel {
	private final ObjectWrapper obj_wrapper;
	private final HttpServletRequest request;
	private final HttpSession session;
	private final ServletContext application;
	@SuppressWarnings("unchecked")
	private final Map map;
	
	/**
	 * 构造.
	 * @param obj_wrapper
	 * @param request
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	public ServletHashModel(ObjectWrapper obj_wrapper, HttpServletRequest request, Map map) {
		this.obj_wrapper = obj_wrapper;
		this.request = request;
		if (request != null) {
			this.session = request.getSession();
			this.application = this.session.getServletContext();
		} else {
			this.session = null;
			this.application = null;
		}
		this.map = map;
	}
	
	/**
	 * 包装指定对象为 freemarker 所需对象模型.
	 * @param object
	 * @return
	 * @throws TemplateModelException
	 */
	protected TemplateModel wrap(Object object) throws TemplateModelException {
		return obj_wrapper.wrap(object);
	}

	public TemplateModel get(String key) throws TemplateModelException {
		// 先从 map 中找.
		if (map != null) {
			if (map.containsKey(key))
				return wrap(map.get(key));
		}
		
		Object obj;
		// from request.
		if (request != null) {
			obj = request.getAttribute(key);
			if (obj != null) return wrap(obj);
		}
		
		// from session.
		if (session != null) {
			obj = session.getAttribute(key);
			if (obj != null) return wrap(obj);
		}
		
		// from application.
		if (application != null) {
			obj = application.getAttribute(key);
			if (obj != null) return wrap(obj);
		}
		
		// not found.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateHashModel#isEmpty()
	 */
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}
}
