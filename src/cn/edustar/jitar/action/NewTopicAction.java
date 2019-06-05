package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edustar.jitar.pojos.PlugInTopic;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PlugInTopicService;

/**
 * 发起讨论
 * 
 * @author renliang
 */
public class NewTopicAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3312411684624107440L;
	public CommonData commondata = null;
	private PageFrameService pageFrameService = null;
	private PlugInTopicService plugInTopicService = null;
	String returl = null;

	protected String execute(String cmd) throws Exception {
		String specialSubjectId = params.safeGetStringParam("specialSubjectId");
		commondata = new CommonData(request);
		pageFrameService = commondata.getPageFrameService();
		
		if ("save".equals(cmd)) {
			save_or_update();
		}
		
		if (commondata.getParentGuid().trim().equals("") || commondata.getParentType().trim().equals("")) {
			addActionError("无效的访问。");
			return "error";
		}

		if (super.getLoginUser() == null) {
			addActionError("请先登录。");
			return super.LOGIN.toLowerCase();
		}

		returl = params.safeGetStringParam("returl");

//		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
//			return save_or_update();
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("loginUser", super.getLoginUser());
		map.put("head_nav", "special_subject");
		map.put("returl", returl);
		map.put("guid", commondata.getParentGuid());
		map.put("specialSubjectId", specialSubjectId);
		map.put("parentGuid", commondata.getParentGuid());
		map.put("parentType", commondata.getParentType());

		String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/special_topic_new.ftl");

		String page_frame = pageFrameService.getFramePage(commondata.getParentGuid(), commondata.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "发起讨论");
		commondata.writeToResponse(page_frame);

		return null;
	}

	private String save_or_update() throws Exception {
		String t_title = params.safeGetStringParam("ttitle");
		String t_content = params.safeGetStringParam("tcontent");
		if (t_title.trim().equals("") || t_content.trim().equals("")) {
			addActionError("请输入讨论标题或者讨论内容。");
			return "error";
		}
		PlugInTopic plugInTopic = new PlugInTopic();
		plugInTopic.setTitle(t_title);
		plugInTopic.setCreateDate(new Date());
		plugInTopic.setCreateUserId(super.getLoginUser().getUserId());
		plugInTopic.setCreateUserName(super.getLoginUser().getTrueName());
		plugInTopic.setTopicContent(t_content);
		plugInTopic.setAddIp(commondata.getClientIp());
		plugInTopic.setParentGuid(commondata.getParentGuid());
		plugInTopic.setParentObjectType(commondata.getParentType());
		plugInTopicService.addPluginTopic(plugInTopic);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<script>alert('发布成功');window.location.href='" + params.safeGetStringParam("backUrl") + "';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	public void setPlugInTopicService(PlugInTopicService plugInTopicService) {
		this.plugInTopicService = plugInTopicService;
	}
	
}
