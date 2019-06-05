package org.jasig.cas.client.util;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class CasConstFilter implements Filter{
	protected final Log log = LogFactory.getLog(getClass());
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        //if(CasConst.getInstance().getCasServerLoginUrl()==null){
        	String casServerLoginUrl=null;String casServerUrlPrefix=null;
        	
        	casServerUrlPrefix=request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
        	if(casServerUrlPrefix==null || casServerUrlPrefix.length()==0){
        		casServerUrlPrefix=CommonUtils.getCasServerUrlPrefix(request);
        	}

        	casServerLoginUrl=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl");
        	if(casServerLoginUrl==null || casServerLoginUrl.length()==0){
        		casServerLoginUrl=CommonUtils.getCasServerLoginUrl(request);
        	}
        	
	        CasConst.getInstance().setCasServerLoginUrl(casServerLoginUrl);
	        CasConst.getInstance().setCasServerUrlPrefix(casServerUrlPrefix);
	        CasConst.getInstance().setSiteServer(CommonUtils.getSiteServer(request));
	        CasConst.getInstance().setSiteServerUrl(CommonUtils.getSiteUrl(request));
        //}
        //log.debug("常量 casServerLoginUrl="+CasConst.getInstance().getCasServerLoginUrl());
        //log.debug("常量 casServerUrlPrefix="+CasConst.getInstance().getCasServerUrlPrefix());
	        /*
	    HttpSession session = request.getSession(false);
	    System.out.println("-------------------------检查Session的时间设置-------------------------------------");
	    if(session==null){
	    	System.out.println("session:不存在！"); 
	    }
	    else{
	    	System.out.println("获取session的Id:" + session.getId());
	    	java.util.Date date1 = new java.util.Date(session.getCreationTime());
	    	System.out.println("获取session的创建时间:" + date1.toString());
	    	java.util.Date date2 = new java.util.Date(session.getLastAccessedTime());
	    	System.out.println("获取上次与服务器交互时间:" + date2.toString());
	    	System.out.println("获取session最大的不活动的间隔时间，以秒为单位:"+session.getMaxInactiveInterval());	        
	    }
	    */
	        
        filterChain.doFilter(request, response);
		return;
        
    }	
    
    public void init(final FilterConfig filterConfig) throws ServletException {
    	
    }    
    public void destroy() {
        // nothing to do
    }    
}
