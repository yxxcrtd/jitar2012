package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.edustar.jitar.dao.ActionUserDao;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.ActionUserUnit;
import cn.edustar.jitar.pojos.User;

public class ActionUserDaoHibernate extends BaseDaoHibernate implements ActionUserDao {

	public void addActionUser(ActionUser actionUser) {
		this.getSession().saveOrUpdate(actionUser);
	}

	@SuppressWarnings("unchecked")
	public List<ActionUserUnit> getActionUserWithDistUnit() {
		String queryString = "FROM ActionUserUnit";
		return (List<ActionUserUnit>) this.getSession()
				.createQuery(queryString).list();
	}

	@SuppressWarnings("unchecked")
	public List<ActionUserUnit> getActionUserWithDistUnit(int actionId) {
		String queryString = "FROM ActionUserUnit WHERE actionId = :actionId Order By actionUserId DESC";
		return (List<ActionUserUnit>) this.getSession()
				.createQuery(queryString).setInteger("actionId", actionId)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<ActionUserUnit> getActionUserWithDistUnitByUserId(int userId) {
		String queryString = "FROM ActionUserUnit WHERE userId = :userId Order By actionUserId DESC";
		return (List<ActionUserUnit>) this.getSession()
				.createQuery(queryString).setInteger("userId", userId).list();
	}
	public void addActionUser(int actionId, User user, int inviteUserId) {
		if (user != null) {
			if (this.getActionUserByActionIdAndUserId(actionId,
					user.getUserId()) == null) {
				ActionUser u = new ActionUser();
				u.setActionId(actionId);
				u.setAttendUserCount(1);
				u.setInviteUserId(inviteUserId);
				u.setIsApprove(1);
				u.setStatus(0);
				u.setUserId(user.getUserId());
				this.getSession().save(u);
				u = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionUser getActionUserByActionIdAndUserId(int actionId, int userId) {
		String queryString = "FROM ActionUser WHERE actionId = :actionId and userId = :userId";
		List<ActionUser> al = (List<ActionUser>) this.getSession()
				.createQuery(queryString).setInteger("actionId", actionId)
				.setInteger("userId", userId).list();
		if (al.size() > 0)
			return al.get(0);
		else
			return null;
	}
	public void delActionUser(int actionId) {
		String queryString = "DELETE FROM ActionUser WHERE actionId = :actionId";
		this.getSession().createQuery(queryString)
				.setInteger("actionId", actionId);
	}
	public void delActionUser(int actionId, int userId) {
		ActionUser u = this.getActionUserByActionIdAndUserId(actionId, userId);
		if (u != null) {
			this.getSession().delete(u);
		}
	}

	public void delActionUserById(int actionUserId) {
		ActionUser u = (ActionUser) this.getSession().get(ActionUser.class,
				actionUserId);
		if (u != null) {
			this.getSession().delete(u);
		}
	}
}
