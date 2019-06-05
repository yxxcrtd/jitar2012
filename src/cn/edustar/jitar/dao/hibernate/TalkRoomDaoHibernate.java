package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.TalkRoomDao;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.pojos.ChatUser;
import cn.edustar.jitar.service.TalkRoomService;

public class TalkRoomDaoHibernate extends BaseDaoHibernate implements TalkRoomDao {

	@SuppressWarnings("unchecked")
	public List<ChatRoom> getListChatRoom(Pager pager) {
		String hql = "from ChatRoom ORDER By createDate DESC , roomId DESC";
		return this.getSession().createQuery(hql).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#saveRoom(cn.edustar.jitar.pojos.ChatRoom)
	 */
	public void saveRoom(ChatRoom room) {
		this.getSession().saveOrUpdate(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getChatRoomById(int)
	 */
	public ChatRoom getChatRoomById(int roomId) {
		return (ChatRoom) this.getSession().get(ChatRoom.class, roomId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#delRoom(cn.edustar.jitar.pojos.ChatRoom)
	 */
	public void delRoom(ChatRoom room) {
		this.getSession().delete(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getChatRoomUserMsgeDataTable(int, int, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getChatRoomUserMsgeDataTable(int groupId, int roomId, Pager pager) {
		QueryHelper query = new QueryHelper();
		query.selectClause = " SELECT " + TalkRoomService.CHATROOM_USER_MESSAGE;
		query.fromClause = "FROM ChatRoom R, ChatUser U , ChatMessage M";
		query.whereClause = " WHERE R.roomId = U.roomId " + " AND ( U.userId = M.userId)" + " AND (R.groupId = :groupId)";
		query.setInteger("groupId", groupId);
		query.orderClause = "ORDER BY M.sendDate DESC";
		return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getChatMessage(int)
	 */
	public ChatMessage getChatMessage(int ChatMessageId) {
		return (ChatMessage) this.getSession().get(ChatMessage.class, ChatMessageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#saveChatUser(cn.edustar.jitar.pojos.ChatUser
	 */
	public void saveChatUser(ChatUser user) {
		this.getSession().saveOrUpdate(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecn.edustar.jitar.dao.TalkRoomDao#saveMessage(cn.edustar.jitar.pojos.ChatMessage)
	 */
	public void saveMessage(ChatMessage message) {
		this.getSession().saveOrUpdate(message);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getChatUser(int)
	 */
	public ChatUser getChatUser(int chatUserId, int roomId) {
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT U";
		query.fromClause = " From ChatUser as U, ChatRoom as R";
		query.whereClause = " WHERE U.roomId = R.roomId" + " AND (U.userId = :chatUserId) " + " AND (R.roomId = :roomId) ";
		query.setInteger("chatUserId", chatUserId);
		query.setInteger("roomId", roomId);
		return (ChatUser) query.querySingleData(this.getSession());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#updateFontColor(java.lang.String, int, int)
	 */
	public void updateFontColor(String color, int roomId, int userId) {
		String queryString = "UPDATE ChatUser SET fontColor = ? WHERE roomId = ? AND userId = ?";
		this.getSession().createQuery(queryString).setString(0, color).setInteger(1, roomId).setInteger(2, userId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getChatMessageByDate(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> getPublicChatMessage(String lastdate, int roomId, int userId) {
		QueryHelper query = new QueryHelper();

		query.fromClause = " from ChatMessage";
		query.whereClause = " where (isPrivate=0" + " and roomId=" + roomId
				+ " and sendDate>" + lastdate + ") or (roomId=" + roomId
				+ " and sendDate>" + lastdate + ""
				+ " and isPrivate=1 and(senderId=" + userId + " or receiverId="
				+ userId + ")) " + "order by sendDate";
		return query.queryData(this.getSession());
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.TalkRoomDao#getPrivateChatMessage(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> getPrivateChatMessage(String lastdate, int roomId, int userId) {
		QueryHelper query = new QueryHelper();
		query.fromClause = " from ChatMessage";
		query.whereClause = " where roomId=" + roomId + " and (senderId = " + userId + " or receiverId=" + userId + ")" + " and sendDate>" + lastdate + " " + " order by sendDate";
		return query.queryData(this.getSession());
	}

}
