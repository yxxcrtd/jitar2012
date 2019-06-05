package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.CategoryService;

/**
 * 组内文章(群组)模块.
 * 
 *
 *
 */
public class GroupArticleModule extends AbstractModuleWithTP {
	/** 群组服务 */
	private GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupArticleModule() {
		super("group_article", "协作组文章");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到当前群组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);

		List<GroupArticle> new_list = null;
		List<GroupArticle> hot_list = null;
		List<GroupArticle> best_list = null;

		// 得到群组内的文章. 
		String cateUUid=group_svc.getGroupCateUuid(group_model);
		if(cateUUid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			//是课题组
			new_list = group_svc.getNewGroupArticleList(group_model.getGroupId(), 8,true);
			hot_list = group_svc.getHotGroupArticleList(group_model.getGroupId(), 8,true);
			best_list = group_svc.getBestGroupArticleList(group_model.getGroupId(), 8,true);
		}else{
			new_list = group_svc.getNewGroupArticleList(group_model.getGroupId(), 8);
			hot_list = group_svc.getHotGroupArticleList(group_model.getGroupId(), 8);
			best_list = group_svc.getBestGroupArticleList(group_model.getGroupId(), 8);
		}
		// 合成数据输出.
		outputHtml(group_model, new_list, hot_list, best_list, response);
	}
	

	private void outputHtml(Group group,
			List<GroupArticle> new_list,
			List<GroupArticle> hot_list,
			List<GroupArticle> best_list,
			ModuleResponse response) throws IOException {
		// 设置数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("new_list", new_list);
		root_map.put("hot_list", hot_list);
		root_map.put("best_list", best_list);
		
		//下面这个数据是前端所用到的，用来进行唯一标识一个元素，请勿删除.
		//孟宪会	UUID.
		root_map.put("guid", java.util.UUID.randomUUID().toString().replace("-", ""));
		
		String template_name = "/WEB-INF/group/default/group_article.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
