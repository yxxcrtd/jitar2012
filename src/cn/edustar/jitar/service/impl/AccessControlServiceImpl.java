package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.AccessControlDao;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;

public class AccessControlServiceImpl implements AccessControlService {

	private AccessControlDao accessControlDao;

	public AccessControl getAccessControlById(int accessControlId)
	{
		return this.accessControlDao.getAccessControlById(accessControlId);
	}
	
	public void setAccessControlDao(AccessControlDao accessControlDao) {
		this.accessControlDao = accessControlDao;
	}

	public void deleteAccessControl(AccessControl accessControl) {
		this.accessControlDao.deleteAccessControl(accessControl);
	}

	public List<AccessControl> getAllAccessControlByObject(int objectType,
			int objectId) {
		return this.accessControlDao.getAllAccessControlByObject(objectType,
				objectId);
	}
	
	public AccessControl getAccessControlByUserAndObject(int userId,int objectType,int objectId)
	{
		return this.accessControlDao.getAccessControlByUserAndObject(userId, objectType, objectId);
	}

	public List<AccessControl> getAllAccessControlByUser(User user) {
		return this.accessControlDao.getAllAccessControlByUser(user);
	}

	public void saveOrUpdateAccessControl(AccessControl accessControl) {
		this.accessControlDao.saveOrUpdateAccessControl(accessControl);
	}

	public boolean checkUserAccessControlIsExists(int userId,int objectType,int objectId)
	{
		return this.accessControlDao.checkUserAccessControlIsExists(userId, objectType, objectId);
	}
	public void deleteAccessControlByUserIdObjectTypeObjectId(int userId,int objectType,int objectId)
	{
		this.accessControlDao.deleteAccessControlByUserIdObjectTypeObjectId(userId, objectType, objectId);
	}
	
	/** 检查是否是系统管理员 */
	public boolean isSystemAdmin(User user){
		if(null == user) return false;
		return this.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_SUPERADMIN, 0);		
	}
	
	/** 检查是否是系统内容管理员 */
	public boolean isSystemContentAdmin(User user)
	{		
		if(null == user) return false;
		if(this.isSystemAdmin(user)) return true;
		return this.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0);
	}
	
	/** 检查是否是系统用户管理员 */
	public boolean isSystemUserAdmin(User user)
	{		
		if(null == user) return false;
		
		if(this.isSystemAdmin(user)) return true;
		return this.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0);		
	}
	
	public boolean userIsInObject(int userId, int objectId)
	{
		return this.accessControlDao.userIsInObject(userId, objectId);
	}
	
	/** 判断给定用户是否可以是给定机构的超级管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitSystemAdmin(User user,Unit unit)
	{
		if(null == user || null == unit) return false;
		// 先判断给定用户的系统管理权限
		if(this.isSystemAdmin(user)) return true; //系统管理员，则也是机构的系统管理员
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i=0;i<acl.size();i++)
		{
			AccessControl ac = (AccessControl)acl.get(i);
			// 本机构的系统管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITSYSTEMADMIN && ac.getObjectId() == unit.getUnitId())
			{
				return true;
			}
			
			//所管理的上级机构的管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITSYSTEMADMIN && unit.getUnitPathInfo().indexOf("/"+String.valueOf(ac.getObjectId())+ "/") >-1)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/** 判断给定用户是否可以是给定机构的用户管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitUserAdmin(User user,Unit unit)
	{
		if(null == user || null == unit) return false;
		if(this.isSystemAdmin(user)) return true;
		if(this.isSystemUserAdmin(user)) return true; //系统用户管理员，则也是机构的系统用户管理员
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i=0;i<acl.size();i++)
		{
			AccessControl ac = (AccessControl)acl.get(i);
			// 本机构的系统用户管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITUSERADMIN && ac.getObjectId() == unit.getUnitId())
			{
				return true;
			}
			
			//所管理的上级机构的用户管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITUSERADMIN && unit.getUnitPathInfo().indexOf("/"+String.valueOf(ac.getObjectId())+ "/") >-1)
			{
				return true;
			}
		}
		return false;
	}
	
	/** 判断给定用户是否可以是给定机构的内容管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitContentAdmin(User user,Unit unit)
	{
		if(null == user || null == unit) return false;
		if(this.isSystemAdmin(user)) return true;
		if(this.isSystemContentAdmin(user)) return true; //系统用户管理员，则也是机构的系统内容管理员
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i=0;i<acl.size();i++)
		{
			AccessControl ac = (AccessControl)acl.get(i);
			// 本机构的系统内容管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITCONTENTADMIN && ac.getObjectId() == unit.getUnitId())
			{
				return true;
			}
			
			//所管理的上级机构的内容管理员
			if(ac.getUserId()==user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_UNITCONTENTADMIN && unit.getUnitPathInfo().indexOf("/"+String.valueOf(ac.getObjectId())+ "/") >-1)
			{
				return true;
			}
		}
		return false;
	}
	
	/** 判断给定用户是否是给定学科的系统管理员 */
	public boolean userIsSubjectAdmin(User user, Subject subject){
		if(null == user || null == subject) return false;
		if(this.isSystemAdmin(user)) return true;
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i = 0;i<acl.size();i++){
			AccessControl ac = (AccessControl)acl.get(i);
			if(ac.getUserId() == user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_SUBJECTSYSTEMADMIN && ac.getObjectId() == subject.getSubjectId())
			{
				return true;
			}
		}
		return false;
	}
	
	/** 判断给定用户是否是给定学科的用户管理员 */
	public boolean userIsSubjectUserAdmin(User user, Subject subject){
		if(null == user || null == subject) return false;
		if(this.isSystemAdmin(user)) return true;
		if(this.isSystemUserAdmin(user)) return true;
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i = 0;i<acl.size();i++){
			AccessControl ac = (AccessControl)acl.get(i);
			if(ac.getUserId() == user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_SUBJECTUSERADMIN && ac.getObjectId() == subject.getSubjectId())
			{
				return true;
			}
		}
		return false;
	}
	
	/** 判断给定用户是否是给定学科的系统管理员 */
	public boolean userIsSubjectContentAdmin(User user, Subject subject){
		if(null == user || null == subject) return false;
		if(this.isSystemAdmin(user)) return true;
		if(this.isSystemContentAdmin(user)) return true;
		List<AccessControl> acl = this.getAllAccessControlByUser(user);
		for(int i = 0;i<acl.size();i++){
			AccessControl ac = (AccessControl)acl.get(i);
			if(ac.getUserId() == user.getUserId() && ac.getObjectType() == AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN && ac.getObjectId() == subject.getSubjectId())
			{
				return true;
			}
		}
		return false;
	}
	
	public List<AccessControl> getAllAccessControlByUserAndObjectType(User user,int objectType)
	{
		return this.accessControlDao.getAllAccessControlByUserAndObjectType(user, objectType);
	}
}
