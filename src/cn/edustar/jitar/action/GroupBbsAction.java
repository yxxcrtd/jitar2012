package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.CategoryService;

/*******************************************************************************
 * 群组论坛管理.
 * 
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class GroupBbsAction extends BaseGroupAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 3223874685067508116L;

	/** 论坛服务 */
	private BbsService bbs_svc;

	/** 论坛服务的set方法 */
	public void setBbsService(BbsService bbsService) {
		this.bbs_svc = bbsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证在群组中.
		if (hasCurrentGroup() == false)
			return ERROR;

		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}		
		if (cmd == null || cmd.length() == 0)
			cmd = "topiclist";
		if ("topic_list".equalsIgnoreCase(cmd)) // 修改过.
			return topic_list();
		else if ("reply_list".equalsIgnoreCase(cmd)) // 修改过.
			return reply_list();

		// 验证登录.
		if (isGroupMember() == false)
			return ERROR;

		if ("add_topic".equalsIgnoreCase(cmd))
			return add_topic();
		else if ("edit_topic".equalsIgnoreCase(cmd))
			return edit_topic();
		else if ("delete_topic".equals(cmd))
			return delete_topic();
		else if ("save_topic".equalsIgnoreCase(cmd))
			return save_topic();
		else if ("best_topic".equalsIgnoreCase(cmd))
			return best_topic();
		else if ("unbest_topic".equalsIgnoreCase(cmd))
			return unbest_topic();
		else if ("top_topic".equalsIgnoreCase(cmd))
			return top_topic();
		else if ("untop_topic".equalsIgnoreCase(cmd))
			return untop_topic();
		else if ("add_reply".equalsIgnoreCase(cmd))
			return add_reply();
		else if ("save_reply".equalsIgnoreCase(cmd))
			return save_reply();
		else if ("edit_reply".equals(cmd))
			return edit_reply();
		else if ("delete_reply".equalsIgnoreCase(cmd))
			return delete_reply();
		else if ("best_reply".equalsIgnoreCase(cmd))
			return best_reply();
		else if ("unbest_reply".equalsIgnoreCase(cmd))
			return unbest_reply();

		// TODO: 未测试的 - 当前也不使用的.
		/*
		 * if ("unrefTopic".equalsIgnoreCase(cmd)) { return unref_topic(); } else
		 * if ("refTopic".equals(cmd)) { return ref_topic(); } else if
		 * ("unrefReply".equalsIgnoreCase(cmd)) { return unrefReply(); } else if
		 * ("refReply".equalsIgnoreCase(cmd)) { return ref_reply(); }
		 */
		return unknownCommand(cmd);
	}

	/** 分頁列出主题 */
	private String topic_list() {
		int groupId = group_model.getGroupId();
		
		/** 构造查询参数和分页参数 */
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("主题", "个");

		/** 找出非置顶的主题 */
		DataTable topic_list = group_svc.getGroupTopicList(group_model
				.getGroupId(), pager);

		if (pager.getCurrentPage() == 1) {
			/** 找出置顶主题 */
			DataTable top_topic_list = bbs_svc.getTopTopicList(groupId);

			request.setAttribute("top_topic_list", top_topic_list);
		}

		setRequestAttribute("topic_list", topic_list);
		setRequestAttribute("pager", pager);
		return "Topiclist_Success";
	}

	/**
	 * 显示一个主题及其回复.
	 */
	private String reply_list() {
		// 得到主题对象并检测.
		int topicId = param_util.getIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		if (checkTopic(topic) == false)
			return ERROR;

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

		return "Replylist_Success";
	}

	/**
	 * 编辑修改一个主题的内容.
	 */
	private String edit_topic() {
		// 获得主题.
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		if (checkTopic(topic) == false)
			return ERROR;

		// 检查权限.
		if (canEditTopic(topic) == false) {
			addActionError("您没有编辑该主题的权限.");
			return ERROR;
		}

		request.setAttribute("topic", topic);
		setRequestAttribute("__referer", getRefererHeader());

		return "EditTopic_Success";
	}

	/**
	 * 物理删除一个或多个主题.
	 */
	private String delete_topic() {
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要删除的主题.");
			return ERROR;
		}

		// 循环删除.
		int oper_count = 0;
		for (Integer topicId : ids) {
			// 得到主题.
			Topic topic = bbs_svc.getTopicById(topicId);

			// 验证.
			if (topic == null) {
				addActionError("未能找到标识为 " + topicId + " 的主题.");
				continue;
			}
			if (topic.getGroupId() != group_model.getGroupId()) {
				addActionError("主题 " + topic.toDisplayString()
						+ " 不属于当前协作组, 您不能操作该主题.");
				continue;
			}
			if (canDeleteTopic(topic) == false) {
				addActionError("您没有删除主题 " + topic.toDisplayString() + " 的权限.");
				continue;
			}

			// 实际删除主题.
			bbs_svc.deleteTopic(topic);

			++oper_count;
			addActionMessage("主题" + topic.toDisplayString() + "已删除.");
		}

		addActionMessage("共删除了 " + oper_count + "个主题.");

		return SUCCESS;
	}

	/**
	 * 删除一个或多个回复.
	 */
	private String delete_reply() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("replyId");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要操作的回复.");
			return ERROR;
		}

		// 循环删除.
		int oper_count = 0;
		for (Integer replyId : ids) {
			// 得到回复.
			Reply reply = bbs_svc.getReplyById(replyId);
			if (reply == null) {
				addActionError("未找到标识为 " + replyId + " 的回复.");
				continue;
			}
			if (reply.getGroupId() != group_model.getGroupId()) {
				addActionError("要删除的回复 " + reply.toDisplayString()
						+ " 不属于当前协作组, 您不能删除其它协作组的回复.");
				continue;
			}
			if (canDeleteReply(reply) == false) {
				addActionError("您没有删除回复 " + reply.toDisplayString() + " 的权限.");
				continue;
			}

			// 实际删除.
			bbs_svc.deleteReply(reply);
			++oper_count;

			addActionMessage("回复 " + reply.toDisplayString() + " 已删除");
		}

		addActionMessage("共对 " + oper_count + " 个回复执行了删除操作.");

		return SUCCESS;
	}

	/**
	 * 添加一个主题.
	 */
	private String add_topic() {
		if (isGroupMember() == false)
			return ERROR;
		if (group_member.getStatus() == GroupMember.STATUS_LOCKED) {
			addActionError("您在协作组中的成员身份被管理员锁定了, 不能在协作组论坛中发表主题, 但是能够查看主题.");
			return ERROR;
		}
		String redirect = param_util.safeGetStringParam("redirect");
		// 新建主题对象.
		Topic topic = new Topic();
		topic.setGroupId(group_model.getGroupId());

		setRequestAttribute("topic", topic);
		setRequestAttribute("__referer", getRefererHeader());
		setRequestAttribute("redirect", redirect);
		return "AddTopic_Success";
	}

	/**
	 * 保存一个主题.
	 */
	private String save_topic() throws Exception {
		// 得到主题.
		int topicId = param_util.getIntParam("topicId");
		Topic topic = new Topic();
		if (topicId != 0) {
			topic = bbs_svc.getTopicById(topicId);
			if (checkTopic(topic) == false)
				return ERROR;
		}

		if (topicId == 0) {
			// 验证权限, 必须是协作组成员才能发表主题.
			if (isGroupMember() == false) {
				addActionError("只有协作组组员才能发表主题.");
				return ERROR;
			}
			if (group_member.getStatus() == GroupMember.STATUS_LOCKED) {
				addActionError("您在协作组中的成员身份被管理员锁定了, 不能在协作组论坛中发表主题, 但是能够查看主题.");
				return ERROR;
			}
		} else {
			// 验证权限, 必须是协作组管理员或自己才能修改自己的主题.
			if (canEditTopic(topic) == false) {
				addActionError("您没有编辑该主题的权限.");
				return ERROR;
			}
		}

		String title = param_util.safeGetStringParam("title");
		String content = param_util.safeGetStringParam("content");
		String tags = param_util.safeGetStringParam("tags");
		String redirect = param_util.safeGetStringParam("redirect");
		String frompage = param_util.safeGetStringParam("frompage");

		// 校验.
		if (title == null || "".equalsIgnoreCase(title))
			addActionError("标题不能为空.");
		if (title != null && title.length() > 128)
			addActionError("标题内容过长.");
		if (tags != null && tags.length() >= 128)
			addActionError("标签内容过长.");
		if (super.hasActionErrors())
			return ERROR;

		// 以下字段在发表的时候才设置.
		if (topic.getTopicId() == 0) {
			topic.setGroupId(group_model.getGroupId());
			topic.setUserId(getLoginUser().getUserId());
		}

		// 这些字段修改时候被设置.
		topic.setTitle(title);
		topic.setContent(content);
		topic.setTags(tags);

		// 保存主题.
		if (topic.getTopicId() == 0)
			bbs_svc.createTopic(topic);
		else
			bbs_svc.updateTopic(topic);

		if(!("".equals(frompage)))
		{
			//g/Test123/py/showGroupTopic.py?groupId=39&topicId=120
			response.sendRedirect(SiteUrlModel.getSiteUrl()
					+ "g/" + group_model.getGroupName() + "/py/showGroupTopic.py?groupId=" + topic.getGroupId()
					+ "&topicId=" + topic.getTopicId());
			return NONE;
			
		}
		
		// redirect:页面返回标识 Test123/py/showGroupTopic.py?groupId=39&topicId=120
		if (!("".equals(redirect))) {
			response.sendRedirect(SiteUrlModel.getSiteUrl()
					+ "showGroupTopic.action?groupId=" + topic.getGroupId()
					+ "&topicId=" + topic.getTopicId());
			return NONE;
		}
		addActionMessage("主题 '" + topic.toDisplayString() + " ' 已保存");
		return SUCCESS;
	}

	/**
	 * 编辑修改回复的内容. 
	 */
	private String edit_reply() {
		// 得到主题并验证.
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		if (checkTopic(topic) == false)
			return ERROR;

		// 得到回复并验证.
		int replyId = param_util.safeGetIntParam("replyId");
		Reply reply = bbs_svc.getReplyById(replyId);
		if (checkReply(reply, topic) == false)
			return ERROR;

		// 权限验证.
		if (canEditReply(reply) == false) {
			addActionError("您没有编辑回复 " + reply.toDisplayString() + " 的权限.");
			return ERROR;
		}

		setRequestAttribute("topic", topic);
		setRequestAttribute("reply", reply);
		setRequestAttribute("__referer", getRefererHeader());

		return "EditReply_Success";
	}

	/** 添加一个回复. */
	private String add_reply() {
		// 判断能否回复的权限.
		if (isGroupMember() == false)
			return ERROR;
		if (group_member.getStatus() == GroupMember.STATUS_LOCKED) {
			addActionError("您在协作组中的成员身份被管理员锁定了, 不能在协作组论坛中发表回复.");
			return ERROR;
		}

		// 得到主题并验证.
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		if (checkTopic(topic) == false)
			return ERROR;

		// 创建回复对象, 并放置变量到模板环境中.
		Reply reply = new Reply();
		reply.setGroupId(group_model.getGroupId());
		reply.setUserId(getLoginUser().getUserId());
		reply.setTopicId(topic.getTopicId());

		setRequestAttribute("__referer", getRefererHeader());
		setRequestAttribute("topic", topic);
		setRequestAttribute("reply", reply);

		return "AddReply_Success";
	}

	/**
	 * 保存一个回复.
	 */
	private String save_reply() throws Exception {
		// 获得主题.
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = bbs_svc.getTopicById(topicId);
		String redirect = param_util.safeGetStringParam("redirect"); // 返回标识(返回到前一个页面)

		if (checkTopic(topic) == false)
			return ERROR;

		// 获得回复.
		int replyId = param_util.getIntParam("replyId");
		Reply reply = new Reply();
		if (replyId == 0) {
			// 新回复, 设置 id 类型字段.
			reply.setGroupId(group_model.getGroupId());
			reply.setUserId(getLoginUser().getUserGroupId());
			reply.setTopicId(topic.getTopicId());

			// 验证权限, 协作组成员才能回复.
			if (isGroupMember() == false) {
				addActionError("您不是当前协作组成员, 不能回复此主题.");
				return ERROR;
			}
		} else {
			// 编辑回复.
			reply = bbs_svc.getReplyById(replyId);
			if (checkReply(reply, topic) == false)
				return ERROR;

			// 验证权限.
			if (canEditReply(reply) == false) {
				addActionError("您没有编辑回复 " + reply.toDisplayString() + " 的权限.");
				return ERROR;
			}
		}

		// 获取和校验提交的数据.
		String title = param_util.safeGetStringParam("title");
		String content = param_util.safeGetStringParam("content");
		if (title.length() <= 0 && "".equalsIgnoreCase(title))
			addActionError("标题不能为空");

		if (hasActionErrors())
			return ERROR;

		// 创建主题对象.
		reply.setTitle(title);
		reply.setContent(content);
		reply.setUserId(getLoginUser().getUserId());
		// 保存回复.
		if (reply.getReplyId() == 0) {
			bbs_svc.createReply(reply);
			addActionMessage("回复 ' " + reply.getTitle() + " ' 发表成功.");
		} else {
			bbs_svc.updateReply(reply);
			addActionMessage("回复 ' " + reply.getTitle() + " ' 修改成功.");
		}

		// redirect:页面返回标识
		if (!("".equals(redirect))) {
			response.sendRedirect(super.getRefererHeader());
			return NONE;
		}
		return SUCCESS;
	}

	/**
	 * 设置群组精华主题.
	 * 
	 * @return
	 */
	private String best_topic() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}

		// 循环每个主题进行操作.
		int oper_count = 0;
		for (Integer topicId : ids) {
			Topic topic = bbs_svc.getTopicById(topicId);
			// 验证.
			if (topic == null) {
				addActionError("未能找到标识为 " + topicId + " 的主题.");
				continue;
			}
			if (topic.getGroupId() != group_model.getGroupId()) {
				addActionError("主题 " + topic.toDisplayString()
						+ " 不属于当前协作组, 您不能操作该主题.");
				continue;
			}
			if (canBestTopic(topic) == false) {
				addActionError("您没有对主题 " + topic.toDisplayString() + " 执行设置精华的权限.");
				continue;
			}

			bbs_svc.bestGroupTopic(topic);

			++oper_count;
			addActionMessage("主题 " + topic.toDisplayString() + "' 成功设置为协作组精华主题.");
		}
		addActionMessage("共 " + oper_count + " 个主题设置为协作组精华主题.");

		return SUCCESS;
	}

	/**
	 * 取消群组精华文章.
	 * 
	 * @return
	 */
	private String unbest_topic() {
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}

		// 循环进行操作.
		int oper_count = 0;
		for (Integer topicId : ids) {
			Topic topic = bbs_svc.getTopicById(topicId);
			// 验证.
			if (topic == null) {
				addActionError("未能找到标识为 " + topicId + " 的主题.");
				continue;
			}
			if (topic.getGroupId() != group_model.getGroupId()) {
				addActionError("主题 " + topic.toDisplayString()
						+ " 不属于当前协作组, 您不能操作该主题.");
				continue;
			}
			if (canBestTopic(topic) == false) {
				addActionError("您没有对主题 " + topic.toDisplayString() + " 执行设置精华的权限.");
				continue;
			}

			bbs_svc.unbestGroupTopic(topic);
			++oper_count;

			addActionMessage("主题 '" + topic.getTitle() + "' 取消了协作组精华主题");
		}

		addActionMessage("共 " + oper_count + " 个主题取消了协作组精华主题");

		return SUCCESS;
	}

	/**
	 * 设置群组精华回复.
	 * 
	 * @return
	 */
	private String best_reply() {
		List<Integer> ids = param_util.safeGetIntValues("replyId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的回复.");
			return ERROR;
		}

		// 循环进行操作.
		int oper_count = 0;
		for (Integer replyId : ids) {
			// 获得回复.
			Reply reply = bbs_svc.getReplyById(replyId);
			if (reply == null) {
				addActionError("未找到标识为 " + replyId + " 的回复.");
				continue;
			}
			if (reply.getGroupId() != group_model.getGroupId()) {
				addActionError("要操作的回复 " + reply.toDisplayString()
						+ " 不属于当前协作组, 您不能删除其它协作组的回复.");
				continue;
			}

			// 验证权限.
			if (canDeleteReply(reply) == false) {
				addActionError("您没有操作回复 " + reply.toDisplayString() + " 的权限.");
				continue;
			}

			// 执行操作.
			bbs_svc.bestGroupReply(reply);
			++oper_count;

			addActionMessage("回复 " + reply.toDisplayString() + " 成功设置为精华回复.");
		}
		addActionMessage("共 " + oper_count + " 个回复设置为精华回复.");

		return SUCCESS;
	}

	/**
	 * 取消群组精华贴.
	 * 
	 * @return
	 */
	private String unbest_reply() {
		List<Integer> ids = param_util.safeGetIntValues("replyId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的回复");
			return ERROR;
		}

		// 循环操作.
		int oper_count = 0;
		for (Integer replyId : ids) {
			// 获得回复.
			Reply reply = bbs_svc.getReplyById(replyId);
			if (reply == null) {
				addActionError("未找到标识为 " + replyId + " 的回复.");
				continue;
			}
			if (reply.getGroupId() != group_model.getGroupId()) {
				addActionError("要操作的回复 " + reply.toDisplayString()
						+ " 不属于当前协作组, 您不能删除其它协作组的回复.");
				continue;
			}

			// 验证权限.
			if (canDeleteReply(reply) == false) {
				addActionError("您没有操作回复 " + reply.toDisplayString() + " 的权限.");
				continue;
			}

			bbs_svc.unbestGroupReply(reply);

			++oper_count;
			addActionMessage("回复 " + reply.toDisplayString() + " 取消了精华回复.");
		}

		addActionMessage("共 " + oper_count + " 个回复取消了精华回复.");

		return SUCCESS;
	}

	/**
	 * 将主题置顶.
	 * 
	 * @return
	 */
	private String top_topic() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}

		// 循环操作.
		int oper_count = 0;
		for (Integer topicId : ids) {
			Topic topic = bbs_svc.getTopicById(topicId);

			// 验证.
			if (topic == null) {
				addActionError("未能找到标识为 " + topicId + " 的主题.");
				continue;
			}
			if (topic.getGroupId() != group_model.getGroupId()) {
				addActionError("主题 " + topic.toDisplayString()
						+ " 不属于当前协作组, 您不能操作该主题.");
				continue;
			}
			if (canBestTopic(topic) == false) {
				addActionError("您没有对主题 " + topic.toDisplayString() + " 执行置顶的权限.");
				continue;
			}

			bbs_svc.topTopic(topic);

			++oper_count;
			addActionMessage("主题 '" + topic.getTitle() + "' 已置顶");
		}

		addActionMessage("共 " + oper_count + " 个主题已置顶");

		return SUCCESS;
	}

	/**
	 * 取消置顶.
	 * 
	 * @return
	 */
	private String untop_topic() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}

		// 循环操作.
		int oper_count = 0;
		for (Integer topicId : ids) {
			Topic topic = bbs_svc.getTopicById(topicId);

			// 验证.
			if (topic == null) {
				addActionError("未能找到标识为 " + topicId + " 的主题.");
				continue;
			}
			if (topic.getGroupId() != group_model.getGroupId()) {
				addActionError("主题 " + topic.toDisplayString()
						+ " 不属于当前协作组, 您不能操作该主题.");
				continue;
			}
			if (canBestTopic(topic) == false) {
				addActionError("您没有对主题 " + topic.toDisplayString() + " 执行取消置顶的权限.");
				continue;
			}

			bbs_svc.untopTopic(topic);

			++oper_count;
			addActionMessage("帖子 '" + topic.getTitle() + "' 已取消置顶");
		}

		addActionMessage("共 " + oper_count + " 个主题已取消置顶");

		return SUCCESS;
	}

	/**
	 * 移除对文章的引用.
	 * 
	 * @return
	 */
	public String unref_topic() {

		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}

		// 消除引用(Topic).
		int oper_count = 0;
		for (Integer id : ids) {
			Topic topic = bbs_svc.getTopicById(id);
			bbs_svc.deleteTopicRef(topic);
			++oper_count;

			addActionMessage("主题 '" + topic.getTitle() + "' 已经从群组中移除");

		}
		addActionMessage("共 '" + oper_count + "' 个主题从群组中移除");

		// 更新群组主题数量. TODO
		return SUCCESS;
	}

	/**
	 * 恢复对主题的引用.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ref_topic() {
		// 得到参数
		List<Integer> ids = param_util.safeGetIntValues("topicId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的主题");
			return ERROR;
		}
		// 消除引用(Topic).
		int oper_count = 0;
		for (Integer id : ids) {
			Topic topic = bbs_svc.getTopicById(id);
			bbs_svc.topicRef(topic);
			++oper_count;

			addActionMessage("主题 '" + topic.getTitle() + "' 已经恢复引用");

		}
		addActionMessage("共 '" + oper_count + "' 个主题已经恢复引用");

		return SUCCESS;
	}

	/**
	 * 移除对回复的引用.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String unrefReply() {

		// 得到参数
		List<Integer> ids = param_util.safeGetIntValues("replyId");

		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的回复");
			return ERROR;
		}

		// 消除引用(reply).
		int oper_count = 0;
		for (Integer id : ids) {
			Reply reply = bbs_svc.getReplyById(id);
			bbs_svc.deleteReplyRef(reply);
			++oper_count;
			addActionMessage("回复 '" + reply.getTitle() + "' 已经从群组中移除");
		}
		addActionMessage("共 '" + oper_count + "' 个主题从群组中移除");

		return SUCCESS;
	}

	/**
	 * 恢复对回复的引用.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String ref_reply() {

		// 得到参数
		List<Integer> ids = param_util.safeGetIntValues("replyId");

		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的回复");
			return ERROR;
		}

		// 消除引用(reply).
		int oper_count = 0;
		for (Integer id : ids) {
			Reply reply = bbs_svc.getReplyById(id);
			bbs_svc.ReplyRef(reply);
			++oper_count;
			addActionMessage("回复 '" + reply.getTitle() + "' 已经恢复引用");
		}
		addActionMessage("共 '" + oper_count + "' 个回复已恢复引用");

		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.BaseGroupAction#isGroupMember()
	 */
	@Override
	protected boolean isGroupMember() {
		if (super.isGroupMember() == false) {
			addActionError("您不是当前协作组成员, 从而不能进行发表主题、回复等操作.");
			return false;
		}
		return true;
	}

	/**
	 * 检测 topic 是否存在, 是否未删除, 是否在当前协作组.
	 * 
	 * @param topic
	 * @return
	 */
	private boolean checkTopic(Topic topic) {
		if (topic == null) {
			addActionError("指定标识的主题不存在, 请确定您是从有效链接进入的.");
			return false;
		}
		if (topic.getGroupId() != group_model.getGroupId()) {
			addActionError("主题 " + topic.toDisplayString()
					+ " 不属于当前协作组, 请确定您是从有效链接进入的.");
			return false;
		}
		if (topic.getIsDeleted()) {
			addActionError("该主题已经被删除了.");
			return false;
		}

		return true;
	}

	/**
	 * 检测 reply 是否存在, 是否未删除, 是否在当前协作组, 是否是所给主题回复.
	 * 
	 * @param reply
	 * @param topic
	 * @return
	 */
	private boolean checkReply(Reply reply, Topic topic) {
		if (reply == null) {
			addActionError("指定标识的回复不存在, 请确定您是从有效链接进入的.");
			return false;
		}
		if (reply.getGroupId() != group_model.getGroupId()) {
			addActionError("回复 " + reply.toDisplayString()
					+ " 不属于当前协作组, 请确定您是从有效链接进入的.");
			return false;
		}
		if (reply.getIsDeleted()) {
			addActionError("该回复已经被删除了.");
			return false;
		}
		if (reply.getTopicId() != topic.getTopicId()) {
			addActionError("回复不是指定主题的回复, 请确定您是从有效链接进入的.");
			return false;
		}

		return true;
	}

	/**
	 * 判断当前用户是否有权限删除指定主题.
	 * 
	 * @param topic
	 * @return 当前业务为: 协作组管理员, 副管理员能够删除主题.
	 */
	private boolean canDeleteTopic(Topic topic) {
		// 非本协作组成员肯定不能修改.
		if (group_member == null)
			return false;

		// 协作组管理员能够删除主题.
		if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
			return true;

		return false;
	}

	/**
	 * 判断当前用户是否有权限删除指定回复.
	 * 
	 * @param reply
	 * @return
	 */
	private boolean canDeleteReply(Reply reply) {
		// 非本协作组成员肯定不能修改.
		if (group_member == null)
			return false;

		// 协作组管理员能够删除回复.
		if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
			return true;

		return false;
	}

	/**
	 * 判断当前用户是否有权限编辑指定主题.
	 * 
	 * @param topic
	 * @return 业务为: 1. 非本协作组成员肯定不能修改; 2. 自己能够修改自己的主题; 3. 协作组管理员能够修改所有主题.
	 */
	private boolean canEditTopic(Topic topic) {
		// 非本协作组成员肯定不能修改.
		if (group_member == null)
			return false;

		// 自己能够修改自己的主题;
		if (topic.getUserId() == getLoginUser().getUserId())
			return true;

		// 协作组管理员能够修改所有主题.
		if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
			return true;

		return false;
	}

	/**
	 * 判断当前用户是否有权限设置/取消主题精华. 当前实现为等同于 canDeleteTopic().
	 * 
	 * @param topic
	 * @return
	 */
	private boolean canBestTopic(Topic topic) {
		return canDeleteTopic(topic);
	}

	/**
	 * 判断当前用户是否有权限编辑指定回复.
	 * 
	 * @param topic
	 * @return 业务为: 1. 非本协作组成员肯定不能修改; 2. 自己能够修改自己的回复; 3. 协作组管理员能够修改所有回复.
	 */
	private boolean canEditReply(Reply reply) {
		if (reply == null)
			return false;

		// 非本协作组成员肯定不能修改.
		if (group_member == null)
			return false;

		// 自己能够修改自己的回复;
		if (reply.getUserId() == getLoginUser().getUserId())
			return true;

		// 协作组管理员能够修改所有回复.
		if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
			return true;

		// 其它情况皆不能修改.
		return false;
	}
}

/*
 * update t set replyCount = (select count(*) from bbs_reply r where
 * t.topicId=r.topicId) from bbs_topic t
 */