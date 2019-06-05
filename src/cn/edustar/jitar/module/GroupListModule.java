package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupService;

/**
 * 系统模块, 显示群组列表.
 *
 *
 */
public class GroupListModule extends AbstractModuleWithTP {
	/** 群组服务 */
	private GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupListModule() {
		super("group_list", "最新协作组");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		GroupQueryParam param = new GroupQueryParam();
		param.count = 6;
		
		// 查询群组列表.
		List<Group> group_list = group_svc.getGroupList(param, null);
		
		// 合成输出数据.
		outputHtml(group_list, response);
	}
	
	private void outputHtml(List<Group> group_list, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group_list", group_list);
		
		String template_name = "/WEB-INF/system/default/group_list.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
