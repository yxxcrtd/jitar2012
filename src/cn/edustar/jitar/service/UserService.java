package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.usermgr.BaseUser;

/**
 * 获得本地用户信息的服务接口定义
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 3:00:34 PM
 */
public interface UserService {
	/** 事件: 正准备创建一个新用户，事件对象为 User 对象。(对象尚未保存到数据库, 没有 id) */
	public static final String EVENT_USER_CREATING = "jitar.user.creating";

	/** 事件: 成功创建一个新用户，事件对象为 User 对象。(对象已经保存到了数据库) */
	public static final String EVENT_USER_CREATED = "jitar.user.created";

	/**
	 * 得到指定登录名的用户对象. 实际上等同于调用 getUserByLoginName(loginName, true)
	 * 
	 * @param loginName 用户登录名
	 * @return 如果有该标识的用户，则返回该用户对象，否则返回 null
	 */
	public User getUserByLoginName(String loginName);
	public User getUserByEmail(String email);
	public User getUserByNickName(String nickName);
	public BaseUser getUserByUsername(String username);

	public User getUserByAccountId(String accountId);
	/**
	 * 得到指定登录名的用户对象, 可以选择是否从缓存中获取
	 * 
	 * @param loginName 用户登录名
	 * @param canFromCache true 表示如果 cache 里面有则尽量从 cache 中获取; false 表示直接从数据库中获取，不通过 cache. 从 cache 中获取能够加快速度，但不能保证一定是最新的
	 * @return 如果有该登录名的用户，则返回该用户对象，否则返回 null
	 */
	public User getUserByLoginName(String loginName, boolean canFromCache);

	/**
	 * 得到指定标识的用户信息，如果在缓存中存在，则直接从缓存中获取. 实际上等同于调用 getUserById(userId, true)
	 * 
	 * @param userId 用户标识
	 * @return 如果有该登录名的用户，则返回该用户对象，否则返回 null
	 */
	public User getUserById(int userId);
	
	/**
	 * 
	 * 根据Guid得到用户对象
	 * @param guid
	 * @return
	 */
	public User getUserByGuid(String guid);
	
	/**
	 * 从缓存中查找用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserFromCache(int userId);

	/**
	 * 得到指定标识的用户信息，如果在缓存中存在，则直接从缓存中获取
	 * 
	 * @param userId
	 * @param canFromCache true 表示如果 cache 里面有则尽量从 cache 中获取; false 表示直接从数据库中获取，不通过 cache. 从 cache 中获取能够加快速度，但不能保证一定是最新的
	 * @return 如果有该标识的用户，则返回该用户对象，否则返回 null
	 */
	public User getUserById(int userId, boolean canFromCache);

	/**
	 * 得到指定标识的一组用户
	 * 
	 * @param user_ids 用户标识的集合
	 * @return 返回该组用户的集合
	 */
	public List<User> getUserByIds(List<Integer> user_ids); 
		
	/**
	 * 根据输入的用户登录名查找用户是否存在
	 * 
	 * @param friendName
	 * @return
	 */
	public List<User> findLoginUserExist(String friendName);
	
	/**
	 * 得到用户数据表
	 * 
	 * @param param 查询参数
	 * @param pager 分页条件
	 * @return
	 */
	public List<User> getUserList(UserQueryParam param, Pager pager);
	
	/**
	 * 得到所有用户的统计列表
	 *
	 * @return
	 */
	public List<User> getUserList();
	
	/**
	 * 得到用户管理数据表
	 *
	 * @param param
	 * @param pager
	 * @return
	 */
	public DataTable getUserAdminDataTable(AdminUserQueryParam param, Pager pager);

	public void createUser(BaseUser baseUser, User user);
	
	/**
	 * 创建一个新用户，根据逻辑可能给该用户发送电子邮件，欢迎短消息等业务实现
	 * 
	 * @param user
	 */
	public void createUser(User user);
	public void createUser(User user,Boolean checkLoginName);
	/**
	 * 更新一个用户对象的信息
	 * 
	 * @param user
	 */
	public void updateUser(User user);
	public void updateUser(User user,boolean updateSSO);
	
	public void updateUserIconTemp(User user);
	/**
	 * 逻辑上删除用户，用户及其相关内容并为真实删除，只是设置了删除标志
	 *
	 * @param user
	 */
	public void removeUser(User user);
	
	/**
	 * 彻底删除用户
	 *
	 * @param userId
	 */
	public void deleteUser(int userId);

	/**
	 * 验证'用户登录名'是否重复
	 *
	 * @param strLoginName
	 * @return
	 */
	public boolean isDuplicateLoginName(String strLoginName);

	/**
	 * 验证'用户邮箱'是否重复
	 * 
	 * @param strEmail
	 * @return
	 */
	public boolean isDuplicateEmail(String strEmail);

	/**
	 * 为指定用户重新统计各项统计数据
	 * 
	 * @param user
	 * @return
	 */
	public Object statForUser(User user);
	public void statHistoryArticleForUser(User user);
	

	/**
	 * 更新用户状态
	 * 
	 * @param user
	 * @param status - User.USER_STATUS_XXX.
	 */
	public void updateUserStatus(User user, int status);
	
	/**
	 * 根据用户名重置密码
	 *
	 * @param username
	 * @param password
	 */
	public void resetPassword(String username, String password);
	public String getPassword(String userloginname);

			
	/**
	 * 返回机构的用户
	 * 
	 * @param unitId
	 * @return
	 */
	public List<User> getUserUnitList(int unitId);

	public Unit getUnitByUser(User user);
	/**
	 * 设置为待推送
	 * @param user
	 * @param operateUser
	 */
	public void setToPush(User user,User operateUser);
	
	/**
	 * 设置为已经推送
	 * @param user
	 */
	public void setPushed(User user);
	
	/**
	 * 取消推送
	 * @param user
	 */
	public void setUnPush(User user);
	

	public void updateUserUnit(User user);
	
	public void saveOrUpdateUserSubjectGrade(UserSubjectGrade userSubjectGrade);
	public void deleteUserSubjectGrade(UserSubjectGrade userSubjectGrade);
	public UserSubjectGrade getUserSubjectGradeById(int userSubjectGradeId);
	public List<UserSubjectGrade> getAllUserSubjectGradeListByUserId(int userId);
	public List<UserSubjectGrade> getUserSubjectGradeListByUserId(int userId);	
	
	public int getMaxUserId();
	public int getMinUserId();
	
	public void addVisitCount(int userId);
	
	/** 用户类型：如名师、教研员、专家等 */
	public void saveOrUpdateUserType(UserType userType);
	public void deleteUserType(UserType userType);
	public void updateUserType(int userId, String userType);
	public List<UserType> getAllUserType();
	public UserType getUserTypeById(int userTypeId);
	public UserType getUserTypeByName(String userTypeName);
	
	public List<Integer> getAllUserIdByUserType(int userType);
	
	public String getErrorInfo();
	public int getUserCount();
}
