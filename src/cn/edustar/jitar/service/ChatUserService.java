package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.ChatUser;

@SuppressWarnings("unchecked")
public interface ChatUserService {
	public ChatUser getChatUser(int id);
	public ChatUser getChatUser(int roomId,int userId);
	public ChatUser getCacheChatUser(int roomId,int userId);
	public List<ChatUser> getChatUsers(int roomId);
	public List<ChatUser> getCacheChatUsers(int roomId);
	public int updateChatUserIsSay(int roomId,int userId, boolean isSay);
	public int updateChatUserIsLeave(int roomId,int userId, boolean isLeave);
	public void updateChatUserCurrentDate(int roomId,int userId);
	public void saveChatUser(ChatUser chatUser);
	public void saveChatUserFontColor(int roomId,int userId,String fontColor);
	public List getFaceList();
}
