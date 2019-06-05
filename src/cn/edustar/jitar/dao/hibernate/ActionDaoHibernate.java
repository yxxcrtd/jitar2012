package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.ActionDao;
import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.service.ActionQueryParam;

public class ActionDaoHibernate extends BaseDaoHibernate implements ActionDao {	
	public void addAction(Action action)
	{
		this.getSession().save(action);
	}
	
	public void saveAction(Action action)
	{
		this.getSession().update(action);
	}	
	
	public void deleteAction(Action action)
	{
		this.getSession().delete(action);
	}
	
	public Action getActionById(int actionId)
	{
		return (Action)this.getSession().get(Action.class,actionId);
	}
	public Action getActionByActionUserId(int actionUserId)
	{	
		ActionUser au = (ActionUser)this.getSession().get(ActionUser.class, actionUserId);
		if(au == null)
		{
			return null;
		}
		else
		{
			return this.getActionById(au.getActionId());
		}
	}	
	
	/* 统计活动人数 */
	@SuppressWarnings("unchecked")
	public void updateActionUserStatById(int actionId)
	{		
		String queryString;
		int userCount = 0;
		queryString = "FROM ActionUser WHERE actionId = :actionId";
		List<ActionUser> userList = (List<ActionUser>)this.getSession().createQuery(queryString).setInteger("actionId", actionId).list();
		if (userList != null)
		{
			userCount = userList.size();
		}
		queryString = "UPDATE Action SET attendCount = :attendCount Where actionId = :actionId";
		this.getSession().createQuery(queryString).setInteger("attendCount", userCount).setInteger("actionId", actionId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Action> getActionList(ActionQueryParam param, Pager pager)
	{
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}
	
	public int getUserCountByActionId(int actionId)
	{
		String queryString = "SELECT SUM(attendUserCount) FROM ActionUser WHERE actionId = :actionId";
		Object sum = this.getSession().createQuery(queryString).setInteger("actionId", actionId).uniqueResult();
		if(sum == null) return 0;
		return Integer.valueOf(sum.toString()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public boolean userIsInAction(int userId, int actionId)
	{
		String queryString = "FROM ActionUser Where userId = :userId And actionId = :actionId";		
		List<ActionUser> userlist = (List<ActionUser>)this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("actionId", actionId).list();
		return userlist.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public ActionUser getActionUserByUserIdAndActionId(int userId, int actionId)
	{
		String queryString = "FROM ActionUser Where userId = :userId And actionId = :actionId";
		List<ActionUser> userlist = (List<ActionUser>)this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("actionId", actionId).list();
		if (userlist == null || userlist.size() == 0) return null;
		return (ActionUser)userlist.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActionUser> getActionUserIdListByActionId(int actionId)
	{	String queryString = "FROM ActionUser Where actionId = :actionId";
		List<ActionUser> user_id_list = (List<ActionUser>)this.getSession().createQuery(queryString).setInteger("actionId", actionId).list();
		return user_id_list;
	}
	
	public void updateActionStatus(int statusValue, int actionId)
	{
		String queryString;
		if(statusValue == -2)
		{
			//删除回复和用户
			queryString = "Delete From ActionReply Where actionId = :actionId";
			this.getSession().createQuery(queryString).setInteger("actionId",actionId).executeUpdate();
			queryString = "Delete From ActionUser Where actionId = :actionId";
			this.getSession().createQuery(queryString).setInteger("actionId",actionId).executeUpdate();
			queryString = "Delete From Action Where actionId = :actionId";
			this.getSession().createQuery(queryString).setInteger("actionId",actionId).executeUpdate();
		}
		else
		{
			queryString = "UPDATE Action Set status = :status Where actionId = :actionId";
			this.getSession().createQuery(queryString).setInteger("status",statusValue).setInteger("actionId",actionId).executeUpdate();
		}
	}
	
	/* 删除用户的所有讨论 */
	public void deleteActionReplyByUserId(int userId)
	{
		String queryString = "Delete From ActionReply Where userId = :userId";
		this.getSession().createQuery(queryString).setInteger("userId",userId).executeUpdate();
	}
	
	public void deleteActionReplyById(int actionReplyId)
	{
		String queryString = "Delete From ActionReply Where actionReplyId = :actionReplyId";
		this.getSession().createQuery(queryString).setInteger("actionReplyId",actionReplyId).executeUpdate();
	}
	
}
