package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.TalkRoomDao;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.pojos.ChatUser;
import cn.edustar.jitar.service.TalkRoomService;

public class TalkRoomServiceImpl implements TalkRoomService {
	
	/** 讨论室对象*/
	private TalkRoomDao talkdao;
	
	/** 论坛对象的set方法 */
	public void setTalkRoomDao(TalkRoomDao talkdao) {
		this.talkdao = talkdao;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getChatRoomList(cn.edustar.data.Pager)
	 */
	public List<ChatRoom> getChatRoomList(Pager pager) {
		return talkdao.getListChatRoom(pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#saveRoom(cn.edustar.jitar.pojos.ChatRoom)
	 */
	public void saveRoom(ChatRoom room) {
		talkdao.saveRoom(room);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getChatRoomById(int)
	 */
	public ChatRoom getChatRoomById(int roomId) {
		return talkdao.getChatRoomById(roomId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#delRoom(cn.edustar.jitar.pojos.ChatRoom)
	 */
	public void delRoom(ChatRoom room) {
		talkdao.delRoom(room);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getChatRoomUserMsgeDataTable(int, int, cn.edustar.data.Pager)
	 */
	public DataTable getChatRoomUserMsgeDataTable(int groupId, int roomId, Pager pager) {
		List<Object[]> list = talkdao.getChatRoomUserMsgeDataTable(groupId, roomId, pager);
		DataTable datatable = new DataTable(new DataSchema(TalkRoomService.CHATROOM_USER_MESSAGE), list);
		return datatable;
	}


	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getChatMessage(cn.edustar.jitar.pojos.ChatMessage)
	 */
	public ChatMessage getChatMessage(int ChatMessageId) {
		return talkdao.getChatMessage(ChatMessageId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#saveChatUser(cn.edustar.jitar.pojos.ChatUser)
	 */
	public void saveChatUser(ChatUser user) {
		talkdao.saveChatUser(user);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#saveMessage(cn.edustar.jitar.pojos.ChatMessage)
	 */
	public void saveMessage(ChatMessage message) {
		talkdao.saveMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getChatUser(int)
	 */
	public ChatUser getChatUser(int chatUserId, int roomId) {
		return talkdao.getChatUser(chatUserId, roomId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#updateFontColor(java.lang.String, int, int)
	 */
	public void updateFontColor(String color, int roomId, int userId) {
		talkdao.updateFontColor(color, roomId, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getPublicChatMessage(java.lang.String, int, int)
	 */
	public List<ChatMessage> getPublicChatMessage(String lastdate, int roomId, int userId) {
		return talkdao.getPublicChatMessage(lastdate, roomId, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.TalkRoomService#getPrivateChatMessage(java.lang.String, int, int)
	 */
	public List<ChatMessage> getPrivateChatMessage(String lastdate, int roomId, int userId) {
		return talkdao.getPrivateChatMessage(lastdate, roomId, userId);
	}
	
	
}
