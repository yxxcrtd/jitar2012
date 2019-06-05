package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

/**
 * 实现用户个人信息的模块实现. 
 * 
 * 使用的模板: '/WEB-INF/user/default/profile.ftl'
 * 
 *
 */
public class UserProfileModule extends AbstractModuleWithTP {
	/**
	 * 构造.
	 */
	public UserProfileModule() {
		super("profile", "个人档案");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest,
	 *      cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);

		// 使用位于 WEB-INF/user/default 下面的 profile.ftl 模板.
		String template_name = "/WEB-INF/user/default/user_profile.ftl";

		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
