package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.VoteResult;
import cn.edustar.jitar.pojos.VoteUser;

public interface VoteService {

	/**
	 * 创建一个投票
	 * @param vote
	 */
	public void addVote(Vote vote);
	
	/**
	 * 创建投票问题
	 * @param voteQuestion
	 */
	public void addVoteQuestion(VoteQuestion voteQuestion);
	
	/**
	 * 添加问题选项
	 * @param voteQuestionAnswer
	 */
	public void addVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer);
	
	/**
	 * 根据标识加载投票
	 * @param voteId
	 * @return
	 */
	public Vote getVoteById(int voteId);
	
	/**
	 * 投票是否已经过期
	 * @param voteId
	 * @return
	 */
	public boolean voteHasExpires(int voteId);
	
	/**
	 * 添加投票结果
	 * @param voteResult
	 */
	public void addVoteResult(VoteResult voteResult);
	
	/**
	 * 创建投票用户
	 * @param voteUser
	 */
	public void addVoteUser(VoteUser voteUser);
	
	/**
	 * 检查用户是否参加了投票
	 * @param voteId
	 * @param userId
	 * @return
	 */
	public boolean checkVoteResultWithUserId(int voteId, int userId);
	
	/**
	 * 得到投票的问题列表
	 * @param voteId
	 * @return
	 */
	public List<VoteQuestion> getVoteQuestionList(int voteId);
	
	/**
	 * 得到投票用户数
	 * @param voteId
	 * @return
	 */
	public int getVoteUserCount(int voteId);
	
	/**
	 * 重新计算投票数据
	 * @param vote
	 */
	public void reCountVoteData(Vote vote);
	
	/**
	 * 得到问题选项列表
	 * @param voteQuestionId
	 * @return
	 */
	public List<VoteQuestionAnswer> getVoteQuestionAnswerList(int voteQuestionId);
	
	/**
	 * 得到容器对象的投票列表
	 * @param parentGuid
	 * @param topCount
	 * @return
	 */
	public List<Vote> getVoteList(String parentGuid,int topCount);
	
	/**
	 * 得到容器对象的最新的一个投票
	 * @param parentGuid
	 * @return
	 */
	public Vote getNewestVote(String parentGuid);
	
	/**
	 * 根据用户标识和投票标识删除投票
	 * @param voteId
	 * @param createUserId
	 */
	public void deleteVoteWithVoteIdAndCreateUserId(int voteId, int createUserId);
	
	/**
	 * 删除一个人投票
	 * @param vote
	 */
	public void deleteVote(Vote vote);
	
	/**
	 * 根据标识删除投票
	 * @param voteId
	 */
	public void deleteVoteById(int voteId);
	
	/**
	 * 根据 id 得到调查问题
	 * @return
	 */
	public VoteQuestion getVoteQuestionById(int voteQuestionId);
	
	/**
	 * 根据 id 得到问题选项
	 * @return
	 */
	public VoteQuestionAnswer getVoteQuestionAnswerById(int voteQuestionAnswerId);
	
	/**
	 * 更新一个问题
	 * @param voteQuestion
	 */
	public void updateVoteQuestion(VoteQuestion voteQuestion);
	
	/**
	 * 更新一个问题选项
	 * @param voteQuestionAnswer
	 */
	public void updateVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer);
	
	/**
	 * 更新一个投票选项
	 * @param vote
	 */
	public void updateVote(Vote vote);
	
	/**
	 * 根据问题的 id 删除一个问题及其问题下的选项、投票结果
	 * @param voteQuestionId
	 */
	public void deleteQuestionById(int voteQuestionId);
	
	
	/**
	 * 根据选项的 id 删除一个选项
	 * @param questionAnswerId
	 */
	public void deleteQuestionAnswerById(int questionAnswerId);
	
}
