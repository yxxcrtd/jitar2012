package com.jitar2Infowarelab.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.action.AbstractBasePageAction;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.MeetingsService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;

import com.chinaedustar.fcs.bi.enums.DeleteSrcEnum;
import com.chinaedustar.fcs.bi.enums.ObjectEnum;
import com.chinaedustar.fcs.bi.exception.BiException;
import com.chinaedustar.fcs.bi.service.BiServiceImpl;
import com.chinaedustar.fcs.bi.vo.TaskVo;
import com.huawei.eie.api.sm.SMS;
import com.jitar2Infowarelab.model.Attendee;
import com.jitar2Infowarelab.model.CreatedMeeting;
import com.jitar2Infowarelab.service.InfowarelabService;
import com.jitar2Infowarelab.utils.Utils;

public abstract class MeetingAction extends AbstractBasePageAction {

	private static final long serialVersionUID = 2916144178278274537L;

	private InfowarelabService infowarelabService;
	private UserService userService;
	private MeetingsService meetingsService;
	private MessageService messageService;
	
	private File file;
	private String fileFileName;
	private String fileContentType;	
	
	public String update_save(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			addActionError("缺少会议的Id.");
			return ERROR;
		}
		
		Meetings  m = this.getMeetingsService().getMeetings(Id);
		if( null == m){
			addActionError("没有找到会议.");
			return ERROR;
		}
		
		m = combineMeetings(m);
		if( null == m){
			return ERROR;
		}		

		//参会人员
		List<Attendee> listAttendee = combineAttendees();
		//if(listAttendee == null ){
		//	return ERROR;	
		//}
		
		
		if(!Utils.isNullorBlank(m.getConfKey())){
			//更新到会议服务器上
			try {
				boolean b = this.getInfowarelabService().updateReserveMeeting(m.getConfKey(),
						m.getStartTime(), m.getEndTime(), listAttendee, 
						m.getSubject(), m.getHostName(), m.getCreator(),
						m.getOpenType(),m.getPasswd(),m.getBeforehandTime(),m.getAgenda(),m.getAttendeeAmount());
				if(!b){
					addActionError(this.getInfowarelabService().getReason());
					return ERROR;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		
		this.getMeetingsService().updateMeetings(m);
		
		//发消息
		ArrayList<String> phones = new ArrayList<String>(); //得到参会者的手机电话，以备发送短信
		
		//发消息
		String content = "视频会议'" + m.getSubject() + "'将于"+m.getStartTime().toString()+"进行。会议室密码是"+ m.getPasswd() +",请准时参加视频会议.<a href='"+m.getAttendeeUrl()+"'>会议地址</a>";
		int senderId = this.getLoginUser().getUserId();
		if(listAttendee!=null){
			for(int i = 0; i < listAttendee.size() ; i++){
				Attendee attendee = listAttendee.get(i);
				Message msg = new Message();
			    msg.setSendId(senderId);
			    msg.setReceiveId(attendee.getUserId());
			    msg.setTitle("请准时参加视频会议");
			    msg.setContent(content);		
			    this.messageService.sendMessage(msg);
			    
			    //得到参会者的手机电话，以备发送短信
			    if(attendee.getMobilePhone()!=null){
				    if(attendee.getMobilePhone().length() > 0){
				    	phones.add(attendee.getMobilePhone());
				    }
			    }
			}
		}
		
		String hostId =  param_util.safeGetStringParam("hostId");
		//主持人也通知一份 
		if(hostId.length()>0){
			User hostUser = this.userService.getUserById(Integer.parseInt(hostId));
			if(hostUser!=null){
				if(hostUser.getMobilePhone()!=null){
					if(hostUser.getMobilePhone().length()>0){
						phones.add(hostUser.getMobilePhone());
					}
				}
			}
		}
		if(Utils.getInitParameter("DBSMProxy_SMS_User")!=null){
			if(Utils.getInitParameter("DBSMProxy_SMS_User").length()>0){
			    if(phones.size() > 0){
				    String[] CellPhone = new String[phones.size()];
				    phones.toArray(CellPhone);
				    //发送手机短信
				    SMS.SendSMS(request, CellPhone, content);
			    }		
			}
		}
		return SUCCESS;
		
	}
	
	private void setResourceList(Meetings m){
		//得到资源列表   resource_list
		String resId = m.getResourceIds();
		if(!Utils.isNullorBlank(resId)){
			ResourceQuery qry = new ResourceQuery(" r.resourceId, r.href, r.title, r.fsize, r.createDate,r.auditState ");
		    qry.resourceIds = resId;
		    qry.auditState=null;
		    List resource_list = qry.query_map();
		    setRequestAttribute("resource_list", resource_list);
		}
		
	}
	/**
	 * 根据红杉树视频平台，更新会议室状态
	 * @param m
	 */
	public void updateMeetingStatus(Meetings m){
		if(m.getStatus()!=2 && m.getStatus()!=0){	//2==已经结束      0-未开始  1-开始中 3-已经锁定
			String st = this.getInfowarelabService().readMeetingStatus(m.getConfKey());
			if(st!=null){
				//更新状态
				if(null == m.getStatus()){
					m.setStatus(Integer.parseInt(st));
					this.getMeetingsService().updateMeetings(m);
				}else if(m.getStatus()!=Integer.parseInt(st)){
					m.setStatus(Integer.parseInt(st));
					this.getMeetingsService().updateMeetings(m);
				}
			}
		}		
	}
	/**
	 * 更新所有的 视频会议 
	 */
	public void updateAllMeetingStatus(){
		List<Meetings> meetings = this.getMeetingsService().getUnfinishedMeetings();
		for(int i = 0;i < meetings.size();i++){
			Meetings m = meetings.get(i);
			updateMeetingStatus(m);
		}	
	}
	public String modifyMeetings(){
		int Id = param_util.getIntParam("Id");
        if (Id == 0){
            addActionError("缺少参数Id,不能加载会议对象。");
            return ERROR;
        }
        Meetings m = this.getMeetingsService().getMeetings(Id);
		request.setAttribute("meetings", m);
		
		//得到资源列表   resource_list
		setResourceList(m);
		
		return "modify";	
		
	}
	public String editMeetings(){
		//修改
		int Id = param_util.getIntParam("Id");
        if (Id == 0){
            addActionError("缺少参数Id,不能加载会议对象。");
            return ERROR;
        }
        Meetings m = this.getMeetingsService().getMeetings(Id);
		request.setAttribute("meetings", m);
		return "edit";	
	}
	
	public String uploadify() throws IOException, BiException{
		PrintWriter out = response.getWriter();
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			out.print("b");
			out.flush();
			out.close();
			return NONE;
		}
		
		Meetings  m = this.getMeetingsService().getMeetings(Id);
		if( null == m){
			out.print("b");
			out.flush();
			out.close();
			return NONE;
		}			
		if (null != file || "".equals(fileFileName)) {
			int obj;
			ServletContext sc = request.getSession().getServletContext();			
			String fileConfigPath = sc.getInitParameter("videoUploadPath");
			String mappingPath = sc.getInitParameter("mappingPath");
			
			// 前缀
			String prefix = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
			// 后缀
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length()).toLowerCase();
			// 最后合成视频的原始全路径
			String destString = new StringBuffer().append(fileConfigPath).append(File.separator).append(prefix).append(".").append(suffix).toString();

			// 仅仅是验证上传文件的后缀在不在系统支持的范围内
			if (!CommonUtil.isContainsString("video", suffix)) {
				out.print("e");
				out.flush();
				out.close();
				return NONE;
			}
			
			// 检查目标文件所在的目录是否存在
			File destFile = new File(destString);
			// 如果目标文件所在的目录不存在，则创建父目录
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在的目录失败！");
					out.print("e");
					out.flush();
					out.close();
					return NONE;
				}
			}
			
			// 文件上传，同时也适应 Linux 环境
			FileUtils.copyFile(file, destFile);
			
			m.setHref(destString);
			
			StringBuffer fcs_tar = new StringBuffer().append(fileConfigPath).append(File.separator).append(prefix);
			if (CommonUtil.isContainsString("play", suffix)) {
				m.setFlvHref(new StringBuffer().append(mappingPath).append(prefix).append(".").append(suffix).toString());
				fcs_tar.append(".").append(suffix).toString();
				obj = 3;
			} else {
				m.setFlvHref(new StringBuffer().append(mappingPath).append(prefix).append(".flv").toString());
				fcs_tar.append(".flv").toString();
				obj = 1;
			}
			String thumbNail = new StringBuffer().append(mappingPath).append(prefix).append(".jpg").toString();
			thumbNail = thumbNail.replaceAll("\\\\", "\\/");
			m.setFlvThumbNailHref(thumbNail);
			
			this.getMeetingsService().updateMeetings(m);
			
			// 转换服务
			TaskVo taskVo;
			if (CommonUtil.isContainsString("play", suffix)) {
				taskVo = new TaskVo(m.getSubject(), destString,fileConfigPath + File.separator + prefix + ".jpg", "150*120",ObjectEnum.TASK_OBJECT_IMAGE);
				taskVo.setDeleteSrcEnum(DeleteSrcEnum.NOT_DELETE);
			} else {
				taskVo = new TaskVo(m.getSubject(), destString,fcs_tar.toString(), "150*120",ObjectEnum.TASK_OBJECT_HD);
				taskVo.setDeleteSrcEnum(DeleteSrcEnum.DELETE);
			}
			new BiServiceImpl().send(taskVo);
			
			// 返回
			out.print(m.getId());
			out.flush();
			out.close();
			return NONE;			
		}
		return NONE;
	}
	
	/**
	 * 得到 启动会议的URL
	 * @param confKey
	 * @return
	 */
	public String getMeetingStartUrl(String confKey){
		String url = this.getInfowarelabService().readStartMeetingURL(confKey);
		if(Utils.isNullorBlank(url)){
			addActionError(this.getInfowarelabService().getReason());
			return null;
		}else{
			return url;
		}
	}
	public String deleteMeetingsfile(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			addActionError("缺少会议的Id.");
			return ERROR;
		}
		
		Meetings  m = this.getMeetingsService().getMeetings(Id);
		if( null == m){
			addActionError("没有找到会议.");
			return ERROR;
		}
		
		String filepath =  m.getHref();
		if(!Utils.isNullorBlank(filepath)){
			
			try {
				JitarRequestContext request = JitarRequestContext.getRequestContext();
				File file = new File( request.getServletContext().getRealPath(filepath) );
				file.delete();
			} catch (Exception e) {
				addActionMessage("文件  " + filepath + " 未删除,请手动删除！");
			}			
			m.setHref(null);
			this.getMeetingsService().updateMeetings(m);
		}
		return SUCCESS;		
	}
	public String showMeetingFile(){
		int Id = param_util.getIntParam("id");
		if(Id == 0){
			addActionError("缺少会议的id.");
			return ERROR;
		}
		
		Meetings  m = this.getMeetingsService().getMeetings(Id);
		if( null == m){
			addActionError("没有找到会议.");
			return ERROR;
		}
		
		String filepath =  m.getHref();
		if(Utils.isNullorBlank(filepath)){
			addActionError("会议视频还没有上载.");
			return ERROR;
		}	
		
		//得到资源列表   resource_list
		setResourceList(m);
		
		request.setAttribute("meetings", m);
		return "show";	
		
	}
	public String delete(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			addActionError("缺少会议的Id.");
			return ERROR;
		}
		
		Meetings  m = this.getMeetingsService().getMeetings(Id);
		if( null == m){
			addActionError("没有找到会议.");
			return ERROR;
		}
		if(!Utils.isNullorBlank(m.getConfKey())){
			//去会议服务器上删除
			boolean bDelete = this.getInfowarelabService().deleteMeeting(m.getConfKey());
			if(bDelete==false){
				if(!this.getInfowarelabService().getExceptionID().equals("0x0600001")){
					addActionError(this.getInfowarelabService().getReason());
					return ERROR;
				}
			}
		}
		
		this.getMeetingsService().deleteMeetings(Id);
		
		return SUCCESS;
	}
	
	public String add_save(Meetings m){
		Meetings newMeetings = combineMeetings(m);
		if(newMeetings == null){
			return ERROR;		
		}
		
		//参会人员
		List<Attendee> listAttendee = combineAttendees();
		//if(listAttendee == null ){
		//	return ERROR;	
		//}
		String hostId =  param_util.safeGetStringParam("hostId");
		
		//向视频服务器上提交
		CreatedMeeting  createdMeeting = null;
		try {
			//创建预约会议
			createdMeeting = this.getInfowarelabService().createReserveMeeting(
					newMeetings.getStartTime(), newMeetings.getEndTime(), listAttendee, 
					newMeetings.getSubject(), newMeetings.getHostName(), newMeetings.getCreator(),
					newMeetings.getOpenType(),newMeetings.getPasswd(),newMeetings.getBeforehandTime(),
					newMeetings.getAgenda(),newMeetings.getAttendeeAmount());
		} catch (ParseException e) {
			e.printStackTrace();
			return ERROR;
		}
		
		if(null == createdMeeting){
			addActionError("创建视频会议失败。"+this.getInfowarelabService().getReason());
			return ERROR;
		}
		
		//创建成功，得到会议信息，写入教研数据库
		newMeetings.setConfId(createdMeeting.getConfId());
		newMeetings.setConfKey(createdMeeting.getConfKey());
		newMeetings.setHostUrl(createdMeeting.getHostUrl());
		newMeetings.setAttendeeUrl(createdMeeting.getAttendeeUrl());
		
		this.getMeetingsService().addMeetings(newMeetings);
		
		//发消息
		ArrayList<String> phones = new ArrayList<String>(); //得到参会者的手机电话，以备发送短信
		
		String content = "视频会议'" + newMeetings.getSubject() + "'将于"+newMeetings.getStartTime().toString()+"进行,会议室密码是"+ newMeetings.getPasswd() +",请准时参加视频会议。<a href='"+createdMeeting.getAttendeeUrl()+"'>会议地址</a>";
		int senderId = this.getLoginUser().getUserId();
		if(listAttendee!=null){
			for(int i = 0; i < listAttendee.size() ; i++){
				Attendee attendee = listAttendee.get(i);
				Message msg = new Message();
			    msg.setSendId(senderId);
			    msg.setReceiveId(attendee.getUserId());
			    msg.setTitle("请准时参加视频会议");
			    msg.setContent(content);		
			    this.messageService.sendMessage(msg);
			    
			    //得到参会者的手机电话，以备发送短信
			    if(attendee.getMobilePhone()!=null){
			    	if(attendee.getMobilePhone().length() > 0){
			    		phones.add(attendee.getMobilePhone());
			    	}
			    }
			}
		}
		
		//主持人也通知一份 
		if(hostId.length()>0){
			User hostUser = this.userService.getUserById(Integer.parseInt(hostId));
			if(hostUser!=null){
				if(hostUser.getMobilePhone()!=null){
					if(hostUser.getMobilePhone().length()>0){
						phones.add(hostUser.getMobilePhone());
					}
				}
			}
		}
		//判断是否发送到手机 
		if(Utils.getInitParameter("DBSMProxy_SMS_User")!=null){
			if(Utils.getInitParameter("DBSMProxy_SMS_User").length()>0){
			    if(phones.size() > 0){
				    String[] CellPhone = new String[phones.size()];
				    phones.toArray(CellPhone);
				    //发送手机短信
				    SMS.SendSMS(request, CellPhone, content);
			    }
			}
		}
	    
		addActionMessage("视频会议创建成功");
		return SUCCESS;	
	}
	
	private List<Attendee> combineAttendees(){
	    List<Integer> ids = param_util.getIdList("userIds");
	    if(ids == null || ids.size() == 0){
	      //addActionError("没有选择参会者.");
	      return null;
	    }
	    List<Attendee> attendees =  new ArrayList<Attendee>();  
	    // 循环操作并验证.
	    for(Integer Id : ids){
	      User u = this.getUserService().getUserById(Id);
	      if(null == u){
	        //addActionError("未找到指定标识为 "+Id+" 的用户." );
	        return null;
	      }else{
	    	  Attendee a = new Attendee();
	    	  a.setName(u.getLoginName());
	    	  a.setEmail(u.getEmail());
	    	  a.setUserId(Id);
	    	  a.setMobilePhone(u.getMobilePhone());
	    	  attendees.add(a);
	      }
	    }
	    if(attendees.size() == 0){
	    	//addActionError("请选择参会者.");
	    	return null;
	    }
		return attendees;
	}
	
	/**
	 * 根据提交的信息，组合成一个视频会议对象
	 * @return
	 */
	private Meetings combineMeetings(Meetings m){

		String subject = param_util.safeGetStringParam("subject");
		if(subject.length()==0){
            addActionError("必须输入会议主题。");
			return null;
		}
		m.setSubject(subject);
		
		String startTime = param_util.safeGetStringParam("startTime");
		String endTime = param_util.safeGetStringParam("endTime");
		if(startTime.length()==0){
            addActionError("必须输入会议开始时间。");
			return null;
		}
		if(endTime.length()==0){
            addActionError("必须输入会议结束时间。");
			return null;
		}
		
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date startDate = null;
		try {
			startDate = bartDateFormat.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date endDate = null;
		try {
			endDate = bartDateFormat.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(null == startDate) {
            addActionError("输入正确的会议开始时间。");
			return null;
		}
		if(null == endDate) {
            addActionError("输入正确的会议结束时间。");
			return null;
		}
		
		m.setStartTime(startDate);
		m.setEndTime(endDate);
		
		String hostName = param_util.safeGetStringParam("hostName");
		if(hostName.length() == 0){
            addActionError("必须输入主持人。");
			return null;
		}
		
		m.setCreator(getLoginUser().getLoginName());
		
		m.setOpenType(true);
		m.setAgenda(param_util.safeGetStringParam("agenda"));
		m.setBeforehandTime(param_util.getIntParam("beforehandTime"));
		m.setAttendeeAmount(param_util.getIntParam("attendeeAmount"));
		m.setPasswd(param_util.safeGetStringParam("passwd"));
		m.setConferenceType(0);						//预约会议 
		m.setWebBaseUrl(Utils.getInfowarelab_ServerURL());
		
		m.setAttendeeIds(param_util.safeGetStringParam("userIds"));
		m.setAttendeeNames(param_util.safeGetStringParam("userNames"));
		
		//主持人 
		int hostId = param_util.getIntParam("hostId");
	      User u = this.getUserService().getUserById(hostId);
	      if(null == u){
	    	 //当前用户为主持人 
	    	 m.setHostName(getLoginUser().getLoginName()); 
	      }else{
	    	  m.setHostName(u.getLoginName());
	      }
		return m;
	}	
	
	public InfowarelabService getInfowarelabService() {
		return infowarelabService;
	}

	public void setInfowarelabService(InfowarelabService infowarelabService) {
		this.infowarelabService = infowarelabService;
	}

	public MeetingsService getMeetingsService() {
		return meetingsService;
	}

	public void setMeetingsService(MeetingsService meetingsService) {
		this.meetingsService = meetingsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}	
}
