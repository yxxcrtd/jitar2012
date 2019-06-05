package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.TagService;

/**
 * 相关协作组.
 * 列出使用聚合关键词的组，并找出最近的前三个组.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class RelativeGroupModule extends AbstractModuleWithTP {
	/** 模块名称 */
	public static final String MODULE_NAME = "relative_group"; 
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 标签服务 */
	private TagService tag_svc;
	
	/**
	 * 构造.
	 */
	public RelativeGroupModule() {
		super("relative_group", "相关协作组");
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		/**
		 * 找出最近的几个组  
		 * GroupQueryParam类型的参数未知 
		 * TODO: 以后还要改进这个方法
		 */
		List<Group> recent_groups  = group_svc.getGroupList(new GroupQueryParam(), null);
		/*List<GroupModel> recent_groups = null;
		for(int i = 0; i<=2; i++) {
			recent_groups.add(i, groups.get(i));
		}*/
		
		//得到最新5个标签.		List<Tag> tag_list = tag_svc.getNewTagList(3);
		
		//合成输入.
		outputHtmlResult(recent_groups, tag_list, response);
	}
	
	private void outputHtmlResult(List<Group> groups, List<Tag> tag_list, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("recent_groups", groups);
		root_map.put("tag_list", tag_list);			  
		String template_Name = "/Web-INF/group/default/relative_group.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_Name);
	}
}
