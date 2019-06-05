package cn.edustar.jitar.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


import cn.edustar.jitar.dao.VoteDao;
import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.VoteResult;
import cn.edustar.jitar.pojos.VoteUser;


/**
 * @author 孟宪会
 *
 *	投票数据库访问接口的实现
 */
public class VoteDaoHibernate extends BaseDaoHibernate implements VoteDao {

	
	/**
	 * 创建一个投票
	 */
	public void addVote(Vote vote)
	{
		this.getSession().saveOrUpdate(vote);
	}
	
	/**
	 * 创建一个投票问题
	 */
	public void addVoteQuestion(VoteQuestion voteQuestion)
	{
		this.getSession().saveOrUpdate(voteQuestion);
	}
	
	/**
	 * 创建投票的选项
	 */
	public void addVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer)
	{
		this.getSession().saveOrUpdate(voteQuestionAnswer);		
	}
	
	/**
	 * 添加一个投票用户对象
	 */
	public void addVoteUser(VoteUser voteUser)
	{
		this.getSession().save(voteUser);
	}
	
	/**
	 * 根据投票标识得到投票
	 */
	public Vote getVoteById(int voteId)
	{
		return (Vote)this.getSession().get(Vote.class, voteId);
	}
	
	/**
	 * 创建一个投票的结果记录
	 */
	public void addVoteResult(VoteResult voteResult)
	{
		this.getSession().save(voteResult);
	}
	
	/**
	 * 检查用户是否参与了一个投票
	 */
	@SuppressWarnings("unchecked")
	public boolean checkVoteResultWithUserId(int voteId, int userId)
	{
		String queryString = "FROM VoteUser Where voteId = ? And userId = ?";
		List<VoteUser> vr = (List<VoteUser>)this.getSession().createQuery(queryString).setInteger(0, voteId).setInteger(1, userId).list();
		return vr.size() > 0;
	}
	
	/**
	 * 得到投票参与的用户数
	 */
	public int getVoteUserCount(int voteId)
	{
		String queryString = "SELECT COUNT(*) FROM VoteUser Where voteId = " + voteId;
		Object ret = this.getSession().createQuery(queryString).uniqueResult();
		if(ret == null)
		{
			return 0;
		}
		else
		{
			return Integer.valueOf(ret.toString()).intValue();
		}
	}
	
	/**
	 * 重新统计投票数据
	 */
	public void reCountVoteData(Vote vote)
	{
		if(vote == null) return;
		List<VoteQuestion> q_list = this.getVoteQuestionList(vote.getVoteId());
		for(VoteQuestion q : q_list)
		{
			List<VoteQuestionAnswer> qa_list = this.getVoteQuestionAnswerList(q.getVoteQuestionId());
			for(VoteQuestionAnswer qa : qa_list)
			{
				String queryString = "SELECT COUNT(*) FROM VoteResult Where voteQuestionAnswerId=" + qa.getVoteQuestionAnswerId();
				Object ct = this.getSession().createQuery(queryString).uniqueResult();
				int count;
				if(ct == null)
				{
					count = 0;
				}
				else
				{
					count = Integer.valueOf(ct.toString()).intValue();
				}
				
				queryString = "UPDATE VoteQuestionAnswer Set voteCount = ? Where voteQuestionAnswerId=?";
				this.getSession().createQuery(queryString).setInteger(0, count).setInteger(1,  qa.getVoteQuestionAnswerId()).executeUpdate();
			}
		}
	}
	
	/**
	 * 得到一个投票的问题列表
	 */
	@SuppressWarnings("unchecked")
	public List<VoteQuestion> getVoteQuestionList(int voteId)
	{			
		String queryString = "FROM VoteQuestion Where voteId = ? Order BY itemIndex Asc";
		return (List<VoteQuestion>)this.getSession().createQuery(queryString).setInteger(0, voteId).list();
	}
	
	/**
	 * 得到问题的选项列表
	 */
	@SuppressWarnings("unchecked")
	public List<VoteQuestionAnswer> getVoteQuestionAnswerList(int voteQuestionId)
	{
		String queryString = "FROM VoteQuestionAnswer Where voteQuestionId = ? Order BY itemIndex Asc";
		return (List<VoteQuestionAnswer>)this.getSession().createQuery(queryString).setInteger(0, voteQuestionId).list(); 		
	}
	
	/**
	 * 得到投票结果列表
	 * @param voteQuestionAnswerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VoteResult> getVoteResultList(int voteQuestionAnswerId)
	{
		return (List<VoteResult>) this.getSession().createQuery("FROM VoteResult Where voteQuestionAnswerId = ?").setInteger(0, voteQuestionAnswerId).list(); 		
	}
	
	/**
	 * 得到容器对象的投票列表
	 */
	@SuppressWarnings("unchecked")
	public List<Vote> getVoteList(String parentGuid,int topCount)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sf.format(new Date());		
		String queryString = " From Vote Where parentGuid = ? And ('" + d + "' between '2008-12-30' and endDate) Order By voteId DESC";

		//List<Vote> lv = (List<Vote>)this.getSession().createQuery(queryString).setString(0, parentGuid).setFirstResult(topCount).list(); 
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		List<Vote> lv = (List<Vote>)this.getSession().createQuery(queryString).setString(0, parentGuid).setFirstResult(0).setMaxResults(topCount).list();
		return lv;
	}
	
	/**
	 * 根据投票标识和创建者标识删除投票
	 */
	public void deleteVoteWithVoteIdAndCreateUserId(int voteId, int createUserId)
	{
		Vote vote = this.getVoteById(voteId);
		if(vote == null) return;
		if(vote.getCreateUserId() != createUserId ) return;
		this.deleteVote(vote);
	}
	
	/**
	 * 删除一次投票
	 */
	public void deleteVote(Vote vote)
	{	
		//this.getSession().setCacheQueries(false);
		//this.getSession().setCheckWriteOperations(false);
		if(vote == null) return;
		String queryString;
		List<VoteQuestion> q_list = this.getVoteQuestionList(vote.getVoteId());
		for(VoteQuestion q : q_list)
		{	
			List<VoteQuestionAnswer> an_list = this.getVoteQuestionAnswerList(q.getVoteQuestionId());
			
		
			for(VoteQuestionAnswer qa : an_list)
			{	
				queryString = "DELETE From VoteResult Where voteQuestionAnswerId = ?";
				this.getSession().createQuery(queryString).setInteger(0, qa.getVoteQuestionAnswerId()).executeUpdate();

				queryString = "DELETE From VoteQuestionAnswer Where voteQuestionAnswerId = ?";
				this.getSession().createQuery(queryString).setInteger(0, qa.getVoteQuestionAnswerId()).executeUpdate();
			}
			
			queryString = "DELETE From VoteQuestion Where voteQuestionId = ?";
			this.getSession().createQuery(queryString).setInteger(0, q.getVoteQuestionId()).executeUpdate();
		}
		queryString = "DELETE From VoteUser Where voteId = ?";
		this.getSession().createQuery(queryString).setInteger(0, vote.getVoteId()).executeUpdate();
	
		queryString = "DELETE From Vote Where voteId = ?";
		this.getSession().createQuery(queryString).setInteger(0, vote.getVoteId()).executeUpdate();
		//this.getSession().flush();
		//this.getSession().delete(voteObject);
	}
	
	/**
	 * 根据标识删除投票
	 * @param voteId
	 */
	public void deleteVoteById(int voteId)
	{
		Vote vote = this.getVoteById(voteId);
		if(vote == null) return;
		this.deleteVote(vote);
	}
	
	/**
	 * 根据 id 得到调查问题
	 * @return
	 */
	public VoteQuestion getVoteQuestionById(int voteQuestionId){
		return (VoteQuestion)this.getSession().get(VoteQuestion.class, voteQuestionId);
	}
	
	/**
	 * 根据 id 得到问题选项
	 * @return
	 */
	public VoteQuestionAnswer getVoteQuestionAnswerById(int voteQuestionAnswerId){
		return (VoteQuestionAnswer)this.getSession().get(VoteQuestionAnswer.class, voteQuestionAnswerId);
	}
	
	/**
	 * 更新一个投票选项
	 * @param vote
	 */
	public void updateVote(Vote vote)
	{
		this.getSession().update(vote);
	}
	
	/**
	 * 更新一个问题
	 * @param voteQuestion
	 */
	public void updateVoteQuestion(VoteQuestion voteQuestion){
		this.getSession().update(voteQuestion);
	}
	
	/**
	 * 更新一个问题选项
	 * @param voteQuestionAnswer
	 */
	public void updateVoteQuestionAnswer(VoteQuestionAnswer voteQuestionAnswer)
	{
		this.getSession().update(voteQuestionAnswer);
	}
	
	/**
	 * 根据问题的 id 删除一个问题及其问题下的选项、投票结果
	 * @param voteQuestionId
	 */
	public void deleteQuestionById(int voteQuestionId)
	{
		String queryString;
		//得到问题的选项
		List<VoteQuestionAnswer> an_list = this.getVoteQuestionAnswerList(voteQuestionId);
		for(VoteQuestionAnswer qa : an_list)
		{	
			queryString = "DELETE From VoteResult Where voteQuestionAnswerId = ?";
			this.getSession().createQuery(queryString).setInteger(0,  qa.getVoteQuestionAnswerId()).executeUpdate();			

			queryString = "DELETE From VoteQuestionAnswer Where voteQuestionAnswerId = ?";
			this.getSession().createQuery(queryString).setInteger(0,  qa.getVoteQuestionAnswerId()).executeUpdate();	
		}
		
		queryString = "DELETE From VoteQuestion Where voteQuestionId = ?";
		this.getSession().createQuery(queryString).setInteger(0,  voteQuestionId).executeUpdate();	
	}
	
	/**
	 * 根据选项的 id 删除一个选项
	 * @param questionAnswerId
	 */
	public void deleteQuestionAnswerById(int questionAnswerId)
	{
		String queryString;
		queryString = "DELETE From VoteResult Where voteQuestionAnswerId = ?";
		this.getSession().createQuery(queryString).setInteger(0, questionAnswerId).executeUpdate();			

		queryString = "DELETE From VoteQuestionAnswer Where voteQuestionAnswerId = ?";
		this.getSession().createQuery(queryString).setInteger(0, questionAnswerId).executeUpdate();	
	}
}
