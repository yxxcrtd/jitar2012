package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.VoteResult;
import cn.edustar.jitar.pojos.VoteUser;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.VoteService;

import com.alibaba.fastjson.JSONObject;

/**
 * 活动 课程
 * 
 * @author renliang
 */
public class GetContentAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430759716178464202L;
	private VoteService voteService = null;
	private CommonData commonData = null;
	private Vote vote = null;
	private PageFrameService pageFrameService = null;

	@Override
	public String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (commonData.getParentGuid().trim().equals("")
				|| commonData.getParentType().trim().equals("")) {
			addActionError("无效的访问。");
			return "error";
		}
		Integer voteId = params.safeGetIntParam("voteId");
		if (voteId == 0) {
			addActionError("请选择一个投票。");
			return "error";
		}
		vote = voteService.getVoteById(voteId);
		if (vote == null) {
			addActionError("无法加载投票，可能已经被删除、或者禁用。");
			return "error";
		}
		// 投票是否已经过期
		boolean voteHasExpires = voteService.voteHasExpires(vote.getVoteId());
		if (voteHasExpires) {
			voteHasExpires = true;
		} else {
			voteHasExpires = false;
		}

		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			return save_post();
		}
		// """ 得到投票问题和答案 """
		List<VoteQuestion> vote_question_list = voteService
				.getVoteQuestionList(vote.getVoteId());
		List<JSONObject> vote_question_answer_list = new ArrayList<JSONObject>();
		for (VoteQuestion q : vote_question_list) {
			List<VoteQuestionAnswer> vote_answer_list = voteService
					.getVoteQuestionAnswerList(q.getVoteQuestionId());
			JSONObject vote_question = new JSONObject();
			/*
			 * vote_question = {"question":None,"answer":None} #for x in
			 * vote_question: # print x
			 */

			vote_question.put("question", q);
			vote_question.put("answer", vote_answer_list);
			/*
			 * vote_question["question"] = q vote_question["answer"] =
			 * vote_answer_list
			 */
			vote_question_answer_list.add(vote_question);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("vote", vote);
		map.put("vote_question_answer_list", vote_question_answer_list);
		map.put("voteHasExpires", voteHasExpires);
		map.put("loginUser", super.getLoginUser());
		map.put("parentGuid", commonData.getParentGuid());
		map.put("parentType", commonData.getParentType());

		String pagedata = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/vote/getcontent.ftl");

		String page_frame = pageFrameService.getFramePage(
				commonData.getParentGuid(), commonData.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "调查投票");
		commonData.writeToResponse(page_frame);
		return null;
	}

	private String save_post() {
		if (super.getLoginUser() == null) {
			addActionError("要参与投票，请先登录。");
			return "error";
		}
		boolean hasVoted = voteService.checkVoteResultWithUserId(
				vote.getVoteId(), super.getLoginUser().getUserId());
		if (hasVoted) {
			addActionError("你已经投过票了，谢谢你的参与。");
			return "error";
		}
		List<Integer> q_list = params.safeGetIntValues("q_list");
		for (Integer q : q_list) {
			List<Integer> answer_id = params.safeGetIntValues("q_" + q);
			for (Integer a_id : answer_id) {
				VoteResult q_ret = new VoteResult(a_id, new Date(), super
						.getLoginUser().getUserId());
				voteService.addVoteResult(q_ret);
			}
		}

		VoteUser voteUser = new VoteUser();
		voteUser.setVoteId(vote.getVoteId());
		voteUser.setUserId(super.getLoginUser().getUserId());
		voteUser.setAddIp(commonData.getClientIp());
		voteService.addVoteUser(voteUser);
		// #更新投票数据
		voteService.reCountVoteData(vote);
		try {
			response.sendRedirect("showresult.action?guid="
					+ commonData.getParentGuid() + "&type="
					+ commonData.getParentType() + "&voteId="
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
}
