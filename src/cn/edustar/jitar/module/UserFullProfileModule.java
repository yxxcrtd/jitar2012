package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

/**
 * 用户完整档案信息模块, 用于在 $user/profile 页面显示该用户完整的档案信息.
 *
 *
 */
public class UserFullProfileModule extends AbstractModuleWithTP {
	/**
	 * 构造.
	 */
	public UserFullProfileModule() {
		super("full_profile", "用户档案");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户数据.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 模板合成.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		
		String template_name = "/WEB-INF/user/default/full_profile_module.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
