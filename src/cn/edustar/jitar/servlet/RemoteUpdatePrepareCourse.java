package cn.edustar.jitar.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCourseEdit;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.impl.JitarContextImpl;


public class RemoteUpdatePrepareCourse extends HttpServlet {

	/**
	 * 
	 */
	private Logger log = LoggerFactory.getLogger(RemoteUpdatePrepareCourse.class);
	private static final long serialVersionUID = 6080156312774431318L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService userService;
	private PrepareCourseService prepareCourseService;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		userService = JitarContext.getCurrentJitarContext().getUserService();
		prepareCourseService = (PrepareCourseService)JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("prepareCourseService");
		String queryString = request.getQueryString();
		if(queryString == null) queryString = "";		
		if(queryString.indexOf("cmd=updatemember")>-1)
		{
			this.updateCourseMember();
		}
		else if(queryString.indexOf("cmd=updatecommon")>-1)
		{
			this.updateCourseCommon();
		}
		else if(queryString.indexOf("cmd=deletelockstatus")>-1)
		{
			this.deleteLockStatus();
		}
		else
		{
			this.response.getWriter().write("error:无需的命令。");
		}
	}
	
	private void deleteLockStatus() throws IOException
	{
		JSONObject json = null;
		try {			
			//:{"PrepareCourseId":"1623","UserGuid":"F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3","FileExt":".docx"}
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    } 
		    String data = sb.toString();
			json = (JSONObject)JSONObject.parse(data);
			
			String prepareCourseId = (String)json.get("PrepareCourseId");
			String userGuid = (String)json.get("UserGuid");		
			PrepareCourse pc = prepareCourseService.getPrepareCourse(Integer.valueOf(prepareCourseId));			
			User user = userService.getUserByGuid(userGuid);
			if(	pc.getLockedUserId() != 0 && pc.getLockedUserId() != user.getUserId())
			{
				this.response.getWriter().write("error:该共案已被 id= " + pc.getLockedUserId() + " 的用户签出， 你无法解除锁定。");
				return;
			}			
			
            pc.setLockedUserId(0);
            prepareCourseService.updatePrepareCourse(pc);            
			this.response.getWriter().write("success:解除锁定成功。" );
		} catch (Exception e) {
			this.response.getWriter().write("error:" + e.getLocalizedMessage());
		}
		finally
		{
			json = null;
		}
	}
	
	
	
	private void updateCourseCommon() throws IOException
	{
		JSONObject json = null;
		try {			
			//:{"PrepareCourseId":"1623","UserGuid":"F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3","FileExt":".docx"}
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    } 
		    
		    String data = sb.toString();
		    System.out.println("data = " + data);
			json = (JSONObject)JSONObject.parse(data);
			//System.out.println("json = " + json);
			String prepareCourseId = (String)json.get("PrepareCourseId");
			String userGuid = (String)json.get("UserGuid");
			String fileExt = (String)json.get("FileExt");			
			PrepareCourse pc = prepareCourseService.getPrepareCourse(Integer.valueOf(prepareCourseId));			
			User user = userService.getUserByGuid(userGuid);
			if(	pc.getLockedUserId() != 0 && pc.getLockedUserId() != user.getUserId())
			{
				this.response.getWriter().write("error:该共案已被 id= " + pc.getLockedUserId() + " 锁定。");
				return;
			}

			String docFileName = userGuid + "_" + UUID.randomUUID().toString().toUpperCase() + fileExt;
			PrepareCourseEdit prepareCourseEdit = new PrepareCourseEdit(); 
            prepareCourseEdit.setContent(docFileName);
            prepareCourseEdit.setEditDate(new Date());
            prepareCourseEdit.setLockStatus(0);
            prepareCourseEdit.setEditUserId(user.getUserId());
            prepareCourseEdit.setPrepareCourseId(Integer.parseInt(prepareCourseId));      
            prepareCourseService.updatePrepareCourseEdit(prepareCourseEdit);
            pc.setLockedUserId(0);
            pc.setCommonContent(docFileName);
            pc.setPrepareCourseEditId(prepareCourseEdit.getPrepareCourseEditId());
            prepareCourseService.updatePrepareCourse(pc);            
			this.response.getWriter().write("success:" + docFileName);
		} catch (Exception e) {
			System.out.println("RemoteUpdatePrepareCourse updateCourseCommon 出错：" + e.getLocalizedMessage());
			this.response.getWriter().write("error:" + e.getLocalizedMessage());
		}
		finally
		{
			json = null;
		}
	}
	
	
	private void updateCourseMember() throws IOException
	{
		JSONObject json = null;
		try {			
			//:{"PrepareCourseId":"1623","UserGuid":"F3CEE26C-0C76-46B9-98FB-E0DFE3325CB3","FileExt":".docx"}
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    } 
		    String data = sb.toString();
		    log.info("data = " + data);
			json = (JSONObject)JSONObject.parse(data);
			log.info("json = " + json);
			String prepareCourseId = (String)json.get("PrepareCourseId");
			String userGuid = (String)json.get("UserGuid");
			String fileExt = (String)json.get("FileExt");
			PrepareCourse pc = prepareCourseService.getPrepareCourse(Integer.valueOf(prepareCourseId));
			User user = userService.getUserByGuid(userGuid);
			//System.out.println("user = " + user);
			//System.out.println("userGuid = " + userGuid);
			String docFileName = pc.getObjectGuid() + "_" + userGuid + fileExt;
			PrepareCourseMember m = prepareCourseService.getPrepareCourseMemberByCourseIdAndUserId(pc.getPrepareCourseId(), user.getUserId());
			//System.out.println("m = " + m);
			m.setContentLastupdated(new Date());
			m.setPrivateContent(docFileName);
			prepareCourseService.updatePrepareCourseMember(m);
			this.response.getWriter().write("success:" + docFileName);
		} catch (Exception e) {
			System.out.println("RemoteUpdatePrepareCourse updateCourseMember 出错：" + e.getLocalizedMessage());
			this.response.getWriter().write("error:" + e.getLocalizedMessage());
		}
		finally
		{
			json = null;
		}
	}
}
