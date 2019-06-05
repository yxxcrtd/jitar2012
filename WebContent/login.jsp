<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.zdsoft.passport.service.client.PassportClient" %>
<%@page import="java.net.URLEncoder"%>
<%
String dsssoserverLogin = "";
String dsssoserver = "";
if(request.getServletContext().getFilterRegistration("filterchainproxy") != null)
{
    if (request.getServletContext().getFilterRegistration("filterchainproxy").getClassName() == "dsidealsso.FilterChainProxy")
    {
        dsssoserverLogin = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl");
        dsssoserver = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerUrlPrefix");
    }
}

	String passportURL="";
	if(request.getServletContext().getServletRegistration("passportClientInit")!=null)
	{
	    passportURL = PassportClient.getInstance().getPassportURL();
	}
    String loginUrl=null;
    
    if( request.getServerPort() == 80)
    {
        loginUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
    }
    else
    {
        loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }	
    if (dsssoserverLogin.length()!=0) 
    {
    	response.sendRedirect(loginUrl+"dsideal/index.jsp");
    }
    else if(null==passportURL || passportURL.length()==0)
	{
		String url = request.getParameter("redUrl");
		if (url == null)
		{
		    url = request.getHeader("referer");
		}
		
		if (url == null)
		{	
		    url = request.getRequestURL().toString();
		}    		

		
		if(url!=null)
		{
		    response.sendRedirect(loginUrl+"login/index.jsp?ruu=" + URLEncoder.encode(url, "UTF-8"));
		}
		else
		{
		    response.sendRedirect(loginUrl+"login.action");
		}
	}
	else
	{		
		//response.sendRedirect(loginUrl+"index.py");
	}
    
    String returnFlag = "";
    if(null != request.getParameter("returnFlag")){
           returnFlag = request.getParameter("returnFlag");
           if("1".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "用户名不存在");
                %>
                  <script>
                  //alert("用户名不存在");
                  </script>
                <%         
           }else if("2".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "无访问权限");
               %>
               <script>
               //alert("无访问权限");
               </script>
             <%         
           }else if("3".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "用户名或密码不对");
               %>
               <script>
               //alert("用户名或密码不对");
               </script>
             <%         
           }else if("4".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "需要重新登录");
               %>
               <script>
               //alert("需要重新登录");
               </script>
             <%         
           }else if("5".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "用户未审核");
               %>
               <script>
               //alert("用户未审核");
               </script>
             <%
           }else if("6".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "用户被禁用");
               %>
               <script>
               //alert("用户被禁用");
               </script>
             <%                 
           }else if("7".equals(returnFlag)){
               session.setAttribute("returnFlagMsg", "用户已删除");
               %>
               <script>
               //alert("用户已删除");
               </script>
             <%                 
           }
    }    
%>
<script type="text/javascript">
window.open ("<%=loginUrl%>index.action","_top");
</script>