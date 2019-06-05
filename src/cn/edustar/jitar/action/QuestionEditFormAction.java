package cn.edustar.jitar.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.QuestionAnswerService;

/**
 * 提出问题
 * 
 * @author renliang
 */
public class QuestionEditFormAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213211887857583255L;
	private CommonData commonData = null;
	private QuestionAnswerService questionAnswerService = null;
	

	private Question question = null;
	private PageFrameService pageFrameService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (commonData.getParentGuid().trim().equals("")
				|| commonData.getParentType().trim().equals("")) {
			return "not_found";
		}

		if (super.getLoginUser() == null) {
			return "not_logined";
		}

		Integer questionId = params.safeGetIntParam("qid");
		if (questionId != null && questionId != 0) {
			question = questionAnswerService.getQuestionById(questionId);
		}

		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			String q_topic = params.safeGetStringParam("quesition_title");
			String q_content = params.safeGetStringParam("quesition_content");
			if (q_topic.trim().equals("")) {
				addActionError("请输入问题。");
				return "error";
			}
			if (q_content.trim().equals("")) {
				addActionError("请输入问题描述。");
				return "error";
			}
			Question question = save_or_update();
			response.sendRedirect(pageFrameService.getSiteUrl()
					+ "mod/questionanswer/question_getcontent.action?guid="
					+ commonData.getParentGuid() + "&type="
					+ commonData.getParentType() + "&qid="
					+ question.getQuestionId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question);
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());

		String pagedata = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/questionanswer/editform.ftl");

		String page_frame = pageFrameService.getFramePage(
				commonData.getParentGuid(), commonData.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "提出问题");
		commonData.writeToResponse(page_frame);
		return null;
	}

	private Question save_or_update() {
		String q_topic = params.safeGetStringParam("quesition_title");
		String q_content = params.safeGetStringParam("quesition_content");
		Question question = null;
		if (question == null) {
			// #objectGuid = UUID.randomUUID().toString().upper()
			question = new Question();
			question.setParentGuid(commonData.getParentGuid());
			// #question.setObjectGuid(str(objectGuid))
			question.setCreateDate(new Date());
			question.setParentObjectType(commonData.getParentType());
			question.setAddIp(commonData.getClientIp());
			question.setCreateUserName(super.getLoginUser().getTrueName());
			question.setCreateUserId(super.getLoginUser().getUserId());
		}

		question.setTopic(q_topic);
		question.setQuestionContent(q_content);
		questionAnswerService.saveOrUpdate(question);
		return question;
	}
	public void setQuestionAnswerService(QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}
}
