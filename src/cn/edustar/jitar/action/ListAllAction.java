package cn.edustar.jitar.action;

import java.util.HashMap;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PlugInTopicQuery;

/**
 * 全部话题讨论
 * 
 * @author renliang
 */
public class ListAllAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3312411684624107440L;
	public CommonData commondata = null;

	protected String execute(String cmd) throws Exception {
		commondata = new CommonData();
		if ("".equals(commondata.getParentGuid().trim())
				|| "".equals(commondata.getParentType().trim())) {
			addActionError("无效的标识。");
			return "ERROR";
		}

		PlugInTopicQuery qry = new PlugInTopicQuery(
				"pt.plugInTopicId, pt.title, pt.createDate, pt.createUserId, pt.createUserName");
		qry.setParentGuid(commondata.getParentGuid().trim());
		qry.setParentObjectType(commondata.getParentType().trim());
		Pager pager = params.createPager();
		pager.setItemName("讨论");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		Object topic_list = qry.query_map(pager);
		PageFrameService pageFrameService = commondata.getPageFrameService();
		Map map = new HashMap();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("topic_list", topic_list);
		map.put("pager", pager);
		map.put("loginUser", super.getLoginUser());
		map.put("parentGuid", commondata.getParentGuid());
		map.put("parentType", commondata.getParentType());
		map.put("unitId", params.getIntParamZeroAsNull("unitId"));

		String pagedata = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/topic/listall.ftl");
		String page_frame = pageFrameService.getFramePage(
				commondata.getParentGuid(), commondata.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "全部话题讨论");
		commondata.writeToResponse(page_frame);
		return null;
	}
}
