package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 用户资源分类显示模块.
 *
 *
 */
public class UserResCateModule extends AbstractModuleWithTP {
	/** 分类服务 */
	private CategoryService cat_svc;
	
	/**
	 * 构造.
	 */
	public UserResCateModule() {
		super("user_rcate", "资源分类");
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
		// 得到用户.
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		FileCache fc = new FileCache();
		String content = fc.getUserFileCacheContent(user.getLoginName(), "user_res_cate.html",14400);
        if (!content.equals(""))
        {
        	response.setContentType(Module.TEXT_HTML_UTF_8);
            response.getOut().write(content);
            fc = null;
            return;
        }
        fc = null;
		// 得到该用户的资源分类.
		String itemType = CommonUtil.toUserResourceCategoryItemType(user.getUserId());
		CategoryTreeModel usercate_tree = cat_svc.getCategoryTree(itemType);
		
		// 产生 XHTML 结果.
		outputHtmlResult(user, usercate_tree, response);
	}
	
	// 产生 XHTML 输出.
	private void outputHtmlResult(User user, CategoryTreeModel usercate_tree, 
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("usercate_tree", usercate_tree);
		String template_name = "/WEB-INF/user/default/user_res_cate.ftl";
		String content = this.getTemplateProcessor().processTemplate(root_map, template_name,"utf-8");
		FileCache fc = new FileCache();
		fc.writeUserFileCacheContent(user.getLoginName(), "user_res_cate.html",content);
        fc = null;
        response.setContentType(Module.TEXT_HTML_UTF_8);
		response.getOut().write(content);
		//processTemplate(root_map, response.getOut(), template_name);
	}
}
