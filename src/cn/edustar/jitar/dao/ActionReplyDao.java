package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.ActionReply;
public interface ActionReplyDao {
	
	/** 添加评论 */
	
	public void addComment(ActionReply actionReply);
	public void deleteActionReply(int actionReplyId);

	
	/** 得到评论列表 */
	
	public List<ActionReply> getActionReplyByActionId(int actionId);
}
