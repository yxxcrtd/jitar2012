package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupService;

/**
 * 群组资源模块.
 *
 *
 */
public class GroupResourceModule extends AbstractModuleWithTP {
	/** 模块名字 */
	public static final String MODULE_NAME = "group_resource";

	/** 群组服务 */
	private GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupResourceModule() {
		super("group_resource", "协作组资源");
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
		// 得到当前群组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		List<ResourceModelEx> new_list = null;
		List<ResourceModelEx> hot_list = null;
		List<ResourceModelEx> best_list = null;
		// 得到群组内的文章. 
		String cateUUid=group_svc.getGroupCateUuid(group_model);
		if(cateUUid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			new_list = group_svc.getNewGroupResourceList(group_model.getGroupId(), 8,true);
			hot_list = group_svc.getHotGroupResourceList(group_model.getGroupId(), 8,true);
			best_list = group_svc.getBestGroupResourceList(group_model.getGroupId(), 8,true);
		}else{
			// 得到群组内的资源. 
			// TODO: count - CONFIG
			new_list = group_svc.getNewGroupResourceList(group_model.getGroupId(), 8);
			hot_list = group_svc.getHotGroupResourceList(group_model.getGroupId(), 8);
			best_list = group_svc.getBestGroupResourceList(group_model.getGroupId(), 8);
		}
		// 合成数据输出.
		outputHtml(group_model, new_list, hot_list, best_list, response);
	}
	
	private void outputHtml(Group group,
			List<ResourceModelEx> new_list,
			List<ResourceModelEx> hot_list,
			List<ResourceModelEx> best_list,
			ModuleResponse response) throws IOException {
		// 设置数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("new_list", new_list);
		root_map.put("hot_list", hot_list);
		root_map.put("best_list", best_list);
		root_map.put("guid", java.util.UUID.randomUUID().toString().replace("-",""));
		
		String template_name = "/WEB-INF/group/default/group_resource.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
