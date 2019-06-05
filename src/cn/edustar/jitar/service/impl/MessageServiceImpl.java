package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.MessageDao;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageQueryParam;
import cn.edustar.jitar.service.MessageService;

/**
 * 短信息服务
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:37:54 PM
 */
public class MessageServiceImpl implements MessageService {

	/** 消息数据库服务对象 */
	private MessageDao messageDao;

	/** 消息数据库服务对象 */
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.FriendService#findById(java.lang.Integer)
	 */
	public Message findById(int id) {
		return messageDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.FriendService#getFriendDataTable(cn.edustar.jitar.service.iface.FriendQueryParam, cn.edustar.jitar.util.Pager)
	 */
	public DataTable getMessageDataTable(MessageQueryParam param, Pager pager) {
		return messageDao.getMessageDataTable(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.MessageService#saveMessage(cn.edustar.jitar.pojos.Message)
	 */
	public void sendMessage(Message message) {
		messageDao.saveMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.MessageService#delMessage(java.lang.Integer)
	 */
	public void crashMessage(Message message) {
		messageDao.deleteMessage(message);
	} 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.MessageService#senderDelMessage(int)
	 */
	public void senderDelMessage(Message message) {
		messageDao.senderDelMessage(message.getId(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.MessageService#getTotalRows(java.lang.Integer)
	 */
	public int getTotalMessages(int id) {
		return messageDao.getTotalMessages(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.MessageService#getUnReadRows(java.lang.Integer)
	 */
	public int getUnreadMessages(int id) {
		return messageDao.getUnreadMessages(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.MessageService#getMessageList(int)
	 */
	@SuppressWarnings("static-access")
	public DataTable getMessageList(int userId) {
		List<Object[]> list = messageDao.getMessageList(userId);
		DataTable dt = new DataTable(new DataSchema(messageDao.GET_MESSAGE_LIST), list);
		return dt;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.MessageService#moveMessageToRecycle(cn.edustar.jitar.pojos.Message)
	 *
	 */
	public void moveMessageToRecycle(Message message) {
		messageDao.setMessageRecycleState(message.getId(), true);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.MessageService#unMoveMessageToRecycle(cn.edustar.jitar.pojos.Message)
	 */
	public void unMoveMessageToRecycle(int messageId) {
		messageDao.setMessageRecycleState(messageId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.MessageService#setMessageIsRead(int, int)
	 */
	public void setMessageIsRead(int messageId, int receiveId) {
		messageDao.setMessageReaderState(messageId, receiveId, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.MessageService#updateReplyStatus(int)
	 */
	public void updateReplyStatus(int messageId) {
		messageDao.setMessageReplyState(messageId, true);
	}
	
	public void deleteAllMessageByUserId(int userId)
	{
		this.messageDao.deleteAllMessageByUserId(userId);
	}

	@Override
	public void moveAllMessageToRecycle(int receiveId) {
		messageDao.setAllMessageRecycleState(receiveId, true);
	}

	@Override
	public void senderDelAllMessage(int sendId) {
		messageDao.senderDelAllMessage(sendId, true);
	}

	@Override
	public void unMoveMessageToAllRecycle(int receiveId) {
		messageDao.setMessageAllRecycleState(receiveId, false);
	}

	@Override
	public void crashAllMessage(int receiveId) {
		messageDao.deleteAllMessage(receiveId,true);
	}
}
