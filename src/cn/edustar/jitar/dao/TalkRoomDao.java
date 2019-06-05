package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.pojos.ChatUser;

/**
 * @author Yang Xinxin
 */
public interface TalkRoomDao {
	
	/***
	 * 得到讨论室列表
	 * @param pager
	 * @return
	 */
	public List<ChatRoom> getListChatRoom(Pager pager);
	
	/**
	 * 保存一个讨论室
	 * @param room
	 */
	 
	public void saveRoom(ChatRoom room);
	
	/**
	 * 根据标识得到讨论室
	 * @param roomId
	 * @return
	 */
	public ChatRoom getChatRoomById(int roomId);
	
	
	/***
	 * 删除讨论室
	 * @param room
	 */
	public void delRoom(ChatRoom room);
	
	/***
	 * 得到讨论室的所有信息, 包括ChatRoom, ChatUser, ChatMessage
	 * @param groupId
	 * @param pager
	 * @return
	 */
	public List<Object[]> getChatRoomUserMsgeDataTable(int groupId , int roomId, Pager pager);
	
	
	/***
	 * 保存聊天信息
	 */
	public void saveMessage(ChatMessage message);
	
	/**
	 * 得到一个聊天信息
	 * @param message
	 */
	public ChatMessage getChatMessage(int ChatMessageId);
	
	/**
	 * 保存一个ChatUser对象
	 * @param user
	 */
	public void saveChatUser(ChatUser user);
	
	/***
	 * 得到一个ChatUser对象
	 * @param chatUserId
	 * @return
	 */
	public ChatUser getChatUser(int chatUserId, int roomId);
	
	/***
	 * 设置某个聊天者的字体颜色
	 * @param color
	 * @param roomId
	 * @param userId
	 */
	public void updateFontColor(String color, int roomId, int userId);
	
	/***
	 * 查询指定时间段内的公共聊天内容
	 * @param lastdate
	 * @param roomId
	 * @param userId
	 * @return
	 */
	public List<ChatMessage> getPublicChatMessage(String lastdate, int roomId, int userId);
	
	
	/***
	 * 得到指定时间段内的私有聊天信息
	 * @param lastdate
	 * @param roomId
	 * @param userId
	 * @return
	 */
	public List<ChatMessage> getPrivateChatMessage(String lastdate, int roomId, int userId);
} 
