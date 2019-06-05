package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.ActionUser;
import cn.edustar.jitar.pojos.ActionUserUnit;
import cn.edustar.jitar.pojos.User;


public interface ActionUserDao {

	public void addActionUser(ActionUser actionUser);
	public List<ActionUserUnit> getActionUserWithDistUnit();
	public List<ActionUserUnit> getActionUserWithDistUnit(int actionId);
	public List<ActionUserUnit> getActionUserWithDistUnitByUserId(int userId);
	
	public void addActionUser(int actionId,User user, int inviteUserId);
	public ActionUser getActionUserByActionIdAndUserId(int actionId,int userId);
	public void delActionUser(int actionId,int userId);
	public void delActionUser(int actionId);
	public void delActionUserById(int actionUserId);
}
