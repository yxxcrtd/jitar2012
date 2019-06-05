package cn.edustar.jitar.service;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;

/**
 * 论坛服务接口定义.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public interface BbsService {
	/** 投影查询字段 */
	public static final String GET_TOPIC_LIST = 
		"U.loginName as loginName, U.nickName as nickName, U.userIcon as userIcon, U.userId as userId,"
		+ "T.topicId as topicId, T.title as title, T.content as content, T.createDate as createDate, T.isBest as isBest, T.isTop as isTop, T.isDeleted as isDeleted,"
		+ "T.replyCount as replyCount, T.viewCount as viewCount, T.tags as tags, T.groupId as groupId";
	
	/** 投影查询字段 AS MAP */
	public static final String GET_TOPIC_LIST_MAP = 
		"new Map(U.loginName as loginName, U.nickName as nickName, U.userIcon as userIcon, U.userId as userId,"
		+ "T.topicId as topicId, T.title as title, T.content as content, T.createDate as createDate, T.isBest as isBest, T.isTop as isTop, T.isDeleted as isDeleted,"
		+ "T.replyCount as replyCount, T.viewCount as viewCount, T.tags as tags, T.groupId as groupId)";
	
	/** 投影查询字段 */
	public static final String GET_REPLY_LIST = 
		"U.loginName, U.nickName, U.userIcon,U.userId,"
		+ "R.replyId, R.topicId ,R.groupId, R.title, R.content, R.targetReply, R.createDate, R.isBest, R.isDeleted";
	
	/**
	 * 通过Id得到主题.
	 * @param
	 * @return
	 */
	public Topic getTopicById(int topicId);

	/**
	 * 得到指定参数的主题列表.
	 * @param - boarId 主题标识.
	 * @param - page 分页.
	 * @return 字段包括 BbsDao.GET_TOPIC_LIST 所列字段. 
	 */
	public DataTable getTopicDataTable(int boardId, Pager pager);
	
	/**
	 * 得到指定数量的主题列表.
	 * @param boardId
	 * @param count
	 * @return
	 */
	public DataTable getRecentTopicDataTable(int groupId, int count);
	
	/**
	 * 得到置顶主题列表.
	 * @param boardId
	 * @return
	 */
	public DataTable getTopTopicList(int groupId);
	
	/**
	 * 得到指定数量的主题回复列表. 
	 * @param - topicId
	 * @param - count
	 * 
	 */
	public DataTable getReplyDataTable(int topicId, Pager pager);
	
	/**
	 * 新建一个主题.
	 * @param
	 * @return
	 */
	public void createTopic(Topic topic);
	
	/**
	 * 修改一个主题.
	 * @param topic
	 */
	public void updateTopic(Topic topic);
	
	/**
	 * 刪除一個主題.
	 * @param TopicId
	 */
	public void deleteTopic(Topic topic);
	
	/**
	 * 删除一个回复.
	 * @param reply
	 */
	public void deleteReply(Reply reply);
	
	/**
	 * 得到一个回复.
	 * @param
	 * @return
	 * 
	 */
	public Reply getReplyById(int replyId);
	
	/**
	 * 新建一个回复.
	 * @param
	 * @return 
	 */
	public void createReply(Reply reply);
	
	/**
	 * 更新一个回复.
	 * @param reply
	 */
	public void updateReply(Reply reply); 
	
	/**
	 * 得到指定标识的主题和发表该主题的用户信息.
	 * @param topicId
	 */
	@SuppressWarnings("unchecked")
	public Map getTopicAndUser(int topicId);
	
	/**
	 * 设置精华主题.
	 * @param topic
	 */
	public void bestGroupTopic(Topic topic);
	
	/**
	 * 取消精华主题.
	 * @param topic
	 */
	public void unbestGroupTopic(Topic topic);
	
	/**
	 * 设置群组精华回复贴.
	 * @param reply
	 */
	public void bestGroupReply(Reply reply);

	/**
	 * 取消精华回复贴.
	 * @param reply
	 */
	public void unbestGroupReply(Reply reply);

	/**
	 * 将帖子置顶.
	 * @param topic
	 */
	public void topTopic(Topic topic);
	
	/**
	 * 取消置顶.
	 * @param topic
	 */
	public void untopTopic(Topic topic);
	
	/**
	 * 移除对主题的引用.
	 * @param topic
	 */
	public void deleteTopicRef(Topic topic);
	
	/**
	 * 恢复对主题的引用.
	 * @param topic
	 */
	public void topicRef(Topic topic);
	
	/**
	 * 移除对回复的引用.
	 * @param reply
	 */
	public void deleteReplyRef(Reply reply);
	
	/**
	 * 恢复对回复的引用.
	 * @param reply
	 */
	public void ReplyRef(Reply reply);
	

	/**
	 * 查询指定条件下的主题列表.
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

	/** getTopicDataTable 方法缺省获得的字段列表 */
	public static final String GET_TOPIC_DATATABLE = 
		"U.loginName, U.nickName, U.userIcon, U.userId"
		+ ", T.topicId, T.title, T.createDate, T.isBest, T.isTop"
		+ ", T.replyCount, T.viewCount, T.groupId"
		+ ", G.groupTitle, G.groupName";

	
	/**
	 * 得到主页上使用的最新主题列表, 其中包括 user, group 的相关信息.
	 * @param param
	 * @param pager
	 * @return 字段参见 GET_TOPIC_DATATABLE, 其可以在 param 中指定.
	 */
	public DataTable getTopicDataTable(BbsTopicQueryParam param, Pager pager);

	/** getReplyDataTable 方法缺省获得的投影查询字段 */
	public static final String GET_REPLY_DATATABLE = 
		"U.loginName, U.nickName, U.userIcon, U.userId, "
		+ "R.replyId, R.topicId, R.groupId, R.title, R.createDate, R.isBest, "
		+ "G.groupTitle, G.groupName";
	
	/**
	 * 得到主页上使用的最新回复列表, 其中包括 user, group 的相关信息.
	 * @param param
	 * @param pager
	 * @return 字段参见 GET_REPLY_DATATABLE, 其可以在 param 中指定.
	 */
	public DataTable getReplyDataTable(BbsReplyQueryParam param, Pager pager);

	/**
	 * 增加或减少指定协作组主题的访问次数.
	 * @param topic
	 * @param count
	 */
	public void incTopicViewCount(Topic topic, int count);
}

