package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserQueryParam;

/**
 * 列出使用标签的博客(用户)的模块.
 *
 *
 */
public class TagBlogModule extends TagModuleBase {
	/**
	 * 构造.
	 */
	public TagBlogModule() {
		super("tag_blog", "标签工作室");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到标签.
		Tag tag = super.getTag(request);
		if (tag == null) {
			throw new RuntimeException("Tag not exist");
		}
		
		// 构造查询参数.
		UserQueryParam param = new UserQueryParam();
		
		Pager pager = new Pager();
		pager.setCurrentPage(1);
		pager.setPageSize(20);

		// 得到使用标签的用户列表.
		List<User> user_list = tag_svc.getUserListByTag(tag.getTagId(), param, pager);
		
		// 合成输出 html.
		outputHtml(tag, user_list, pager, response);
	}

	private void outputHtml(Tag tag, List<User> user_list, Pager pager,
			ModuleResponse response) throws IOException {
		// 构造 root_map, 合成模板
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("tag", tag);
		root_map.put("user_list", user_list);
		root_map.put("pager", pager);
		
		String template_name = "/WEB-INF/system/default/tag_blog.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
