package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;

import cn.edustar.jitar.dao.VoteDao;
import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.VoteResult;
import cn.edustar.jitar.pojos.VoteUser;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.VoteService;

public class VoteServiceImpl implements VoteService {

	/**
	 * 投票Dao
	 */
	private VoteDao voteDao;
	
	/**
	 * 缓存服务
	 */
	private CacheService cache_svc;
	
	/**
	 * 添加一个投票
	 */
	public void addVote(Vote vote)
	{
		this.voteDao.addVote(vote);
	}
	
	/**
	 * 添加投票的问题
	 */
	public void addVoteQuestion(VoteQuestion voteQuestion)
	{
		this.voteDao.addVoteQuestion(voteQuestion);
	}
	
	/**
	 * 添加投票问题选项
	 */
	public void addVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer)
	{
		this.voteDao.addVoteQuestionAnswer(voteQuestionAnswer);
	}
	
	
	/**
	 * 添加投票用户
	 */
	public void addVoteUser(VoteUser voteUser)
	{
		this.voteDao.addVoteUser(voteUser);
	}
	
	/**
	 * 根据投票标识得到投票
	 */
	public Vote getVoteById(int voteId)
	{
		return this.voteDao.getVoteById(voteId);
	}
	
	/**
	 * 添加一个投票结果
	 */
	public void addVoteResult(VoteResult voteResult)
	{
		this.voteDao.addVoteResult(voteResult);
	}
	
	/**
	 * 检查用户是否参与了投票
	 */
	public boolean checkVoteResultWithUserId(int voteId, int userId)
	{
		return this.voteDao.checkVoteResultWithUserId(voteId, userId);
	}
	
	/**
	 * 得到投票参与的用户数
	 */
	public int getVoteUserCount(int voteId)
	{
		return this.voteDao.getVoteUserCount(voteId);
	}
	
	/**
	 * 重新计算投票数据
	 */
	public void reCountVoteData(Vote vote)
	{
		this.voteDao.reCountVoteData(vote);
	}
	
	/**
	 * 投票是否已经过期
	 */
	public boolean voteHasExpires(int voteId)
	{
		Vote v = this.getVoteById(voteId);
		if(v == null) return true;
		return (v.getEndDate().before(new Date()));
	}
	
	/**
	 * 得到投票的问题列表
	 */
	public List<VoteQuestion> getVoteQuestionList(int voteId)
	{
		return this.voteDao.getVoteQuestionList(voteId);
	}
	
	/**
	 * 得到问题的选项列表
	 */
	public List<VoteQuestionAnswer> getVoteQuestionAnswerList(int voteQuestionId)
	{
		return this.voteDao.getVoteQuestionAnswerList(voteQuestionId);
	}
	
	/**
	 * 得到容器对象的投票列表
	 */
	public List<Vote> getVoteList(String parentGuid,int topCount)
	{
		return this.voteDao.getVoteList(parentGuid, topCount);
	}
	
	/**
	 * 最新的投票
	 */
	public Vote getNewestVote(String parentGuid)
	{
		List<Vote> v = this.getVoteList(parentGuid,1);
		if (v == null || v.size() <1) return null;
		return v.get(0);
	}
	
	/**
	 * 删除某人创建的调查
	 */
	public void deleteVoteWithVoteIdAndCreateUserId(int voteId, int createUserId){
		this.voteDao.deleteVoteWithVoteIdAndCreateUserId(voteId, createUserId);
	}
	
	/**
	 * 删除某个调查
	 */
	public void deleteVote(Vote vote){
		this.voteDao.deleteVote(vote);
	}
	
	/**
	 * 根据标识删除投票
	 * @param voteId
	 */
	public void deleteVoteById(int voteId)
	{
		this.voteDao.deleteVoteById(voteId);
	}
	
	
	/**
	 * 根据 id 得到调查问题
	 * @return
	 */
	public VoteQuestion getVoteQuestionById(int voteQuestionId)
	{
		return this.voteDao.getVoteQuestionById(voteQuestionId);
	}
	
	/**
	 * 根据 id 得到问题选项
	 * @return
	 */
	public VoteQuestionAnswer getVoteQuestionAnswerById(int voteQuestionAnswerId)
	{
		return this.voteDao.getVoteQuestionAnswerById(voteQuestionAnswerId);
	}
	
	/**
	 * 更新一个问题
	 * @param voteQuestion
	 */
	public void updateVoteQuestion(VoteQuestion voteQuestion)
	{
		this.voteDao.updateVoteQuestion(voteQuestion);
	}
	
	/**
	 * 更新一个问题选项
	 * @param voteQuestionAnswer
	 */
	public void updateVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer){
		this.voteDao.updateVoteQuestionAnswer(voteQuestionAnswer);
	}
	
	
	/**
	 * 更新一个投票选项
	 * @param vote
	 */
	public void updateVote(Vote vote)
	{
		this.voteDao.updateVote(vote);
	}
	
	/**
	 * 根据问题的 id 删除一个问题及其问题下的选项、投票结果
	 * @param voteQuestionId
	 */
	public void deleteQuestionById(int voteQuestionId)
	{
		this.voteDao.deleteQuestionById(voteQuestionId);
	}
	
	/**
	 * 根据选项的 id 删除一个选项
	 * @param questionAnswerId
	 */
	public void deleteQuestionAnswerById(int questionAnswerId)
	{
		this.voteDao.deleteQuestionAnswerById(questionAnswerId);
	}
	
	
	
	/**
	 * 数据Dao
	 * @return
	 */
	public VoteDao getVoteDao() {
		return voteDao;
	}
	
	/**
	 * 数据Dao
	 * @param voteDao
	 */
	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
	}
	
	/**
	 * 缓存服务
	 * @return
	 */
	public CacheService getCacheService() {
		return cache_svc;
	}

	/**
	 * 缓存服务
	 * @param cache_svc
	 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

}
