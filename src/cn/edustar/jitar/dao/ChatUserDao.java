package cn.edustar.jitar.dao;
import java.util.List;

import cn.edustar.jitar.pojos.ChatUser;

/**
 * 聊天室用户的数据库访问接口定义.
 * 
 * @author baimindong
 */
public interface ChatUserDao {
	/**
	 * 得到指定标识的聊天室用户.
	 * 
	 * @param id
	 * @return
	 */
	public ChatUser getChatUser(int id);
	
	/**
	 * 得到聊天室用户.
	 * @param roomId
	 * @param userId
	 * @return
	 */
	public ChatUser getChatUser(int roomId,int userId);


	/**
	 * 得到聊天室全部用户
	 * @param roomId
	 * @return
	 */
	public List<ChatUser> getChatUsers(int roomId);
	
	/**
	 * 允许发言.
	 *
	 * @param roomId
	 * @param userId
	 * @param isSay
	 */
	public int updateChatUserIsSay(int roomId,int userId, boolean isSay);

	/**
	 * 设置离开.
	 *
	 * @param roomId
	 * @param userId
	 * @param isLeave
	 */
	public int updateChatUserIsLeave(int roomId,int userId, boolean isLeave);

	/**
	 * 设置当前时间.
	 *
	 * @param roomId
	 * @param userId
	 */
	public void updateChatUserCurrentDate(int roomId,int userId);
	
	/**
	 * 保存一个聊天用户.
	 * 
	 * @param comment
	 */
	public void saveChatUser(ChatUser chatUser);

	public void saveChatUserFontColor(int roomId,int userId,String fontColor);
	
}
