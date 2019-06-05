package cn.edustar.jitar.action;

import java.util.HashMap;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginService;
import cn.edustar.jitar.service.QuestionQuery;

/**
 * 学科全部问题
 * 
 * @author renliang
 */
public class QuestionListAllAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3312411684624107440L;
	public CommonData commondata = null;
	private PluginService pluginService = null;
	private PageFrameService pageFrameService = null;

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}

	protected String execute(String cmd) throws Exception {
		commondata = new CommonData();
		pageFrameService = commondata.getPageFrameService();
		if (!pluginService.checkPluginEnabled("questionanswer")) {
			request.setAttribute("message", "该插件已经被管理员禁用。");
			return "show_text";
		}
		if (commondata.getParentGuid().trim().equals("")
				|| commondata.getParentType().trim().equals("")) {
			return "not_found";
		}

		String pageIndex = params.safeGetStringParam("page");
		if (!pageIndex.matches("^[0-9]+$")) {
			pageIndex = "1";
		}

		QuestionQuery qry = new QuestionQuery(
				"q.questionId,q.topic,q.createDate,q.createUserId,q.createUserName,q.objectGuid,q.createUserId, q.createUserName");
		qry.setParentGuid(commondata.getParentGuid());
		Pager pager = params.createPager();
		pager.setItemName("问题");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setCurrentPage(Integer.parseInt(pageIndex));
		pager.setTotalRows(qry.count());
		Object q_list = qry.query_map(pager);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("q_list", q_list);
		map.put("pager", pager);
		map.put("parentGuid", commondata.getParentGuid());
		map.put("parentType", commondata.getParentType());

		String pagedata = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/questionanswer/listall.ftl");

		String page_frame = pageFrameService.getFramePage(
				commondata.getParentGuid(), commondata.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "全部问题列表");

		commondata.writeToResponse(page_frame);
		return null;
	}
}
