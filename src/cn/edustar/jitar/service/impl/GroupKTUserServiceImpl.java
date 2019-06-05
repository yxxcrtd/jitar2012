package cn.edustar.jitar.service.impl;
import java.util.List;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.dao.GroupKTUserDao;
import cn.edustar.jitar.service.GroupKTUserService;
public class GroupKTUserServiceImpl implements GroupKTUserService{
	
	private GroupKTUserDao groupKTUserDao;
	
	public GroupKTUserDao geGroupKTUserDao() {
		return groupKTUserDao;
	}
	public void setGroupKTUserDao(GroupKTUserDao groupKTUserDao) {
		this.groupKTUserDao = groupKTUserDao;
	}
	
	public GroupKTUser GetGroupKTUser(int id){
		return this.groupKTUserDao.GetGroupKTUser(id);
	}
	public GroupKTUser GetGroupKTUser(int groupId,int userId){
		return this.groupKTUserDao.GetGroupKTUser(groupId,userId);
	}	
	public List<GroupKTUser> GetGroupKTUsers(int groupId){
		return this.groupKTUserDao.GetGroupKTUsers(groupId);
	}
	public void CreateGroupKTUser(GroupKTUser ktUser){
		this.groupKTUserDao.CreateGroupKTUser(ktUser);
	}
	public void UpdateGroupKTUser(GroupKTUser ktUser){
		this.groupKTUserDao.UpdateGroupKTUser(ktUser);
	}
	public void DeleteGroupKTUser(GroupKTUser ktUser){
		this.groupKTUserDao.DeleteGroupKTUser(ktUser);
	}
	public void DeleteGroupKTUser(int id){
		this.groupKTUserDao.DeleteGroupKTUser(id);
	}
	public void DeleteGroupKTUsers(int groupId){
		this.groupKTUserDao.DeleteGroupKTUsers(groupId);
	}

}
