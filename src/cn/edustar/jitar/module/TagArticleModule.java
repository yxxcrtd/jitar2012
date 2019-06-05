package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Tag;

/**
 * 列出使用某个标签的文章列表的模块.
 * 
 *
 */
public class TagArticleModule extends TagModuleBase {
	/**
	 * 构造.
	 */
	public TagArticleModule() {
		super("tag_article", "标签文章");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到标签.
		Tag tag = getTag(request);
		if (tag == null) {
			throw new RuntimeException("Tag not exist");
		}
		
		// 得到分页参数.
		Pager pager = new Pager();
		pager.setCurrentPage(1);
		pager.setPageSize(20);		// TODO: config
		
		// 得到文章列表.
		Object article_list = tag_svc.getArticleListByTag(tag.getTagId(), 0, pager);
		
		// 合成数据，产生输出.		outputHtml(tag, article_list, pager, response);
	}

	private void outputHtml(Tag tag, Object article_list, Pager pager, 
			ModuleResponse response) throws IOException {
		// 构造 root_map, 合成模板
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("tag", tag);
		root_map.put("article_list", article_list);
		root_map.put("pager", pager);
		
		String template_name = "/WEB-INF/system/default/tag_article.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
