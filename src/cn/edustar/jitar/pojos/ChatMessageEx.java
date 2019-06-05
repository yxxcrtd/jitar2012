package cn.edustar.jitar.pojos;

import java.io.Serializable;
import cn.edustar.jitar.pojos.ChatMessage;
/***
 * 聊天内容扩展
 * @author baimindong
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public class ChatMessageEx implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3755499382468456377L;
	private ChatMessage chatmsg;
	@SuppressWarnings("unused")
	private boolean saveed;
	
	public void setChatMessage(ChatMessage chatmsg)
	{
		this.chatmsg=chatmsg;
	}
	public ChatMessage getChatMessage()
	{
		return this.chatmsg;
	}
}
