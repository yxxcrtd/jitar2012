package cn.edustar.jitar.service;
import java.util.Date;
import java.util.List;
import cn.edustar.jitar.pojos.ChatMessage;
public interface ChatMessageService {
	public List<ChatMessage> getAllChatMessages(int roomId);
	public List<ChatMessage> getChatMessages(int roomId,int userID);
	public List<ChatMessage> getChatMessages(int roomId,Date fromDate);
	public List<ChatMessage> getChatMessages(int roomId,int userID,Date fromDate);
	public List<ChatMessage> getTodayChatMessages(int roomId);
	public List<ChatMessage> getNewChatMessages(int roomId,int fromChatMessageId);
	public void SaveChatMessage(ChatMessage chatMessage);
	
}
