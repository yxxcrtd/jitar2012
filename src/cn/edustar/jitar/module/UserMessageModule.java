package cn.edustar.jitar.module;

import java.io.IOException;
import cn.edustar.jitar.service.MessageService;

/**
 * 短消息模块的实现。使用的模板: /WEB-INF/user/default/user_messages.html
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 27, 2008 10:51:25 PM
 */
public class UserMessageModule extends AbstractModuleWithTP {
	/** 此模块的名字。 */
	public static final String MODULE_NAME = "messages";

	/** 短消息服务 */
	@SuppressWarnings("unused")
	private MessageService msg_svc;

	/**
	 * 构造.
	 */
	public UserMessageModule() {
		super("messages", "我的短消息");
	}
	
	/** 短消息服务 */
	public void setMessageService(MessageService messageService) {
		this.msg_svc = messageService;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest,
	 *      cn.edustar.jitar.module.ModuleResponse)
	 */
	@SuppressWarnings("static-access")
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 当前先不支持.
		/*
		UserModel user = (UserModel) request.getAttribute(request.USER_MODEL_KEY);
		
		Object message_list = msg_svc.getMessageList(user.getUserId());
		
		// 合成模板
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("message_list", message_list);

		// 使用位于 WEB-INF 下面的用户缺省首页模板
		String template_name = "/WEB-INF/user/default/user_messages.ftl";
		response.setContentType("text/html; charset=UTF-8");
		processTemplate(root_map, response.getOut(), template_name);
		*/
	}
}
