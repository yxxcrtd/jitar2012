package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.UserDeletedDao;
import cn.edustar.jitar.pojos.UserDeleted;

public class UserDeletedDaoHibernate extends BaseDaoHibernate implements UserDeletedDao {
	
	public UserDeleted getByUserLoginName(String loginName){
		List list = this.getSession().createQuery("FROM UserDeleted WHERE loginName = :loginName").setString("loginName", loginName).list();
		if (list != null && list.size() >= 1) {
			return (UserDeleted) list.get(0);
		}
		return null;
	}
	public UserDeleted getByUserGuid(String guid){
		List list = this.getSession().createQuery("FROM UserDeleted WHERE userGuid = :userGuid").setString("userGuid", guid).list();
		if (list != null && list.size() >= 1) {
			return (UserDeleted) list.get(0);
		}
		return null;	
	}
	
	public void createUser(UserDeleted user){
		this.getSession().save(user);
		this.getSession().flush();		
	}
	
	public void deleteUser(UserDeleted user){
		this.getSession().delete(user);
		this.getSession().flush();		
	}	
	
	public void evict(Object object) {
		this.getSession().evict(object);
		
	}

	public void flush() {
		this.getSession().flush();
		
	}	
}
