package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.pojos.User;

public class UserRssFeedModule  extends AbstractModuleWithTP {
	
	public UserRssFeedModule() 
	{
		super("rssfeed", "订阅本站");
	}
	
	public void handleRequest(ModuleRequest request, ModuleResponse response)throws IOException {
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 合成模板.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		
		// 使用位于 WEB-INF/user/default 下面的 user_photo.ftl 模板.
		String template_name = "/WEB-INF/user/default/user_rssfeed.ftl";
		
		// 提取最新的照片
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
		}

}
