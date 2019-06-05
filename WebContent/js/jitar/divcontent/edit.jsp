<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.edustar.jitar.model.SiteUrlModel"%>
<%@ page import="cn.edustar.jitar.pojos.User" %>
<%@page import="cn.edustar.jitar.util.CommonUtil"%>
<%@ page import="org.springframework.web.context.support.*"%>   
<%@ page import="org.springframework.context.ApplicationContext" %>
<%
  String widgetId = request.getParameter("widgetId");  
    String logiName = "";
    if(session.getAttribute(User.SESSION_LOGIN_USERMODEL_KEY)!=null){
    	   User u = (User)session.getAttribute(User.SESSION_LOGIN_USERMODEL_KEY);
    	   if(u !=null){
    		    logiName = u.getLoginName();
    	   }
    }
	String siteUrl = CommonUtil.getContextUrl(request);

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>编辑</title>
  <script src='../core.js'></script>
<!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "<%=siteUrl%>";
    window.UEDITOR_USERLOGINNAME = "<%=logiName%>";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=siteUrl%>manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="<%=siteUrl%>manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="<%=siteUrl%>manage/ueditor/lang/zh-cn/zh-cn.js"></script>  
  <script type='text/javascript'>
  function Init()
  {
  	var t = window.opener.document.getElementById("webpart_<%=widgetId %>_h").innerHTML;
  	var c = window.opener.document.getElementById("webpart_<%=widgetId %>_c").innerHTML;
  	document.getElementById("editForm").widgetTitle.value = t;
  	document.getElementById("DHtml").innerHTML = c;
  }  
  function saveText()
  {
	  var s = editor.getContent();
	    window.opener.DivUtil.saveSimpleTextData(<%=widgetId %>,document.getElementById("editForm").widgetTitle.value,s,window)
   }
  </script>
 </head>
<body>
<form id="editForm" method='post' action='saveEdit.action'>
<input name='widgetId' type='hidden' value='<%=widgetId %>' />
标题：<input name='widgetTitle' style='width:100%' maxlength="6" />
内容： <script id="DHtml" name="content" type="text/plain" style="width:840px;height:350px;">
      </script>                          
      <script type="text/javascript">
          var editor = UE.getEditor('DHtml');
          Init();
      </script>
              
<input type="button" class='button' value="保存内容" onclick='saveText()' />
<input type="button" class='button' value="关闭窗口" onclick = 'window.close();' />
</form>
</body>
</html>
