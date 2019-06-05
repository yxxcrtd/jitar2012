package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.QuestionAnswerDao;
import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.pojos.QuestionAnswer;
import cn.edustar.jitar.service.QuestionAnswerService;

public class QuestionAnswerServiceImpl implements QuestionAnswerService{
	
	private QuestionAnswerDao questionAnswerDao;
	
	/** 增加一个问题，并返回该问题的全球统一标识符 */
	public String saveOrUpdate(Question question)
	{
		return this.questionAnswerDao.saveOrUpdate(question);
	}	
	
	/** 得到前面的数几条记录 */
	public List<Question> getQuestionList(String parentObject,String sortType,int topCount)
	{
		return this.questionAnswerDao.getQuestionList(parentObject, sortType, topCount);
	}

	/** 根据问题的 id 标识得到一个问题对象 */
	public Question getQuestionById(int questionId)
	{
		return this.questionAnswerDao.getQuestionById(questionId);
	}
	
	/**
	 * 添加一个问题解答
	 */
	public void saveAnswer(QuestionAnswer questionAnswer)
	{
		this.questionAnswerDao.saveAnswer(questionAnswer);
	}
	
	/**
	 * 得到一个问题的回复列表
	 */
	public List<QuestionAnswer> getAnswerListByQuestionId(int questionId)
	{
		return this.questionAnswerDao.getAnswerListByQuestionId(questionId);
	}
	
	/**
	 * questionAnswerDao 访问器
	 * @return questionAnswerDao
	 */
	public QuestionAnswerDao getQuestionAnswerDao() {
		return questionAnswerDao;
	}

	/**
	 * 设置 questionAnswerDao
	 * @param questionAnswerDao
	 */
	public void setQuestionAnswerDao(QuestionAnswerDao questionAnswerDao) {
		this.questionAnswerDao = questionAnswerDao;
	}
	
	/**
	 * 删除自己创建的一个问题
	 * @param questionId
	 * @param createUserId
	 */
	public void deleteQuestionByQuestionIdAndCreateUserId(int questionId, int createUserId)
	{
		this.questionAnswerDao.deleteQuestionByQuestionIdAndCreateUserId(questionId, createUserId);
	}
	
	/**
	 * 删除一个问题
	 * @param questionId
	 */
	public void deleteQuestionByQuestionId(int questionId)
	{
		this.questionAnswerDao.deleteQuestionByQuestionId(questionId);
	}

}
