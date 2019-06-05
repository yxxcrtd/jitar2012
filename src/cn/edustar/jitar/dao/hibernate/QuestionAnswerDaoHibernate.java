package cn.edustar.jitar.dao.hibernate;

import java.util.List;


import cn.edustar.jitar.dao.QuestionAnswerDao;
import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.pojos.QuestionAnswer;

public class QuestionAnswerDaoHibernate extends BaseDaoHibernate implements QuestionAnswerDao {

	/** 增加一个问题，并返回该问题的全球统一标识符 */
	public String saveOrUpdate(Question question)
	{
		this.getSession().saveOrUpdate(question);
		return question.getObjectGuid();
	}
	
	/** 得到前面的数几条记录 */
	@SuppressWarnings("unchecked")
	public List<Question> getQuestionList(String parentObject,String sortType,int topCount)
	{
		String orderBy = "";
		if(sortType.equals("new"))
		{
			orderBy = " Order By questionId DESC";
		}
		String queryString = "From Question Where parentGuid = ? " + orderBy;	
		List<Question> q_l = (List<Question>)this.getSession().createQuery(queryString).setString(0, parentObject).setFirstResult(0).setMaxResults(topCount).list();
		return q_l;
		//return (List<Question>)this.getSession().findTopCount(queryString, topCount, params).find(queryString);
	}
	
	/** 根据问题的 id 标识得到一个问题对象 */
	public Question getQuestionById(int questionId)
	{
		/*
		 * getHibernateTemplate.load() 存在延迟加载问题。
		 * getHibernateTemplate.get()  不存在此问题，她是不采用lazy机制的。
		 * 当记录不存在时候，get方法返回null,load方法产生异常，即get()可以取空的数据集,但load()不行。
		 * 
		 */
		return (Question)this.getSession().get(Question.class, questionId);
	}
	
	/**
	 * 添加一个问题解答
	 */
	public void saveAnswer(QuestionAnswer questionAnswer)
	{
		this.getSession().save(questionAnswer);
	}
	
	/**
	 * 得到一个问题的回复列表
	 */
	@SuppressWarnings("unchecked")
	public List<QuestionAnswer> getAnswerListByQuestionId(int questionId)
	{
		String queryString = "From QuestionAnswer Where questionId=? Order By answerId ASC";
		return (List<QuestionAnswer>)this.getSession().createQuery(queryString).setInteger(0, questionId).list();
	}
	
	
	/**
	 * 删除自己创建的一个问题
	 * @param questionId
	 * @param createUserId
	 */
	public void deleteQuestionByQuestionIdAndCreateUserId(int questionId, int createUserId)
	{
		Question question = this.getQuestionById(questionId);
		if(question == null) return;
		if(question.getCreateUserId() != createUserId) return;
		String queryString = "DELETE FROM QuestionAnswer Where questionId = ?";
		this.getSession().createQuery(queryString).setInteger(0, question.getQuestionId()).executeUpdate();
		this.deleteQuestionByQuestionId(question.getQuestionId());
	}
	
	/**
	 * 删除一个问题
	 * @param questionId
	 */
	public void deleteQuestionByQuestionId(int questionId)
	{
		Question question = this.getQuestionById(questionId);
		if(question == null) return;
		String queryString = "DELETE FROM QuestionAnswer Where questionId = ?";
		this.getSession().createQuery(queryString).setInteger(0, question.getQuestionId()).executeUpdate();
		this.getSession().delete(question);
	}
}
