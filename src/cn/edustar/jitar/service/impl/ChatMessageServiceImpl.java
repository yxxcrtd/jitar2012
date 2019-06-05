package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.dao.ChatMessageDao;
import cn.edustar.jitar.service.ChatMessageService;

public class ChatMessageServiceImpl  implements ChatMessageService{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ChatMessageServiceImpl.class);
	/** 数据访问对象 */
	private ChatMessageDao chatMessageDao;
	
	/** 数据访问对象. */
	public void setChatMessageDao(ChatMessageDao chatMessageDao) {
		this.chatMessageDao = chatMessageDao;
	}
	
	public ChatMessageServiceImpl()
	{
		
	}
	public List<ChatMessage> getAllChatMessages(int roomId)
	{
		return this.chatMessageDao.getAllChatMessages(roomId);
	}
	public List<ChatMessage> getChatMessages(int roomId,int userID)
	{
		return this.chatMessageDao.getChatMessages(roomId,userID);
	}
	public List<ChatMessage> getChatMessages(int roomId,Date fromDate)
	{
		return this.chatMessageDao.getChatMessages(roomId,fromDate);
	}
	public List<ChatMessage> getChatMessages(int roomId,int userID,Date fromDate)
	{
		return this.chatMessageDao.getChatMessages(roomId,userID, fromDate);
	}
	public List<ChatMessage> getTodayChatMessages(int roomId)
	{
		return this.chatMessageDao.getTodayChatMessages(roomId);
	}
	
	public List<ChatMessage> getNewChatMessages(int roomId,int fromChatMessageId)
	{
		return this.chatMessageDao.getNewChatMessages(roomId,fromChatMessageId);
	}
	public void SaveChatMessage(ChatMessage chatMessage)
	{
		this.chatMessageDao.SaveChatMessage(chatMessage);
	}
}
