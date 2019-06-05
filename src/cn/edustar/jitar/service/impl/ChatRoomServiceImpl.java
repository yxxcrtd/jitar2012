package cn.edustar.jitar.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.jitar.dao.ChatRoomDao;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.service.ChatRoomService;
public class ChatRoomServiceImpl  implements ChatRoomService{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ChatRoomServiceImpl.class);
	/** 数据访问对象 */
	private ChatRoomDao chatRoomDao;
	
	/** 数据访问对象. */
	public void setChatRoomDao(ChatRoomDao chatRoomDao) {
		this.chatRoomDao = chatRoomDao;
	}
	
	public ChatRoomServiceImpl()
	{
		
	}
	public int getPrepareCourseRoomId(int prepareCourseId)
	{
		return chatRoomDao.getPrepareCourseRoomId(prepareCourseId);
	}
	public int getRoomId(int groupId)
	{
		return chatRoomDao.getRoomId(groupId);
	}

	public void saveChatRoom(ChatRoom chatRoom) {
		// TODO Auto-generated method stub
		
	}
}
