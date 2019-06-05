package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagQueryParam;
import cn.edustar.jitar.service.TagService;

/**
 * 最热门 xx 个标签列表.
 *
 *
 */
public class HotTagsModule extends AbstractModuleWithTP {
	/** 标签服务 */
	private TagService tag_svc;

	/**
	 * 构造.
	 */
	public HotTagsModule() {
		super("hot_tags", "热门标签");
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		TagQueryParam param = new TagQueryParam();
		param.count = 20;
		
		List<Tag> tag_list = tag_svc.getTagList(param, null);
		
		// 合成数据输出.
		output(tag_list, response);
	}
	
	// 合成数据输出.
	private void output(List<Tag> tag_list, ModuleResponse response) throws IOException {
		// 构造 root_map, 合成模板.
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("tag_list", tag_list);
		
		String template_name = "/WEB-INF/system/default/hot_tag_list.ftl";
		
		response.setContentType(TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
