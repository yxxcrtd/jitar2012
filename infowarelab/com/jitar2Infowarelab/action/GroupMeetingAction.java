package com.jitar2Infowarelab.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jitar2Infowarelab.model.Attendee;
import com.jitar2Infowarelab.model.CreatedMeeting;
import com.jitar2Infowarelab.model.MeetingObjectType;
import com.jitar2Infowarelab.utils.Utils;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;

/**
 * 协助组的视频会议
 * @author dell
 *
 */
public class GroupMeetingAction extends MeetingAction {

	private static final long serialVersionUID = -1512645660768682634L;
	
	private static final String obj = MeetingObjectType.OBJECT_TYPE_GROUP;
	
	private GroupService groupService;
	
	private Integer groupId;
	
	private String meetingsurl;
	
	private Group group;
	@Override
	protected String execute(String cmd) throws Exception {
		// 判断用户登录.
		if (isUserLogined() == false) return LOGIN;
		
		this.groupId = param_util.safeGetIntParam("groupId");
        if (this.groupId == 0){
            addActionError("缺少参数groupId,不能加载协作组对象。");
            return ERROR;
        }	
        
        this.group = this.groupService.getGroup(this.groupId);
        
        if(null == this.group){
            addActionError("不能加载协作组对象。");
            return ERROR;
        }
        
        request.setAttribute("group", this.group);
        
		// 以下操作需要协作组管理员/副管理员权限.
		//if (canManageMember() == false)
		//	return ERROR;
        
		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equals(cmd)){
			return list();
		}else if("show".equals(cmd)){
			return showMeeting();
		}else if ("deletefile".equals(cmd)){
			return deletefile();
		}else if ("add".equals(cmd)){
			return add();
		}else if ("uploadify".equals(cmd)){
			return uploadify();
		}else if ("modify".equals(cmd)){
			return modifyMeetings();
		}else if ("edit".equals(cmd)){
			return editMeetings();
		}else if ("delete".equals(cmd)){
			return deleteMeetings();
		}else if ("save".equals(cmd)){
			return save();
		}else if ("startmeeting".equals(cmd)){
			return startmeeting();
		}
		return unknownCommand(cmd);
	}
	
	private String deletefile(){
		return super.deleteMeetingsfile();
	}
	
	private String showMeeting(){
		return super.showMeetingFile();
	}
	
	
	private String startmeeting(){
		int Id = param_util.getIntParam("id");
		if(Id==0){
            addActionError("缺少id。");
			return ERROR;
		}	
		
		Meetings m = this.getMeetingsService().getMeetings(Id);
		if(null == m){
            addActionError("没有找到会议。");
			return ERROR;
		}

		String startUrl = this.getInfowarelabService().startMeeting(m.getHostName(),m.getHostName(),m.getConfKey(),m.getPasswd(),"");
		if(Utils.isNullorBlank(startUrl)){
			addActionError(this.getInfowarelabService().getReason());
			return ERROR;
		}
		String [] urls = startUrl.split("\\|");
		String url = urls[0] + "&token="+urls[1]; 
		if(!Utils.isNullorBlank(url)){
			this.meetingsurl = url;
			return "redirect";
		}else{
			return ERROR;
		}
	}
	
	private String deleteMeetings(){
		return super.delete();
	}
	
	private String save(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			Meetings m = new Meetings();
			m.setObj(this.obj);
			m.setObjId(this.groupId);
			return super.add_save(m);
		}else{
			
			String returnString =  super.update_save();
			if(returnString.equals(SUCCESS)){
				//addActionMessage(""");
				addActionLink("返回列表", "groupMeeting.action?cmd=list&groupId=" + this.groupId, "_self");
				return SUCCESS;
			}else{
				return returnString;
			}
			
		}
	}
	
	private String list(){
		List<Meetings> meetings = this.getMeetingsService().getMeetings(obj, groupId);
		//对每个会议检查状态？？？
		List<Meetings> newMeets = new ArrayList<Meetings> ();
		for(int i = 0;i < meetings.size();i++){
			Meetings m = meetings.get(i);
			if(isUserLogined()){
				if(m.getStatus()==null || m.getStatus()!=2){	//2==已经结束      0-未开始  1-开始中 3-已经锁定
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
			newMeets.add(m);
		}
        request.setAttribute("meetings_list", newMeets);		
        return LIST_SUCCESS;
	}
	
	/**
	 * 创建一个视频会议
	 * @return
	 */
	private String add(){
		//增加
		Meetings m = new Meetings();
		m.setObj(this.obj);
		m.setObjId(this.groupId);
		request.setAttribute("meetings", m);
		return "add";
	}
	

	
	public GroupService getGroupService(){
		return this.groupService;
	}
	
	public void setGroupService(GroupService groupService){
		this.groupService = groupService;
	}

	public String getMeetingsurl() {
		return meetingsurl;
	}

	public void setMeetingsurl(String meetingsurl) {
		this.meetingsurl = meetingsurl;
	}
}
