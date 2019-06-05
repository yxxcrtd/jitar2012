package cn.edustar.jitar.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.MessageQuery;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UserService;
/**
 * 消息
 * 
 * @author baimindong
 *
 */
public class MessageAction  extends ManageBaseAction{
	
	private static final long serialVersionUID = -2626593524662968340L;
    private MessageService messageService;
    private FriendService friendService;
    private UserService userService;
	@Override
	public String execute(String cmd) throws Exception {
	    //// 验证登录才可用.
	    if(getLoginUser() == null){
	    	return LOGIN;
	    }
	    if(this.canVisitUser(getLoginUser()) == false){
	    	return ERROR;		
	    }
	    if("".equals(cmd) || cmd == null || "list".equals(cmd) || "inbox".equals(cmd)){   // : //默认显示收件箱.
	      return  inbox(); //         // 收件箱消息列表 .
	    }else if("outbox".equals(cmd)){ // :        // 发件箱消息列表.
	      return  outbox();
	    }else if("trash".equals(cmd)){ //        // 回收站消息列表.
	      return  trash();
	    }
	    //// 以下为未锁定情况下可用.
	    if (canManageBlog(getLoginUser()) == false){
	      return ERROR;
	    }
	    
	    if("write".equals(cmd)){ //            //写短消息.
	      return  write();
	    }else if("send".equals(cmd) || "save".equals(cmd)){ //:           //发送短消息.
	      return  send();
	    }else if("reply".equals(cmd)){ // :          // 回复短消息.
	      return  reply();
	    }else if("delete".equals(cmd)){//         // 删除收件箱中收到的短消息.
	      return  delete();
	    }else if("recover".equals(cmd)){ //        // 恢复回收站中的消息.
	      return  recover();
	    }else if("crash".equals(cmd)){//          // 彻底删除短消息.   
	      return  crash();
	    }else if("senderdel".equals(cmd)){//      //删除发件箱的消息.
	      return  senderdel();
	    }else if("getnew".equals(cmd)){//         //计算有多少条未读消息.
	      return  getNew();
	    }else if("show".equals(cmd)){//           //ajax处理,点击更新未读消息数目.
	      return  show();
	    }
	    addActionError("未知命令：" + cmd);
	    return ERROR;
	}
	
	  // 列出收件箱中的消息.
	private String inbox(){
		MessageQuery query = new MessageQuery(" msg.id, msg.sendId, msg.title, msg.content, msg.sendTime, msg.isRead, msg.isReply,send.loginName,send.trueName,send.email ");
	    query.receiveId = getLoginUser().getUserId();     // 接收者是自己.
	    query.isDel = false;         // 未删除的.
	    
		Pager pager = param_util.createPager();
		pager.setItemName("短消息");
		pager.setItemUnit("条");
		pager.setPageSize(20);
        pager.setTotalRows(query.count());
        
	    List message_list = (List)query.query_map(pager);
	    
	    request.setAttribute("pager", pager);
	    request.setAttribute("message_list", message_list);
	    request.setAttribute("type", "receive");
	    
	    int totalRows = pager.getTotalRows();  // self.getMessageTotalRows();
	    int unreadRows = getMessageUnreadRows();
	    
	    request.setAttribute("totalRows", totalRows);
	    request.setAttribute("unreadRows", unreadRows);
	    return "inbox";
	}
	  
	  // 发件箱箱的消息.
	private String outbox(){
	    // 构造查询.
		MessageQuery query = new MessageQuery(" msg.id, msg.receiveId, msg.title, msg.content, msg.sendTime, msg.isRead, msg.isSenderDel, recv.loginName, recv.trueName, recv.email, recv.blogName ");
	    query.sendId = getLoginUser().getUserId();    // 发送者是自己.
	    query.isSenderDel = false;   // 没有被自己删除.
		Pager pager = param_util.createPager();
		pager.setItemName("短消息");
		pager.setItemUnit("条");
		pager.setPageSize(20);
        pager.setTotalRows(query.count());
	    List message_list = (List)query.query_map(pager);
	    
	    request.setAttribute("pager", pager);
	    request.setAttribute("message_list", message_list);

	    return "outbox";
	}
	  // 回收站消息列表.
	private String trash(){
		MessageQuery query = new MessageQuery(" msg.id, msg.sendId, msg.title, msg.content, msg.sendTime, msg.isRead,msg.isReply, send.loginName, send.trueName, send.email ");
	    //query = self.createQuery()
	    query.receiveId = getLoginUser().getUserId();     // 接收者是自己.
	    query.isDel = true;          // 已经删除的.
	    
		Pager pager = param_util.createPager();
		pager.setItemName("短消息");
		pager.setItemUnit("条");
		pager.setPageSize(20);
        pager.setTotalRows(query.count());
        List message_list = (List)query.query_map(pager);
	    request.setAttribute("pager", pager);
	    request.setAttribute("message_list", message_list);
	    
	    return "trash";
	}
	  
	 
	  // 写消息.
	private String write(){
	    int curLoginUserId = getLoginUser().getUserId();
	    // 得到登陆者的好友列表. 提供给发送短消息的好友(下拉框)列表.
	    DataTable friend_list = friendService.getFriendList(curLoginUserId);
	    request.setAttribute("friend_list", friend_list);
	    return "add";
	}
	  
	  // 发送短消息
	private String send() throws IOException{
	    // 得到参数
	    int curLoginUserId = getLoginUser().getUserId();
	    String redirect = param_util.getStringParam("redirect");
	    String strMessageReceiver = param_util.safeGetStringParam("messageReceiver");
	    String messageTitle = param_util.getStringParam("messageTitle");
	    String strMessageContent = param_util.safeGetStringParam("messageContent");
	    
	    // 验证字段.
	    if("".equals(strMessageReceiver) || strMessageReceiver == null){
	      addActionError("没有给出消息接收者.");
	      return ERROR;
	    }
	    if("".equals(messageTitle) || messageTitle == null){
	      addActionError("消息标题不能为空.");
	      return ERROR;
	    }
	    if ("".equals(strMessageContent) || strMessageContent == null){
	      addActionError("消息内容不能未空.");
	      return ERROR;
	    }

	    // 检查当前系统中是否存在将要被添加的用户.
	    User receiver = userService.getUserByLoginName(strMessageReceiver);
	    if(receiver == null){
	      addActionError("接收者 "+strMessageReceiver+" 不存在, 其不是一个有效的用户登录名.");
	      return ERROR ;
	    }

	    // TODO: 处理黑名单.
	    
	    // TODO: 处理回复标志.
	    
	    // 组装消息发出.
	    Message msg = new Message();
	    msg.setSendId(getLoginUser().getUserId());
	    msg.setReceiveId(receiver.getUserId());
	    msg.setTitle(messageTitle);
	    msg.setContent(strMessageContent);
	    
	    // 执行发送.
	    messageService.sendMessage(msg);

	    // 如果设置了 'redirect' 参数, 则重定向到该页面.
	    if(redirect != null && redirect.length() > 0){
	    	PrintWriter out = response.getWriter();
	        out.write("<script>alert('消息已发送');window.location='" + getRefererHeader() + "';</script>");
	        return NONE;
	    }
	    // 其他操作成功后的处理(返回到前一个页面).
	    addActionLink("返回", "?cmd=inbox");
	    addActionMessage("消息 '"+messageTitle+"' 已发送给了"+receiver.getNickName()+"("+receiver.getLoginName()+").");
	    return SUCCESS;
	    //self.addActionLink返回
	}
	    
	  // 删除发件箱的消息.
	private String senderdel(){
	    // 得到参数.
	    List<Integer> ids = param_util.getIdList("messageId");
	    if(ids == null || ids.size() == 0){ 
	      addActionError("没有选择消息");
	      return ERROR;
	    }
	    // 循环操作并验证.
	    for(Integer msgId : ids){
	    	Message msg = messageService.findById(msgId);
	      if(msg == null){
	        addActionError("未找到指定标识为 "+msgId+" 的短消息." );
	        continue;
	      }
	      if(msg.getSendId() != getLoginUser().getUserId()){
	        addActionError("试图操作其它人发送的短消息.");
	        continue;
	      }
	      messageService.senderDelMessage(msg);    // 发消息者删除发件箱的消息.
	    // end for msgId in ids.
	    }   
	    addActionMessage("操作成功");
	    addActionLink("返回", "?cmd=outbox"); //返回发件箱.

	    return SUCCESS;
	}
	  
	  // 彻底删除短消息.
	private String crash(){
	    List<Integer> ids = param_util.getIdList("messageId");
	    
	    if(ids == null || ids.size() == 0){
	      addActionError("没有选择消息");
	      return ERROR;
	    }
	    // 循环操作并验证.
	    for(Integer msgId : ids){
	    	Message msg = messageService.findById(msgId);
	      if(msg == null){
	        addActionError("未找到指定标识为 "+msgId+" 的短消息." );
	        continue;
	      }
	      if(msg.getReceiveId() != getLoginUser().getUserId()){
	        addActionError("试图操作其它人的短消息.");
	        continue;
	      }
	      messageService.crashMessage(msg);
	    }
	    addActionMessage("操作成功");
	    addActionLink("返回", "?cmd=trash"); //返回回收站.

	    return SUCCESS;
	}
	    
	  // 回复.
	private String reply(){
	    // 得到要回复的短消息对象.
	    int messageId = param_util.getIntParam("messageId");
	    Message message = messageService.findById(messageId);
	    if (message == null){
	      addActionError("未找到指定标识的短消息, 请确定您点击的链接有效.");
	      return ERROR;
	    }
	    if(message.getReceiveId() != getLoginUser().getUserId()){
	      addActionError("不能回复他人的短消息.");
	      return ERROR;
	    }
	    if(message.getSendId() <= 0){
	      addActionError("该短消息是系统消息, 其不能回复.");
	      return ERROR;
	    }
	    
	    User user = userService.getUserById(message.getSendId());
	    request.setAttribute("user", user);
	    request.setAttribute("message", message);
	    
	    return "reply";
	}
	  
	  // 将消息删除至回收站.
	private String delete(){
	    // 得到参数.
	    List<Integer> ids = param_util.getIdList("messageId");
	    
	    if(ids == null || ids.size() == 0){
	      addActionError("没有选择消息.");
	      return ERROR;
	    }
	    // 循环操作并验证.
	    for(Integer msgId : ids){
	      Message msg = messageService.findById(msgId);
	      if(msg == null){
	        addActionError("未找到指定标识为 "+msgId+" 的短消息." );
	        continue;
	      }
	      if(msg.getReceiveId() != getLoginUser().getUserId()){
	        addActionError("试图操作其它人的短消息.");
	        continue;
	      }
	      messageService.moveMessageToRecycle(msg);
	    }
	    // 提示信息.
	    addActionMessage("操作成功");
	    addActionLink("返回", "?cmd=inbox"); //返回收件箱.
	    
	    return SUCCESS;
	}
	  
	  // 恢复回收站中的消息.
	private String recover(){
		List<Integer> ids = param_util.getIdList("messageId");
	    
		if(ids == null || ids.size() == 0){
	      addActionError("没有选择消息.");
	      return ERROR;
		}
	    
	    // 循环操作并验证.
		for(Integer msgId : ids){
	      Message msg = messageService.findById(msgId);
	      if(msg == null){
	        addActionError("未找到指定标识为"+msgId+" 的短消息." );
	        continue;
	      }
	      if(msg.getReceiveId() != getLoginUser().getUserId()){
	        addActionError("试图操作其它人的短消息.");
	        continue;
	      }
	      messageService.unMoveMessageToRecycle(msgId);
		}  
	    addActionMessage("操作成功");
	    addActionLink("返回", "?cmd=trash"); // 返回到回收站.
	    
	    return SUCCESS; 
	}
	  // 个人空间右下角有未读消息的提示.计算有多少条未读消息.
	private String getNew(){
	    int unreadRows = getMessageUnreadRows();;
	    if(unreadRows > 0){ 
	      String xjson = "[{uid:"+getLoginUser().getUserId()+", msgcount:"+unreadRows+"}]";
	      response.setHeader("X-JSON", xjson);
	    }
	    return NONE;
	}
	  
	  // (ajax处理). 点击消息,动态更新未读消息数目.
	private String show() throws IOException{
	    int curLoginUserId = getLoginUser().getUserId();
	    int unReadId = param_util.getIntParam("unreadId");
	    //print "unReadId = ", unReadId
	    if(unReadId == 0){ return NONE;}

	    // 设置该消息已经读取过了.
	    messageService.setMessageIsRead(unReadId, getLoginUser().getUserId());
	    
	    int totalRows = messageService.getTotalMessages(curLoginUserId);
	    int unreadRows = messageService.getUnreadMessages(curLoginUserId);
	    
	    // 直接输出.
	    PrintWriter out = response.getWriter();
	    out.println(""+totalRows+" 条短消息, "+unreadRows+" 条未读" );
	    return NONE;
	}

	  //当前登录用户收到的短消息总数.
	private int getMessageTotalRows(){
	    int totalRows = messageService.getTotalMessages(getLoginUser().getUserId());
	    return totalRows;
	}
	  //当前登录用户收到的未读消息总数.
	private int getMessageUnreadRows(){
	    int unreadRows = messageService.getUnreadMessages(getLoginUser().getUserId());
	    return unreadRows;
	}
	    		
	public MessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public FriendService getFriendService() {
		return friendService;
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
}
