package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.module.Module;
import cn.edustar.jitar.module.ModuleRequestImpl;
import cn.edustar.jitar.module.ModuleResponseImpl;
import cn.edustar.jitar.pojos.Friend;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FriendQueryParam;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.ModuleContainer;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.PageContent;

/**
 * 好友管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 7, 2008 3:00:51 PM
 */
public class FriendAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 2918915538814830305L;
	
	/** cmd = list_black 黑名单列表的缺省成功返回. */
	public static final String LIST_BLACK_SUCCESS = "List_Black_Success";

	/** cmd = add_black 添加黑名单操作默认的成功返回. */
	public static final String ADD_BLACK_SUCCESS = "Add_Black_Success";

	/** cmd = move_to_black 默认将好友移动到黑名单中的成功返回. */
	public static final String MOVE_TO_BLACK = "Move_To_Black";

	/** cmd = move_to_friend 默认将黑名单移动到好友中的成功返回. */
	public static final String MOVE_TO_FRIEND = "Move_To_Friend";

	/** 好友管理服务对象 */
	private FriendService friendService;
    
	/** 消息服务对象 */
	private MessageService messageService;
	
	/** 用户对象 */
	private UserService userService;

	/** 好友对象 */
	private Friend friend = null;

	/** 将要被添加的用户列表 */
	protected List<User> listAddFriend;

	/** 成功登录之后的 Session 中保存的 用户Id */
	private int userId;

	/** 根据'当前登录的用户Id'和'将要被添加的用户Id'得到的 List */
	protected List<Friend> listIsBlack;

	/** 是否是黑名单 */
	private Boolean isBlack;
 
	/* 模块容器服务 */
	private ModuleContainer mod_cont;
	
	/** 短消息标题 */
	private String messageTitle;
	
	/** 短消息内容 */
	private String messageContent;

	Calendar c = Calendar.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute(String cmd) throws Exception {
		// 登录验证.
		if (isUserLogined() == false)
			return LOGIN;
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if ("list".equalsIgnoreCase(cmd) || null == cmd || "".equalsIgnoreCase(cmd)) {
			return List();
		} else if ("list_black".equalsIgnoreCase(cmd)) {
			return List_Black();
		} else if ("add".equalsIgnoreCase(cmd)) {
			return Add();		
		} else if ("add_black".equalsIgnoreCase(cmd)) {
			return Add_Black();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if ("savemulti".equalsIgnoreCase(cmd)) {
			return savemulti();
		} else if ("save_black".equalsIgnoreCase(cmd)) {
			return Save_Black();
		} else if ("del".equalsIgnoreCase(cmd)) {
			return Delete();
		} else if ("del_black".equalsIgnoreCase(cmd)) {
			return Delete_Black();
		} else if ("move_to_black".equalsIgnoreCase(cmd)) {
			return Move_To_Black();
		} else if ("move_to_friend".equalsIgnoreCase(cmd)) {
			return Move_To_Friend();
		} else if ("module".equalsIgnoreCase(cmd)) {
			return this.Module();
		} else if ("send_message".equalsIgnoreCase(cmd)) {
			return send_message();
		}

		return unknownCommand(cmd);
	}

	/**
	 * 显示好友列表
	 * 
	 * @return
	 */
	private String List() {
		
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		
		FriendQueryParam param = new FriendQueryParam();
		param.userId = super.getLoginUser().getUserId();
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("好友", "个");
		
		/** 查询并设置数据到 request 中 */
		Object friend_list = this.friendService.getFriendDataTable(param, pager);
		setRequestAttribute("friend_list", friend_list);
		setRequestAttribute("pager", pager);
		return LIST_SUCCESS;
	}

	/**
	 * 黑名单列表.
	 * 
	 * @return
	 */
	private String List_Black() {
		
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;


		FriendQueryParam param = new FriendQueryParam();
		param.userId = super.getLoginUser().getUserId();
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("黑名单", "个");

		/** 查询并设置数据到 request 中 */
		Object black_list = this.friendService.getBlackDataTable(param, pager);
		setRequestAttribute("black_list", black_list);
		setRequestAttribute("pager", pager);

		return LIST_BLACK_SUCCESS;
	}

	/**
	 * 添加好友.
	 * 
	 * @return
	 */
	private String Add() throws java.lang.Exception {
		
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		
		setRequestAttribute("__referer", getRefererHeader());
		return ADD_SUCCESS;
	}

	/**
	 * 添加黑名单
	 * 
	 * @return
	 */
	private String Add_Black() {
		
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		
		return ADD_BLACK_SUCCESS;
	}
	
	/**
	 * 保存好友
	 * 
	 * @return
	 * @throws Exception
	 */
	private String save() throws Exception {
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		
		int curLoginUserId = getLoginUser().getUserId();
		PrintWriter out = response.getWriter();
		
		User lgUser = this.userService.getUserById(curLoginUserId);
		String strFriendName = param_util.safeGetStringParam("friendName");
		int friendId = param_util.safeGetIntParam("friendId");
		User f = this.userService.getUserByLoginName(strFriendName);
		if(f == null)
		{
			f = this.userService.getUserById(friendId);
		}
		if(f == null)
		{
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('不能加载你选择的好友！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		strFriendName = f.getLoginName();
		friendId = f.getUserId();
		
		if (friendId <= 0 && "".equalsIgnoreCase(strFriendName)) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('请输入您要添加的好友用户名！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		
		
		
		/** 好友说明 */
		String strRemark = param_util.safeGetStringParam("remark");		
		
		/** 检查当前系统中是否存在将要被添加的用户 */
		//listAddFriend = this.userService.findLoginUserExist(strFriendName);		
		/** 如果存在这个用户，否则提示用户不存在. */
		//if (listAddFriend.size() > 0) {
			/** 根据用户登录名得到该用户的Id */
			//User _User = null;
			//_User = (User) listAddFriend.get(0);
			//userId = _User.getUserId();
			// 现在已经从页面中取到了用户ID
			
			
			/** 自己不能将自己添加为好友 */
			if (curLoginUserId == friendId) {
				out.append(PageContent.PAGE_UTF8);
				out.println("<script>alert('自己不能将自己添加为好友！');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}

			/** 验证当前登录的用户的好友列表中是否已经存在该好友。如果存在，给出提示信息，否则添加该好友 */
			if (this.friendService.isDuplicateFriend(curLoginUserId, friendId)) {
				
				/** 在已经存在的好友列表中判断是好友还是黑名单 根据 当前登录的用户Id 和 将要被添加的用户Id 得到该好友信息的黑名单标识 */
				listIsBlack = this.friendService.findIsBlackByUserIdAndFriendId(curLoginUserId, friendId);
				if (listIsBlack.size() > 0) {
					friend = (Friend) listIsBlack.get(0);
					isBlack = friend.getIsBlack();

					if (!isBlack) {
						// this.addActionError(this.getText("已经是您的好友！"));
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('" + strFriendName + " 已经是您的好友了！');window.history.go(-1);</script>");
						out.flush();
						out.close();
						return NONE;
					} else {
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('" + strFriendName + " 已经在您的黑名单中了！');window.history.go(-1);</script>");
						out.flush();
						out.close();
						return NONE;
					}
				}
			} else {
				/** 加好友时给对方发消息 */
				String leavContent  =  param_util.safeGetStringParam("remark");
				Message _Message = new Message();
				_Message.setSendId(curLoginUserId);
				_Message.setReceiveId(friendId);
				_Message.setTitle(lgUser.getTrueName() + " 添加你为好友");
				_Message.setContent(leavContent.replaceAll("\n", "<br />"));
				_Message.setSendTime(c.getTime());
				_Message.setIsRead(false);
				_Message.setIsDel(false);
				_Message.setIsReply(false);
				messageService.sendMessage(_Message);
				
				Friend _Friend = new Friend();
				_Friend.setUserId(curLoginUserId);
				_Friend.setFriendId(friendId);
				_Friend.setAddTime(c.getTime());
				_Friend.setRemark(strRemark);
				_Friend.setIsBlack(false);
				friendService.saveFriend(_Friend);
			}
		//} else {
			//out.append(PageContent.PAGE_UTF8);
			//out.println("<script>alert('对不起，没有这个用户！');window.history.go(-1);</script>");
			//out.flush();
			//out.close();
			//return NONE;
		//}
		
		//保存之后就返回提示信息和转向页面
		/*Out.append(PageContent.PAGE_UTF8);
		  Out.println("<script>alert('  好友添加成功！');window.history.go(-1);</script>");*/
		
		
		/* 这里不要删除，多个地方调用，如果删除，请调整结果的返回的格式！！！ */
		out.append(PageContent.PAGE_UTF8);
		out.println("<script>\r\n");
		out.println("alert('成功将 " + strFriendName + " 添加为好友！');\r\n");
		out.println("if(window.parent && window.parent.document.getElementById('shareDiv')){");
		out.println("window.parent.document.getElementById('shareDiv').style.display = 'none';\r\n");
		out.println("}\r\n</script>");
		out.println("<script>window.history.go(-1);</script>");
		out.flush();
		out.close();
		super.addActionMessage("成功将 '" + strFriendName + "' 添加为好友！");
		return SUCCESS;
	}
	
	private String savemulti() throws Exception {
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		User f,f2;
		int friendId;
		String errMsg = "",strFriendName;
		int curLoginUserId = getLoginUser().getUserId();
		f2 = this.userService.getUserById(curLoginUserId);
		PrintWriter out = response.getWriter();		
		
		List<Integer> friendIds = param_util.safeGetIntValues("friendId");
		if (friendIds.size()<1) {
			errMsg+="请选择用户。";
			super.addActionMessage(errMsg);
			return ERROR;
		}
		
		
		/** 好友说明 */
		String strRemark = param_util.safeGetStringParam("remark");	
		for(int i = 0;i<friendIds.size();i++)
		{
			
			friendId = friendIds.get(i);
			f = this.userService.getUserById(friendId);
			if(f == null)
			{
				errMsg += "用户标识 "+friendId+" 不存在该用户。<br/>";
			}
			else
			{
				strFriendName = f.getLoginName();
				/** 自己不能将自己添加为好友 */
				if (curLoginUserId == friendId) {
					errMsg += "自己不能将自己添加为好友！<br/>";
					continue;
				}
				/** 验证当前登录的用户的好友列表中是否已经存在该好友。如果存在，给出提示信息，否则添加该好友 */
				if (this.friendService.isDuplicateFriend(curLoginUserId, friendId)) {
					
					/** 在已经存在的好友列表中判断是好友还是黑名单 根据 当前登录的用户Id 和 将要被添加的用户Id 得到该好友信息的黑名单标识 */
					listIsBlack = this.friendService.findIsBlackByUserIdAndFriendId(curLoginUserId, friendId);
					if (listIsBlack.size() > 0) {
						friend = (Friend) listIsBlack.get(0);
						isBlack = friend.getIsBlack();

						if (!isBlack) {
							errMsg += strFriendName + " 已经是您的好友了！！<br/>";
							
						} else {
							errMsg += strFriendName + " 已经在您的黑名单中了！！<br/>";
							
						}
						continue;
					}
				} else {
					/** 加好友时给对方发消息 */
					String leavContent  =  param_util.safeGetStringParam("remark");
					Message _Message = new Message();
					_Message.setSendId(curLoginUserId);
					_Message.setReceiveId(friendId);
					_Message.setTitle(f2.getTrueName() + "添加您为好友。");
					_Message.setContent(leavContent.replaceAll("\n", "<br />"));
					_Message.setSendTime(c.getTime());
					_Message.setIsRead(false);
					_Message.setIsDel(false);
					_Message.setIsReply(false);
					messageService.sendMessage(_Message);
					
					Friend _Friend = new Friend();
					_Friend.setUserId(curLoginUserId);
					_Friend.setFriendId(friendId);
					_Friend.setAddTime(c.getTime());
					_Friend.setRemark(strRemark);
					_Friend.setIsBlack(false);
					friendService.saveFriend(_Friend);
				}
				
			}
		}
		if(errMsg.equals("")) errMsg= "操作成功。";
		super.addActionMessage(errMsg);
		return SUCCESS;
	}

	/**
	 * 保存黑名单
	 * 
	 * @return
	 */
	private String Save_Black() throws java.lang.Exception {
		PrintWriter out = response.getWriter();

		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		int curLoginUserId = getLoginUser().getUserId();

		String strBlackName = param_util.safeGetStringParam("blackName");
		if (strBlackName.length() <= 0 && "".equalsIgnoreCase(strBlackName)) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('请输入您要添加的黑名单用户名！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}

		String strRemark = param_util.safeGetStringParam("remark");
		listAddFriend = this.userService.findLoginUserExist(strBlackName); // 检查当前系统中是否存在将要被添加的用户

		/** 如果存在这个用户，否则提示用户不存在。 */
		if (listAddFriend.size() > 0) {
			/** 根据用户名得到该用户的Id */
			User _User = null;
			_User = (User) listAddFriend.get(0);
			userId = _User.getUserId();
			
			// 自己不能将自己添加为黑名单
			if (curLoginUserId - userId == 0) {
				out.append(PageContent.PAGE_UTF8);
				out.println("<script>alert('自己不能将自己添加为黑名单！');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
			
			/** 验证当前登录的用户中是否已经存在该黑名单。如果存在，给出提示信息，否则添加该黑名单 */
			if (this.friendService.isDuplicateBlack(curLoginUserId, userId)) {
				/**
				 * 在已经存在的好友列表中判断是好友还有黑名单。 根据 当前登录的用户Id 和 将要被添加的用户Id
				 * 得到该好友信息的黑名单标识
				 */
				listIsBlack = this.friendService.findIsBlackByUserIdAndFriendId(curLoginUserId, userId);
				if (listIsBlack.size() > 0) {
					friend = (Friend) listIsBlack.get(0);
					isBlack = friend.getIsBlack();

					if (!isBlack) {
						// this.addActionError(this.getText("已经是您的好友！"));
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('" + strBlackName + " 已经是您的好友了！');window.history.go(-1);</script>");
						out.flush();
						out.close();
						return NONE;
					} else {
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('" + strBlackName + " 已经在您的黑名单中了！');window.history.go(-1);</script>");
						out.flush();
						out.close();
						return NONE;
					}
				}
			} else {
				Friend _Friend = new Friend();
				_Friend.setUserId(curLoginUserId);
				_Friend.setFriendId(userId);
				_Friend.setAddTime(c.getTime());
				_Friend.setRemark(strRemark);
				_Friend.setIsBlack(true);
				friendService.saveFriend(_Friend);
			}
		} else {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('对不起，没有这个用户！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		/** 保存之后就返回提示信息和转向页面 */
		/*Out.append(PageContent.PAGE_UTF8);
		Out.println("<script>alert(' 黑名单添加成功！');window.history.go(-1);</script>");*/
		return SUCCESS;
	}

	/**
	 * 删除好友
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String Delete() throws java.lang.Exception {
		String[] idsArray = request.getParameterValues("friendId");
		Integer tempId = 0;
		for (int i = 0; i < idsArray.length; i++) {
			tempId = Integer.valueOf(idsArray[i]);
			
			Friend friend = this.friendService.findById(tempId);
			
			if(null == friend) {
				continue;
			}
			//权限验证
			if(friend.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能删除他人好友！");
				return ERROR;
			}
			
			friendService.delFriend(tempId);
		}
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		out.println("<script>alert('    删除成功！');window.location.href='" + PageContent.getAppPath() + "/manage/friend.action?cmd=list';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 删除黑名单
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String Delete_Black() throws java.lang.Exception {
		String[] idsArray = request.getParameterValues("myblackid");
		Integer tempId = 0;
		for (int i = 0; i < idsArray.length; i++) {
			tempId = Integer.valueOf(idsArray[i]);

			Friend friend = this.friendService.findById(tempId);
			if(friend == null) {
				return ERROR;
			}
			
			//权限验证
			if(friend.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能删除别人的黑名单！");
				return ERROR;
			}			
			friendService.delFriend(tempId);
		}
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		out.println("<script>alert('    删除成功！');window.location.href='" + PageContent.getAppPath() + "/manage/friend.action?cmd=list_black';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 将好友移动到黑名单中
	 * 
	 * @return
	 */
	private String Move_To_Black() throws java.lang.Exception {
		String[] idsArray = request.getParameterValues("friendId");
		Integer tempId = 0;
		for (int i = 0; i < idsArray.length; i++) {
			tempId = Integer.valueOf(idsArray[i]);
			
			Friend friend = this.friendService.findById(tempId);
			if(friend == null) {
				return ERROR;
			}
			//权限验证
			if(friend.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能删除别人的黑名单");
				return ERROR;
			}
			
			friendService.moveToBlack(tempId);
		}
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		out.println("<script>alert('已将选中的好友移到黑名单！');window.location.href='" + PageContent.getAppPath() + "/manage/friend.action?cmd=list';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 将黑名单移动到好友中
	 * 
	 * @return
	 */
	private String Move_To_Friend() throws java.lang.Exception {
		String[] idsArray = request.getParameterValues("myblackid");
		Integer tempId = 0;
		for (int i = 0; i < idsArray.length; i++) {
			tempId = Integer.valueOf(idsArray[i]);
			
			Friend friend = this.friendService.findById(tempId);
			if(friend == null) {
				return ERROR;
			}
			
			//权限验证
			if(friend.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能删除别人的黑名单");
				return ERROR;
			}
			
			friendService.moveToFriend(tempId);
		}
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		out.println("<script>alert('已将选中的黑名单移到好友列表！');window.location.href='" + PageContent.getAppPath() + "/manage/friend.action?cmd=list_black';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String Module() throws Exception {
		String mod_name = this.param_util.safeGetStringParam("module");
		Module mod = mod_cont.getModule(mod_name);
		
		ModuleRequestImpl req = new ModuleRequestImpl("GET", request.getParameterMap());
		ModuleResponseImpl resp = new ModuleResponseImpl(response);

		int myid = Integer.valueOf(request.getParameter("userId"));
		// 根据 用户Id 得到 用户
		User user = userService.getUserById(myid);
		req.setAttribute(ModuleRequestImpl.USER_MODEL_KEY, user);
		mod.handleRequest(req, resp);

		return NONE;
	}
	
	/**
	 * 向选择的好友发送短消息 
	 *
	 * @return
	 * @throws Exception
	 */
	private String send_message() throws Exception {		
		List<Integer> ids = param_util.getIdList("friendId");
		for (Integer receiveId : ids) {
			// userId 暂不判断其他的情况
			sendMessage(receiveId);
		}
		// 返回信息
		PrintWriter out = response.getWriter();
		response.reset();
		response.resetBuffer();
		out.println("OK");
		out.flush();
		out.close();
		return NONE;
	}
	
	/**
	 * 发送短消息
	 */
	private void sendMessage(int receiveId) {		
		Message message = new Message();
		message.setSendId(this.getLoginUser().getUserId());
		message.setReceiveId(receiveId);
		message.setTitle(messageTitle);
		message.setContent(messageContent);		
		message.setSendTime(c.getTime());
		message.setIsRead(false);
		message.setIsDel(false);
		message.setIsReply(false);
		messageService.sendMessage(message);
	}
	
	/** Get and Set */
	public FriendService getFriendService() {
		return this.friendService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getListAddFriend() {
		return listAddFriend;
	}

	public void setListAddFriend(List<User> listAddFriend) {
		this.listAddFriend = listAddFriend;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Friend> getListIsBlack() {
		return listIsBlack;
	}

	public void setListIsBlack(List<Friend> listIsBlack) {
		this.listIsBlack = listIsBlack;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}

	public void setModuleContainer(ModuleContainer mod_cont) {
		this.mod_cont = mod_cont;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
}
