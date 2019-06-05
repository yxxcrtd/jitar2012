package cn.edustar.jitar.module;

import java.util.Map;

/**
 * 定义向模块发出请求时使用的请求接口
 * 
 *
 */
public interface ModuleRequest {
	
	/** 放在 Attribute 里面的用户对象 的键 */
	public static final String USER_MODEL_KEY = "jitar.user.model";
	
	/** 放在 Attribute 里面的群组对象 的键 */
	public static final String GROUP_MODEL_KEY = "jitar.group.model";
	
	/** 放在 Attribute 里面的标签对象 的键 */
	public static final String TAG_MODEL_KEY = "jitar.tag.model";
	
	/** 放在 Attribute 里面的学科对象 的键 */
	public static final String SUBJECT_MODEL_KEY = "jitar.subject.pojos";
	
	/**
	 * 得到请求的方法，例如 'AJAX', 'GET' 等
	 * 
	 * @return
	 */
	public String getMethod();
	
	/**
	 * 得到请求参数，一般直接对应到 ServletRequest.getParameters()
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getParameters();

	/**
	 * 得到指定键的属性值
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key);
	
	/**
	 * 设置指定键的属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value);
	
}
