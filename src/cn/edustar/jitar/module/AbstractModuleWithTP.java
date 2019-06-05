package cn.edustar.jitar.module;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.service.TemplateProcessor;

/**
 * AbstractModule 扩展了含有 TemplateProcessor 属性的基类
 * 
 *
 */
public abstract class AbstractModuleWithTP extends AbstractModule {
	
	/** 模板合成器 */
	private TemplateProcessor t_proc;

	/**
	 * 构造
	 * 
	 * @param moduleName
	 * @param moduleTitle
	 */
	protected AbstractModuleWithTP(String moduleName, String moduleTitle) {
		super(moduleName, moduleTitle);
	}
	
	/** 模板合成器 */
	public TemplateProcessor getTemplateProcessor() {
		return this.t_proc;
	}
	
	/** 模板合成器 */
	public void setTemplateProcessor(TemplateProcessor t_proc) {
		this.t_proc = t_proc;
	}
	
	/**
	 * 使用指定的 root_map 执行模板合成请求，将使用 ServletHashModel 提供更多数据
	 * 
	 * @param root_map
	 * @param writer
	 * @param template_name
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void processTemplate(Map root_map, Writer writer, String template_name) throws IOException {
		HttpServletRequest request = (HttpServletRequest)JitarRequestContext.getRequestContext().getRequest();
		Object root_hash = t_proc.createRootMap(request, root_map);
		t_proc.processTemplate(root_hash, writer, template_name, null);
	}

	/**
	 * 合成指定模板，并直接返回结果字符串，实质上等于写入到内存字符串中
	 * 
	 * @param root_map
	 * @param template_name
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String processTemplate2(Map root_map, String template_name) throws IOException {
		HttpServletRequest request = (HttpServletRequest)JitarRequestContext.getRequestContext().getRequest();
		Object root_hash = t_proc.createRootMap(request, root_map);
		return t_proc.processTemplate(root_hash, template_name, null);
	}
	
	/**
	 * 设置没有输出缓存
	 * 
	 * @param response
	 */
	protected void noResponseCache(ModuleResponse response) throws IOException {
		response.setHeader(NAME_CACHE_CONTROL, VALUE_NO_CACHE);
		response.setHeader(NAME_PRAGMA, VALUE_NO_CACHE);
		response.setDateHeader(NAME_EXPIRES, VALUE_EXPIRES);
		// response.setHeader(NAME_CACHE_CONTROL, VALUE_NO_STORE);		
	}
	
}
