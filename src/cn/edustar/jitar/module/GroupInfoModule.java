package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.Group;

/**
 * 显示群组信息的模块.
 * 
 *
 * @deprecated 已经被 js/jitar/module/group_info.py 取代.
 * 
 */
@Deprecated
public class GroupInfoModule extends AbstractModuleWithTP {
	/**
	 * 构造.
	 */
	public GroupInfoModule() {
		super("group_info", "协作组信息");
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到群组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		
		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();

		root_map.put("group", group_model);
		
		String template_name = "/WEB-INF/group/default/group_info.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
