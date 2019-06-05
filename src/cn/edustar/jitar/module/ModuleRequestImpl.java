package cn.edustar.jitar.module;

import java.util.HashMap;
import java.util.Map;

/**
 * ModuleRequest 的一个简单实现
 * 
 *
 */
public class ModuleRequestImpl implements ModuleRequest {
	
	/** 请求方法 */
	private String method = "GET";
	
	/** 请求参数 */
	@SuppressWarnings("unchecked")
	private Map parameters;
	
	/** HashMap */
	@SuppressWarnings("unchecked")
	private Map attrs = new HashMap();
	
	/**
	 * 构造一个 ModuleRequestImpl 的新实例
	 */
	public ModuleRequestImpl() {
		
	}
	
	/**
	 * 构造一个 ModuleRequestImpl 的新实例
	 * 
	 * @param method
	 * @param parameters
	 */
	@SuppressWarnings("unchecked")
	public ModuleRequestImpl(String method, Map parameters) {
		this.method = method;
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.module.ModuleRequest#getMethod()
	 */
	public String getMethod() {
		return this.method;
	}
	
	/**
	 * 请求方法
	 *
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.ModuleRequest#getParameters()
	 */
	@SuppressWarnings("unchecked")
	public Map getParameters() {
		return this.parameters;
	}
	
	/**
	 * 请求参数
	 *
	 * @param parameters
	 */
	@SuppressWarnings("unchecked")
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.ModuleRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String key) {
		return this.attrs.get(key);
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.module.ModuleRequest#setAttribute(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void setAttribute(String key, Object value) {
		this.attrs.put(key, value);
	}
	
}
