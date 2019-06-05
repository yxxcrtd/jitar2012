<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${group.groupTitle!?html} - 群组管理</title>
  <script>
  if(window.location.href !=top.location.href) top.location.href = window.location.href;
  </script>
 </head>
 <frameset cols="180,6,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="?cmd=manage_left&amp;groupId=${group.groupId}" frameborder="no" noresize="true"  />
  <frame src="middle.jsp" scrolling="no" noresize="true" />
  <frameset rows="32,*" frameborder="no" framespacing="0" border="0">
    <frame src="top.jsp" name="topframe" frameborder="no" noresize="true" />
    <frame src="${url!('?cmd=home&amp;groupId='+group.groupId?string)}" name="main" />
  </frameset>  
    
  <noframes>
   <body>
    您的浏览器不支持帧.
   </body>
  </noframes>
 </frameset>
</html>
