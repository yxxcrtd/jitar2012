package cn.edustar.jitar.service;
import cn.edustar.jitar.pojos.ChatRoom;
public interface ChatRoomService {
	public int getRoomId(int groupId);
	public int getPrepareCourseRoomId(int prepareCourseId);
	public void saveChatRoom(ChatRoom chatRoom);
}
