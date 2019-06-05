package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;

import cn.edustar.data.DataTable;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.BbsService;

/**
 * 最近的主题列表(组内论坛)
 * 
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public class BbsGroupTopicModule extends AbstractModuleWithTP {
	
	/** 论坛服务 */
	private BbsService bbs_svc;
	
	/**
	 * 构造
	 */
	public BbsGroupTopicModule() {
		super("recent_topiclist", "组内论坛");
	}
	
	/** 论坛服务 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		
		// 得到群组
		Group group = (Group)request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
		
		//String board_name = CommonUtil.toGroupBoardName(group.getGroupId());
		int groupId = group.getGroupId();
		
		// 得到最近的主题列表
		DataTable dt = bbs_svc.getRecentTopicDataTable(groupId, 3);
		
		// 合成输出
		outputHtmlResult(dt, group , response);
	}
	
	/**
	 * 输出到模板
	 *
	 * @param dt
	 * @param group
	 * @param response
	 * @throws IOException
	 */
	private void outputHtmlResult(DataTable dt, Group group, ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("recent_topiclist", dt);
		root_map.put("group", group);
		response.setContentType(Module.TEXT_HTML_UTF_8);
		String template_Name = "/WEB-INF/group/default/recent_topiclist.ftl";
		processTemplate(root_map, response.getOut(), template_Name);
	}
	
}
