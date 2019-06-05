<#if prepareCourse?? >
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${prepareCourse.title} - 备课管理</title>
  <script type='text/javascript'>
   if(window != window.top)
   {
    window.top.location.href=window.location.href
   }
  </script>
 </head>
 <frameset cols="180,6,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="manage_pc.py?cmd=left&amp;prepareCourseId=${prepareCourse.prepareCourseId}" frameborder="no" noresize="true"  />
  <frame src="../middle.jsp" scrolling="no" noresize="true" />
  <frameset rows="32,*" frameborder="no" framespacing="0" border="0">
    <frame src="../top.jsp" name="topframe" frameborder="no" noresize="true" />
    <frame src="manage_pc.py?cmd=right&amp;prepareCourseId=${prepareCourse.prepareCourseId}" name="main" />
  </frameset>    
  <noframes>
   <body>
    您的浏览器不支持帧.
   </body>
  </noframes>
 </frameset>
</html>
</#if>
