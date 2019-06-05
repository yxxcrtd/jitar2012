package cn.edustar.jitar.service.impl;

import java.util.List;



import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.ActionDao;
import cn.edustar.jitar.dao.GroupDao;
import cn.edustar.jitar.dao.PrepareCourseDao;
import cn.edustar.jitar.dao.ActionUserDao;
import cn.edustar.jitar.dao.ActionReplyDao;
import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionReply;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.ActionUserUnit;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionQueryParam;
import cn.edustar.jitar.service.ActionService;

public class ActionServiceImpl implements ActionService {

	private ActionDao act_dao;
	private ActionUserDao act_user_dao;
	private ActionReplyDao act_rpl_dao;
	private GroupDao groupDao;
	private PrepareCourseDao prepareCourseDao;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public PrepareCourseDao getPrepareCourseDao() {
		return prepareCourseDao;
	}

	public void setPrepareCourseDao(PrepareCourseDao prepareCourseDao) {
		this.prepareCourseDao = prepareCourseDao;
	}
	
	public void setActionReplyDao(ActionReplyDao act_rpl_dao)
	{
		this.act_rpl_dao = act_rpl_dao;
	}
	
	public ActionReplyDao getActionReplyDao()
	{
		return this.act_rpl_dao;
	}
	
	public void setActionDao(ActionDao act_dao) {
		this.act_dao = act_dao;
	}

	public void setActionUserDao(ActionUserDao act_user_dao)
	{
		this.act_user_dao = act_user_dao;
	}
	
	public void addAction(Action action)
	{
		this.act_dao.addAction(action);		
	}
	
	public void saveAction(Action action)
	{
		this.act_dao.saveAction(action);
	}

	public void deleteAction(Action action)
	{
		int actionId= action.getActionId();
		act_user_dao.delActionUser(actionId);
		this.act_dao.deleteAction(action);		
	}
	
	public List<Action> getActionList(ActionQueryParam param, Pager pager)
	{			
		return this.act_dao.getActionList(param, pager);
	}
	
	public Action getActionById(int actionId)
	{
		return this.act_dao.getActionById(actionId);
	}
	public Action getActionByActionUserId(int actionUserId)
	{
		return this.act_dao.getActionByActionUserId(actionUserId);
	}
	
	public void addActionUser(ActionUser actionUser)
	{
		this.act_user_dao.addActionUser(actionUser);
	}
	
	public int getUserCountByActionId(int actionId)
	{
		return this.act_dao.getUserCountByActionId(actionId);
	}
	public boolean userIsInAction(int userId, int actionId)
	{
		return this.act_dao.userIsInAction(userId, actionId);
	}
	public ActionUser getActionUserByUserIdAndActionId(int userId, int actionId)
	{
		return this.act_dao.getActionUserByUserIdAndActionId(userId, actionId);
	}
	public void updateActionUserStatById(int actionId)
	{
		this.act_dao.updateActionUserStatById(actionId);
	}
	//作废
	public List<ActionUser> getActionUserIdListByActionId(int actionId)
	{
		return this.act_dao.getActionUserIdListByActionId(actionId);
	}
	
	public List<ActionUserUnit> getActionUserWithDistUnit()
	{
		return this.act_user_dao.getActionUserWithDistUnit();
	}
	
	public List<ActionUserUnit> getActionUserWithDistUnit(int actionId)
	{
		return this.act_user_dao.getActionUserWithDistUnit(actionId);
	}
	public void addActionUser(int actionId,User user, int inviteUserId)
	{
		this.act_user_dao.addActionUser(actionId, user, inviteUserId);		
	}
	public ActionUser getActionUserByActionIdAndUserId(int actionId,int userId)
	{
		return this.act_user_dao.getActionUserByActionIdAndUserId( actionId, userId);
	}
	public void delActionUser(int actionId,int userId)
	{
		this.act_user_dao.delActionUser(actionId, userId);
	}
	
	/**
	 * 根据一个标识删除一个ActionUser记录
	 */
	public void delActionUserById(int actionUserId)
	{
		this.act_user_dao.delActionUserById(actionUserId);
	}
	
	/**
	 * 获取某个用户参加的所有活动
	 */
	public List<ActionUserUnit> getActionUserWithDistUnitByUserId(int userId)
	{
		return this.act_user_dao.getActionUserWithDistUnitByUserId(userId);
	}
	
	public void addComment(ActionReply actionReply)
	{
		this.act_rpl_dao.addComment(actionReply);
	}
	
	public void deleteActionReply(int actionReplyId)
	{
		this.act_rpl_dao.deleteActionReply(actionReplyId);
	}
	
	public void updateActionStatus(int statusValue, int actionId)
	{
		this.act_dao.updateActionStatus(statusValue, actionId);
	}
	public void deleteActionReplyByUserId(int userId)
	{
		this.act_dao.deleteActionReplyByUserId(userId);
	}
	public void deleteActionReplyById(int actionReplyId)
	{
		this.act_dao.deleteActionReplyById(actionReplyId);
	}
	
	/**
	 * 判断用户是否可以管理活动
	 * 1，admin,
	 * 2，活动的创建者
	 * 3，个人活动的主人
	 * 4，群组活动的群组管理员
	 * 5，集备活动的发起人和主备人
	 */ 	
	public boolean canManageAction(Action action, User user)
	{
		if(user == null || action == null )
		{
			return false;
		}
		AccessControlService acs = (AccessControlService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("accessControlService");
		
		if(acs.isSystemAdmin(user))
		{
			return true;
		}
		
		if(user.getUserId() == action.getCreateUserId())
		{
			return true;
		}
		
		// 如果是群组活动
		if(action.getOwnerType().equalsIgnoreCase("group"))
		{
			GroupMember gm = this.groupDao.getGroupMemberByGroupIdAndUserId(action.getOwnerId(), user.getUserId());
			if(gm == null)
			{
				return false;
			}
			else
			{
				if(gm.getGroupRole() < GroupMember.GROUP_ROLE_VICE_MANAGER)
				{
					return false;
				}
				else
				{
					return true;
				}				
			}
		}
		
		if(action.getOwnerType().equalsIgnoreCase("preparecourse"))
		{
			PrepareCourse prepareCourse = this.prepareCourseDao.getPrepareCourse(action.getOwnerId());
			if(prepareCourse == null)
			{
				return false;				
			}
			else
			{
				if(prepareCourse.getLeaderId() == user.getUserId() || prepareCourse.getCreateUserId() == user.getUserId())
				{
					return true;
				}
				else
				{
					return false;
				}
			}			
		}		
		return false;
	}	
}
