package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.UserDeleted;

public interface UserDeletedService {
	public UserDeleted getByUserLoginName(String loginName);
	public UserDeleted getByUserGuid(String guid);
	public void createUser(UserDeleted user);
	public void deleteUser(UserDeleted user);
}
