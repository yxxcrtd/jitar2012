package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupService;

/**
 * 小组最新成员(群组)模块.
 *
 *
 */
public class GroupNewbieModule extends AbstractModuleWithTP {
	/** 群组服务 */
	private GroupService group_svc;

	public GroupNewbieModule() {
		super("group_newbie", "协作组最新成员");
	}

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	@SuppressWarnings("unchecked")
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到当前群组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);

		// 得到新加入群组的人员.
		List list = group_svc.getNewGroupMember(group_model.getGroupId(), 10);
		
		// 合成输出.
		outputHtml(group_model, list, response);
	}
	
	// 合成输出
	@SuppressWarnings("unchecked")
	private void outputHtml(Group group, List ugm_list,
			ModuleResponse response) throws IOException {
		// 合成数据
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("ugm_list", ugm_list);
		
		String template_name = "/WEB-INF/group/default/group_newbie.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
