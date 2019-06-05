<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.service.UserService"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //System.out.println("request.getQueryString()="+request.getQueryString());
    /*
      User user = null;
      ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
      UserService userservice = (UserService) ac.getBean("userService");
      user = userservice.getUserByLoginName(ssoId);
      if (null != user) {
          session.setAttribute(User.SESSION_LOGIN_NAME_KEY, ssoId);
          session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
      }
    */
%>
<script type="text/javascript">
   window.parent.document.location.href = "<%=path%>/index.action";
</script>

