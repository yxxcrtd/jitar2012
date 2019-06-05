package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;

/**
 * 用户资源列表(主页上).
 *
 *
 */
public class UserResourcesModule extends AbstractModuleWithTP {
	/** 资源服务 */
	private ResourceService res_svc;
	
	/**
	 * 构造.
	 */
	public UserResourcesModule() {
		super("user_resources", "工作室资源");
	}
	
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户数据.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 得到用户该分类下的资源.
		ResourceQueryParam param = new ResourceQueryParam();
		param.userId = user.getUserId();
		param.userCateId = null;
		List<ResourceModelEx> list = res_svc.getResourceList(param, null);
		
		// 合成并输出结果.
		outputHtml(user, list, response);
	}

	/** 合并输出 */
	@SuppressWarnings("unchecked")
	private void outputHtml(User user, List<ResourceModelEx> resource_list, 
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("resource_list", resource_list);
		
		String template_name = "/WEB-INF/user/default/user_resources.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
