package com.jitar2Infowarelab.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.context.ContextLoader;

import com.jitar2Infowarelab.model.MeetingObjectType;
import com.jitar2Infowarelab.utils.Utils;

import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 集体备课的视频会议  
 * @author dell
 *
 */
public class PrepareCourseMeetingAction extends MeetingAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8796176324333327599L;

	private static final String obj = MeetingObjectType.OBJECT_TYPE_PREPARECOURSE;
	
	private PrepareCourseService prepareCourseService;
	
	private Integer prepareCourseId;
	
	private String meetingsurl;
	
	private PrepareCourse prepareCourse;
	

	
	@Override
	protected String execute(String cmd) throws Exception {
		this.prepareCourseId = param_util.safeGetIntParam("prepareCourseId");
        if (this.prepareCourseId == 0){
            addActionError("缺少参数prepareCourseId,不能得到集体备课对象。");
            return ERROR;
        }	
        
        this.prepareCourse = this.prepareCourseService.getPrepareCourse(this.prepareCourseId);
        
        if(null == this.prepareCourse){
            addActionError("不能加载集体备课对象。");
            return ERROR;
        }
        
        request.setAttribute("prepareCourseFinished", 0);
        if(this.prepareCourse.getEndDate()!=null){
        	//String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        	if(this.prepareCourse.getEndDate().before(new Date())){
        		request.setAttribute("prepareCourseFinished", 1);
        	}else{
        		request.setAttribute("prepareCourseFinished", 0);
        	}
        }
        
        
        boolean bManage = canManage();
        request.setAttribute("prepareCourse", this.prepareCourse);
        request.setAttribute("bManage", bManage);
        
		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equals(cmd)){
			return list();
		}else if("show".equals(cmd)){
			return showMeeting();
		}else if ("saveresource".equals(cmd)){
			if (bManage){
				return saveresource();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("add".equals(cmd)){
			if (bManage){
				return add();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("edit".equals(cmd)){
			if (bManage){
				return editMeetings();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("uploadify".equals(cmd)){
				return uploadify();
		}else if ("modify".equals(cmd)){
			if (bManage){
				return modifyMeetings();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("deletefile".equals(cmd)){
			if (bManage){
				return deletefile();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("delete".equals(cmd)){
			if (bManage){
				return deleteMeetings();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("save".equals(cmd)){
			if (bManage){
				return save();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("startmeeting".equals(cmd)){
			if(isUserLogined()){
				return startmeeting();
			}else{
				addActionError("请先登录。");
				return ERROR;
			}
		}
		return unknownCommand(cmd);
	}
	
	private String showMeeting(){
		return super.showMeetingFile();
	}
	

	
	/**
	 * 为上传上来的指定文件生成服务器端名字
	 * 
	 * @param file
	 *            上传上来的文件
	 * @param oldName
	 *            用户客户端该文件的名字
	 * @return
	 */
	private String createFileName(File file, String oldName) {
		String extension = CommonUtil.getFileExtension(oldName);
		String newName = UUID.randomUUID().toString().toLowerCase();
		return newName + "." + extension;
	}
	
	private boolean canManage(){
        if(this.prepareCourse == null){ return false;}
        if(isUserLogined() == false){ return false;}
        request.setAttribute("loginUser", getLoginUser());
        AccessControlService accessControlService = (AccessControlService)ContextLoader.getCurrentWebApplicationContext().getBean("accessControlService");
        if(accessControlService.isSystemAdmin(this.getLoginUser())){
            return true;
        }
        if (this.prepareCourse.getCreateUserId() == this.getLoginUser().getUserId()  ||
        		this.prepareCourse.getLeaderId() == this.getLoginUser().getUserId()){
            return true;
        }
        return false;
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
			request.setAttribute("meetingsurl", this.meetingsurl);
			return "redirect";
		}else{
			return ERROR;
		}
	}

	
	private String deleteMeetings(){
		return super.delete();
		//return list();
	}
	
	private String deletefile(){
		return super.deleteMeetingsfile();
	}
	
	private String save(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			Meetings m = new Meetings();
			m.setObj(this.obj);
			m.setObjId(this.prepareCourseId);
			String returnV = add_save(m);
			addActionLink("返回", request.getContextPath() + "/p/" + this.prepareCourseId + "/0/");
			//if(returnV.equals(SUCCESS)){
			//	addActionLink("返回", request.getContextPath() + "/p/" + this.prepareCourseId + "/0/");
			//}
			return returnV;
		}else{
			return super.update_save();
		}
	}
	
	private String list(){
		List<Meetings> meetings = this.getMeetingsService().getMeetings(obj, prepareCourseId);
		//对每个会议检查状态？？？
		List<Meetings> newMeets = new ArrayList<Meetings> ();
		for(int i = 0;i < meetings.size();i++){
			Meetings m = meetings.get(i);
			//if(isUserLogined()){
				//根据红杉树视频平台，更新会议室状态
				updateMeetingStatus(m);
			//}
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
		m.setObjId(this.prepareCourseId);
		request.setAttribute("meetings", m);
		return "add";
	}
	
	private String saveresource(){
		String resIds = params.safeGetStringParam("resId");
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
		
		m.setResourceIds(resIds);
		this.getMeetingsService().updateMeetings(m);
		
		//返回到 prepareCourseMeeting.action?cmd=modify&prepareCourseId=1&Id=25
		return modifyMeetings();
	}
	
   public PrepareCourseService getPrepareCourseService(){
	   return this.prepareCourseService;
   }
   
   public void setPrepareCourseService(PrepareCourseService prepareCourseService){
	   this.prepareCourseService = prepareCourseService;
   }
	
	public String getMeetingsurl() {
		return meetingsurl;
	}

	public void setMeetingsurl(String meetingsurl) {
		this.meetingsurl = meetingsurl;
	}
	
	
}