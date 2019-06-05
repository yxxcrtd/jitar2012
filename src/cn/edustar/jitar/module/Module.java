package cn.edustar.jitar.module;

import java.io.IOException;

/**
 * 定义服务器端使用的模块接口
 * 
 *
 */
public interface Module {
	
	/** 系统内部使用的标准编码方式 */
	public static final String UTF_8 = "UTF-8";

	/** 标准输出格式：'text/html; charset=UTF-8' */
	public static final String TEXT_HTML_UTF_8 = "text/html; charset=UTF-8";

	/** 标准输出格式：'text/xhtml; charset=UTF-8' */
	public static final String TEXT_XHTML_UTF_8 = "text/xhtml; charset=UTF-8";

	/** 标准输出格式：'text/xml; charset=UTF-8' */
	public static final String TEXT_XML_UTF_8 = "text/xml; charset=UTF-8";

	/** 标准输出格式：'text/json; charset=UTF-8' */
	public static final String TEXT_JSON_UTF_8 = "text/json; charset=UTF-8";

	/** 页面缓存的name：Cache-Control (HTTP 1.1) */
	public static final String NAME_CACHE_CONTROL = "Cache-Control";
	
	/** 页面缓存的name：Pragma (HTTP 1.0) */
	public static final String NAME_PRAGMA = "Pragma";
	
	/** 页面缓存的name：Expires (prevents caching at the proxy server) */
	public static final String NAME_EXPIRES = "Expires";
	
	/** 页面缓存的value：no-cache */
	public static final String VALUE_NO_CACHE = "no-cache";

	/** 页面缓存的value：no-store (HTTP 1.1) */
	public static final String VALUE_NO_STORE = "no-store";

	/** 页面缓存的value：0 */
	public static final long VALUE_EXPIRES = 0;
	
	/**
	 * 获得该模块是否启用标志
	 * 
	 * @return
	 */
	public boolean getEnabled();
	
	/**
	 * 得到此模块的唯一名字
	 * 
	 * @return
	 */
	public String getModuleName();

	/**
	 * 得到此模块的中文名，可选实现
	 * 
	 * @return
	 */
	public String getModuleTitle();
	
	/**
	 * 向模块发出指定的请求，并期待得到响应
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 在向 response 中输出结果时候产生错误
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException;
	
}			
