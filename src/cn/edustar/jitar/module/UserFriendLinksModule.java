package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 好友模块的实现。使用的模板: '/WEB-INF/user/default/user_friendlinks.ftl'.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 27, 2008 10:58:23 PM
 */
public class UserFriendLinksModule extends AbstractModuleWithTP {
	/** 好友服务 */
	private FriendService friend_svc;

	/** 缺省显示的好友数 */
	public static final int DEFAULT_COUNT = 6; 
	
	/**
	 * 构造.
	 */
	public UserFriendLinksModule() {
		super("friendlinks", "好友列表");
	}
	
	/** 好友服务 */
	public void setFriendService(FriendService friendService) {
		this.friend_svc = friendService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest,
	 *      cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		User user = (User) request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		Object friend_list = friend_svc.getFriendList(user.getUserId());

		// 得到参数.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		int count = param_util.getIntParam("count");
		if (count <= 0) count = DEFAULT_COUNT;
		
		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("friend_list", friend_list);
		root_map.put("count", count);

		// 使用位于 WEB-INF 下面的模板.
		response.setContentType("text/html; charset=UTF-8");
		String template_name = "/WEB-INF/user/default/user_friendlinks.ftl";
		processTemplate(root_map, response.getOut(), template_name);
	}
}
