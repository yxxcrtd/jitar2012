package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.FileCache;
import cn.edustar.jitar.util.PageContent;

/**
 * 留言.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 28, 2008 3:32:16 PM
 */
public class LeavewordAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = -1809852830435553635L;

	/** 留言服务 */
	private LeavewordService lw_svc;

	/** 用户服务 */
	private UserService userService;

	/** 好友服务 */
	@SuppressWarnings("unused")
	private FriendService friendService;

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	/** 用户服务 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/** 留言服务的set方法 */
	public void setLeavewordService(LeavewordService leavewordService) {
		this.lw_svc = leavewordService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	protected String execute(String cmd) throws Exception {
		// 登录验证.
		if (isUserLogined() == false)
			return LOGIN;
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if ("list".equalsIgnoreCase(cmd)) {
			return this.List();
		} else if ("reply".equalsIgnoreCase(cmd)) {
			return leaveword_reply(); //
		} else if ("reply_leaveword".equalsIgnoreCase(cmd)) {
			return reply_leaveword_form();
		}else if ("save_reply".equalsIgnoreCase(cmd)) {
			return save_reply();
		}
		else if ("save_leaveword".equalsIgnoreCase(cmd)) {
			return leaveword_save();
		} else if ("write".equalsIgnoreCase(cmd)) {
			return leaveword_write();
		} else if ("del".equalsIgnoreCase(cmd)) {
			return leaveword_del();
		}

		return super.unknownCommand(cmd);
	}

	/**
	 * 所有留言列表
	 * 
	 * @return
	 */
	private String List() {
		
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		User login_user = super.getLoginUser();

		LeavewordQueryParam param = new LeavewordQueryParam();
		param.userId = getLoginUser().getUserId();

		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("留言", "条");

		Object leaveword_list = lw_svc.getPersonalLeaveWordList(login_user.getUserId(), pager);
		request.setAttribute("leaveword_list", leaveword_list);
		request.setAttribute("pager", pager);

		return LIST_SUCCESS;
	}

	/**
	 * 写留言.
	 * 
	 * @return
	 */
	private String leaveword_write() {
		LeaveWord leaveword = new LeaveWord();
		setRequestAttribute("leaveword", leaveword);
		setRequestAttribute("__referer", getRefererHeader());
		return "AddLeaveword_Success";
	}

	/**
	 * 回复留言.
	 * 
	 * @return
	 */
	private String leaveword_reply() {
		User user = super.getLoginUser();
		int leavewordId = param_util.safeGetIntParam("leavewordId");
		LeaveWord leaveword = lw_svc.getLeaveWord(leavewordId);
		if (leaveword == null) {
			addActionError("未找到标识为 '" + leaveword.getId() + "' 的留言");
			return ERROR;
		}

		// 权限验证
		if (leaveword.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId()
				|| leaveword.getObjId() != user.getUserId()) {
			addActionError("权限不足,不能回复别人的留言");
			return ERROR;
		}

		setRequestAttribute("leaveword", leaveword);
		setRequestAttribute("__referer", getRefererHeader());
		return "AddLeaveword_Success";
	}

	private String reply_leaveword_form(){
		int leavewordId = param_util.safeGetIntParam("leavewordId");
		LeaveWord leaveword = lw_svc.getLeaveWord(leavewordId);
		if (leaveword == null) {
			addActionError("未找到标识为 '" + leaveword.getId() + "' 的留言");
			return ERROR;
		}

		setRequestAttribute("leaveword", leaveword);
		return "ReplyLeavewordForm";
	}
	
	private String save_reply() throws Exception{
		int leavewordId = param_util.safeGetIntParam("leavewordId");
		if(leavewordId == 0){
			addActionError("留言标识为0。");
			return ERROR;
		}
		
		String content = param_util.safeGetStringParam("LeavewordContent");
		if (content == null && content.length() == 0) {
			addActionError("请输入留言内容。");
			return ERROR;
		}
		LeaveWord leaveword = lw_svc.getLeaveWord(leavewordId);
		if(leaveword == null){
			addActionError("不能加载留言。");
			return ERROR;
		}
		User login_user = super.getLoginUser(); // 得到登陆用户
		if (leaveword.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId()
				|| leaveword.getObjId() != login_user.getUserId()) {
			addActionError("不能操作对别的对象的留言");
			return ERROR;
		}
		
		LeaveWord word = new LeaveWord();
		word.setUserId(login_user.getUserId()); // 留言者的Id(即登录者的Id)
		word.setTitle("RE:"+ leaveword.getTitle());
		word.setContent(content.replaceAll("\n", "<br />"));
		word.setCreateDate(new Date());
		word.setObjType(ObjectType.OBJECT_TYPE_USER.getTypeId());

		word.setObjId(leaveword.getUserId()); // 接收者的Id
		word.setReply(null);
		word.setLoginName(login_user.getLoginName());
		word.setNickName(login_user.getTrueName());
		word.setIp(request.getRemoteAddr());
		word.setEmail(login_user.getEmail());
		lw_svc.saveLeaveWord(word);
		this.addActionLink("返回列表", "leaveword.action?cmd=list");
		return SUCCESS;
		
	}
	
	/**
	 * 保存留言.
	 * 
	 * @return
	 */
	private String leaveword_save() throws Exception {

		PrintWriter out = response.getWriter();

		// 得到数据.
		String title = param_util.safeGetStringParam("LeavewordTitle");
		String content = param_util.safeGetStringParam("LeavewordContent");
		String redirect = param_util.safeGetStringParam("redirect");
		// 后台管理中,为了测试,需要填写消息接收者 leavewordReceiver
		// String leavewordReceiver =
		// param_util.safeGetStringParam("LeavewordReceiver");

		/** receiverId是某空间主人的Id */
		int receiverId = param_util.safeGetIntParam("receiverId");

		// 验证数据.
		if (title == null || title.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   留言标题不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}

		if (content == null && content.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   留言内容不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		
		User login_user = super.getLoginUser(); // 得到登陆用户
		User spaceMaster = userService.getUserById(receiverId);
		LeaveWord word = new LeaveWord();
		word.setUserId(login_user.getUserId()); // 留言者的Id(即登录者的Id)
		word.setTitle(title);
		word.setContent(content.replaceAll("\n", "<br />"));
		word.setCreateDate(new Date());
		word.setObjType(ObjectType.OBJECT_TYPE_USER.getTypeId());

		word.setObjId(spaceMaster.getUserId()); // 接收者的Id
		word.setReply(null);
		word.setLoginName(login_user.getLoginName());
		word.setNickName(login_user.getNickName());
		word.setIp(request.getRemoteAddr());
		word.setEmail(login_user.getEmail());

		lw_svc.saveLeaveWord(word);
		FileCache fc = new FileCache();
		fc.deleteUserLeavewordCache(spaceMaster.getLoginName());
		fc = null;
		
		// redirect:页面返回标识.
		if (!("".equals(redirect))) {
			response.sendRedirect(super.getRefererHeader());
			return NONE;
		}
		addActionMessage("留言 '" + word.getTitle() + "' 已保存");
		return SUCCESS;
	}

	/****************************************************************************
	 * 删除留言.
	 * 
	 * @return
	 */
	private String leaveword_del() {
		User login_user = super.getLoginUser();
		List<Integer> ids = param_util.safeGetIntValues("leavewordId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要删除的留言");
			return ERROR;
		}
		int oper_count = 0;
		for (Integer id : ids) {

			LeaveWord leaveword = lw_svc.getLeaveWord(id);
			if (leaveword == null) {
				addActionError("未找到标识为 " + id + " 的留言");
				continue;
			}

			// 权限验证.
			if (leaveword.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId()
					|| leaveword.getObjId() != login_user.getUserId()) {
				addActionError("不能操作对别的对象的留言");
				continue;
			}
			lw_svc.deleteLeaveWord(leaveword);
			addActionMessage("留言 '" + leaveword.getTitle() + "' 已删除");
			++oper_count;
		}
		addActionMessage("共删除了" + oper_count + "条留言");
		return SUCCESS;
	}
}
