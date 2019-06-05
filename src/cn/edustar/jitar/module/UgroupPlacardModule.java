package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PlacardService;

/**
 * 协作组公告(用户加入的那些) 模块.
 * 
 * 使用的模板：'/WEB-INF/user/default/ugroup_placard.html'
 * 
 *
 *
 */
public class UgroupPlacardModule extends AbstractModuleWithTP {
	/** 群组服务 */
	@SuppressWarnings("unused")
	private GroupService group_svc;
	
	/** 公告服务 */
	@SuppressWarnings("unused")
	private PlacardService pla_svc;
	
	/**
	 * 构造.
	 */
	public UgroupPlacardModule() {
		super("ugroup_placard", "协作组公告");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到用户.
		@SuppressWarnings("unused")
		User user = (User)request.getAttribute(ModuleRequest.USER_MODEL_KEY);
		
		// 得到用户加入的群组, TODO: 也许这个可以被缓存起来.
		// TODO: 改造为 py 版本.
		/*
		List<Group> group_list = group_svc.getMyJoinedGroupList(user.getUserId());
		
		// 得到这些群组的公告.
		List<Integer> group_ids = calcGroupIds(group_list);
		List<Placard> placard_list = pla_svc.getMultiRecentPlacard(ObjectType.OBJECT_TYPE_GROUP, group_ids, 4);
		
		// 合成模板.
		genHtmlResult(user, group_list, placard_list, response);
		*/
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private void genHtmlResult(User user, List<Group> group_list, 
			List<Placard> placard_list, ModuleResponse response) throws IOException {
		// 把 placard, group 合并在一起.
		List data = combinePlacardGroup(placard_list, group_list);
		
		// 构造 root_map, 合成模板.
		Map<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("user", user);
		root_map.put("data", data);
		root_map.put("placard_list", placard_list);
		root_map.put("group_list", group_list);
		
		String template_name = "/WEB-INF/user/default/ugroup_placard.html";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
	
	// 合成 Group, Placard 数据。返回为 List<Map<String, Object>>
	//   其中 Map 中 key='placard' 为公告对象， key='group' 为群组对象
	private List<Map<String, Object>> combinePlacardGroup(List<Placard> placard_list, List<Group> group_list) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < placard_list.size(); ++i) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("placard", placard_list.get(i));
			map.put("group", findGroup(group_list, placard_list.get(i).getObjId()));
			list.add(map);
		}
		return list;
	}
	
	private Group findGroup(List<Group> group_list, int group_id) {
		for (int i = 0; i < group_list.size(); ++i)
			if (group_list.get(i).getGroupId() == group_id)
				return group_list.get(i);
		return null;
	}
	
	// 从 group_list 中得到公告列表
	@SuppressWarnings("unused")
	private static List<Integer> calcGroupIds(List<Group> group_list) {
		List<Integer> group_ids = new ArrayList<Integer>();
		for (int i = 0; i < group_list.size(); ++i) {
			group_ids.add(group_list.get(i).getGroupId());
		}
		return group_ids;
	}
}
