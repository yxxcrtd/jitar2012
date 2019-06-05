package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.MessageDao;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageQueryParam;

/**
 * 短消息数据库访问实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:57:54 PM
 */
public class MessageDaoHibernate extends BaseDaoHibernate implements MessageDao {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#findById(java.lang.Integer)
	 */
	public Message findById(int id) {
		String hql = "FROM Message WHERE id = :id";
		List<Message> ms = this.getSession().createQuery(hql).setInteger("id", id).list();
		if(ms==null || ms.size() == 0) return null;
		return (Message)ms.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#getMessageDataTable(cn.edustar.jitar.service.iface.MessageQueryParam, cn.edustar.jitar.util.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getMessageDataTable(MessageQueryParam param, Pager pager) {
		// 构造查询器
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT m.id, m.sendId, m.receiveId, m.title, m.content, m.sendTime, m.isRead, "
				+ "m.isDel, m.isReply, u.loginName, u.nickName, u.email, u.blogName, u.userIcon";
		query.fromClause = "FROM Message m, User u ";
		if("".equals(param.type) || param.type.length() == 0) {
			query.whereClause = "WHERE (m.receiveId = u.userId) AND ((m.sendId = " + param.sendId + ") OR (m.receiveId = " + param.receiveId + ") )";
		} else if ("all".equalsIgnoreCase(param.type)) {
			query.whereClause = "WHERE (m.receiveId = u.userId) AND ((m.sendId = " + param.sendId + ") OR (m.receiveId = " + param.receiveId + ") )";
		} else if ("receive".equalsIgnoreCase(param.type)) {
			query.whereClause = "WHERE (m.receiveId = u.userId) AND (m.receiveId = " + param.receiveId+ ") AND (m.isDel = false" +")";
		} else if ("send".equalsIgnoreCase(param.type)) {
			query.whereClause = "WHERE (m.sendId = u.userId) AND (m.sendId = " + param.sendId + ") AND (m.isSenderDel = false)";
		} else if("recycle".equalsIgnoreCase(param.type)) {
			query.whereClause = "WHERE (m.receiveId = u.userId) AND(m.receiveId = "+ param.receiveId + " ) AND (m.isDel = true" + ")";
		} else {
			query.whereClause = "WHERE (m.receiveId = u.userId) AND ((m.sendId = " + param.sendId + ") OR (m.receiveId = " + param.receiveId + ") )";
		}
		query.orderClause = "ORDER BY id DESC";
		pager.setTotalRows(query.queryTotalCount(getSession()));
		List list = query.queryData(getSession(), pager);
		String schema_str = "m.id, m.sendId, m.receiveId, m.title, m.content, m.sendTime, m.isRead, "
				+ "m.isDel, m.isReply, u.loginName, u.nickName, u.email, u.blogName, u.userIcon";
		DataTable dt = new DataTable(new DataSchema(schema_str), list);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#saveMessage(cn.edustar.jitar.pojos.Message)
	 */
	public void saveMessage(Message message) {
		this.getSession().save(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#delMessage(cn.edustar.jitar.pojos.Message)
	 */
	public void deleteMessage(Message message) {
		this.getSession().delete(message);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.MessageDao#senderDelMessage(int, boolean)
	 */
	public void senderDelMessage(int messageId, boolean senderDel) {
		String hql = "UPDATE Message SET isSenderDel = :isSenderDel WHERE id = :id" ;
		this.getSession().createQuery(hql).setBoolean("isSenderDel", senderDel).setInteger("id", messageId).executeUpdate();
		
	}

	/*
	 * 当前登录用户收到的短消息总数
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#getTotalRows(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public int getTotalMessages(int userId) {
		String hql = "SELECT COUNT(*) FROM Message msg WHERE msg.receiveId = :receiveId AND (msg.isDel = false) ";
		Object o = this.getSession().createQuery(hql).setInteger("receiveId", userId).uniqueResult();
		if(o == null) return 0;
		return Integer.valueOf(o.toString()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#getUnReadRows(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	/** 当前登录用户收到的短消息总数 */
	public int getUnreadMessages(int userId) {
		String hql = "SELECT COUNT(*) FROM Message msg " +
				"WHERE msg.receiveId = :receiveId AND (msg.isRead = false) AND (msg.isDel = false)";
		Object o = this.getSession().createQuery(hql).setInteger("receiveId", userId).uniqueResult();
		if(o == null) return 0;
		return Integer.valueOf(o.toString()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.MessageDao#getMessageList(int)
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<Object[]> getMessageList(int userId) {
		QueryHelper query = new QueryHelper();
		MessageDao messageDao = null;
		query.selectClause = "SELECT " + messageDao.GET_MESSAGE_LIST;
		query.fromClause = " FROM User U, Message M";
		query.whereClause = " WHERE((U.userId = M.receiveId) AND (M.receiveId = " + userId + "))";
		List list = query.queryData(this.getSession());
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.MessageDao#setMessageRecycleState(int, boolean)
	 */
	public void setMessageRecycleState(int messageId, boolean toRecycle) {
		String hql = " UPDATE Message " +
					 " SET isDel = :isDel "  +
					 " WHERE id = :id ";
		this.getSession().createQuery(hql).setBoolean("isDel", toRecycle).setInteger("id", messageId).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.MessageDao#setMessageReaderState(int)
	 */
	public int setMessageReaderState(int messageId, int receiveId, boolean isRead) {
		String hql = " UPDATE Message " +
					" SET isRead = :isRead " +
					" WHERE id = :id AND receiveId = :receiveId" ;
		return this.getSession().createQuery(hql).setBoolean("isRead", isRead).setInteger("id", messageId).setInteger("receiveId", receiveId).executeUpdate();
		//return getSession().bulkUpdate(hql, new Object[]{isRead, messageId, receiveId});
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.MessageDao#setMessageReplyState(int, boolean)
	 */
	public void setMessageReplyState(int messageId, boolean isReply) {
		String hql = " UPDATE Message " +
					" SET isReply = :isReply" +
					" WHERE id = :id" ;
		this.getSession().createQuery(hql).setBoolean("isReply", isReply).setInteger("id", messageId).executeUpdate();
		//this.getSession().bulkUpdate(hql, new Object[]{isReply, messageId});
	}
	
	public void deleteAllMessageByUserId(int userId)
	{
		String hql = "DELETE FROM Message msg WHERE msg.receiveId = :receiveId or msg.sendId = :sendId ";
		this.getSession().createQuery(hql).setInteger("receiveId", userId).setInteger("sendId", userId).executeUpdate();
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
		
	}

	@Override
	public void flush() {
		this.getSession().flush();
		
	}

	@Override
	public void setAllMessageRecycleState(int receiveId, boolean isDel) {
		String hql = " UPDATE Message " +
				" SET isDel = :isDel" +
				" WHERE receiveId = :receiveId" ;
		this.getSession().createQuery(hql).setBoolean("isDel", isDel).setInteger("receiveId", receiveId).executeUpdate();
	}

	@Override
	public void senderDelAllMessage(int sendId, boolean isSenderDel) {
		String hql = "UPDATE Message SET isSenderDel = :isSenderDel WHERE sendId = :sendId" ;
		this.getSession().createQuery(hql).setBoolean("isSenderDel", isSenderDel).setInteger("sendId", sendId).executeUpdate();
	}

	@Override
	public void setMessageAllRecycleState(int receiveId, boolean isDel) {
		String hql = " UPDATE Message " +
				 " SET isDel = :isDel "  +
				 " WHERE receiveId = :receiveId ";
		this.getSession().createQuery(hql).setBoolean("isDel", isDel).setInteger("receiveId", receiveId).executeUpdate();
	}

	@Override
	public void deleteAllMessage(int receiveId,Boolean isDel) {
		String hql = "DELETE Message WHERE receiveId = :receiveId AND isDel =:isDel" ;
		this.getSession().createQuery(hql).setInteger("receiveId", receiveId).setBoolean("isDel", isDel).executeUpdate();
	}
}
