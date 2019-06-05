package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FriendService;

/**
 * 好友新鲜事模块
 * 
 *
 */
public class FriendThingsModule extends AbstractModuleWithTP {

	/** 好友服务 */
	private FriendService friend_svc;

	/**
	 * 构造
	 */
	public FriendThingsModule() {
		super("friend_things", "好友新鲜事");
	}
	
	/** 好友服务 */
	public void setFriendService(FriendService friend_svc) {
		this.friend_svc = friend_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 得到用户数据
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);		
		// 得到好友近期发生的事件		List<Object[]> list = friend_svc.getRecentFriendThings(user.getUserId(), 6);		
		// 输出
		outputHtml(list, response);
	}

	/**
	 * 合成并输出 HTML 格式数据
	 *
	 * @param list
	 * @param response
	 * @throws IOException
	 */
	private void outputHtml(List<Object[]> list, ModuleResponse response) throws IOException {
		response.setContentType(TEXT_HTML_UTF_8);		
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("data_list", list);		
		String template_name = "/WEB-INF/user/default/friend_things.ftl";
		processTemplate(root_map, response.getOut(), template_name);
	}
	
}
