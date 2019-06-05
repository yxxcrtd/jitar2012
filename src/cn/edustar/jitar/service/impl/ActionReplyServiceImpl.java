package cn.edustar.jitar.service.impl;

import cn.edustar.jitar.dao.ActionReplyDao;
import cn.edustar.jitar.pojos.ActionReply;
import cn.edustar.jitar.service.ActionReplyService;

public class ActionReplyServiceImpl implements ActionReplyService {
	
	private ActionReplyDao act_rpl_dao;
	
	public ActionReplyDao getActionReplyDao()
	{
		return this.act_rpl_dao;
	}
	
	public void setActionReplyDao(ActionReplyDao act_rpl_dao)
	{
		this.act_rpl_dao = act_rpl_dao;
	}
	
	
	public void addComment(ActionReply actionReply)
	{
		this.act_rpl_dao.addComment(actionReply);
	}
}
