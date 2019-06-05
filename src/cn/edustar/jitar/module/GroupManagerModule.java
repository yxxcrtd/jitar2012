package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.UserService;

/**
 * 协作组组长(群组) 模块.
 *
 *
 */
public class GroupManagerModule extends AbstractModuleWithTP {
	/** 群组服务 */
	private GroupService group_svc;
	private CategoryService cate_svc;
	/** 用户服务 */
	private UserService user_svc;
	/**
	 * 构造.
	 */
	public GroupManagerModule() {
		super("group_manager", "协作组组长");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}	

	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到群组.
		Group group_model = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		
		boolean isKtGroup=false;
		if(group_model.getCategoryId()!=null){
			Category cate= cate_svc.getCategory(group_model.getCategoryId());
			if(cate!=null){
				if(cate.getObjectUuid().equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
					//是课题组
					isKtGroup=true;
				}
			}
		}
		if(isKtGroup==false){
			// 得到群组的管理者.			//User manager = group_svc.getGroupManager(group_model.getGroupId());
			List<User> manager = group_svc.getGroupManagers(group_model.getGroupId());
			
			// 合成和输出数据.			//outputHtml(group_model, manager, response);
			outputHtml2(group_model, manager, response);
		}
		else{
			List<GroupKTUser> GetGroupKTUsers=group_svc.GetGroupKTUsers(group_model.getGroupId());
			List<User> us=new ArrayList<User>();
			for(GroupKTUser tu : GetGroupKTUsers){
				User u=user_svc.getUserById(tu.getTeacherId());
				us.add(u);
			}			
			
			outputHtml3(group_model, GetGroupKTUsers,us, response);
		}
	}
	
	// 合成和输出数据.	private void outputHtml(Group group, User manager, 
				ModuleResponse response) throws IOException {
		// 合成数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("manager", manager);
		
		String template_name = "/WEB-INF/group/default/group_manager.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
	
	// 合成和输出数据.
	private void outputHtml2(Group group, List<User> manager, 
				ModuleResponse response) throws IOException {
		// 合成数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("manager", manager);
		
		String template_name = "/WEB-INF/group/default/group_manager.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}

	// 合成和输出数据.
	private void outputHtml3(Group group, List<GroupKTUser> manager, List<User> userlist,
				ModuleResponse response) throws IOException {
		// 合成数据.
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("group", group);
		root_map.put("manager", manager);
		root_map.put("users", userlist);
		String template_name = "/WEB-INF/group/default/group_ktusers.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
	
}
