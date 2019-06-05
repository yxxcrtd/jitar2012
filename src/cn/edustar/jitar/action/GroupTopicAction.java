package cn.edustar.jitar.action;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsService;

/**
 * @author Yang Xinxin
 */
public class GroupTopicAction extends BaseGroupAction {
	private static final long serialVersionUID = -2715954898414047115L;
	
	/** 论坛服务 */
	private BbsService bbs_svc;
	
	/** 当前所在管理的群组. */
	protected Group group_model;
	Integer groupId = 0;
	
	/** 论坛服务的set方法 */
	public void setBbsService(BbsService bbsService) {
		this.bbs_svc = bbsService;
	}
	
	
	@Override
	protected String execute( String cmd) throws Exception {
		groupId = param_util.safeGetIntParam("groupId");		
		this.group_model = group_svc.getGroupMayCached(groupId);		
		if (this.group_model == null) return "todo";
		setRequestAttribute("group", group_model);
		if("save_replay".equalsIgnoreCase(cmd))
		{
			return saveReplay();
		}
		else
		{	
			return replayList();
		}
	}

	private String saveReplay()
	{
		return "todo";
	}

	private String replayList()
	{
		// 得到主题对象并检测.
		int topicId = param_util.getIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		if (checkTopic(topic) == false) return ERROR;
		
		// 构造查询参数和分页参数.
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("回复", "个");
		
		DataTable reply_list = bbs_svc.getReplyDataTable(topicId, pager);
		
		// 放到模板环境中.
		setRequestAttribute("topic", topic);
		setRequestAttribute("reply_list", reply_list);
		setRequestAttribute("pager", pager);		
		
		// 增加该主题的访问计数.
		bbs_svc.incTopicViewCount(topic, 1);
		return SUCCESS;
		

	}
	/**
	 * 检测 topic 是否存在, 是否未删除, 是否在当前协作组.
	 * @param topic
	 * @return
	 */
	private boolean checkTopic(Topic topic) {
		if (topic == null) {
			addActionError("指定标识的主题不存在, 请确定您是从有效链接进入的.");
			return false;
		}
		if (topic.getGroupId() != group_model.getGroupId()) {
			addActionError("主题 " + topic.toDisplayString() + " 不属于当前协作组, 请确定您是从有效链接进入的.");
			return false;
		}
		if (topic.getIsDeleted()) {
			addActionError("该主题已经被删除了.");
			return false;
		}
		
		return true;
	}
}
