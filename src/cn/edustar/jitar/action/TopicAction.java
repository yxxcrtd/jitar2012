package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 论坛的主题.
 * 
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public class TopicAction extends AbstractServletAction {

	/** srialVersionUID */
	private static final long serialVersionUID = -2891479971339145325L;
	
	/** 获取参数辅助对象 */
	private ParamUtil param_util;
	
	/** 论坛服务 */
	private BbsService bbsService;
	
	/** 论坛服务的set方法 */
	public void setBbsService(BbsService bbsService) {
		this.bbsService = bbsService;
	}
	
	public String execute() throws Exception {
		/** 得到上下文对象*/
		this.param_util = new ParamUtil(getActionContext().getParameters());
		
		/** 得到页面的参数 */
		String cmd = param_util.safeGetStringParam("cmd");
		if(cmd == null || cmd.length() == 0)
			cmd = "list";
		
		if("list".equalsIgnoreCase(cmd)) {
			return list();
		} else if("add".equalsIgnoreCase(cmd)) {
			return add();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if("view".equalsIgnoreCase(cmd)){
			return view();
		} else if ("inser".equalsIgnoreCase(cmd)) {
			return inser();
		}
		return super.unknownCommand(cmd);
	}
	
	private String list() throws Exception {
		
		/** 登陆验证 */
		/*if(!isUserLogined()) {
			return LOGIN;
		}*/
		
		/** 登陆用户Id */
		/*int curLoginUserId = (Integer) this.getSession().getAttribute(User.SESSION_LOGIN_USERID_KEY);*/
		
		/** 构造查询参数的分页参数 */
		int page = param_util.safeGetIntParam("page", 1);
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(10);
		pager.setItemNameAndUnit("回复", "个");
		
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = this.bbsService.getTopicById(topicId);
		if (topic == null) {
			addActionError("主題不存在");
			return ERROR;
		}
		request.setAttribute("topic", topic);
		
		/*Board board = bbsService.getBoardById(topic.getBoardId());
		request.setAttribute("board", board);*/
		
		
		Object reply_list = this.bbsService.getReplyDataTable(topicId, pager);
		request.setAttribute("reply_list", reply_list);
		request.setAttribute("pager", pager);
		
		return LIST_SUCCESS;
	}
	
	
	/**
	 * 增加一個主題
	 * @return
	 */
	private String add() {
		
		
		 
		
		String cmd = param_util.safeGetStringParam("cmd");
		int groupId = param_util.getIntParam("groupId");
		
		request.setAttribute("cmd", cmd);
		request.setAttribute("groupId", groupId);
		return "Add_Success";
	}
	/***
	 * 保存一个主题
	 * @return
	 */
	private String save() {
		Topic topic = collectTopicData();
		String title = topic.getTitle();
		String content = topic.getContent();
		String tags = topic.getTags();
		
		/** 校验 */
		
		if(title.length() <= 0  && "".equalsIgnoreCase(title)) {
			addActionError("标题不能为空!");
		} 
		if(title.length() > 128) {
			addActionError("标题内容过长!");
		}
		if(content.length() <= 0 && "".equalsIgnoreCase(content)) {
			addActionError("主题内容不能为空!");
		}
		if(tags.length() <= 0 && "".equalsIgnoreCase(tags)) {
			addActionError("标签不能为空!");
		}
		if(tags.length() >= 128) {
			addActionError("标签内容过长");
		}
		if (super.hasActionErrors()) return ERROR;
		
		bbsService.createTopic(topic);
		return SUCCESS;
		
	}
	/** 得到要插入的数据 */
	private Topic collectTopicData() {
		Topic topic = new Topic();
		topic.setUserId(1);
		topic.setGroupId(param_util.safeGetIntParam("groupId"));
		topic.setTitle(param_util.safeGetStringParam("title"));
		topic.setContent(param_util.safeGetStringParam("content"));
		topic.setTags(param_util.safeGetStringParam("tags"));
		
		return topic;
	}
	
	/** 保存一个回复 */
	public String inser() {
		int groupId = param_util.safeGetIntParam("groupId");
		int topicId = param_util.safeGetIntParam("topicId");
		String title = param_util.safeGetStringParam("title");
		String content = param_util.safeGetStringParam("content");
		
		Topic topic = bbsService.getTopicById(topicId);
		
		/** 校验数据 */
		if(topic == null) {
			addActionError("回复的主题不存在");
		}
		if(title.length() <= 0 && "".equalsIgnoreCase(title)) {
			addActionError("标题不能为空");
		}
		if(content.length() <= 0 && "".equalsIgnoreCase(content)) {
			addActionError("内容不能为空");
		}
		if (super.hasActionErrors()) 
			return ERROR;
		
		Reply reply = new Reply();
		
		reply.setGroupId(groupId);
		reply.setTopicId(topicId);
		reply.setUserId(1);
		reply.setTitle(title);
		reply.setContent(content);
		
		bbsService.createReply(reply);
		
		return SUCCESS;
	}
	
	/** 查看一个回复内容 */
	private String view() {
		int topicId = param_util.safeGetIntParam("topicId");
		int replyId = param_util.safeGetIntParam("replyId");
		int boardId = param_util.safeGetIntParam("boardId");
		Reply reply = bbsService.getReplyById(replyId);
		
		request.setAttribute("boardId", boardId);
		request.setAttribute("topicId", topicId);
		request.setAttribute("replyId", replyId);
		request.setAttribute("reply", reply);
		
		return "View_Success";
	}
	
}
