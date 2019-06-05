package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagService;

/**
 * 聚合关键词模块.
 * 把跟关键词相关的群组列出来. 
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public class ConvergeKeyWordModule extends AbstractModuleWithTP {
	/** 标签服务 */
	public TagService tagService;

	/**
	 * 构造.
	 */
	public ConvergeKeyWordModule() {
		super("group_keyword", "聚合关键词");
	}
	
	/** 标签服务  */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到群组.
		Group group = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
	
		// 得到群组的所有标签.
		//String[] g_tag = group.getGroupTags();
		List<Tag> tag_list = tagService.getRefTagList(group.getGroupId(), ObjectType.OBJECT_TYPE_GROUP);
		if (tag_list.size() == 0) {
			return;
		}

		// 得到具有这些标签的群组.
		List<Group> group_ids = tagService.getGroupListByTags(getTagIds(tag_list)); 
		
		// 合成输出.
		outputHtml(group_ids, response);
	}
	
	private void outputHtml(List<Group> group_ids, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group_ids", group_ids);
		
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		String template_Name = "/WEB-INF/group/default/group_keyword.ftl";
		processTemplate(root_map, response.getOut(), template_Name);
	}
	
	/** 将List<Tag> 转换为 List<Integer>*/
	private static final List<Integer> getTagIds(List<Tag> tags) {
		List<Integer> ids = new ArrayList<Integer>();
		if (tags == null || tags.size() == 0) 
			return ids;
		for (int i = 0; i < tags.size(); ++i)
			ids.add(tags.get(i).getTagId());
		return ids;
	}
}
