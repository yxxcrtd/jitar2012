package cn.edustar.jitar.dao;

import java.util.List;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Action;
import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.service.ActionQueryParam;

/**
 * 
 * @author admin
 *
 */
public interface ActionDao {

	public void addAction(Action action);
	public void saveAction(Action action);
	public void deleteAction(Action action);
	public Action getActionById(int actionId);
	
	/* 统计活动人数 */
	public void updateActionUserStatById(int actionId);
	
	public Action getActionByActionUserId(int actionUserId);
	public int getUserCountByActionId(int actionId);
	public boolean userIsInAction(int userId, int actionId);
	public ActionUser getActionUserByUserIdAndActionId(int userId, int actionId);
	public List<Action> getActionList(ActionQueryParam param, Pager pager);
	public List<ActionUser> getActionUserIdListByActionId(int actionId);
	public void updateActionStatus(int statusValue, int actionId);
	public void deleteActionReplyByUserId(int userId);
	public void deleteActionReplyById(int actionReplyId);
}
