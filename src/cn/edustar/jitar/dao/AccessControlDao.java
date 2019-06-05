package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.User;

/**
 * 权限控制接口
 * 
 * @author admin
 * 
 */
public interface AccessControlDao {
    public void saveOrUpdateAccessControl(AccessControl accessControl);
    public void deleteAccessControl(AccessControl accessControl);
    public AccessControl getAccessControlById(int accessControlId);
    public List<AccessControl> getAllAccessControlByUser(User user);
    public List<AccessControl> getAllAccessControlByObject(int objectType, int objectId);
    public AccessControl getAccessControlByUserAndObject(int userId, int objectType, int objectId);
    public boolean checkUserAccessControlIsExists(int userId, int objectType, int objectId);
    public void deleteAccessControlByUserIdObjectTypeObjectId(int userId, int objectType, int objectId);
    public boolean userIsInObject(int userId, int objectId);
    public List<AccessControl> getAllAccessControlByUserAndObjectType(User user, int objectType);
}