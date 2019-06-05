package cn.edustar.jitar.dao;
import cn.edustar.jitar.pojos.ChatRoom;
public interface ChatRoomDao {
	public int getRoomId(int groupId);
	public int getPrepareCourseRoomId(int prepareCourseId);
	public void saveChatRoom(ChatRoom chatRoom);
}
