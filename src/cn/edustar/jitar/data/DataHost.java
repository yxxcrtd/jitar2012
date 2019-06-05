package cn.edustar.jitar.data;

import cn.edustar.jitar.util.ParamUtil;

/**
 * jitar.data 中各数据提供 bean 的所需容器接口定义.
 *
 *
 */
public interface DataHost {
	/**
	 * 设置指定名称的数据到环境中, 通常是放到 request 中.
	 * @param name
	 * @param value
	 */
	public void setData(String name, Object value);

	/**
	 * 得到页面参数.
	 * @param key
	 * @return
	 */
	public ParamUtil getParameters();
	
	/**
	 * 得到指定名字的环境对象, 如 'tag', 'user' 等.
	 * @param name
	 * @return
	 */
	public Object getContextObject(String name);
}
