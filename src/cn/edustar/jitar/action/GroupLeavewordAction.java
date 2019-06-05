package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.util.PageContent;

/**
 * 协作组留言管理.
 *
 *
 */
@SuppressWarnings("serial")
public class GroupLeavewordAction extends BaseGroupAction {
	
	/** 留言管理的辅助类 */
	private LeavewordActionHelper lw_helper = new LeavewordActionHelper();
	
	/** 设置留言服务 */
	public void setLeavewordService(LeavewordService lw_svc) {
		lw_helper.setLeavewordService(lw_svc);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证登录, 以及协作组和成员. 
		if (isUserLogined() == false) return LOGIN;
		if (hasCurrentGroupAndMember() == false) return ERROR;
		
		if ("list".equals(cmd))
			return list();
		else if ("reply".equals(cmd))
			return reply();
		else if ("save_reply".equals(cmd))
			return save_reply();
		else if ("delete".equals(cmd))
			return delete();
		else if ("save_leaveword".equalsIgnoreCase(cmd)) {
			return save_leaveword();
		}
		return unknownCommand(cmd);
	}
	
	/**
	 * 列出小组中的留言.
	 * @return
	 */
	private String list() {
		// 构造查询, 分页对象.
		Pager pager = lw_helper.createPager(param_util);
		pager.setItemNameAndUnit("留言", "条");
		pager.setPageSize(20);
		pager.setUrlPattern("groupLeaveword.action?cmd=list&amp;groupId="+ group_model.getGroupId() + "&amp;page={page}");
		LeavewordQueryParam param = lw_helper.createLeavewordQueryParam(param_util);
		param.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId();
		param.objId = group_model.getGroupId();
		
		lw_helper.list(this, pager, param);
		 
		setRequestAttribute("group", group_model);
		
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
		return LIST_SUCCESS;
	}
	
	/***
	 * 保存一个留言
	 * @return
	 */
	@SuppressWarnings("null")
	private String save_leaveword() throws Exception {
		PrintWriter out = response.getWriter();
		
		int groupId = param_util.safeGetIntParam("groupId");
		String redirect = param_util.safeGetStringParam("redirect"); //页面返回标识
		String title = param_util.safeGetStringParam("LeavewordTitle");
		String content = param_util.safeGetStringParam("LeavewordContent");
		
		//数据校验
		if(title == null || title.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   留言标题不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		
		if(content == null && content.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   留言内容不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		
		User user_model = super.getLoginUser();
		
		LeaveWord leaveword = new LeaveWord();
		leaveword.setObjType(ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		leaveword.setObjId(groupId);
		leaveword.setUserId(user_model.getUserId());
		leaveword.setTitle(title);
		leaveword.setContent(content);
		leaveword.setReply(null); //TODO
		leaveword.setLoginName(user_model.getLoginName());
		leaveword.setNickName(user_model.getNickName());
		leaveword.setIp(request.getRemoteAddr());
		leaveword.setEmail(user_model.getEmail());
		
		/** 保存留言 */
		lw_helper.getLeavewordService().saveLeaveWord(leaveword);
		
		
		if(!("".equals(redirect))) {
			response.sendRedirect(super.getRefererHeader());
			return NONE;
		}
		addActionMessage("留言 '" + leaveword.getTitle() + "' 已保存");
		return SUCCESS;
	}
	
	/**
	 * 回复一个留言.
	 * @return
	 */
	private String reply() {
		if (getLeaveWordAndCheck() == false) return ERROR;
		
		setRequestAttribute("leaveword", leaveword);
		setRequestAttribute("group", group_model);
		
		return "Reply_LeaveWord";
	}
	
	/**
	 * 保存回复.
	 * @return
	 */
	private String save_reply() {
		if (getLeaveWordAndCheck() == false) return ERROR;
		
		// 得到提交的留言内容.
		String reply = param_util.safeGetStringParam("reply");
		if (reply == null || reply.length() == 0) {
			addActionError("未给出回复的内容");
			return ERROR;
		}
		
		// 更新留言的回复, 也许用模板生成更省力一些.
		String old_reply = leaveword.getReply();
		if (old_reply == null) old_reply = "";
		String new_reply = old_reply +
			"<div class='leaveword_reply'>" +
			"<div class='leaveword_reply_title'>以下是 " + getLoginUser().getNickName() + " 的回复:</div>" +
			"<div class='leaveword_reply_content'>" +
			reply +
			"</div></div>";
		leaveword.setReply(new_reply);
		leaveword.setReplyTimes(leaveword.getReplyTimes() + 1);
		lw_helper.getLeavewordService().updateLeaveWord(leaveword);
		
		addActionMessage("回复留言成功完成");
		
		return SUCCESS;
	}
	
	/**
	 * 删除一个留言.
	 * @return
	 */
	private String delete() {
		List<Integer> ids = param_util.safeGetIntValues("leavewordId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要删除的留言");
			return ERROR;
		}
		
		int oper_count = 0;
		for (Integer id : ids) {
			// 获得留言.
			LeaveWord leaveword = lw_helper.getLeavewordService().getLeaveWord(id);
			if (leaveword == null) {
				addActionError("未找到标识为 " + id + " 的留言");
				continue;
			}
			
			// 验证.
			if (leaveword.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() ||
					leaveword.getObjId() != group_model.getGroupId()) {
				addActionError("试图操作别的对象的留言, 请确定您是从有效链接进入的");
				continue;
			}
			
			// 执行.
			lw_helper.getLeavewordService().deleteLeaveWord(leaveword);
			
			++oper_count;
		}
		
		addActionMessage("共删除了 " + oper_count + " 条留言");
		
		return SUCCESS;
	}

	/** 当前要操作的留言对象, 在 getLeaveWordAndCheck() 函数中设置 */
	private LeaveWord leaveword;
	
	/**
	 * 得到当前要操作的留言对象, 并检查是否属于此群组.
	 * @return
	 */
	private boolean getLeaveWordAndCheck() {
		// 得到页面参数.
		int id = param_util.getIntParam("leavewordId");
		if (id == 0) {
			addActionError("未给出要操作的留言标识");
			return false;
		}
		
		// 获得留言对象.
		this.leaveword = lw_helper.getLeavewordService().getLeaveWord(id);
		if (this.leaveword == null) {
			addActionError("未找到指定标识的留言, 请确定您是从有效链接进入的");
			return false;
		}
		
		// 验证属于群组.
		if (leaveword.getObjType() != ObjectType.OBJECT_TYPE_GROUP.getTypeId() ||
				leaveword.getObjId() != group_model.getGroupId()) {
			addActionError("试图操作别的对象的留言, 请确定您是从有效链接进入的");
			return false;
		}
		
		return true;
	}
}
