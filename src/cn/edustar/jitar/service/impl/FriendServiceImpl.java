package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.FriendDao;
import cn.edustar.jitar.pojos.Friend;
import cn.edustar.jitar.service.FriendQueryParam;
import cn.edustar.jitar.service.FriendService;

/**
 * 好友服务接口的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:37:54 PM
 */
public class FriendServiceImpl implements FriendService {

	/** 好友DAO */
	private FriendDao friendDao;

	/** 好友DAO的set方法 */
	public FriendDao getFriendDao() {
		return friendDao;
	}

	/** 好友DAO的get方法 */
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#findById(int)
	 */
	public Friend findById(int id) {
		return friendDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#getFriendDataTable(cn.edustar.jitar.service.FriendQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getFriendDataTable(FriendQueryParam param, Pager pager) {
		return friendDao.getFriendDataTable(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#getBlackDataTable(cn.edustar.jitar.service.FriendQueryParam,
	 *      cn.edustar.data.Pager)
	 */
	public DataTable getBlackDataTable(FriendQueryParam param, Pager pager) {
		return friendDao.getBlackDataTable(param, pager);
	}

	
	public boolean isUserFriend( int friendId, int userId)
	{
		return this.friendDao.isUserFriend(friendId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#getFriendList(int)
	 */
	@SuppressWarnings("static-access")
	public DataTable getFriendList(int userId) {
		List<Object[]> list = friendDao.getFriendList(userId);
		DataTable dt = new DataTable(new DataSchema(friendDao.GET_FRIEND_LIST), list);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#findIsBlackByUserIdAndFriendId(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List findIsBlackByUserIdAndFriendId(int curLoginUserId, int userId) {
		return friendDao.findIsBlackByUserIdAndFriendId(curLoginUserId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#saveFriend(cn.edustar.jitar.pojos.Friend)
	 */
	public void saveFriend(Friend friend) {
		friendDao.saveFriend(friend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#isDuplicateFriend(int, int)
	 */
	public boolean isDuplicateFriend(int curLoginUserId, int userId) {
		return friendDao.isDuplicateFriend(curLoginUserId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#isDuplicateBlack(int, int)
	 */
	public boolean isDuplicateBlack(int curLoginUserId, int userId) {
		return friendDao.isDuplicateBlack(curLoginUserId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#delFriend(int)
	 */
	public void delFriend(int id) {
		Friend friend = friendDao.findById(id);
		friendDao.delFriend(friend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#moveToBlack(int)
	 */
	public void moveToBlack(int id) {
		friendDao.moveToBlack(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#moveToFriend(int)
	 */
	public void moveToFriend(int id) {
		friendDao.moveToFriend(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#getRecentFriendThings(int, int)
	 */
	public List<Object[]> getRecentFriendThings(int userId, int count) {
		return friendDao.getRecentFriendThings(userId, count);
	}
}
