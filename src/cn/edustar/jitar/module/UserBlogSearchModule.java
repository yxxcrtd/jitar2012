package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

/**
 * 搜索(用户文章搜索)模块，也叫 博客搜索.
 * 
 * 使用的模板:   /WEB-INF/user/default/blog_search.html
 *  
 *
 *
 */
public class UserBlogSearchModule extends AbstractModuleWithTP {
	/** 模块名字 */
	public static final String MODULE_NAME = "blog_search";
	
	/**
	 * 构造.
	 */
	public UserBlogSearchModule() {
		super("blog_search", "文章搜索");
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 获得用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 合成 XHTML.
		genHtmlResult(user, response);
	}
	
	// 生成 XHTML 格式.
	private void genHtmlResult(User user, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		
		String template_name = "/WEB-INF/user/default/blog_search.ftl";
		
		response.setContentType(TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
