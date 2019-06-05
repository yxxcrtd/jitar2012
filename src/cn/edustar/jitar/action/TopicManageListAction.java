package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PlugInTopicQuery;
import cn.edustar.jitar.service.PlugInTopicService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;

/**
 * 调查投票管理
 * 
 * @author renliang
 */
public class TopicManageListAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3964793642528488803L;
	public PluginAuthorityCheckService pluginAuthorityCheckService = null;
	public PluginAuthorityCheckService getPluginAuthorityCheckService() {
		return pluginAuthorityCheckService;
	}

	public void setPlugInTopicService(PlugInTopicService plugInTopicService) {
		this.plugInTopicService = plugInTopicService;
	}

	public PageFrameService pageFrameService = null;
	public CommonData commonData = null;
	public PlugInTopicService plugInTopicService = null;

	@Override
	public String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if ("".equals(commonData.getParentGuid().trim())
				|| "".equals(commonData.getParentType().trim())) {
			addActionError("无效的标识。");
			return "error";
		}
		boolean canManage = pluginAuthorityCheckService
				.canManagePluginInstance(commonData.getCommonObject(),
						super.getLoginUser());
		if (!canManage) {
			addActionError("权限被拒绝。");
			return "error";
		}

		if (request.getMethod().trim().equals("POST")) {
			post_form();
		}
		return get_form();
	}

	private String get_form() {
		String show_type = params.safeGetStringParam("show");
		PlugInTopicQuery qry = new PlugInTopicQuery("pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName");
		qry.setParentGuid(commonData.getParentGuid().trim());
		Pager pager = params.createPager();
		pager.setItemName("讨论");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		Object t_list = qry.query_map(pager);

		if ("".equals(show_type.trim())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SiteUrl", pageFrameService.getSiteUrl());
			map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
			map.put("t_list", t_list);
			map.put("pager", pager);
			map.put("loginUser", super.getLoginUser());
			map.put("parentGuid", commonData.getParentGuid());
			map.put("parentType", commonData.getParentType());
			String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/special_topic_manage_list.ftl");
			String page_frame = pageFrameService.getFramePage(
					commonData.getParentGuid(), commonData.getParentType());
			page_frame = page_frame.replace("[placeholder_content]", pagedata);
			page_frame = page_frame.replace("[placeholder_title]", "讨论管理");
			try {
				commonData.writeToResponse(page_frame);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			request.setAttribute("t_list", t_list);
			request.setAttribute("pager", pager);
			return "manage_list_nohead";
		}

	}

	private void post_form() {
		List<Integer> guid = params.safeGetIntValues("q_guid");
		for (int g : guid) {
			plugInTopicService.deletePluginTopicById(g);
		}
		String refurl = request.getHeader("Referer");
		if (refurl == null || refurl.trim().equals("")) {
			refurl = pageFrameService.getSiteUrl()
					+ "mod/topic/topic_manage_list.action?guid="
					+ commonData.getParentGuid() + "&type="
					+ commonData.getParentType();
		}

		try {
			response.sendRedirect(refurl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setPluginAuthorityCheckService(
			PluginAuthorityCheckService pluginAuthorityCheckService) {
		this.pluginAuthorityCheckService = pluginAuthorityCheckService;
	}

}
