package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

/**
 * 用户日历模块. * 
 * 使用的模板:   /WEB-INF/user/default/user_calendar.html
 * 
 *
 *
 */
public class UserCalendarModule extends AbstractModuleWithTP {
	/** 模块名字 */
	public static final String MODULE_NAME = "user_calendar";

	/**
	 * 构造.
	 */
	public UserCalendarModule() {
		super("user_calendar", "用户日历");
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 获得用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// TODO: 获得用户的日历, 我们考虑实现一种日历服务，将所有和时间有关的数据... 
		//   都可以记录在里面.
		
		// 合成 XHTML.
		genHtmlResult(user, response);
	}
	
	// 生成 XHTML 格式.
	private void genHtmlResult(User user, ModuleResponse response)
			throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		
		String template_name = "/WEB-INF/user/default/user_calendar.html";
		
		response.setContentType(TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
