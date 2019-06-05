package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.service.AdminUserQueryParam;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.UserQueryParam;

/**
 * DAO的接口类
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 26, 2008 1:57:23 PM
 */
public interface UserDao extends Dao {
	
	/**
	 * 根据 userId 获得对应用户记录
	 * 
	 * @param userId 用户标识
	 * @return 返回用户对象, 如果不存在则返回 null
	 */
	public User getByUserId(int userId);
	
	public User getByAccountId(String accountId);
	
	/**
	 * 根据指定条件得到用户列表
	 * 
	 * @param param 条件参数
	 * @param pager 分页参数
	 * @return 返回 List&lt;User&gt; 集合
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
	public List<Object[]> getUserAdminDataTable(AdminUserQueryParam param, Pager pager);

	/**
	 * 根据登录名查找用户是否存在
	 * 
	 * @param loginName
	 * @return
	 */
	public List<User> findByLoginName(String loginName);
	
	/**
	 * 根据用户登录名得到对应用户对象
	 * 
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String loginName);
	public User getUserByEmail(String email);
	public User getUserByNickName(String nickName);

	/**
	 * 得到指定标识的一组用户
	 * 
	 * @param user_ids 用户标识的集合
	 * @return 返回该组用户的集合，顺序按照 id 逆序排列
	 * @remark 此方法也可以通过调用多次'getByUserId()'来完成，但效率差
	 */
	public List<User> getUserByIds(List<Integer> user_ids);

	/**
	 * 创建一个新的用户对象
	 * 
	 * @param user
	 */
	public void createUser(User user);

	/**
	 * 更新一个用户对象
	 * 
	 * @param user 用户对象
	 */
	public void updateUser(User user);

	/**
	 * 验证'用户登录名'是否重复.
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
	 * @return - 返回待定义
	 */
	public Object statForUser(User user);
	public void statHistoryArticleForUser(User user);
	public Object statAllUser();

	/**
	 * 更新用户状态
	 * 
	 * @param user
	 * @param status User.USER_STATUS_XXX.
	 */
	public void updateUserStatus(User user, int status);
	
	/**
	 * 删除用户
	 *
	 * @param user
	 */
	public void deleteUser(User user);

	
	/**
	 * 根据'机构Id'得到用户列表
	 *
	 * @param unitId
	 * @return
	 */
	public List<User> findByUnitId(int unitId);
	public User getUserByGuid(String guid);
	public void setStoreManager(StoreManager sto_mgr);
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
	
	public void saveOrUpdateUserSubjectGrade(UserSubjectGrade userSubjectGrade);
	public void deleteUserSubjectGrade(UserSubjectGrade userSubjectGrade);
	public UserSubjectGrade getUserSubjectGradeById(int userSubjectGradeId);
	public List<UserSubjectGrade> getUserSubjectGradeListByUserId(int userId);
	
	public int getMaxUserId();
	public int getMinUserId();
	
	public void addVisitCount(int userId);
	
	/** 用户类型：如名师、教研员、专家等 */
	public void saveOrUpdateUserType(UserType userType);
	public void deleteUserType(UserType userType);
	public void updateUserType(int userId, String userType);
	public List<UserType> getAllUserType();	
	public List<Integer> getAllUserIdByUserType(int userType);
	public int getUserCount();
	
	public void updateUserIconTemp(User user);
}
