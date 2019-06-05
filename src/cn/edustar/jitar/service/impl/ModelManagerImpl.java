package cn.edustar.jitar.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.model.TemplateModelObject;
import cn.edustar.jitar.service.ModelManager;

/**
 * 模型管理器的实现.
 * 
 *
 */
public class ModelManagerImpl implements ModelManager, ServletContextAware {
	
	/** ServletContext */
	private ServletContext servlet_ctxt;

	/** 被共享的模型对象集合，在 Spring 中注入 */
	@SuppressWarnings("unchecked")
	private List shared_model = null;

	/** 被共享的模型对象集合，在 Spring 中注入 */
	@SuppressWarnings("unchecked")
	public void setSharedModels(List value) {
		this.shared_model = value;
	}

	@SuppressWarnings("unchecked")
	public List getSharedModels() {
		return this.shared_model;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/** 初始化方法，在 Spring 配置被用作 init-method */
	public void init() {
		if (shared_model != null) {
			putServletContextSharedModel();
		}
	}

	/** 关闭方法，在 Spring 中被配置为 destroy-method */
	public void destroy() {
		this.shared_model = null;
	}

	/** 将支持 Webapp 级别的全局共享变量放到 servlet_ctxt 里面, 从而任何 struts freemarker 模板均可访问该对象 */
	private void putServletContextSharedModel() {
		for (int i = 0; i < this.shared_model.size(); ++i) {
			Object o = this.shared_model.get(i);
			if (o instanceof TemplateModelObject) {
				TemplateModelObject model = (TemplateModelObject) o;
				this.servlet_ctxt.setAttribute(model.getVariableName(), model);
			}
		}
	}
	
}
