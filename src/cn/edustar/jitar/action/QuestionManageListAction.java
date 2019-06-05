package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.QuestionAnswerService;
import cn.edustar.jitar.service.QuestionQuery;

/**
 * 提出问题
 * 
 * @author renliang
 */
public class QuestionManageListAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213211887857583255L;

	private CommonData commonData = null;
	private QuestionAnswerService questionAnswerService = null;
	private PluginAuthorityCheckService pluginAuthorityCheckService = null;
	private PageFrameService pageFrameService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (commonData.getParentGuid().trim().equals("")
				|| commonData.getParentType().trim().equals("")) {
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
		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			post_form();
		}
		return get_form();
	}

	private String get_form() {
		String show_type = params.safeGetStringParam("show");
		QuestionQuery qry = new QuestionQuery(
				"q.questionId, q.parentGuid, q.parentObjectType, q.topic, q.createDate, q.createUserId, q.createUserName, q.questionContent, q.addIp");
		qry.setParentGuid(commonData.getParentGuid());
		Pager pager = params.createPager();
		pager.setItemName("投票");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		Object q_list = qry.query_map(pager);
		if (show_type.trim().equals("")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SiteUrl", pageFrameService.getSiteUrl());
			map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
			map.put("q_list", q_list);
			map.put("pager", pager);
			map.put("loginUser", super.getLoginUser());
			map.put("parentGuid", commonData.getParentGuid());
			map.put("parentType", commonData.getParentType());

			String pagedata = pageFrameService.transformTemplate(map,
					"/WEB-INF/mod/questionanswer/manage_list.ftl");

			String page_frame = pageFrameService.getFramePage(
					commonData.getParentGuid(), commonData.getParentType());
			page_frame = page_frame.replace("[placeholder_content]", pagedata);
			page_frame = page_frame.replace("[placeholder_title]", "问题管理");
			try {
				commonData.writeToResponse(page_frame);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			request.setAttribute("q_list", q_list);
			request.setAttribute("pager", pager);
			return "manage_list_nohead";
		}
	}

	private void post_form() {
		List<Integer> guid = params.safeGetIntValues("q_guid");
		for (Integer g : guid) {
			questionAnswerService.deleteQuestionByQuestionId(g);
		}
		String refurl = request.getHeader("Referer");
		if (refurl == null || refurl.trim().equals("")) {
			refurl = pageFrameService.getSiteUrl()
					+ "mod/questionanswer/manage_list.action?guid="
					+ commonData.getParentGuid() + "&type="
					+ commonData.getParentType();
		}
		try {
			response.sendRedirect(refurl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setQuestionAnswerService(
			QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}
	public void setPluginAuthorityCheckService(
			PluginAuthorityCheckService pluginAuthorityCheckService) {
		this.pluginAuthorityCheckService = pluginAuthorityCheckService;
	}
}
