package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.data.hql.UpdateHelper;
import cn.edustar.jitar.dao.FriendDao;
import cn.edustar.jitar.pojos.Friend;
import cn.edustar.jitar.service.FriendQueryParam;

/**
 * 好友信息数据库访问实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 2:57:54 PM
 */
public class FriendDaoHibernate extends BaseDaoHibernate implements FriendDao {
	
	/**
	 * LOAD_IS_DUPLICATE_FRIEND
	 */
	private static final String LOAD_IS_DUPLICATE_FRIEND = "FROM Friend F WHERE ((F.userId = ?) AND (F.friendId = ?))";

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#findById(int)
	 */
	public Friend findById(int id) {
		return (Friend) this.getSession().get(Friend.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.FriendDao#getFriendDataTable(cn.edustar.jitar.service.iface.FriendQueryParam, cn.edustar.jitar.util.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getFriendDataTable(FriendQueryParam param, Pager pager) {
		// 构造查询器
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT f.id, f.userId, f.friendId, f.addTime, f.typeId, f.remark, f.isBlack, u.loginName, u.nickName, u.email, u.virtualDirectory, u.userFileFolder, u.blogName, u.userIcon";
		query.fromClause = "FROM Friend f, User u ";
		query.whereClause = "WHERE (f.friendId = u.userId) AND (f.userId = " + param.userId + ") AND (f.isBlack = '0')";
		query.orderClause = "ORDER BY id DESC";

		pager.setTotalRows(query.queryTotalCount(this.getSession()));
		List list = query.queryData(this.getSession(), pager);

		String schema_str = "id, userId, friendId, addTime, typeId, remark, isBlack, loginName, nickName, email, virtualDirectory, userFileFolder, blogName, userIcon";
		DataTable dt = new DataTable(new DataSchema(schema_str), list);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.FriendDao#getFriendDataTable(cn.edustar.jitar.service.iface.FriendQueryParam, cn.edustar.jitar.util.Pager)
	 */
	@SuppressWarnings("unchecked")
	public DataTable getBlackDataTable(FriendQueryParam param, Pager pager) {
		// 构造查询器
		QueryHelper query = new QueryHelper();
		query.selectClause = "SELECT f.id, f.userId, f.friendId, f.addTime, f.typeId, f.remark, f.isBlack, u.loginName, u.nickName, u.email, u.virtualDirectory, u.userFileFolder, u.blogName, u.userIcon";
		query.fromClause = "FROM Friend f, User u ";
		query.whereClause = "WHERE (f.friendId = u.userId) AND (f.userId = " + param.userId + ") AND (f.isBlack = '1')";
		query.orderClause = "ORDER BY id DESC";
				
		pager.setTotalRows(query.queryTotalCount(this.getSession()));
		List list = query.queryData(this.getSession(), pager);
		String schema_str = "id, userId, friendId, addTime, typeId, remark, isBlack, loginName, nickName, email, virtualDirectory, userFileFolder, blogName, userIcon";
		DataTable dt = new DataTable(new DataSchema(schema_str), list);
		
		return dt;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.FriendDao#getFriendList(int)
	 */
	@SuppressWarnings( { "unchecked", "static-access", "null" })
	public List<Object[]> getFriendList(int userId) {
		QueryHelper query = new QueryHelper();
		FriendDao friendDao = null;
		query.selectClause = "SELECT " + friendDao.GET_FRIEND_LIST;
		query.fromClause = " FROM Friend F, User U";
		query.whereClause = "WHERE ((F.friendId = U.userId) AND (F.userId = " + userId + ") AND(F.isBlack = '0'))";
		List list = query.queryData(this.getSession());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#findIsBlackByUserIdAndFriendId(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Friend> findIsBlackByUserIdAndFriendId(int curLoginUserId, int userId) {
		return this.getSession().createQuery(LOAD_IS_DUPLICATE_FRIEND).setInteger(0, curLoginUserId).setInteger(1, userId).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.FriendDao#createFriend(cn.edustar.jitar.model.Friend)
	 */
	public void saveFriend(Friend friend) {
		this.getSession().save(friend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#isDuplicateFriend(int, int)
	 */
	@SuppressWarnings("unchecked")
	public boolean isDuplicateFriend(int curLoginUserId, int userId) {
		List list = this.getSession().createQuery(LOAD_IS_DUPLICATE_FRIEND).setInteger(0, curLoginUserId).setInteger(1, userId).list();
		if (list != null && list.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#isDuplicateBlack(int, int)
	 */
	@SuppressWarnings("unchecked")
	public boolean isDuplicateBlack(int curLoginUserId, int userId) {
		List list = this.getSession().createQuery(LOAD_IS_DUPLICATE_FRIEND).setInteger(0, curLoginUserId).setInteger(1, userId).list();
		if (list != null && list.size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.FriendDao#delFriend(cn.edustar.jitar.model.Friend)
	 */
	public void delFriend(Friend friend) {
		this.getSession().delete(friend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#moveToBlack(int)
	 */
	public void moveToBlack(final int id) {
		String hql = "UPDATE Friend F SET F.isBlack = '1' WHERE F.id = " + id;
		this.getSession().createQuery(hql).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#moveToFriend(int)
	 */
	public void moveToFriend(final int id) {
		String hql = "UPDATE Friend F SET F.isBlack = false WHERE F.id = :id";
		UpdateHelper up = new UpdateHelper(hql);
		up.setInteger("id", id);
		up.executeUpdate(this.getSession());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.FriendDao#getRecentFriendThings(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getRecentFriendThings(int userId, int count) {
		String hql = "SELECT i, u " + " FROM Friend f, FriendThing i, User u WHERE f.userId = ?" + " AND f.friendId = i.userId AND i.userId = u.userId ORDER BY i.createDate DESC";
		List<Object[]> ls = this.getSession().createQuery(hql).setInteger(0, userId).setFirstResult(0).setMaxResults(count).list();
		return ls;//getSession().findTopCount(hql, count, userId);
	}
	
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.FriendDao#isUserFriend(int, int)
	 */
	@SuppressWarnings("unchecked")
	public boolean isUserFriend( int friendId, int userId) {
		List<Friend> f = (List<Friend>)this.getSession().createQuery("FROM Friend WHERE isBlack = 0 AND userId = " + userId + " AND friendId = " + friendId).list();
		return f.size() > 0;
	}
	
}
