package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;

/**
 * 群组留言模块.
 *
 *
 */
public class GroupLeavewordModule extends AbstractModuleWithTP {
	/** 留言服务.*/
	private LeavewordService lw_svc;

	/**
	 * 构造.
	 */
	public GroupLeavewordModule() {
		super("group_leaveword", "协作组留言");
	}
	
	/** 留言服务*/
	public void setLeavewordService(LeavewordService leavewordService) {
		this.lw_svc = leavewordService;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		Group group = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		
		LeavewordQueryParam param = new LeavewordQueryParam();
		param.count = 8;
		param.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId();
		param.objId = group.getGroupId();
		List<LeaveWord> group_leaveword = lw_svc.getLeaveWordList(param, null);
		
		outputHtml(group, group_leaveword, response);
	}
	
	/** 合成 html 格式的输出 */
	public void outputHtml(Group group, List<LeaveWord> group_leaveword, 
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object> ();
		root_map.put("group", group);
		root_map.put("group_leaveword", group_leaveword);
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		
		String template_Name = "/WEB-INF/group/default/group_leaveword.ftl";
		processTemplate(root_map, response.getOut(), template_Name);
	}
}
