package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Friend;

/**
 * 好友管理服务接口定义 (也提供好友新鲜事的服务)
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 3:00:34 PM
 */
public interface FriendService {

	/**
	 * 根据 Id 得到对应记录
	 * 
	 * @param id
	 * @return
	 */
	public Friend findById(int id);

	/**
	 * 按照指定的条件得到用户的好友列表
	 * 
	 * @param param-查询条件
	 * @param pager-分页设置
	 * @return
	 */
	public DataTable getFriendDataTable(FriendQueryParam param, Pager pager);

	/**
	 * 按照指定的条件得到用户的黑名单列表
	 * 
	 * @param param-查询条件
	 * @param pager-分页设置
	 * @return
	 */
	public DataTable getBlackDataTable(FriendQueryParam param, Pager pager);

	
	/**
	 * 根据'用户Id'得到关联用户的好友列表
	 * 
	 * @param userId
	 * @return
	 */
	public DataTable getFriendList(int userId);

	/**
	 * 根据 当前登录的用户Id 和 将要被添加的用户Id 得到该好友信息的黑名单标识
	 * 
	 * @param curLoginUserId
	 * @param userId
	 * @return
	 */
	public List<Friend> findIsBlackByUserIdAndFriendId(int curLoginUserId, int userId);

	/**
	 * 保存一个新的好友
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
	 * 根据 Friend Id 删除对应记录
	 * 
	 * @param id
	 */
	public void delFriend(int id);

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
	 * 得到指定用户的最新 count 个好友新鲜事
	 * 
	 * @param userId 用户标识
	 * @param count 数量
	 * @return 返回 List&lt;Object[FriendThing, User]&gt; 集合.
	 */

	
	public List<Object[]> getRecentFriendThings(int userId, int count);
}
