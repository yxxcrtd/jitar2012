package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;

public interface AccessControlService {
	public AccessControl getAccessControlById(int accessControlId);
	public void saveOrUpdateAccessControl(AccessControl accessControl);
	public void deleteAccessControl(AccessControl accessControl);
	public List<AccessControl> getAllAccessControlByUser(User user);
	public List<AccessControl> getAllAccessControlByObject(int objectType,int objectId);
	public AccessControl getAccessControlByUserAndObject(int userId,int objectType,int objectId);
	public boolean checkUserAccessControlIsExists(int userId,int objectType,int objectId);
	public void deleteAccessControlByUserIdObjectTypeObjectId(int userId,int objectType,int objectId);
	public boolean userIsInObject(int userId, int objectId);
	public List<AccessControl> getAllAccessControlByUserAndObjectType(User user,int objectType);
	
	/** 检查是否是系统管理员 */
	public boolean isSystemAdmin(User user);
	
	/** 检查是否是系统内容管理员 */
	public boolean isSystemContentAdmin(User user);
	
	/** 检查是否是系统用户管理员 */
	public boolean isSystemUserAdmin(User user);	
	
	/** 判断给定用户是否可以是给定机构的超级管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitSystemAdmin(User user,Unit unit);
	
	/** 判断给定用户是否可以是给定机构的用户管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitUserAdmin(User user,Unit unit);
	
	/** 判断给定用户是否可以是给定机构的内容管理员 ，按照上级管理下级的原则进行执行权限的判断 */
	public boolean userIsUnitContentAdmin(User user,Unit unit);
	
	/** 判断给定用户是否是给定学科的系统管理员 */
	public boolean userIsSubjectAdmin(User user, Subject subject);
	
	/** 判断给定用户是否是给定学科的用户管理员 */
	public boolean userIsSubjectUserAdmin(User user, Subject subject);
	
	/** 判断给定用户是否是给定学科的系统管理员 */
	public boolean userIsSubjectContentAdmin(User user, Subject subject);
	
	
}
