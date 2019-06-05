package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.UserDeleted;

public interface UserDeletedDao extends Dao  {
	public UserDeleted getByUserLoginName(String loginName);
	public UserDeleted getByUserGuid(String guid);
	public void createUser(UserDeleted user);
	public void deleteUser(UserDeleted user);
}
