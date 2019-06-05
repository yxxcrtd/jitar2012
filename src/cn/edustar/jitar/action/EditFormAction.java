package cn.edustar.jitar.action;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.VoteService;

/**
 * 发起调查投票
 * 
 * @author renliang
 */
public class EditFormAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 320616138762988727L;
	public VoteService voteService = null;
	public CommonData commondata = null;
	public PluginAuthorityCheckService pluginAuthorityCheckService = null;
	public PageFrameService pageFrameService = null;

	@Override
	public String execute(String cmd) {
		commondata = new CommonData();
		pageFrameService = commondata.getPageFrameService();
		if ("".equals(commondata.getParentGuid().trim()) || "".equals(commondata.getParentType().trim())) {
			addActionError("无效的访问。");
			return "error";
		}

		if (super.getLoginUser() == null) {
			addActionError("请先登录。");
			return super.LOGIN.toLowerCase();
		}
		// # 检查权限：
		boolean canCreate = pluginAuthorityCheckService
				.canManagePluginInstance(commondata.getCommonObject(),
						super.getLoginUser());
		if (!canCreate) {
			addActionError("你无权进行操作。");
			return "error";
		}
		if (request.getMethod() == "POST") {
			return save_or_update();
		}
		Map map = new HashMap();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/vote_add.ftl");
		String page_frame = pageFrameService.getFramePage(commondata.getParentGuid(), commondata.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "发起调查投票");
		try {
			commondata.writeToResponse(page_frame);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String save_or_update() {
		String vote_title = params.safeGetStringParam("vote_title");
		String vote_desc = params.safeGetStringParam("vote_desc");
		String vote_enddate = params.safeGetStringParam("vote_enddate");
		Integer question_num = params.safeGetIntParam("question_num");
		if ("".equals(vote_title.trim())) {
			addActionError("请输入调查或者投票的名称。");
			return "error";
		}
		if ("".equals(vote_enddate.trim())) {
			addActionError("请输入调查或者投票的结束日期。");
			return "error";
		}

		Format format = new SimpleDateFormat("yyyy-MM-dd");
		Date end_date = null;
		try {
			end_date = (Date) format.parseObject(vote_enddate);
		} catch (Exception e) {
			addActionError("输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式。");
			return "error";
		}
		if (question_num < 1) {
			addActionError("请输入调查问题。");
			return "error";
		}
		// # 先保存投票
		Vote vote = new Vote();
		vote.setTitle(vote_title);
		vote.setDescription(vote_desc);
		vote.setCreateDate(new Date());
		vote.setCreateUserId(super.getLoginUser().getUnitId());
		vote.setCreateUserName(super.getLoginUser().getTrueName());
		vote.setEndDate(end_date);
		vote.setParentGuid(commondata.getParentGuid());
		vote.setParentObjectType(commondata.getParentType());
		voteService.addVote(vote);

		// # 保存问题：
		for (int i = 1; i < question_num + 1; i++) {
			String vote_q = params.safeGetStringParam("vote_q_" + i);
			Integer vote_t = params.safeGetIntParam("vote_t_" + i);
			Integer vote_max = params.safeGetIntParam("vote_max_" + i);
			if (!"".equals(vote_q.trim())) {
				VoteQuestion vote_question = new VoteQuestion();
				vote_question.setTitle(vote_q);
				vote_question.setVoteId(vote.getVoteId());
				vote_question.setQuestionType(vote_t.byteValue());
				vote_question.setMaxSelectCount(vote_max);
				vote_question.setItemIndex(i);
				voteService.addVoteQuestion(vote_question);

				String[] vote_a_list = request
						.getParameterValues("vote_a_" + i);
				int m = 0;
				for (String answer : vote_a_list) {
					if (!"".equals(answer.trim())) {
						VoteQuestionAnswer vote_question_answer = new VoteQuestionAnswer();
						vote_question_answer.setAnswerContent(answer.trim());
						vote_question_answer.setVoteQuestionId(vote_question
								.getVoteQuestionId());
						vote_question_answer.setItemIndex(m);
						m++;
						voteService.addVoteQuestionAnswer(vote_question_answer);
					}

				}

			}
		}

		try {
			response.sendRedirect("getcontent.action?guid="
					+ commondata.getParentGuid() + "&type="
					+ commondata.getParentType() + "&voteId="
					+ vote.getVoteId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}

	public void setPluginAuthorityCheckService(
			PluginAuthorityCheckService pluginAuthorityCheckService) {
		this.pluginAuthorityCheckService = pluginAuthorityCheckService;
	}

	public void setPageFrameService(PageFrameService pageFrameService) {
		this.pageFrameService = pageFrameService;
	}
}
