package cn.edustar.jitar.dao.hibernate;

import java.util.List;


import cn.edustar.jitar.dao.AccessControlDao;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.User;

public class AccessControlDaoHibernate extends BaseDaoHibernate implements
		AccessControlDao {

	public AccessControl getAccessControlById(int accessControlId)
	{
		return (AccessControl)this.getSession().get(AccessControl.class, accessControlId);
	}
	public void deleteAccessControl(AccessControl accessControl) {
		this.getSession().delete(accessControl);
	}

	@SuppressWarnings("unchecked")
	public List<AccessControl> getAllAccessControlByObject(int objectType,int objectId) {
		String queryString;
		if(objectId == 0)
		{
			queryString = "FROM AccessControl WHERE objectType = :objectType Order By accessControlId";
			return (List<AccessControl>) this.getSession().createQuery(queryString).setInteger("objectType", objectType).list();
		}
		else
		{
			queryString = "FROM AccessControl WHERE objectType = :objectType And objectId = :objectId Order By accessControlId";
			return this.getSession().createQuery(queryString).setInteger("objectType", objectType).setInteger("objectId", objectId).list();
			//return (List<AccessControl>) this.getSession().find(queryString,new Object[]{objectType,objectId});
		}
			
	}

	@SuppressWarnings("unchecked")
	public List<AccessControl> getAllAccessControlByUser(User user) {
		if (null == user) {
			return null;
		} else {
			String queryString = "FROM AccessControl WHERE userId = :userId Order By accessControlId";
			return this.getSession().createQuery(queryString).setInteger("userId",  user.getUserId()).list();
			//return (List<AccessControl>)this.getSession().find(queryString, user.getUserId());
		}
	}

	public void saveOrUpdateAccessControl(AccessControl accessControl) {
		this.getSession().saveOrUpdate(accessControl);
		this.getSession().flush();
	}
	
	/** 得到用户在特定对象权限 */
	@SuppressWarnings("unchecked")
	public AccessControl getAccessControlByUserAndObject(int userId,int objectType,int objectId)
	{
		String queryString = "FROM AccessControl WHERE userId = :userId And objectType = :objectType And objectId = :objectId";
		List<AccessControl> la = (List<AccessControl>)this.getSession().createQuery(queryString)
				.setInteger("userId", userId)
				.setInteger("objectType", objectType)
				.setInteger("objectId", objectId)
				.list();
				
		if(la.size() >0) return (AccessControl)la.get(0);
		return null;
	}
	@SuppressWarnings("unchecked")
	public boolean checkUserAccessControlIsExists(int userId,int objectType,int objectId)
	{
		String queryString = "FROM AccessControl WHERE userId = :userId And objectType = :objectType And objectId = :objectId";
		List<AccessControl> ac = (List<AccessControl>)this.getSession().createQuery(queryString)
				.setInteger("userId", userId)
				.setInteger("objectType", objectType)
				.setInteger("objectId", objectId)
				.list();
		//System.out.println("userId=" + userId + ", objectType="+objectType +",objectId="+objectId);
		return ac.size() > 0;
	}
	
	public void deleteAccessControlByUserIdObjectTypeObjectId(int userId,int objectType,int objectId)
	{
		String queryString = "DELETE FROM AccessControl WHERE userId = :userId And objectType = :objectType And objectId = :objectId";
		this.getSession().createQuery(queryString)
		.setInteger("userId", userId)
		.setInteger("objectType", objectType)
		.setInteger("objectId", objectId)
		.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public boolean  userIsInObject(int userId, int objectId)
	{
		String queryString = "FROM AccessControl WHERE userId = :userId And objectId = :objectId";
		List<AccessControl> ac = (List<AccessControl>)this.getSession().createQuery(queryString)
				.setInteger("userId", userId)
				.setInteger("objectId", objectId)
				.list();
		return ac.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<AccessControl> getAllAccessControlByUserAndObjectType(User user,int objectType)
	{
		String queryString = "FROM AccessControl WHERE userId = :userId And objectType = :objectType";
		List<AccessControl> ac = (List<AccessControl>)this.getSession().createQuery(queryString)
				.setInteger("userId", user.getUserId())
				.setInteger("objectType", objectType)
				.list();
		return ac;
	}
}
