package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.Group;

/**
 * 群组统计信息的模块
 * 
 *
 */
public class GroupStatModule extends AbstractModuleWithTP {

	/**
	 * 构造
	 */
	public GroupStatModule() {
		super("group_stat", "协作组统计");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 得到群组
		Group group_model = (Group) request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);

		// 计算统计信息，包括:
		// 组内成员数 = group_model.getUserCount();
		// 组内文章数 = ??? (当前没有统计字段)
		// 组内网课 -- 版本2 实现
		// 组内资源 = ??? (当前没有统计字段)
		// 组内活动数 = group_model.getActionCount()

		// 合成模板
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group_model);

		String template_name = "/WEB-INF/group/default/group_stat.ftl";

		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}

}
