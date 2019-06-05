package cn.edustar.jitar.dao;
import java.util.List;

import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.pojos.UGroupUser;
import cn.edustar.jitar.pojos.User;

public interface UGroupUserDao {
	public List<UGroupUser> getUGroupUser();
	public List<UGroupUser> getUGroupUser(int groupId,int managed);
	public List<UGroupUser> getUGroupUserByGroupId(int groupId);
	public List<UGroupUser> getUGroupUserByUserId(int userId);
	public List<UGroupUser> getUGroupUserByUserId(int userId,int managed);
	public Integer[] getGroupIdByUserId(int userId);
	
	public void Delete(UGroupUser uGroupUser);
	public void DeleteGroupUser(int managed);
	public void Delete(int groupId);
	public void Delete(int groupId,int userId);
	public void DeleteUser2(int userId);
	public void Save(UGroupUser uGroupUser);
	public UGroupUser Find(int groupId,int userId);
	public UGroupUser Find(int groupId,int userId,int managed);
	public void InSertUser1(UGroup group,int score1,int score2,int conditionType);
	public boolean isExistUser1(int userId,UGroup group,int score1,int score2,int conditionType);
	public void DeleteUser1(UGroup group,int score1,int score2,int conditionType);
	public List<User> getUser(String hql);
	public UGroupUser getGroupUserManaged(int groupId,int userId);
	
}
