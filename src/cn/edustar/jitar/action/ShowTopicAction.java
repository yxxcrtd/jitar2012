package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.PlugInTopic;
import cn.edustar.jitar.pojos.PlugInTopicReply;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PlugInTopicReplyQuery;
import cn.edustar.jitar.service.PlugInTopicService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;

/**
 * 全部话题讨论
 * 
 * @author renliang
 */
public class ShowTopicAction extends AbstractBasePageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3312411684624107440L;
	public CommonData commondata = null;

	public PlugInTopicService plugInTopicService = null;
	public PluginAuthorityCheckService pluginAuthorityCheckService = null;
	public PageFrameService pageFrameService = null;
	private Integer plugInTopicId = 0;
	private PlugInTopic plugInTopic = null;

	protected String execute(String cmd) throws Exception {
		commondata = new CommonData();
		pageFrameService = commondata.getPageFrameService();
		if ("".equals(commondata.getParentGuid())
				|| "".equals(commondata.getParentType())) {
			addActionError("无效的标识。");
			return "ERROR";
		}
		plugInTopicId = params.safeGetIntParam("topicId");
		if (plugInTopicId == 0) {
			addActionError("无效的讨论标识。");
			return "ERROR";
		}
		plugInTopic = plugInTopicService.getPlugInTopicById(plugInTopicId);
		if (plugInTopic == null) {
			addActionError("无法加载讨论对象。");
			return "ERROR";
		}
		if (request.getMethod() == "POST") {
			String ret = saveReply();
			if (!"".equals(ret)) {
				return ret;
			}
		}
		PlugInTopicReplyQuery qry = new PlugInTopicReplyQuery(
				"ptr.plugInTopicReplyId, ptr.title, ptr.createDate, ptr.createUserId, ptr.createUserName, ptr.replyContent");
		qry.setPlugInTopicId(plugInTopicId);
		Pager pager = params.createPager();
		pager.setItemName("讨论话题");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
		Object topic_reply_list = qry.query_map(pager);

		//TODO 这里暂时注释掉  不清楚用途
		/*boolean canManage = false;
		if (pluginAuthorityCheckService.canManagePluginInstance(
				commondata.getCommonObject(), super.getLoginUser())) {
			canManage = true;
		}*/

		String returl = params.safeGetStringParam("returl");

		Map map = new HashMap();
		map.put("plugInTopic", plugInTopic);
		map.put("pager", pager);
		map.put("topic_reply_list", topic_reply_list);

		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("loginUser", super.getLoginUser());
		map.put("head_nav", "special_subject");
		map.put("returl", returl);
		map.put("parentGuid", commondata.getParentGuid());
		map.put("parentType", commondata.getParentType());
		String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/special_topic_show.ftl");
		String page_frame = pageFrameService.getFramePage(commondata.getParentGuid(), commondata.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", plugInTopic.getTitle() + " 的讨论");
		commondata.writeToResponse(page_frame);
		return null;
	}

	private String saveReply() throws IOException {
		if (super.getLoginUser() == null) {
			addActionError("请先登录参与。");
			return super.LOGIN;
		}

		String t = params.safeGetStringParam("commentTitle");
		String c = params.safeGetStringParam("content");
		if ("".equals(t.trim()) || "".equals(c.trim())) {
			addActionError("请输入必要的内容。");
			return super.ERROR;
		}
		PlugInTopicReply plugInTopicReply = new PlugInTopicReply();
		plugInTopicReply.setPlugInTopicId(plugInTopicId);
		plugInTopicReply.setTitle(t);
		plugInTopicReply.setCreateDate(new Date());
		plugInTopicReply.setCreateUserId(super.getLoginUser().getUnitId());
		plugInTopicReply.setCreateUserName(super.getLoginUser().getTrueName());
		plugInTopicReply.setReplyContent(c);
		plugInTopicReply.setAddIp(commondata.getClientIp());
		plugInTopicService.addPlugInTopicReply(plugInTopicReply);
		String refUrl = request.getHeader("Referer");
		if (refUrl == null || refUrl == "") {
			return "";
		} else {
			response.sendRedirect(refUrl);
		}
		return null;
	}
	
	public void setPlugInTopicService(PlugInTopicService plugInTopicService) {
		this.plugInTopicService = plugInTopicService;
	}

	public void setPluginAuthorityCheckService(
			PluginAuthorityCheckService pluginAuthorityCheckService) {
		this.pluginAuthorityCheckService = pluginAuthorityCheckService;
	}
}
