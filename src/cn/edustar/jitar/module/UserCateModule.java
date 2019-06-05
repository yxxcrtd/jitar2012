package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 博文分类(用户) 模块，也叫文章分类，专题分类等.
 * 
 * 使用的模板:  /WEB-INF/user/default/user_staples.html
 * 
 *
 *
 */
public class UserCateModule extends AbstractModuleWithTP {
	/** 分类服务 */
	private CategoryService cat_svc;
	
	/**
	 * 构造.
	 */
	public UserCateModule() {
		super("user_cate", "文章分类");
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
		
		// 得到该用户的文章分类.
		String itemType = CommonUtil.toUserArticleCategoryItemType(user.getUserId());
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
		
		String template_name = "/WEB-INF/user/default/user_cate.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
