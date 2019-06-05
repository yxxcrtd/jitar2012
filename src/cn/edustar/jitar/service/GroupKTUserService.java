package cn.edustar.jitar.service;
import java.util.List;
import cn.edustar.jitar.pojos.GroupKTUser;
public interface GroupKTUserService {
	public GroupKTUser GetGroupKTUser(int id);
	public GroupKTUser GetGroupKTUser(int groupId,int userId);
	public List<GroupKTUser> GetGroupKTUsers(int groupId);
	public void CreateGroupKTUser(GroupKTUser ktUser);
	public void UpdateGroupKTUser(GroupKTUser ktUser);
	public void DeleteGroupKTUser(GroupKTUser ktUser);
	public void DeleteGroupKTUser(int id);
	public void DeleteGroupKTUsers(int groupId);
}
