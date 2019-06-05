<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//1： 用户名不存在
	//2： 无访问权限（用户管理系统的接入站点管理中该用户不可访问当前站点）
	//3：用户名或密码不对
	//4：需要重新登录
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
	/*
    Map param_map = request.getParameterMap();
    StringBuffer strbuf = new StringBuffer(1024);
    boolean bFistParam = true;
    for (Object key : param_map.keySet()) {
    	if(bFistParam){
    		strbuf.append("?");
    	}else{
    		strbuf.append("&");
    	}
        strbuf.append(key).append("=");
        Object value = param_map.get(key);
        
        if (value == null || !(value instanceof Object[]))
            strbuf.append(value);
        else {
            Object[] a = (Object[])value;
            for (int j = 0; j < a.length; ++j) {
                strbuf.append(a[j]);
            }
        }
        
        bFistParam = false;
        System.out.println(""+strbuf.toString());
    }
	response.sendRedirect(path + "/index.action" + strbuf.toString());
	*/
	//response.sendRedirect(path + "/index.action");
%>
  <script>
  window.open("<%=path%>/index.action","_top");
  
  </script>


