package cn.edustar.jitar.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.dao.ChatMessageDao;
import cn.edustar.jitar.pojos.ChatMessage;

/**
 * 聊天信息访问实现.
 * 
 * @author baimindong
 */
@SuppressWarnings("unchecked")
public class ChatMessageDaoHibernate extends BaseDaoHibernate implements ChatMessageDao {
    /** 日志 */
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ChatUserDaoHibernate.class);

    public List<ChatMessage> getAllChatMessages(int roomId) {
        String hql = "FROM ChatMessage Where roomId = ? Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId).list();
        return list;
    }

    public List<ChatMessage> getChatMessages(int roomId, int userID) {
        String hql = "FROM ChatMessage Where roomId=? and ( senderId= ? or receiverId = ?) Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId).setInteger(1, userID)
                .setInteger(2, userID).list();
        return list;
    }

    public List<ChatMessage> getTodayChatMessages(int roomId) {
        String hql = "FROM ChatMessage Where roomId = ? and DateDiff(n,sendDate,Convert(varchar(10),getDate(),120))<0 Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId).list();
        return list;
    }

    public List<ChatMessage> getChatMessages(int roomId, Date fromDate) {
        String hql = "FROM ChatMessage Where roomId = ? and  sendDate>=? Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId).setDate(1, fromDate).list();

        return list;
    }

    public List<ChatMessage> getNewChatMessages(int roomId, int fromChatMessageId) {
        String hql = "FROM ChatMessage Where roomId = ? and chatMessageId>? Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId)
                .setInteger(1, fromChatMessageId).list();
        return list;
    }

    public List<ChatMessage> getChatMessages(int roomId, int userID, Date fromDate) {
        String hql = "FROM ChatMessage Where roomId = ? and (senderId= ? or receiverId = ?) and sendDate>=? Order by sendDate asc";
        List<ChatMessage> list = this.getSession().createQuery(hql).setInteger(0, roomId).setInteger(1, userID)
                .setInteger(2, userID).setDate(3, fromDate).list();

        return list;
    }
    public void SaveChatMessage(ChatMessage chatMessage) {
        chatMessage.setTalkContent(chatMessage.getTalkContent().replaceAll("\\[face_(\\d{3})\\]", "<img src='../images/face/$1.gif'>"));
        this.getSession().save(chatMessage);
        this.getSession().flush();

    }

}
