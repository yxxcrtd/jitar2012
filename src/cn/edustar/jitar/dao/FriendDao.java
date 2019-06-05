package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Friend;
import cn.edustar.jitar.service.FriendQueryParam;

/**
 * 好友信息数据库访问接口定义 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:41:53 PM
 */
public interface FriendDao {
	
	/** 得到关联的好友投影查询列表 */
	public static final String GET_FRIEND_LIST = "F.id, F.friendId, F.addTime, U.loginName, U.nickName,U.trueName, U.userId, U.blogName, U.userIcon";

	/**
	 * 根据'表对象Id'得到对应的对象列表
	 * 
	 * @param id
	 * @return
	 */
	public Friend findById(int id);

	/**
	 * 按照指定的条件得到用户的好友列表
	 * 
	 * @param param 查询条件
	 * @param pager 分页设置
	 * @return 字段包括： {f.id, f.friendId, f.addTime, f.typeId, f.remark, f.isBlack, u.loginName, u.nickName,
	 * 					 u.email, u.virtualDirectory, u.userFileFolder, u.blogName, u.userIcon}
	 */
	public DataTable getFriendDataTable(FriendQueryParam param, Pager pager);

	/**
	 * 按照指定的条件得到用户的黑名单列表
	 * 
	 * @param param 查询条件
	 * @param pager 分页设置
	 * @return 字段包括： {f.id, f.userId, f.friendId, f.addTime, f.typeId, f.remark, f.isBlack, u.loginName, 
	 * 					  u.nickName, u.email, u.virtualDirectory, u.userFileFolder, u.blogName, u.userIcon}
	 */
	public DataTable getBlackDataTable(FriendQueryParam param, Pager pager);


	/**
	 * 得到指定用户的好友列表
	 * 
	 * @param userId
	 * @return 返回为投影查询结果，字段为：F.id, F.friendId, F.addTime, U.loginName, U.nickName, U.userId, U.blogName, U.userIcon
	 */
	public List<Object[]> getFriendList(int userId);

	/**
	 * 根据 当前登录的用户Id 和 将要被添加的用户Id 得到该好友信息的黑名单标识
	 * 
	 * @param curLoginUserId
	 * @param userId
	 * @return
	 */
	public List<Friend> findIsBlackByUserIdAndFriendId(int curLoginUserId, int userId);

	/**
	 * 创建一个新的好友
	 * 
	 * @param friend
	 */
	public void saveFriend(Friend friend);

	/**
	 * 根据 当前登录的用户Id 和 将要被添加的用户Id 验证好友列表中是否有重复
	 * 
	 * @param curLoginUserId
	 * @param userId
	 * @return
	 */
	public boolean isDuplicateFriend(int curLoginUserId, int userId);

	/**
	 * 根据 当前登录的用户Id 和 将要被添加的用户Id 验证黑名单中是否有重复
	 * 
	 * @param curLoginUserId
	 * @param userId
	 * @return
	 */
	public boolean isDuplicateBlack(int curLoginUserId, int userId);

	/**
	 * 根据 Friend id 删除对应记录
	 * 
	 * @param id
	 */
	public void delFriend(Friend friend);

	/**
	 * 将好友移动到黑名单中
	 * 
	 * @param id
	 */
	public void moveToBlack(int id);

	/**
	 * 将黑名单移动到好友中
	 * 
	 * @param id
	 */
	public void moveToFriend(int id);

	/**
	 * 判断 friendId 是否是 userId 的好友
	 * @param friendId
	 * @param userId
	 * @return
	 */
	public boolean isUserFriend( int friendId, int userId);
	
	/**
	 * 得到指定用户的朋友的新鲜事
	 * 
	 * @param userId 用户标识
	 * @param count 返回最新的数量
	 * @return 返回 List&lt;Object[FriendThing, User]&gt; 集合
	 */
	public List<Object[]> getRecentFriendThings(int userId, int count);
}
