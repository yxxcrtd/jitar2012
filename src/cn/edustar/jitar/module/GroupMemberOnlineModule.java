package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.data.DataRow;
import cn.edustar.data.DataTable;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.OnlineManager;

/**
 * 群组在线成员模块.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 * 
 */
public class GroupMemberOnlineModule extends AbstractModuleWithTP {
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 在线服务 */
	private OnlineManager online_mgr;
	
	/**
	 * 构造.
	 */
	public GroupMemberOnlineModule() {
		super("gm_online", "协作组在线成员");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc){
		this.group_svc = group_svc;
	}
	
	/** 在线服务 */
	public void setOnlineManager(OnlineManager online_mgr){
		this.online_mgr = online_mgr;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到当前组.
		Group group = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		
		// 得到当前组的所有成员.
		GroupMemberQueryParam param = new GroupMemberQueryParam();
		param.count = -1;
		param.groupId = group.getGroupId();
		DataTable group_list = group_svc.getGroupMemberList(param, null);
		
		// 遍历每个成员, 如果在线则加入到 datatable 中.
		DataTable datatable = new DataTable(group_list.getSchema());
		datatable.getSchema().add("onlineTime");
		
		for(DataRow g : group_list) {
			String loginName = (String)g.get("loginName");
			if (online_mgr.isOnline(loginName)) {
				// TODO: onlineTime
				datatable.add(g);
			}
		}

		// 合成输出.
		outputHtmlResult(group, datatable, response);
	}
	
	private void outputHtmlResult(Group group, DataTable datatable, 
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("online_gmlist", datatable);
		
		String template_Name = "/WEB-INF/group/default/gm_online.ftl";

		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_Name);
	}
}
