package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 协作组分类模块.
 * 
 *
 *
 */
public class GroupCateModule extends AbstractModuleWithTP {
	/** 分类服务 */
	private CategoryService cat_svc;

	/**
	 * 构造.
	 */
	public GroupCateModule() {
		super("group_cate", "协作组资源分类");
	}
	
	/** 分类服务 */
	public void setCategoryService(CategoryService cat_svc) {
		this.cat_svc = cat_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到协作组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);

		// 得到该协作组的资源分类.
		String itemType = CommonUtil.toGroupResourceCategoryItemType(group_model.getGroupId());		CategoryTreeModel category_tree = cat_svc.getCategoryTree(itemType);
		
		// 合成数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group_model);
		root_map.put("category_tree", category_tree);
		
		String template_name = "/WEB-INF/group/default/group_cate.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
