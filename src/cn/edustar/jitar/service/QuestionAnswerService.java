package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.pojos.QuestionAnswer;

public interface QuestionAnswerService {

	/** 增加一个问题，并返回该问题的全球统一标识符 */
	public String saveOrUpdate(Question question);
	
	/** 得到前面的数几条记录 */
	public List<Question> getQuestionList(String parentObject,String sortType,int topCount);

	/** 根据问题的 id 标识得到一个问题对象 */
	public Question getQuestionById(int questionId);
	
	/**
	 * 添加一个问题解答
	 */
	public void saveAnswer(QuestionAnswer questionAnswer);
	
	/**
	 * 得到一个问题的回复列表
	 */
	public List<QuestionAnswer> getAnswerListByQuestionId(int questionId);
	
	/**
	 * 删除自己创建的一个问题
	 * @param questionId
	 * @param createUserId
	 */
	public void deleteQuestionByQuestionIdAndCreateUserId(int questionId, int createUserId);
	
	/**
	 * 删除一个问题
	 * @param questionId
	 */
	public void deleteQuestionByQuestionId(int questionId);
}
