package cn.edustar.jitar.service;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionReply;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.ActionUserUnit;
import cn.edustar.jitar.pojos.User;

import java.util.List;

public interface ActionService {

	/**
	 * 创建、修改一个活动
	 * @param action
	 */
	public void addAction(Action action);
	
	/**
	 * 删除一个活动
	 * @param action
	 */
	
	public void deleteAction(Action action);
	
	public void saveAction(Action action);	
	public List<Action> getActionList(ActionQueryParam param, Pager pager);	
	public Action getActionById(int actionId);
	public Action getActionByActionUserId(int actionUserId);
	public void updateActionUserStatById(int actionId);
	
	public void addActionUser(ActionUser actionUser);
	
	public int getUserCountByActionId(int actionId);
	
	public boolean userIsInAction(int userId, int actionId);
	public ActionUser getActionUserByUserIdAndActionId(int userId, int actionId);
	//作废
	public List<ActionUser> getActionUserIdListByActionId(int actionId);
	public void delActionUserById(int actionUserId);
	
	
	public List<ActionUserUnit> getActionUserWithDistUnit();
	public List<ActionUserUnit> getActionUserWithDistUnit(int actionId);
	public List<ActionUserUnit> getActionUserWithDistUnitByUserId(int userId);
	public void addActionUser(int actionId,User user, int inviteUserId);
	

	public ActionUser getActionUserByActionIdAndUserId(int actionId,int userId);
	public void delActionUser(int actionId,int userId);
	public void deleteActionReplyByUserId(int userId);
	public void deleteActionReplyById(int actionReplyId);	
	public void addComment(ActionReply actionReply);
	public void deleteActionReply(int actionReplyId);
	public void updateActionStatus(int statusValue, int actionId);
	
	/**
	 * 判断用户是否可以管理活动
	 * 1，admin,
	 * 2，活动的创建者
	 * 3，个人活动的主人
	 * 4，群组活动的群组管理员
	 * 5，集备活动的发起人和主备人
	 */ 	
	public boolean canManageAction(Action action, User user);
	
	
	
}
