package cn.edustar.jitar.service.impl;

import cn.edustar.jitar.dao.UserDeletedDao;
import cn.edustar.jitar.pojos.UserDeleted;
import cn.edustar.jitar.service.UserDeletedService;

public class UserDeletedServiceImpl implements UserDeletedService{
	private UserDeletedDao userdeletedDao ;
	
	public UserDeletedDao getUserDeletedDao()
	{
		return this.userdeletedDao;
	}
	public void setUserDeletedDao(UserDeletedDao userDeletedDao){
		this.userdeletedDao = userDeletedDao;
	}
	public UserDeleted getByUserLoginName(String loginName){
		return this.userdeletedDao.getByUserLoginName(loginName);
	}
	public UserDeleted getByUserGuid(String guid){
		return this.userdeletedDao.getByUserGuid(guid);
	}
	public void createUser(UserDeleted user){
		this.userdeletedDao.createUser(user);
	}
	public void deleteUser(UserDeleted user){
		this.userdeletedDao.deleteUser(user);
	}
}
