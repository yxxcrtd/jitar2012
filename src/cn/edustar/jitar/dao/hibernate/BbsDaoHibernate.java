package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.BbsDao;
import cn.edustar.jitar.pojos.Board;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsReplyQueryParam;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.BbsTopicQueryParam;

/**
 * 论坛的数据库访问实现.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class BbsDaoHibernate extends BaseDaoHibernate implements BbsDao {
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getBoardById(int)
	 */
	public Board getBoardById(int boardId) {
		return (Board)this.getSession().get(Board.class, boardId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopicById(int)
	 */
	public Topic getTopicById(int topicId) {
		return (Topic)this.getSession().get(Topic.class, topicId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getBoardByName(java.lang.String)
	 */
	public Board getBoardByName(String name) {
		String hql = "FROM Board WHERE name = :name";
		List ol = this.getSession().createQuery(hql).setString("name", name).list();
		if(ol == null || ol.size() == 0) return null;
		return (Board)ol.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getRecentTopicList(int groupId, int count) {
		
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + BbsService.GET_TOPIC_LIST;
		query.fromClause = " FROM Topic T,User U";
		query.whereClause = "WHERE T.userId = U.userId " +
						"AND (T.groupId = :groupId)";
		query.setInteger("groupId", groupId);
		query.orderClause = "ORDER BY T.createDate DESC";
		
		
		return query.queryData(this.getSession(), -1, count);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopicList(int, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopicList(int groupId, Pager pager) {
		QueryHelper query = new QueryHelper();	
		query.selectClause = "SELECT " + BbsService.GET_TOPIC_LIST ;
		query.fromClause = "FROM Topic T, User U";
		query.whereClause = "WHERE T.userId = U.userId " +
			" AND (T.groupId = " + groupId + ")" +
			" AND (T.isTop = false)";
		query.orderClause = "ORDER BY T.createDate DESC";
		
		return query.queryDataAndTotalCount(this.getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getReplyList(int, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getReplyList(int topicId, Pager pager) {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT " + BbsService.GET_REPLY_LIST;
		query.fromClause = " FROM Reply R,User U ";
		query.whereClause = " WHERE R.userId = U.userId" +
					" AND (R.topicId = :topicId)";
		query.orderClause = " ORDER BY R.id DESC ";
		query.setInteger("topicId", topicId);
		
		return query.queryDataAndTotalCount(this.getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopicUser(int)
	 */
	@SuppressWarnings("unchecked")
	public Map getTopicAndUser(int topicId) {
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT " + BbsService.GET_TOPIC_LIST_MAP;
		query.fromClause = "FROM Topic T, User U";
		query.whereClause = "WHERE T.userId = U.userId " +
							"AND (T.topicId = :topicId)";
		query.setInteger("topicId", topicId); 
		List result = query.queryData(this.getSession());
		if (result == null || result.size() == 0) return null;
		
		return (Map)result.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopTopicList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopTopicList(int groupId) {
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT " + BbsService.GET_TOPIC_LIST;
		query.fromClause =   " FROM Topic T, User U ";
		query.whereClause = "WHERE T.userId = U.userId " +
					" AND (T.groupId = " + groupId + ")" +
					" AND T.isTop = true" ;
		query.orderClause = "ORDER BY T.createDate DESC";
		return query.queryData(this.getSession());
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#addTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void createTopic(Topic topic) {
		if (topic == null) throw new IllegalArgumentException("topic == null");
		
		// 保存主题.
		this.getSession().saveOrUpdate(topic);
		this.getSession().flush();
		
		// 更新统计数据.
		incTopicStat(topic, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#updateTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void updateTopic(Topic topic) {
		if (topic == null) throw new IllegalArgumentException("topic == null");
		
		// 保存主题.
		this.getSession().saveOrUpdate(topic);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#deleteTopic(int)
	 */
	public void deleteTopic(Topic topic) {
		// 删除该主题的所有回复.
		String hql = "DELETE FROM Reply WHERE topicId = :topicId";
		this.getSession().createQuery(hql).setInteger("topicId", topic.getTopicId()).executeUpdate();
		
		// 删除主题本身.
		this.getSession().delete(topic);
		this.getSession().flush();
		
		// 更新统计数据.
		incTopicStat(topic, -1);
	}

	private void incTopicStat(Topic topic, int count) {
		// 更新协作组主题数量.
		String hql = "UPDATE Group SET topicCount = topicCount + ? WHERE groupId = ?";
		this.getSession().createQuery(hql).setInteger(0,count).setInteger(1, topic.getGroupId()).executeUpdate();
		
		// 更新用户发表的主题数量.
		hql = "UPDATE User SET topicCount = topicCount + ? WHERE userId = ?";
		this.getSession().createQuery(hql).setInteger(0,count).setInteger(1, topic.getUserId()).executeUpdate();
		
		// 更新该用户在该协作组的主题数量.
		hql = "UPDATE GroupMember SET topicCount = topicCount + ? WHERE groupId = ? AND userId = ?";
		this.getSession().createQuery(hql).setInteger(0,count).setInteger(1, topic.getGroupId()).setInteger(2, topic.getUserId()).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Board> getAllBoards() {
		String hql = "FROM Board";
		return this.getSession().createQuery(hql).list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getReplyById(int)
	 */
	public Reply getReplyById(int replyId) {
		return (Reply)this.getSession().get(Reply.class, replyId);
		
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#addReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void createReply(Reply reply) {
		// 创建回复.
		this.getSession().save(reply);
		this.getSession().flush();
		
		// 修改统计计数.
		incReplyStat(reply, 1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#updateReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void updateReply(Reply reply) {
		// 修改回复.
		this.getSession().update(reply);
		this.getSession().flush();
	}
	
	/**
	 * 增加/减少关于 reply 的统计信息.
	 * @param reply
	 * @param count
	 */
	private void incReplyStat(Reply reply, int count) {
		// 更新在主题上的统计.
		String hql = "UPDATE Topic SET replyCount = replyCount + ? WHERE topicId = ?";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, reply.getTopicId()).executeUpdate();
		
		// 更新在用户协作组上的统计.
		hql = "UPDATE GroupMember SET replyCount = replyCount + ? WHERE groupId = ? AND userId = ? ";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, reply.getGroupId()).setInteger(2, reply.getUserId()).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#deleteReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void deleteReply(Reply reply) {
		this.getSession().delete(reply);
		this.getSession().flush();

		incReplyStat(reply, -1);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#bestGroupTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public int updateGroupTopicState(int topicId, boolean best) {
		String hql = " UPDATE Topic " +
					 " SET isBest = ? " +
					 " WHERE topicId = ? ";
		return this.getSession().createQuery(hql).setBoolean(0, best).setInteger(1, topicId).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#updateGroupReplyState(int, boolean)
	 */
	public int updateGroupReplyState(int replyId, boolean best) {
		String hql = " UPDATE Reply " +
					 " SET isBest = ?" +
					 " WHERE id = ? ";
		return this.getSession().createQuery(hql).setBoolean(0, best).setInteger(1, replyId).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#updateTopicTopState(int, boolean)
	 */
	public void updateTopicTopState(int topicId, boolean top) {
		String hql = "UPDATE Topic " +
					 " SET isTop = ? " +
					 " WHERE id = ? ";
		this.getSession().createQuery(hql).setBoolean(0, top).setInteger(1, topicId).executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#deleteTopicRef(int, boolean)
	 */
	public void deleteTopicRef(int topicId, boolean unref) {
		
		String hql = "UPDATE Topic " +
					 " SET  isDeleted = ?"  +
					 " WHERE id = ? ";
		
		this.getSession().createQuery(hql).setBoolean(0, unref).setInteger(1, topicId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#deleteReplyRef(int, boolean)
	 */
	public void deleteReplyRef(int replyId, boolean unref) {
		String hql = "UPDATE Reply " +
					 "SET isDeleted = ? " +
					 "WHERE id = ? ";
		this.getSession().createQuery(hql).setBoolean(0, unref).setInteger(1, replyId).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getBoardTopicCount(int)
	 */
	public int getBoardTopicCount(int boardId) {
		String hql = "SELECT COUNT(*) FROM Topic WHERE boardId = :boardId";
		Object o =  this.getSession().createQuery(hql).setInteger("boardId", boardId).uniqueResult();
		if(o == null) return 0;
		return Integer.valueOf(o.toString()).intValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopicList(cn.edustar.jitar.service.BbsTopicQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> getTopicList(BbsTopicQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getReplyList(cn.edustar.jitar.service.BbsReplyQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Reply> getReplyList(BbsReplyQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getTopicDataTable(cn.edustar.jitar.service.BbsTopicQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopicDataTable(BbsTopicQueryParam param, Pager pager) {
		// 得到查询条件.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT " + param.selectFields;
		query.fromClause = "FROM Topic T, User U, Group G ";
		query.addAndWhere("T.groupId = G.groupId");
		query.addAndWhere("T.userId = U.userId");
		
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#getReplyDataTable(cn.edustar.jitar.service.BbsReplyQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getReplyDataTable(BbsReplyQueryParam param, Pager pager) {
		// 得到查询条件.
		QueryHelper query = param.createQuery();
		query.selectClause = "SELECT " + param.selectFields;
		query.fromClause = "FROM Reply R, User U, Group G ";
		query.addAndWhere("R.groupId = G.groupId");
		query.addAndWhere("R.userId = U.userId");
		
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.BbsDao#incTopicViewCount(cn.edustar.jitar.pojos.Topic, int)
	 */
	public void incTopicViewCount(Topic topic, int count) {
		if (topic == null || count == 0) return;
		String hql = "UPDATE Topic SET viewCount = viewCount + ? WHERE topicId = ?";
		this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, topic.getTopicId()).executeUpdate();
	}
}
