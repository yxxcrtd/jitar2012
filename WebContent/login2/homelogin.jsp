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
        
        if(null != request.getParameter("returnFlag")){
               String returnFlag = request.getParameter("returnFlag");
               if("1".equals(returnFlag)){
                    %>
                      <script>
                      window.parent.ShowLoginMsg("用户名不存在");
                      </script>
                    <%         
               }else if("2".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("无访问权限");
                   </script>
                 <%         
               }else if("3".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("用户名或密码不对");
                   </script>
                 <%         
               }else if("4".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("需要重新登录");
                   </script>
                 <%    
               }else if("5".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("用户未审核");
                   </script>
                 <%       
               }else if("6".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("用户被禁用");
                   </script>
                 <%                        
               }else if("7".equals(returnFlag)){
                   %>
                   <script>
                   window.parent.ShowLoginMsg("用户已删除");
                   </script>
                 <%                        
               }
        }
%>


