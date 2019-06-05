package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.OnlineManager;

/**
 * 用户所在的协作组在线成员模块
 * 
 *
 * @remark 计算在线成员可能消耗很多时间
 */
public class UserGroupMemberOnlineModule extends AbstractModuleWithTP {

	/** 群组服务 */
	private GroupService group_svc;
	
	/** 在线服务 */
	private OnlineManager online_mgr;

	/**
	 * 构造
	 */
	public UserGroupMemberOnlineModule() {
		super("ugm_online", "协作组在线成员");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 在线服务 */
	public void setOnlineManager(OnlineManager online_mgr) {
		this.online_mgr = online_mgr;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 得到当前用户
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 得到该用户加入的所有协作组的 所有成员
		List<User> user_list = group_svc.getUserJoinedGroupMemberList(user.getUserId(), null);
		
		// 得到这些协作组成员用户, 如果在线, 则添加到 all_dt 里面
		List<User> online_ugm = new ArrayList<User>();
		for (User u : user_list) {
			// 自己不算做在线里面			//if (u.getUserId() != user.getUserId() && online_mgr.isOnline(u.getLoginName()))
			//	online_ugm.add(u);
		}	
		// 产生输出
		outputHtml(online_ugm, response);
	}
	
	/**
	 * 产生 HTML 格式输出
	 * 
	 * @param online_ugm
	 * @param response
	 * @throws IOException
	 */
	private void outputHtml(List<User> online_ugm, ModuleResponse response) throws IOException {
		response.setContentType(Module.TEXT_HTML_UTF_8);
		
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("online_list", online_ugm);
		
		String template_name = "/WEB-INF/user/default/ugm_online.ftl";		
		processTemplate(root_map, response.getOut(), template_name);
	}
	
}
