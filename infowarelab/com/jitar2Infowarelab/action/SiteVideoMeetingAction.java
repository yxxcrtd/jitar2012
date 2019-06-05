package com.jitar2Infowarelab.action;

import java.util.Calendar;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.jitar2Infowarelab.model.MeetingObjectType;
import com.jitar2Infowarelab.utils.Utils;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionQuery;
import cn.edustar.jitar.service.MeetingsQuery;
import cn.edustar.jitar.service.UserPowerService;
import cn.edustar.jitar.util.HtmlPager;

public class SiteVideoMeetingAction extends MeetingAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331034819412392578L;
	
	/**
	 * 是站点教研视频频道的
	 */
	private static final String obj = MeetingObjectType.OBJECT_TYPE_CHANNEL;
	
	/**
	 * 默认是0,如果建立了多个导航的视频会议，可以设置这个navId
	 */
	private int navId = 0;
	
	/** 用户角色权限服务 */
	private UserPowerService userPowerService;
	
	private String meetingsurl;
	
	@Override
	protected String execute(String cmd) throws Exception {
		request.setAttribute("head_nav", "videomeeting");
        String ownerType = params.getStringParam("ownerType");
        String showtype = params.getStringParam("showType");
        int currentPage = params.safeGetIntParam("page");
        
        String k = params.getStringParam("k");
        String filter = params.getStringParam("filter");
        if (filter == null || filter.trim().length() == 0) {
            filter = "title";
        }
        if (showtype == null || showtype.length() == 0) {
            showtype = "ready";
        }
        request.setAttribute("showType", showtype);
        request.setAttribute("ownerType", ownerType);
        request.setAttribute("k", k);
        request.setAttribute("filter", filter);
        boolean bManage = canManage();
        if(bManage){
        	request.setAttribute("bManage", true);
        }else{
        	request.setAttribute("bManage", false);
        }
        if (cmd.equals("ajax")) {
        	//updateAllMeetingStatus();   //更新视频会议的状态 
            Pager pager = params.createPager();
            pager.setItemName("视频教研");
            pager.setItemUnit("个");
            pager.setPageSize(10);
            pager.setCurrentPage(currentPage);
            MeetingsQuery qry = new MeetingsQuery(" m.id,m.subject,m.startTime,m.endTime,m.confId,m.confKey,m.hostUrl,"
            		+ "m.attendeeUrl,m.passwd,m.agenda,m.webBaseUrl,m.attendeeIds,m.attendeeNames,m.href,m.flvThumbNailHref ");
            if (showtype.equals("running")) {
                qry.status = 1; // #正在进行 
            } else if (showtype.equals("finish")) {
                qry.status = 2;// #已经完成的
            } else {
                qry.status = 0;// #即将开始的
            }
            qry.k = k;
            pager.setTotalRows(qry.count());
            List meeting_list = (List) qry.query_map(pager);
            request.setAttribute("meeting_list", meeting_list);
            request.setAttribute("pager", pager);

            String html = HtmlPager.render(pager.getTotalPages(), 3, pager.getCurrentPage());
            request.setAttribute("HtmlPager", html);
            return "ajax";
        }else if("show".equals(cmd)){
			return super.showMeetingFile();
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
				return super.deleteMeetingsfile();
			}else{
				addActionError("没有权限。");
				return ERROR;
			}
		}else if ("delete".equals(cmd)){
			if (bManage){
				return super.delete();
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
			if(bManage){
				return startmeeting();
			}else{
				addActionError("请先登录。");
				return ERROR;
			}
		}
        return "list";
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
	private String save(){
		int Id = param_util.getIntParam("Id");
		if(Id == 0){
			Meetings m = new Meetings();
			m.setObj(this.obj);
			m.setObjId(this.navId);
			String returnV = add_save(m);
			if(returnV.equals(SUCCESS)){
				addActionLink("返回", request.getContextPath() + "/sitevideomeeting.action");
			}
			return returnV;
		}else{
			return super.update_save();
		}
	}	
	/**
	 * 创建一个视频会议
	 * @return
	 */
	private String add(){
		//增加
		Meetings m = new Meetings();
		m.setObj(this.obj);
		m.setObjId(this.navId);
		request.setAttribute("meetings", m);
		Calendar c = Calendar.getInstance();
		//int year = c.get(Calendar.YEAR); 
		//int month = c.get(Calendar.MONTH); 
		//int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		//int second = c.get(Calendar.SECOND);
		minute = minute + 30;
		if(minute>60){
			hour++;
			minute = minute -60;
		}
		request.setAttribute("hour", hour);
		request.setAttribute("minue", minute);
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
		return modifyMeetings();
	}	
	private boolean canManage(){
        if(isUserLogined() == false){ return false;}
        request.setAttribute("loginUser", getLoginUser());
        AccessControlService accessControlService = (AccessControlService)ContextLoader.getCurrentWebApplicationContext().getBean("accessControlService");
        //系统管理员 
        if(accessControlService.isSystemAdmin(this.getLoginUser())){
            return true;
        }
        //判断权限,是否允许创建视频会议
        if (this.userPowerService.AllowVideoConferenceCreate(getLoginUser().getUserId())){
            return true;
        }
        return false;
	}	

	/** 角色权限服务 */
	public void setUserPowerService(UserPowerService userPowerService) {
		this.userPowerService = userPowerService;
	}
	
	public String getMeetingsurl() {
		return meetingsurl;
	}

	public void setMeetingsurl(String meetingsurl) {
		this.meetingsurl = meetingsurl;
	}	
}
