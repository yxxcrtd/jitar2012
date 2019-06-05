/**
 * 
 */
package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.VoteResult;
import cn.edustar.jitar.pojos.VoteUser;

/**
 * @author 孟宪会
 *
 */
public interface VoteDao {

	/**
	 * 创建一个投票
	 * @param vote
	 */
	public void addVote(Vote vote);
	
	/**
	 * 创建一个调查问题
	 * @param voteQuestion
	 */
	public void addVoteQuestion(VoteQuestion voteQuestion);
	
	/**
	 * 创建一个问题的选项
	 * @param voteQuestionAnswer
	 */
	public void addVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer);
	
	/**
	 * 创建一次投票结果
	 * @param voteResult
	 */
	public void addVoteResult(VoteResult voteResult);
	
	/**
	 * 创建一个投票次数
	 * @param voteUser
	 */
	public void addVoteUser(VoteUser voteUser);
	
	/**
	 * 检查用户是否已经参与了投票
	 * @param voteId
	 * @param userId
	 * @return
	 */
	public boolean checkVoteResultWithUserId(int voteId, int userId);
	
	/**
	 * 根据投票标识得到投票对象
	 * @param voteId
	 * @return
	 */
	public Vote getVoteById(int voteId);
	
	/**
	 * 得到投票参与的用户数
	 * @param voteId
	 * @return
	 */
	public int getVoteUserCount(int voteId);
	/**
	 * 重新计算投票统计数据
	 * @param vote
	 */
	public void reCountVoteData(Vote vote);
	
	/**
	 * 得到一个投票的问题列表
	 * @param voteId
	 * @return
	 */
	public List<VoteQuestion> getVoteQuestionList(int voteId);
	
	/**
	 * 得到一个问题的选项列表
	 * @param voteQuestionId
	 * @return
	 */
	public List<VoteQuestionAnswer> getVoteQuestionAnswerList(int voteQuestionId);
	
	/**
	 * 得到一个容器对象的投票列表
	 * @param parentGuid
	 * @param topCount
	 * @return
	 */
	public List<Vote> getVoteList(String parentGuid,int topCount);
	
	/**
	 * 根据投票标识和用户标识删除投票
	 * @param voteId
	 * @param createUserId
	 */
	public void deleteVoteWithVoteIdAndCreateUserId(int voteId, int createUserId);
	
	/**
	 * 删除投票对象
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
	 * 更新一个投票选项
	 * @param vote
	 */
	public void updateVote(Vote vote);
	
	
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
