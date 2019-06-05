package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsService;


/**
 * 论坛主题的界面
 */
public class BoardAction extends ManageBaseAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 8333367997661607305L;

	/** 论坛服务 */
	private BbsService bbsService;

	/** 论坛服务的set方法 */
	public void setBbsService(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	protected String execute(String cmd) throws Exception {
		/** 得到頁面的参数 */
		if (cmd == null || cmd.length() == 0)
			cmd = "list";

		if ("list".equalsIgnoreCase(cmd)) {
			return list();
		} else if ("del".equalsIgnoreCase(cmd)) {
			return delete();
		} else if ("view".equalsIgnoreCase(cmd)) {
			return view();
		}
		return super.unknownCommand(cmd);
	}

	/**
	 * 列出版面中的主题列表.
	 * 
	 * @return
	 */
	private String list() throws Exception {
		/** 登陆验证 */
		/*
		 * if(!isUserLogined()) { return LOGIN; }
		 */

		/** 登陆用户Id */
		// super.getLoginUser();
		/** 构造查询参数和分页参数 */
		int page = param_util.safeGetIntParam("page", 1);
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(10);
		pager.setItemNameAndUnit("主题", "个");

		int boardId = param_util.safeGetIntParam("boardId");
		Object topic_list = this.bbsService.getTopicDataTable(boardId, pager);
		request.setAttribute("topic_list", topic_list);
		request.setAttribute("pager", pager);

		return LIST_SUCCESS;
	}

	/**
	 * 刪除一個主題.
	 * 
	 * @return
	 */
	private String delete() {

		List<Integer> ids = param_util.safeGetIntValues("mytopic");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要删除的评论");
			return ERROR;
		}

		// 循环删除.
		for (Integer topicId : ids) {
			Topic topic = bbsService.getTopicById(topicId);
			bbsService.deleteTopic(topic);
			addActionMessage("帖子 " + topic.getTitle() + " 已经删除");
		}
		return SUCCESS;
	}

	private String view() throws Exception {
		int boardId = param_util.safeGetIntParam("boardId");
		int topicId = param_util.safeGetIntParam("topicId");
		Topic topic = bbsService.getTopicById(topicId);
		request.setAttribute("boardId", boardId);
		request.setAttribute("topic", topic);
		return "View_Success";

	}
}
