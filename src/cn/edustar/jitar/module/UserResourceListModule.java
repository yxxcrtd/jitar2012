package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 用户资源列表模块, 用于显示某个分类或全部资源列表.
 *
 *
 */
public class UserResourceListModule extends AbstractModuleWithTP {
	/** 资源服务. */
	private ResourceService res_svc;
	
	/** 分类服务 */
	@SuppressWarnings("unused")
	private CategoryService cat_svc;
	
	/**
	 * 构造.
	 */
	public UserResourceListModule() {
		super("user_reslist", "资源列表");
	}
	
	/** 资源服务. */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}
	
	/** 用户分类服务 */
	public void setCategoryService(CategoryService cat_svc) {
		this.cat_svc = cat_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户数据.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);

		// 得到当前访问的分类.
		ParamUtil param_util = new ParamUtil(request.getParameters());
		Integer cid = param_util.getIntParamZeroAsNull("cid");
		
		// 分页参数.
		Pager pager = new Pager();
		pager.setCurrentPage(param_util.safeGetIntParam("page", 1));
		pager.setPageSize(40);
		
		// 得到用户该分类下的完全共享的资源.
		ResourceQueryParam param = new ResourceQueryParam();
		param.userCateId = cid;
		param.userId = user.getUserId();
		param.shareMode = Resource.SHARE_MODE_FULL;
		
		List<ResourceModelEx> list = res_svc.getResourceList(param, pager);
		
		// 合成并输出结果.
		outputHtml(user, list, pager, response);
	}

	/** 合并输出 */
	@SuppressWarnings("unchecked")
	private void outputHtml(User user, List<ResourceModelEx> resource_list, 
			Pager pager, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("resource_list", resource_list);
		root_map.put("pager", pager);
		
		String template_name = "/WEB-INF/user/default/user_reslist.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
