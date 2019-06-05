package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.VoteQuery;
import cn.edustar.jitar.service.VoteService;

/**
 * 调查投票管理
 * 
 * @author renliang
 */
public class ManageListAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3964793642528488803L;
	public VoteService voteService = null;
	public PluginAuthorityCheckService pluginAuthorityCheckService = null;
	public PageFrameService pageFrameService = null;
	public CommonData commonData = null;

	private String k = null;
	private String subjectId = null;
	private String gradeId = null;

	private void init() {
		pageFrameService = commonData.getPageFrameService();
		k = params.getStringParam("k");
		subjectId = String.valueOf(params.getIntParamZeroAsNull("subjectId"));
		gradeId = String.valueOf(params.getIntParamZeroAsNull("gradeId"));

		request.setAttribute("subjectId", subjectId);
		request.setAttribute("gradeId", gradeId);
		request.setAttribute("k", k);
	}

	@Override
	public String execute(String cmd) throws Exception {
		commonData = new CommonData();
		init();
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
		VoteQuery qry = new VoteQuery(
				"vote.voteId, vote.title, vote.createDate, vote.createUserId, vote.createUserName, vote.endDate, vote.parentGuid, vote.parentObjectType");
		qry.setParentGuid(commonData.getParentGuid().trim());
		Pager pager = params.createPager();
		pager.setItemName("投票");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		Object vote_list = qry.query_map(pager);

		if ("".equals(show_type.trim())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SiteUrl", pageFrameService.getSiteUrl());
			map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
			map.put("vote_list", vote_list);
			map.put("pager", pager);
			map.put("loginUser", super.getLoginUser());
			map.put("parentGuid", commonData.getParentGuid());
			map.put("parentType", commonData.getParentType());
			String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/vote_manage.ftl");
			String page_frame = pageFrameService.getFramePage(commonData.getParentGuid(), commonData.getParentType());
			page_frame = page_frame.replace("[placeholder_content]", pagedata);
			page_frame = page_frame.replace("[placeholder_title]", "调查投票管理");
			try {
				commonData.writeToResponse(page_frame);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			request.setAttribute("vote_list", vote_list);
			request.setAttribute("pager", pager);
			return "manage_list_nohead";
		}

	}

	private void post_form() {
		List<Integer> guid = params.safeGetIntValues("q_guid");
		for (int g : guid) {
			voteService.deleteVoteById(g);
		}

	}
	
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}

	public void setPluginAuthorityCheckService(
			PluginAuthorityCheckService pluginAuthorityCheckService) {
		this.pluginAuthorityCheckService = pluginAuthorityCheckService;
	}

}
