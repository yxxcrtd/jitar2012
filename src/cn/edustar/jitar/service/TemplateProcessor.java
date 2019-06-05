package cn.edustar.jitar.service;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板执行器组件. 负责使用指定的数据和模板产生模板输出结果. *
 *
 */
public interface TemplateProcessor {
	/**
	 * 使用指定的数据和模板产生输出，其中模板相对于 WEB 应用路径，如 '/WEB-INF/ftl/xxx.ftl' 
	 *   表示网站根目录下 '/WEB-INF/ftl/xxx.ftl' 文件.	 * @param root_map - 根数据模型.	 * @param template_name - 模板名字，位于 WEB 应用路径下.	 * @return
	 */
	public String processTemplate(Object root_map, String template_name, String encoding);
	
	/**
	 * 使用指定的数据和模板产生输出，其中模板相对于 WEB 应用路径，如 '/WEB-INF/ftl/xxx.ftl'. 
	 *   表示网站根目录下 '/WEB-INF/ftl/xxx.ftl' 文件.	 * @param root_map - 根数据模型.	 * @param writer - 结果输出到这里.	 * @param template_name - 模板名字，位于 WEB 应用路径下.	 * @param encoding - 模板文件编码，缺省为 UTF-8.
	 * @return
	 */
	public void processTemplate(Object root_map, Writer writer, String template_name, String encoding)
			throws IOException;

	public String processStringTemplate(Object root_map, String template_text);
	
	/**
	 * 为指定的 request, map 产生一个 Template 使用的 root_map 对象.
	 * @param request
	 * @param map
	 * @return
	 */	
	@SuppressWarnings("rawtypes") 
	public Object createRootMap(HttpServletRequest request,Map map);
}
