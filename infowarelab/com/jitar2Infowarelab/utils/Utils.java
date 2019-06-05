package com.jitar2Infowarelab.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.util.WebUtil;

public class Utils {
    public static String formatForGmtTime(final Date date) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        return dateFormat.format(date);
    }
    
    public static Date getCurrentTimeInGmt() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return c.getTime();
    }
    
    /**
     * Infowarelab_ServiceVersion
     * @return
     */
    public static String getInfowarelab_ServiceVersion(){
    	return getInitParameter("Infowarelab_ServiceVersion");
    }
    
    /**
     * 返回后台管理服务版本号 
     * @return
     */
    public static String getInfowarelab_AdminServiceVersion(){
    	return getInitParameter("Infowarelab_AdminServiceVersion");
    }
    
    public static String getInitParameter(String key){
    	String value = null;
    	ServletRequest request = null;
		if(null != JitarRequestContext.getRequestContext()) {
			request = JitarRequestContext.getRequestContext().getRequest();
			if(null != JitarRequestContext.getRequestContext().getServletContext()) {
				value = JitarRequestContext.getRequestContext().getServletContext().getInitParameter(key);
			}
		}
		if(null != request && null == value){
			if(null != request.getServletContext()){
				value = request.getServletContext().getInitParameter(key);
			}
		}
    	return value; 
    }
    
    /**
     * 得到视频会议管理员用户
     * @return
     */
    public static String getInfowarelab_AdminUser(){
    	return getInitParameter("Infowarelab_AdminUser");
    }
    
    /**
     * 得到视频会议管理员密码
     * @return
     */
    public static String getInfowarelab_AdminPassword(){
    	return getInitParameter("Infowarelab_AdminPassword");
    }    
    /**
     * Infowarelab_SiteName
     * @return
     */
    public static String getInfowarelab_SiteName(){
    	return getInitParameter("Infowarelab_SiteName");
    }
    
    /**
     * Infowarelab_XmlServiceURL
     * @return
     */
    public static String getInfowarelab_XmlServiceURL(){
    	return getInitParameter("Infowarelab_XmlServiceURL");
    }
    
    /**
     * Infowarelab_ServerURL
     * @return
     */
    public static String getInfowarelab_ServerURL(){
    	return getInitParameter("Infowarelab_ServerURL");
    }
    
    /**
     * 得到当前登录用户
     * @return
     */
	public static User getLoginUser() {
		JitarRequestContext req_ctxt = JitarRequestContext.getRequestContext();
		HttpServletRequest req = (HttpServletRequest)req_ctxt.getRequest();
		if(req == null) return null;
		HttpSession session = req.getSession();
		if (session == null)
			return null;
		return WebUtil.getLoginUser(session);
	}    
	
	/**
	 * 得到登陆密码（登录时保存在cookie）
	 * @return
	 */
	public static String getLoginUserPasswordByCookie() {
		JitarRequestContext req_ctxt = JitarRequestContext.getRequestContext();
		HttpServletRequest req = (HttpServletRequest)req_ctxt.getRequest();
		if(req == null) return null;
		Cookie[] cookies = req.getCookies();
		for (int i = 0; i < cookies.length; i++)     
	    {    
	       Cookie c = cookies[i];         
	       if(c.getName().equalsIgnoreCase("loginUserPassword"))    
	       {    
	          return c.getValue();    
	        }    	
	    }
		return null;
	} 
	
    /**
     * 是否启用了红杉树的视频会议
     * 
     * 前端页面也可以判断 UtilModel.isInfowarelabMeeting()
     * 
     * @return
     */
    public static boolean isInfowarelabMeeting(){
    	String url = getInfowarelab_ServerURL();
    	if(isNullorBlank(url)){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    public static boolean isNullorBlank(String str){
    	if(null == str){
    		return true;
    	}
    	if(str.length() == 0){
    		return true;
    	}
    	return false;
    }
}
