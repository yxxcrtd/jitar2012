package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.edustar.jitar.dao.ActionReplyDao;
import cn.edustar.jitar.pojos.ActionReply;

public class ActionReplyDaoHibernate extends BaseDaoHibernate implements ActionReplyDao {
	
	public void addComment(ActionReply actionReply)
	{
		this.getSession().save(actionReply);
	}
	
	public void deleteActionReply(int actionReplyId)
	{
		ActionReply actionReply = (ActionReply)this.getSession().get(ActionReply.class, actionReplyId);
		if(actionReply != null)
		{
			this.getSession().delete(actionReply);
		}
	}
	
	public List<ActionReply> getActionReplyByActionId(int actionId)
	{
		return null;
	}
}
