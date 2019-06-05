package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.jitar.dao.ChatUserDao;
import cn.edustar.jitar.pojos.ChatUser;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.service.ChatMessageService;

/**
 * 聊天用户访问实现
 * 
 * @author baimindong
 */
public class ChatUserDaoHibernate extends BaseDaoHibernate implements ChatUserDao {

	/** 日志 */
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ChatUserDaoHibernate.class);

	
	private ChatMessageService chatMessageService;
	public void setChatMessageService(ChatMessageService chatMessageService) {
		this.chatMessageService=chatMessageService;	
	}
	
	@SuppressWarnings("unchecked")
	public ChatUser getChatUser(int Id) {
		String hql = "FROM ChatUser WHERE id = ?";
		List list = this.getSession().createQuery(hql).setInteger(0,Id).list();
		if (list == null || list.size() == 0)
			return null;
		return (ChatUser) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public ChatUser getChatUser(int roomId,int userId) {
		String hql ;
		//hql = "Update ChatUser Set isLeave = 0,isActived=1 Where roomId = ? and userId =?";
		//getSession().bulkUpdate(hql, new Object[] {roomId, userId });
		hql = "FROM ChatUser WHERE roomId = ? and userId=?";
		List list = this.getSession().createQuery(hql).setInteger(0,roomId).setInteger(1, userId).list(); 
		if (list == null || list.size() == 0)
			return null;
		return (ChatUser) list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ChatUser> getChatUsers(int roomId){
		String hql = "FROM ChatUser WHERE roomId = :roomId ORDER BY sayDate desc ";
		List list = this.getSession().createQuery(hql).setInteger("roomId",roomId).list(); 
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	public int updateChatUserIsSay(int roomId,int userId, boolean isSay) {
		String hql = "Update ChatUser Set isSay = ? Where roomId = ? and userId =?";
		return this.getSession().createQuery(hql).setBoolean(0, isSay).setInteger(1, roomId).setInteger(2,userId).executeUpdate();
	}

	public int updateChatUserIsLeave(int roomId,int userId, boolean isLeave) {
		String hql;
		if(isLeave)
			hql = "Update ChatUser Set isActived= 0,isLeave = 1 Where roomId = ? and userId =?";
		else
			hql = "Update ChatUser Set actTime=getdate(),isActived= 1,isLeave = 0 Where roomId = ? and userId =?";
		
		return this.getSession().createQuery(hql).setInteger(0, roomId).setInteger(1,userId).executeUpdate();
		//return getSession().bulkUpdate(hql, new Object[] {roomId, userId });
	}

	public void updateChatUserCurrentDate(int roomId,int userId) {
		String hql = "Update ChatUser set actTime=getdate() Where roomId= ? and userId= ?";
		this.getSession().createQuery(hql).setInteger(0, roomId).setInteger(1,userId).executeUpdate();
		//getSession().bulkUpdate(hql, new Object[] { roomId, userId });
		
		hql="FROM ChatUser Where DateDiff(n,actTime,getdate())>1 and roomId= :roomId and isLeave=0";
		List list = this.getSession().createQuery(hql).setInteger("roomId", roomId).list();
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				ChatUser chatUser=(ChatUser)list.get(i);
				ChatMessage msg=new ChatMessage();
				msg.setRoomId(chatUser.getRoomId());
				msg.setSenderId(0);
				msg.setSenderName("系统");
				msg.setReceiverId(0);
				msg.setReceiverName("大家");
				msg.setIsPrivate(false);
				msg.setIsSendAll(true);
				msg.setSenderColor("#000000");
				msg.setReceiverColor("#000000");
				msg.setTalkContent(chatUser.getUserName()+"离开了聊天室");
				this.chatMessageService.SaveChatMessage(msg);				
			}
		}
		
		hql="Update ChatUser set isActived=0,isLeave=1 Where DateDiff(n,actTime,getdate())>1 and roomId= ? and isLeave=0 ";
		this.getSession().createQuery(hql).setInteger(0, roomId).executeUpdate();
		//getSession().bulkUpdate(hql,roomId);
	}
	
	public void saveChatUser(ChatUser chatUser) {
		this.getSession().save(chatUser);
		this.getSession().flush();
		ChatMessage msg = new ChatMessage();
		msg.setRoomId(chatUser.getRoomId());
		msg.setSenderId(0);
		msg.setSenderName("系统");
		msg.setReceiverId(0);
		msg.setReceiverName("大家");
		msg.setIsPrivate(false);
		msg.setIsSendAll(true);
		msg.setSenderColor("#000000");
		msg.setReceiverColor("#000000");
		msg.setTalkContent(chatUser.getUserName() + "进入了聊天室");
		this.chatMessageService.SaveChatMessage(msg);
	}
	
	public void saveChatUserFontColor(int roomId, int userId, String fontColor) {
		String hql = "Update ChatUser set fontColor=? Where roomId= ? and userId= ?";
		this.getSession().createQuery(hql).setString(0, fontColor).setInteger(1, roomId).setInteger(2, userId).executeUpdate();
		
		//getSession().bulkUpdate(hql, new Object[] { fontColor, roomId, userId });
	}
	
}
