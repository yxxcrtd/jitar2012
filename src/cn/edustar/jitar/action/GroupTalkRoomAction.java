package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.ChatMessage;
import cn.edustar.jitar.pojos.ChatRoom;
import cn.edustar.jitar.pojos.ChatUser;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.TalkRoomService;
import cn.edustar.jitar.util.PageContent;

/***
 * 群组聊天室管理
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public class GroupTalkRoomAction extends BaseGroupAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 5873982432546505089L;

	
	
	
	/** 公告服务 */
	@SuppressWarnings("unused")
	private TalkRoomService t_svc;
	
	/** 设置公告服务 */
	public void setTalkRoomService(TalkRoomService t_svc) {
		this.t_svc = t_svc;
	}
	
	
	@Override
	protected String execute(String cmd) throws Exception {

		
		//检查是否登陆
		if(isUserLogined() == false) return LOGIN; 
		
		// 必须有一个群组.
		if (hasCurrentGroup() == false)
			return ERROR;
		
		if("listRoom".equalsIgnoreCase(cmd)) {
			return roomList();
		} else if("addRoom".equalsIgnoreCase(cmd)) {
			return addRoom();
		} else if("saveRoom".equalsIgnoreCase(cmd)) {
			return saveRoom();
		} else if("delRoom".equalsIgnoreCase(cmd)) {
			return delRoom();
		} else if("talkingRoom".equalsIgnoreCase(cmd)) {
			return talkingRoom();
		} else if("chatinfo".equalsIgnoreCase(cmd)) {
			return chatinfo();
		} else if("chatuser".equalsIgnoreCase(cmd)) {
			return chatuser();
		} else if("chatsend".equalsIgnoreCase(cmd)) {
			return chatsend();
			
		} else if("saveMessage".equalsIgnoreCase(cmd)) {
			return saveMessage();
			
		} else if("showprivateinfo".equalsIgnoreCase(cmd)) {
			return showprivateinfo();
		} else if("showpubinfo".equalsIgnoreCase(cmd)) {
			return showpubinfo();
			
		} else if("privateinfo".equalsIgnoreCase(cmd)) {
			return privateinfo();
		} else if("publicinfo".equalsIgnoreCase(cmd)) {
			return publicinfo();
		}
		
		
		return unknownCommand(cmd);
	}
	
	/***
	 * 讨论室列表
	 * @return
	 */
	private String roomList() {
		/** 构造查询参数和分页参数 */
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("聊天信息", "条");
		
		List<ChatRoom> room_list = t_svc.getChatRoomList(pager);
		setRequestAttribute("room_list", room_list);
		setRequestAttribute("group", group_model);
		return "listRoom_Success";
	}
	
	/**
	 * 创建讨论室
	 * @return
	 */
	private String addRoom() {
		int groupId = param_util.safeGetIntParam("groupId");
		setRequestAttribute("groupId", groupId);
		setRequestAttribute("group", group_model);
		
		setRequestAttribute("__referer", getRefererHeader());
		
		return "AddRoom_Success";
	}
	
	/***
	 * 保存讨论室
	 * @return
	 * @throws Exception
	 */
	private String saveRoom() throws Exception {
		PrintWriter out = response.getWriter();
		int groupId = param_util.safeGetIntParam("groupId");
		String roomName = param_util.safeGetStringParam("roomName");
		String roomInfo = param_util.safeGetStringParam("roomInfo");
		
		//数据验证
		if(roomName == null || roomName.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert(' 讨论室名称不能为空! ');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		
		User login_user = super.getLoginUser();
		ChatRoom room = new ChatRoom();
		room.setGroupId(groupId); 
		room.setRoomName(roomName);
		room.setCreaterName(login_user.getLoginName());
		room.setRoomInfo(roomInfo);
		room.setIsClosed(false);
		//保存讨论室
		t_svc.saveRoom(room);
		
		addActionMessage("讨论室" + roomName +"已保存");
		return SUCCESS;
	}
	
	/***
	 * 删除讨论室
	 * @return
	 */
	private String delRoom() {
		List<Integer> ids = param_util.safeGetIntValues("chatroomId");
		if(ids == null || ids.size() == 0) {
			addActionError("未选择要删除的讨论室");
			return ERROR;
		}
		int oper_count = 0;
		for(Integer id : ids) {
			ChatRoom room = t_svc.getChatRoomById(id);
			if(room == null) {
				addActionError("未能找到标识为" + id + "的讨论室");
				continue;
			}
			//删除讨论室
			t_svc.delRoom(room);
			addActionMessage("讨论室 " + room.getRoomId() + " 已删除");
			++oper_count;
		}
		addActionMessage("共删除了" + oper_count + "个聊天室");
		return SUCCESS;
	}
	
	/**
	 * 进入讨论室, 其中用iframe分成了三个块
	 * @return
	 */
	private String talkingRoom() {
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("条", "信息");
		
		int groupId = param_util.safeGetIntParam("groupId");
		int roomId = param_util.safeGetIntParam("roomId");
		
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		setRequestAttribute("chatroom",chatroom);
		setRequestAttribute("groupId",groupId);
		setRequestAttribute("group", group_model);
		return "TalkRoom_Success";
	}
	
	/**
	 * 提供讨论室的信息........(还没写完)
	 * @return
	 */
	private String chatinfo() {
		int roomId = param_util.safeGetIntParam("roomId");
		int groupId = param_util.safeGetIntParam("groupId");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		
		setRequestAttribute("chatroom", chatroom);
		setRequestAttribute("groupId",groupId);
		
		return "Chatinfo_Success";
	}
	
	/**
	 * 提供聊天者的信息......(还没写完)
	 * @return
	 */
	private String chatuser() {
		int groupId = param_util.safeGetIntParam("groupId");
		int roomId = param_util.safeGetIntParam("roomId");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		
		setRequestAttribute("chatroom", chatroom);
		setRequestAttribute("groupId",groupId);
		return "Chatuser_Success";
	}
	
	/**
	 * 提供发送的一些功能.....(还没写完)
	 * @return
	 */
	private String chatsend() {
		
		int roomId = param_util.safeGetIntParam("roomId");
		int groupId = param_util.safeGetIntParam("groupId");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		//ChatMessage chatmessage = t_svc.getChatMessage(ChatMessageId);
		
		setRequestAttribute("chatroom", chatroom);
		setRequestAttribute("groupId",groupId);
		return "Chatsend_Success";
	}
	
	/***
	 * 保存聊天信息
	 * @return
	 */
	private String saveMessage() throws Exception {
		User user = super.getLoginUser();
		int userId = user.getUserId();
		
		String chatUserName = user.getNickName();
		int roomId = param_util.safeGetIntParam("roomId");
		//System.out.println(roomId);
		String message= param_util.safeGetStringParam("msg");
		//System.out.println(message);
		String talkto = param_util.safeGetStringParam("talkto");
		//System.out.println(talkto);
		String act = param_util.safeGetStringParam("act"); // 某人的行为, 例如:说话,请问,歌颂,喝彩等
		//System.out.println(act);
		String color = param_util.safeGetStringParam("color"); //字体的颜色
		//System.out.println();
		String face = param_util.safeGetStringParam("face") ; //贴图
		//System.out.println(face);
		Boolean privateChat  = param_util.safeGetBooleanParam("privateChat");//是否私聊
		//System.out.println(privateChat);
		
		
		if(privateChat == null) 
			privateChat = false;
		if(color == null || color.equals("0")) 
			color = "";
		if(face == null  || face.equals("0") ) 
			face = "";
		if(act == null) 
			act = "";
		
		String actText = "@1对@2说";
		if(act.equals("talk")) actText = "@1对@2说";
		if(act.equals("ask")) actText = "@1恭恭敬敬的对@2说:请问";
		if(act.equals("chant")) actText = "@1对@2歌颂:";
		if(act.equals("cheer")) actText="@1对@2鼓着掌,喝采道：";
		if(act.equals("chuckle")) actText="@1轻笑着对@2说:";
		if(act.equals("demand")) actText="@1对@2要求:";
		if(act.equals("groan")) actText="@1呻吟着对@2说:";
		if(act.equals("grumble")) actText="@1满腹牢骚,对@2说:";
		if(act.equals("hum")) actText="@1对着@2自言自语:";
		if(act.equals("moan")) actText="@1对@2悲叹道:";
		if(act.equals("notice")) actText="@1为了引起@2的注意,说道:";
		if(act.equals("order"))  actText="@1命令@2:";
		if(act.equals("ponder")) actText="@1沉思了一会儿，对@2说:";
		if(act.equals("pout")) actText="@1撅着嘴对@2说:";
		if(act.equals("pray"))  actText="@1对@2祈祷:";
		if(act.equals("request")) actText="@1以无比恳求的神情对@2说:";
		if(act.equals("shout"))  actText="@1突然大叫：@2,";
		if(act.equals("sing")) actText="@1唱着歌，对@2说:";
		if(act.equals("smile"))  actText="@1微笑着对@2说:";
		if(act.equals("swear"))  actText="@1举起手对天发誓，说道:@2，";
		if(act.equals("smirk"))  actText="@1一脸假笑，对@2说:";
		if(act.equals("sob"))  actText="@1对着@2哭哭啼啼:";
		if(act.equals("tease"))  actText="@1对着@2嘲笑道:";
		if(act.equals("whimper"))  actText="@1低头呜咽,口中说道:@2,";
		if(act.equals("yawn"))   actText="@1伸伸腰，打了个哈欠,对@2说,";
		if(act.equals("yell"))  actText="@1突然大喊了一声,对@2叫道：";
		
		PrintWriter out = response.getWriter();
		out.println("<li>UserName="+chatUserName);
		out.println("<li>message="+message);
		out.println("<li>talkto="+talkto);
		out.println("<li>act="+act);
		out.println("<li>color="+color);
		out.println("<li>face="+face);
		out.println("<li>privateChat="+privateChat);
		out.flush();
		out.close();
		
		if(t_svc.getChatUser(user.getUserId(),roomId).getIsSay() == false) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('  你被禁止发言!  ');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		message.replace("'", "''");
		
		@SuppressWarnings("unused")
		Boolean sendAll = false;
		@SuppressWarnings("unused")
		String toUserID="0";
		@SuppressWarnings("unused")
		String toUserName="";
		
		if(talkto.equals("everyone")) {
			sendAll = true;
			toUserID = "0";
			toUserName = "";
		} else {
			toUserID = "talkto"; //TODO
			toUserName = "asdf";//TODO 对谁讲话. 在讨论室中的user.还需要查询.
		}
		
		String faceImg = face;
		if(!color.equals("")) {
			t_svc.updateFontColor(color,roomId,userId);
		}
		
		ChatMessage chatmessage = new ChatMessage();
		chatmessage.setRoomId(roomId);
		chatmessage.setSenderId(userId);   //发送者Id .是否要填toUserId
		chatmessage.setSenderName(user.getNickName()); //发送者名称. 
		chatmessage.setReceiverId(1); 	//接收者Id .TODO (Integer.parseInt(toUserID)
		chatmessage.setReceiverName("asdf");//接收者名称 TODO (toUserName)
		chatmessage.setTalkContent(message); 
		chatmessage.setIsPrivate(privateChat); 
		chatmessage.setIsSendAll(sendAll); 
		chatmessage.setActText(actText); 
		chatmessage.setFaceImg(faceImg);
		t_svc.saveMessage(chatmessage);
		
		return "SaveMessage_Success";
	}
	
	/**
	 * 显示私聊的内容
	 * @return
	 */ 
	private String showprivateinfo() {
		int groupId = param_util.safeGetIntParam("groupId");
		int roomId = param_util.safeGetIntParam("roomId");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		setRequestAttribute("roomId", roomId);
		setRequestAttribute("chatroom", chatroom);
		setRequestAttribute("groupId", groupId);
		
		return "Showprivateinfo_Success";
	}
	
	/**
	 * 显示聊天的公共内容
	 * @return
	 */
	private String showpubinfo() {
		int groupId = param_util.safeGetIntParam("groupId");
		int roomId = param_util.safeGetIntParam("roomId");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		
		setRequestAttribute("chatroom",chatroom);
		setRequestAttribute("roomId", roomId);
		setRequestAttribute("groupId", groupId);
		setRequestAttribute("www", "www.baidu.com");
		
		return "Showchatinfo_Success";
	}
	
	/**
	 * 处理ajax请求. 私聊内容
	 * @return
	 */
	private String privateinfo() throws Exception {
		User user = super.getLoginUser();
		
		int userId = user.getUserId();
		int groupId = param_util.safeGetIntParam("groupId");
		
		int roomId = param_util.safeGetIntParam("roomId");
		String lastdate = param_util.safeGetStringParam("lastDate");
		//System.out.println("lastdate = "+ lastdate);
		
		String from = param_util.safeGetStringParam("from");
		String isprivate = param_util.safeGetStringParam("private");
		ChatRoom chatroom = t_svc.getChatRoomById(roomId);
		setRequestAttribute("groupId", groupId);
		setRequestAttribute("chatroom", chatroom);
		
		String yyyy="0000",mm="00",dd="00",hh="00",ff="00",ss="00";
		if(from==null) from="1";	//public win
		if(isprivate==null) isprivate="0";
		if(from.equals("0") && isprivate.equals("0")) {
			return NONE;
		}
		String sLastDate="";
		Calendar calendar=Calendar.getInstance();
		if((lastdate==null) || (lastdate.equals(""))) {
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE,-10);
			yyyy ="0000"+ calendar.get(Calendar.YEAR);
			mm="00"+ (1+calendar.get(Calendar.MONTH));
			dd="00"+ calendar.get(Calendar.DAY_OF_MONTH);
			hh="00"+ calendar.get(Calendar.HOUR_OF_DAY);
			ff="00"+ calendar.get(Calendar.MINUTE);
			ss="00"+ calendar.get(Calendar.SECOND);
			
			yyyy=yyyy.substring(yyyy.length()-4);
			mm=mm.substring(mm.length()-2);
			dd=dd.substring(dd.length()-2);
			hh=hh.substring(hh.length()-2);
			ff=ff.substring(ff.length()-2);	
			ss=ss.substring(ss.length()-2);		
			sLastDate=yyyy+"-"+mm+"-"+dd+" "+hh+":"+ff+":"+ss;
			
			lastdate = "'"+sLastDate + "'";
		} else {
			sLastDate=lastdate;
			lastdate="'"+lastdate+"'";
		}
		List<ChatMessage> chat_messages = null;
		//公共窗体查询
		if(from.equals("1")) {
			/*if(isprivate.equals("0"))	//私有窗体关闭，则显示公开信息以及对自己的私有消息
			else  私有窗体开启，则公开窗体只显示公开信息
			sql ="select * from g_ChatMessage Where (fromUserID="+userID+" or toUserID="+userID+") 
			and roomID="+roomID+" and  DATEDIFF(s,"+lastdate+ ",sendDate)>0 Order by sendDate";*/
			/***String sql="select * from g_ChatMessage " +
					"Where (private=0 and roomId="+roomId+" " +
					"and DATEDIFF(s,"+lastdate+ ",sendDate)>0) or (roomId="+roomId+" " +
					"and  DATEDIFF(s,"+lastdate+ ",sendDate)>0 " +
					"and private=1 and (fromUserID="+userId+" or toUserID="+userId+")) Order by sendDate";*/
			
			chat_messages = t_svc.getPublicChatMessage(lastdate, roomId, userId);
			
		} else {
			//私有窗体寻求信息
			if(isprivate.equals("0")) return NONE;  //私有窗体关闭则不找了
			
		/*	String sql="select * from g_ChatMessage Where roomID="+roomId+" " +
					"And(fromUserID="+userId+" or toUserID="+userId+") " +
					"and  DATEDIFF(s,"+lastdate+ ",sendDate)>0 Order by sendDate";*/
			
			chat_messages = t_svc.getPrivateChatMessage(lastdate, roomId, userId);
		}
		
		
		String s = "";
		String lastsendDate = "";
		boolean bExistData = false;
		String actText = "";
		String faceImg = "";

		for(ChatMessage msg :  chat_messages) {
			if(msg != null) {
				bExistData=true;
				int senderId = msg.getSenderId();
				String senderName = msg.getSenderName();
				int receiverId = msg.getReceiverId();
				String receiverName = msg.getReceiverName();
				actText = msg.getActText();
				faceImg = msg.getFaceImg();
				if(faceImg==null) faceImg="";
				Timestamp dsendDate = (Timestamp)msg.getSendDate();
				
				calendar.setTime(dsendDate);
				
				yyyy ="0000"+ calendar.get(Calendar.YEAR);
				mm="00"+ (1+calendar.get(Calendar.MONTH));
				dd="00"+ calendar.get(Calendar.DAY_OF_MONTH);
				hh="00"+ calendar.get(Calendar.HOUR_OF_DAY);
				ff="00"+ calendar.get(Calendar.MINUTE);
				ss="00"+ calendar.get(Calendar.SECOND);
				
				yyyy=yyyy.substring(yyyy.length()-4);
				mm=mm.substring(mm.length()-2);
				dd=dd.substring(dd.length()-2);
				hh=hh.substring(hh.length()-2);
				ff=ff.substring(ff.length()-2);	
				ss=ss.substring(ss.length()-2);		
				String sendDate=yyyy+"-"+mm+"-"+dd+" "+hh+":"+ff+":"+ss;
				lastsendDate=""+msg.getChatMessageId(); 
				sendDate=mm+"-"+dd+" "+hh+":"+ff+":"+ss;
				
				String message = msg.getTalkContent();
				if(message==null)	message="";
				//message = ChatInfo.asHTML(message);
				ChatUser chatUser = t_svc.getChatUser(senderId, roomId);
				String fontColor_s = chatUser.getFontColor().toString();
				String fontSize_s = "" + chatUser.getFontSize();
				String fontColor_r = "" + chatUser.getFontColor();
				String fontSize_r = "" + chatUser.getFontSize();
				
				if(!s.equals(""))	s = s+"<br/>";
				String s3 = "";
				String s1 = "<font ";
				if(!fontColor_s.equals("")){
					s1 = s1+" color='"+ fontColor_s +"' ";
				}
				if(!fontSize_s.equals("")){
					s1 = s1+" style='font-size:"+ fontSize_s +"' ";
				}
				s3 = s1;
				if(senderId != 0) {
					s1 = s1+" style='cursor:hand' onmouseout=\"className=''\"  onmouseover=\"className='groupFont2'\"  onclick=\"AddSelectChatUser("+senderId+",'"+senderName+"')\" ";
				}
				s1 = s1+">";
				s3 = s3+">";

				String s2 = "<font ";
				if(!fontColor_r.equals("")){
					s2 = s2+" color='"+ fontColor_r +"' ";
				}
				if(!fontSize_r.equals("")) {
					s2 = s2+" style='font-size:"+ fontSize_r +"' ";
				}
				if(receiverId !=0 ){
					s2 = s2+" style='cursor:hand' onmouseout=\"className=''\"  onmouseover=\"className='groupFont2'\" onclick=\"AddSelectChatUser("+receiverId+",'"+receiverName+"')\" ";
				}
				s2 = s2 + ">";
				
				s = s+"["+sendDate+"]&nbsp;&nbsp;";
				
				String senderusermsg=  s1+ senderName +"</font>";
				String receiverusermsg="";
				if(receiverId !=0 ) {
					receiverusermsg = s2 + receiverName + "</font>";
				} else {
					receiverusermsg = s2 + "所有人" + "</font>";	
				}
						
				String ss1=actText.replace("@1",senderusermsg);
				ss1=ss1.replace("@2",receiverusermsg);
				
				//s=s+s1+fromUserName+"</font>";
				//if(fromUserID!=0)
				//	s=s+"对"+s2+toUserName+"</font>说";
				//s=s+"：";
				
				s = s + ss1;
				s = s + s3 + message + "</font>";
				if(!faceImg.equals("")) {
					s=s+"<img border=0 src=\"/Groups/images/chatroom/smile/"+faceImg+"\">";
				}
			}

			// PrintWriter out = response.getWriter();
			// out.println(s);
			//System.out.println(s);
			
		}
		if (bExistData==false){
			s = "";
		} else{
			s=""+lastsendDate+"|"+s;
		}
		setRequestAttribute("str",s);
		return "getPrivateChatInfo_Success";
	}
	
	/***
	 * 处理ajax请求. 公共聊天信息
	 * @return
	 */
	private String publicinfo() throws Exception {
		setRequestAttribute("str","getpublicchatinfo");
		return "getPublicChatInfo_Success";
	}
	
}