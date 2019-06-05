package cn.edustar.jitar.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.pojos.QuestionAnswer;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginService;
import cn.edustar.jitar.service.QuestionAnswerService;

public class QuestionGetContentAction extends AbstractBasePageAction {

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}

	public void setQuestionAnswerService(QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5624935870359415287L;

	private CommonData commonData = null;
	private Integer questionId = 0;
	private Question question = null;
	private PluginService pluginService = null;
	private QuestionAnswerService questionAnswerService = null;
	private PageFrameService pageFrameService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (!pluginService.checkPluginEnabled("questionanswer")) {
			request.setAttribute("message", "该插件已经被管理员禁用。");
			return "show_text";
		}
		if (commonData.getParentGuid().trim().equals("")
				|| commonData.getParentType().trim().equals("")) {
			return "not_found";
		}
		questionId = params.safeGetIntParam("qid");

		if (questionId == 0) {
			return "not_found";
		}

		question = questionAnswerService.getQuestionById(questionId);
		if (question == null) {
			return "not_found";
		}
		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			if (super.getLoginUser() == null) {
				return "not_logined";
			}

			String content = params.getStringParam("content");
			if (content.trim().equals("")) {
				addActionError("请输入解答内容。");
				return "error";
			}

			answer_question();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question", question);
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());

		List<QuestionAnswer> answer_list = questionAnswerService
				.getAnswerListByQuestionId(question.getQuestionId());
		if (answer_list != null) {
			map.put("answer_list", answer_list);
		}

		String content = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/questionanswer/getcontent.ftl");

		String pagedata = pageFrameService.getFramePage(
				commonData.getParentGuid(), commonData.getParentType());
		pagedata = pagedata.replace("[placeholder_content]", content);
		pagedata = pagedata.replace("[placeholder_title]", "问题与解答");

		commonData.writeToResponse(pagedata);
		return null;
	}
	
	//保存
	private void answer_question() {
		String content = params.getStringParam("content");
		QuestionAnswer questionAnswer = new QuestionAnswer();
		questionAnswer.setQuestionId(question.getQuestionId());
		questionAnswer.setCreateDate(new Date());
		questionAnswer.setAnswerUserId(super.getLoginUser().getUserId());
		questionAnswer.setAnswerUserName(super.getLoginUser().getTrueName());
		questionAnswer.setAnswerContent(content);
		questionAnswer.setAddIp(commonData.getClientIp());
		questionAnswerService.saveAnswer(questionAnswer);
	}
}
