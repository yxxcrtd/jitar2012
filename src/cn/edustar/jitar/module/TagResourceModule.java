package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.ResourceQueryParam;

/**
 * 列出使用标签的资源列表模块.
 *
 *
 */
public class TagResourceModule extends TagModuleBase {
	/**
	 * 构造.
	 */
	public TagResourceModule() {
		super("tag_resource", "标签资源");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 获得标签.
		Tag tag = super.getTag(request);
		if (tag == null) {
			throw new RuntimeException("Tag not exist");
		}
		
		// 构造查询参数.
		ResourceQueryParam param = new ResourceQueryParam();
		
		Pager pager = new Pager();
		pager.setCurrentPage(1);
		pager.setPageSize(20);
		
		// 得到使用此标签的资源列表.
		List<ResourceModelEx> resource_list = tag_svc.getResourceListByTag(tag.getTagId(), param, pager);
		
		// 合成输出.
		outputHtml(tag, resource_list, pager, response);
	}
	
	private void outputHtml(Tag tag, List<ResourceModelEx> resource_list, Pager pager, 
			ModuleResponse response) throws IOException {
		// 构造 root_map, 合成模板
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("tag", tag);
		root_map.put("resource_list", resource_list);
		root_map.put("pager", pager);
		
		String template_name = "/WEB-INF/system/default/tag_resource.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
