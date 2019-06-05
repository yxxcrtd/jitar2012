package cn.edustar.jitar.dao;

import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Board;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsReplyQueryParam;
import cn.edustar.jitar.service.BbsTopicQueryParam;

/**
 * 用于论坛数据访问接口定义.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public interface BbsDao {
	/**
	 * 通过版面的Id得到版面.
	 * @param boardId
	 * @return
	 */
	public Board getBoardById(int boardId);
	
	/**
	 * 通过群组论坛的名字得到群组论坛版面.
	 * @param name - 群组论坛的名字.
	 * @return
	 */
	public Board getBoardByName(String name);
	
	/**
	 * 得到指定数量的主题列表.
	 * @param boardId
	 * @param count
	 * @return
	 */
	public List<Object[]> getRecentTopicList(int groupId, int count);
	
	/**
	 * 得到指定版面下的主题. 
	 * @param boardId - 版面标识.
	 * @param - page 分页.
	 * @return 字段包括 GET_TOPIC_LIST 所列字段.
	 */
	public List<Object[]> getTopicList(int groupId, Pager page);
	
	
	/***
	 * 得到置顶主题列表.
	 * @param boardId
	 * @return
	 */
	public List<Object[]> getTopTopicList(int groupId);
	
	
	/**
	 *  得到指定数量的主题回复列表.
	 *  @param - topic
	 *  @param - pager 
	 * 
	 */
	public List<Object[]> getReplyList(int TopicId, Pager pager);
	
	/**
	 * 得到指定的主题. 
	 * @param
	 * @return
	 */
	public Topic getTopicById(int topicId);
	
	/**
	 * 创建一个主题.
	 * @param
	 */
	public void createTopic(Topic topic);
	
	/**
	 * 更新一个主题.
	 * @param topic
	 */
	public void updateTopic(Topic topic);
	
	/**
	 * 刪除一个主題.
	 * @param
	 * @return
	 */
	public void deleteTopic(Topic topic);
	
	/**
	 * 删除一个回复.
	 * @param reply
	 */
	public void deleteReply(Reply reply);
	
	/**
	 * 得到所有的版面.
	 * @param
	 * @return
	 */
	public List<Board> getAllBoards();
	
	/**
	 * 得到一个回复.
	 * @param
	 * @return 
	 * 
	 */
	public Reply getReplyById(int replyId);
	
	/***
	 * 新建一个回复.
	 * @param reply
	 * @return
	 */
	public void createReply(Reply reply);
	
	/**
	 * 更新一个回复.
	 * @param reply
	 */
	public void updateReply(Reply reply); 

	/**
	 * 得到指定标识的主题和User.
	 * @param topicId
	 */
	@SuppressWarnings("unchecked")
	public Map getTopicAndUser(int topicId);
	
	/**
	 * 设置精华主题状态.
	 * @param topic
	 */
	public int updateGroupTopicState(int topicId, boolean best);
	
	/**
	 * 设置精华贴状态.
	 * @param topic
	 */
	public int updateGroupReplyState(int replyId, boolean best);
	
	/***
	 * 设置帖子置顶状态. 
	 * @param topicId
	 * @param top
	 */
	public void updateTopicTopState(int topicId, boolean top);
	
	/***
	 *  移除对主题的引用.
	 * @param topicId
	 * @param ref
	 */
	public void deleteTopicRef(int topicId, boolean unref);
	
	/***
	 * 移除对回复的引用.
	 * @param replyId
	 * @param unref
	 */
	public void deleteReplyRef(int replyId, boolean unref);

	/**
	 * 得到指定条件下的主题列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Topic> getTopicList(BbsTopicQueryParam param, Pager pager);

	/**
	 * 查询指定条件下的回复列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Reply> getReplyList(BbsReplyQueryParam param, Pager pager);

	/**
	 * 得到主页上使用的最新主题列表, 其中包括 user, group 的相关信息.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Object[]> getTopicDataTable(BbsTopicQueryParam param, Pager pager);

	/**
	 * 得到主页上使用的最新回复列表, 其中包括 user, group 的相关信息.
	 * @param param
	 * @param pager
	 * @return 字段参见 GET_REPLY_DATATABLE, 其可以在 param 中指定.
	 */
	public List<Object[]> getReplyDataTable(BbsReplyQueryParam param, Pager pager);

	/**
	 * 增加或减少指定协作组主题的访问次数.
	 * @param topic
	 * @param count
	 */
	public void incTopicViewCount(Topic topic, int count);
}
