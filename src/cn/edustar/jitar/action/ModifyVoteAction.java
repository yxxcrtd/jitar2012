package cn.edustar.jitar.action;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.service.PageFrameService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.VoteService;

import com.alibaba.fastjson.JSONObject;

/**
 * 修改调查投票
 * 
 * @author renliang
 */
public class ModifyVoteAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430759716178464202L;
	private VoteService voteService = null;
	private PluginAuthorityCheckService pluginAuthorityCheckService = null;
	private CommonData commonData = null;
	private Vote vote = null;

	@Override
	public String execute(String cmd) throws Exception {
		commonData = new CommonData();
		if ("".equals(commonData.getParentGuid().trim())
				|| "".equals(commonData.getParentType().trim())) {
			addActionError("无效的标识。");
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
		boolean canManage = pluginAuthorityCheckService
				.canManagePluginInstance(commonData.getCommonObject(),
						super.getLoginUser());
		if (!canManage) {
			addActionError("权限被拒绝。");
			return "error";
		}
		// #voteHasExpires = self.vote_svc.voteHasExpires(self.vote.voteId)
		// #if voteHasExpires == True:
		// # self.addActionError("该投票已经过期，没有必要再修改了吧。")
		// # return self.ERROR

		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			return post_form();
		}
		get_form();
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void get_form() {
		// """ 得到投票问题和答案 """
		List<VoteQuestion> vote_question_list = voteService.getVoteQuestionList(vote.getVoteId());
		List vote_question_answer_list = new ArrayList<>();
		for (VoteQuestion q : vote_question_list) {
			List<VoteQuestionAnswer> vote_answer_list = voteService
					.getVoteQuestionAnswerList(q.getVoteQuestionId());
			// vote_question = {"question":None,"answer":None}
			JSONObject vote_question = new JSONObject();
			vote_question.put("question", null);
			vote_question.put("answer", null);
			// #for x in vote_question:
			// # print x
			vote_question.put("question", q);
			vote_question.put("answer", vote_answer_list);
			vote_question_answer_list.add(vote_question);
		}
		PageFrameService pageFrameService = commonData.getPageFrameService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SiteUrl", pageFrameService.getSiteUrl());
		map.put("UserMgrUrl", pageFrameService.getUserMgrUrl());
		map.put("vote", vote);
		map.put("vote_question_answer_list", vote_question_answer_list);
		map.put("loginUser", super.getLoginUser());
		map.put("parentGuid", commonData.getParentGuid());
		map.put("parentType", commonData.getParentType());
		String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/ftl2/special/vote_update.ftl");
		String page_frame = pageFrameService.getFramePage(commonData.getParentGuid(), commonData.getParentType());
		page_frame = page_frame.replace("[placeholder_content]", pagedata);
		page_frame = page_frame.replace("[placeholder_title]", "修改调查投票");
		try {
			commonData.writeToResponse(page_frame);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String post_form() {
		String strVoteTitle = params.safeGetStringParam("vote_title");
		String strVoteDescription = params.safeGetStringParam("vote_desc");
		String strEndDate = params.safeGetStringParam("vote_enddate");
		if ("".equals(strVoteTitle.trim())) {
			addActionError("请输入调查或者投票的名称。");
			return "error";
		}
		if ("".equals(strEndDate.trim())) {
			addActionError("请输入调查或者投票的结束日期。");
			return "error";
		}

		Format format = new SimpleDateFormat("yyyy-MM-dd");
		Date end_data = null;
		try {
			end_data = (Date) format.parseObject(strEndDate);
		} catch (ParseException e) {
			addActionError("输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式。");
			return "error";
		}

		vote.setTitle(strVoteTitle);
		vote.setDescription(strVoteDescription);
		vote.setEndDate(end_data);
		voteService.updateVote(vote);

		// # print "投票已经更新"
		// # 收集数据，区分新加的与原来的。
		// # 原来的问题集合
		List<Integer> old_q = params.safeGetIntValues("old_q");
		for (Integer old_id : old_q) {
			// # 对于新的问题的，只进行更新，对新的问题选项，则需要插入
			// # 原来的问题
			String old_question_title = params.safeGetStringParam("vote_q_"
					+ old_id);
			Integer old_question_type = params.safeGetIntParam("vote_t_"
					+ old_id);
			Integer old_question_max_selection = params
					.safeGetIntParam("vote_max_" + old_id);
			Integer old_question_inemindex = params
					.safeGetIntParam("vote_order_" + old_id);

			// # 如果原来的问题名字为空，则会删除原来的问题、选项、及其投票的结果
			if (old_question_title.trim().equals(""))
				// # 删除
				voteService.deleteQuestionById(old_id);
			else {
				VoteQuestion old_question = voteService
						.getVoteQuestionById(old_id);
				if (old_question != null) {
					old_question.setTitle(old_question_title);
					old_question.setQuestionType(old_question_type.byteValue());
					old_question.setMaxSelectCount(old_question_max_selection);
					old_question.setItemIndex(old_question_inemindex);
					voteService.updateVoteQuestion(old_question);

					// # 原来问题的选项：
					Integer old_answer_max_itemdex = 0;
					List<Integer> old_all_answer_id = params
							.safeGetIntValues("q_" + old_id + "_a");
					for (Integer old_a_id : old_all_answer_id) {
						String old_answer_desc = params
								.safeGetStringParam("q_a_" + old_a_id);
						if (old_answer_desc.trim().equals("")) {
							voteService.deleteQuestionAnswerById(old_a_id);
						} else {
							VoteQuestionAnswer old_answer = voteService
									.getVoteQuestionAnswerById(old_a_id);
							if (old_answer != null) {
								if (old_answer_max_itemdex < old_answer
										.getItemIndex()) {
									old_answer_max_itemdex = old_answer
											.getItemIndex();
								}
								old_answer.setAnswerContent(old_answer_desc);
								voteService
										.updateVoteQuestionAnswer(old_answer);
							}

						}

					}

					// # 获取新加入的选项：
					String[] new_answer = request
							.getParameterValues("new_answer_" + old_id);
					for (String new_a : new_answer)
						if (new_a != null && !"".equals(new_a)) {
							old_answer_max_itemdex = old_answer_max_itemdex + 1;
							VoteQuestionAnswer answer = new VoteQuestionAnswer();
							answer.setVoteCount(0);
							answer.setItemIndex(old_answer_max_itemdex);
							answer.setVoteQuestionId(old_id);
							answer.setAnswerContent(new_a);
							voteService.addVoteQuestionAnswer(answer);
						}

				}

			}
		}

		// # 对于新的问题：
		Integer new_question_number = params.safeGetIntParam("question_num");
		for (int row = 1; row < new_question_number + 1; row++) {
			String new_question_title = params.safeGetStringParam("new_vote_q_"
					+ row);
			Integer new_question_type = params.safeGetIntParam("new_vote_t_"
					+ row);
			Integer new_question_max_selection = params
					.safeGetIntParam("new_vote_max_" + row);
			Integer new_question_itemindex = params
					.safeGetIntParam("new_vote_order_" + row);
			String[] new_question_answer_array = request
					.getParameterValues("new_vote_a_" + row);
			if (!new_question_title.trim().equals("")) {
				// # 不为空的内容才添加
				VoteQuestion newQuestion = new VoteQuestion();
				newQuestion.setTitle(new_question_title);
				newQuestion.setQuestionType(new_question_type.byteValue());
				newQuestion.setMaxSelectCount(new_question_max_selection);
				newQuestion.setVoteId(vote.getVoteId());
				newQuestion.setItemIndex(new_question_itemindex);
				voteService.addVoteQuestion(newQuestion);

				Integer itemIndex = 0;
				for (String new_as : new_question_answer_array) {
					if (new_as != null && !"".equals(new_as)) {
						itemIndex = itemIndex + 1;
						VoteQuestionAnswer newAnswer = new VoteQuestionAnswer();
						newAnswer.setAnswerContent(new_as);
						newAnswer.setVoteQuestionId(newQuestion
								.getVoteQuestionId());
						newAnswer.setItemIndex(itemIndex);
						voteService.addVoteQuestionAnswer(newAnswer);
					}

				}

			}

		}

		try {
			response.sendRedirect("getcontent.action?guid="
					+ commonData.getParentGuid() + "&type="
					+ commonData.getParentType() + "&voteId="
					+ vote.getVoteId());
		} catch (IOException e) {
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

}
