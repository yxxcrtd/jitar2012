package cn.edustar.jitar.module;

import java.io.IOException;
import java.io.Writer;

/**
 * 定义模块响应接口
 * 
 *
 */
public interface ModuleResponse {
	/**
	 * 设置输出的内容类型。如 "text/json; charset='utf-8'", "text/xml" etc.
	 * 
	 * 缺省为 "text/html; charset='utf-8'".
	 */
	public void setContentType(String contentType) throws IOException;
	
	/**
	 * 得到结果输出对象。
	 * 
	 * @return
	 * @throws IOException
	 */
	public Writer getOut() throws IOException;
	
	/**
	 * 设置页面不缓存
	 *
	 * @param header
	 * @throws IOException
	 */
	public void setHeader(String name, String value) throws IOException;
	
	/**
	 * 禁用'HTML'页面的缓存
	 *
	 * @param name
	 * @param value
	 * @throws IOException
	 */
	public void setDateHeader(String name, long value) throws IOException;
	
}
