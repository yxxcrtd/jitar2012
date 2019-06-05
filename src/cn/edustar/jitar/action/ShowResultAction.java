package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.VoteService;

import com.alibaba.fastjson.JSONObject;

/**
 * 修改调查投票
 * 
 * @author renliang
 */
public class ShowResultAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430759716178464202L;
	private VoteService vote_svc = null;
	private CommonData commonData = null;
	private Vote vote = null;
	private PageFrameService pageFrameService = null;

	public void setVote_svc(VoteService vote_svc) {
		this.vote_svc = vote_svc;
	}

	@Override
	public String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (commonData.getParentGuid().trim().equals("") || commonData.getParentType().trim().equals("")){
			 addActionError("无效的访问。");
	         return "error";
		}
           
        Integer voteId = params.safeGetIntParam("voteId");
        if (voteId == 0){
        	addActionError("请选择一个投票。");
            return "error";
        }
        vote = vote_svc.getVoteById(voteId);
        if (vote == null){
        	addActionError("无法加载投票，可能已经被删除、或者禁用。");
            return "error";
        }
        //#self.vote_svc.reCountVoteData(self.vote)
        Integer voteTotal = vote_svc.getVoteUserCount(vote.getVoteId());
        List<VoteQuestion>  vote_question_list = vote_svc.getVoteQuestionList(vote.getVoteId());
        List<JSONObject> vote_question_answer_list = new ArrayList<JSONObject>();
		for (VoteQuestion q : vote_question_list) {
			List<VoteQuestionAnswer> vote_answer_list = vote_svc
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
		map.put("vote_total", voteTotal);
		map.put("loginUser", super.getLoginUser());
		map.put("parentGuid", commonData.getParentGuid());
		map.put("parentType", commonData.getParentType());

		String pagedata = pageFrameService.transformTemplate(map,
				"/WEB-INF/mod/vote/showresult.ftl");

		String page_frame = pageFrameService.getFramePage(
				commonData.getParentGuid(), commonData.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "调查投票");
		commonData.writeToResponse(page_frame);
		return null;
	}
}
