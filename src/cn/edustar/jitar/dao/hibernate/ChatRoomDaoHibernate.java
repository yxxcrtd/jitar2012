package cn.edustar.jitar.dao.hibernate;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.jitar.dao.ChatRoomDao;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.PrepareCourse;

@SuppressWarnings("unchecked")
public class ChatRoomDaoHibernate extends BaseDaoHibernate implements ChatRoomDao {
	/** 日志 */
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ChatRoomDaoHibernate.class);
	public int getRoomId(int groupId){
		//log.info("groupId======"+groupId);
		String hql = "FROM ChatRoom WHERE groupId = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, groupId).list();
		if (list == null || list.size() == 0)
		{
			hql = "FROM Group WHERE groupId = ?";
			List grouplist = this.getSession().createQuery(hql).setInteger(0, groupId).list();
			if (grouplist == null || grouplist.size() == 0)
			{
				return -1;
			}
			Group g=(Group)grouplist.get(0);
			CreateGroupChatRoom(g);
			hql = "FROM ChatRoom WHERE groupId = ?";
			list = this.getSession().createQuery(hql).setInteger(0, groupId).list();
			if (list == null || list.size() == 0)
			{
				return -1;
			}
		}
		ChatRoom cr=(ChatRoom)list.get(0);
		return cr.getRoomId();
	}
	
	public int getPrepareCourseRoomId(int prepareCourseId)
	{
		String hql = "FROM ChatRoom WHERE prepareCourseId = ?";
		List list = this.getSession().createQuery(hql).setInteger(0, prepareCourseId).list();
		if (list == null || list.size() == 0)
		{
			hql = "FROM PrepareCourse WHERE prepareCourseId = ?";
			List prepareCourselist = this.getSession().createQuery(hql).setInteger(0, prepareCourseId).list();
			if (prepareCourselist == null || prepareCourselist.size() == 0)
			{
				return -1;
			}
			PrepareCourse g=(PrepareCourse)prepareCourselist.get(0);
			CreatePrepareCourseChatRoom(g);
			hql = "FROM ChatRoom WHERE prepareCourseId = ?";
			list = this.getSession().createQuery(hql).setInteger(0, prepareCourseId).list();
			if (list == null || list.size() == 0)
			{
				return -1;
			}
		}
		ChatRoom cr=(ChatRoom)list.get(0);
		return cr.getRoomId();		
	}
	private void CreatePrepareCourseChatRoom(PrepareCourse pc)
	{
		int pcId=pc.getPrepareCourseId();
		String pcName=pc.getTitle();
		ChatRoom chatRoom=new ChatRoom();
		chatRoom.setRoomName(pcName+"聊天室");
		chatRoom.setPrepareCourseId(pcId);
		chatRoom.setIsClosed(false);
		chatRoom.setRoomInfo(pcName+"聊天室");
		chatRoom.setCreaterName("");
		saveChatRoom(chatRoom);
	}
	private void CreateGroupChatRoom(Group g)
	{
		int groupId=g.getGroupId();
		String groupName=g.getGroupTitle();
		ChatRoom chatRoom=new ChatRoom();
		chatRoom.setRoomName(groupName+"聊天室");
		chatRoom.setGroupId(groupId);
		chatRoom.setIsClosed(false);
		chatRoom.setRoomInfo(groupName+"聊天室");
		chatRoom.setCreaterName("");
		saveChatRoom(chatRoom);
		
	}
	
	public void saveChatRoom(ChatRoom chatRoom)
	{
		//this.getSession().setCheckWriteOperations(false);
		this.getSession().save(chatRoom);
		this.getSession().flush();
		//this.getSession().setCheckWriteOperations(true);
	}
}
