package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 我的协作组(用户)模块.
 *
 */
public class JoinedGroupsModule extends AbstractModuleWithTP {
	/** 群组服务. */
	@SuppressWarnings("unused")
	private GroupService group_svc;

	/** 缺省显示的协作组数量 */
	public static final int DEFAULT_COUNT = 6;
	
	/**
	 * 构造.
	 */
	public JoinedGroupsModule() {
		super("joined_groups", "我的协作组");
	}
	
	/** 群组服务. */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		@SuppressWarnings("unused")
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 得到参数.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		int count = param_util.getIntParam("count");
		if (count <= 0) count = DEFAULT_COUNT;
		
		// 得到我加入的协作组列表.
		// TODO: List<Group> group_list = group_svc.getMyJoinedGroupList(user.getUserId());
		// TODO: 改造为 py 版本.
		// if (group_list.size() > count)
		//	group_list = group_list.subList(0, count);
		
		// 合成数据.
		// genHtmlResult(user, group_list, response);
	} 
	
	// 合成产生 HTML 输出.
	@SuppressWarnings("unused")
	private void genHtmlResult(User user, List<Group> group_list, ModuleResponse response)
			throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("group_list", group_list);
		
		String template_name = "/WEB-INF/user/default/joined_groups.ftl";
		
		processTemplate(root_map, response.getOut(), template_name);
	}
}
