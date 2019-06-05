<html>
 <head>
  <title>${loginUser.trueName!?html}管理</title>
  <script>
  if(window != top) top.location.href=window.location.href;
  </script>
 </head>
 <frameset cols="180,6,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="?cmd=nav"  frameborder="no" noresize="true"  />
  <frame src="middle.jsp" scrolling="no" noresize="true" />
  <frameset rows="32,*" frameborder="no" framespacing="0" border="0">
    <frame src="top.jsp" name="topframe" frameborder="no" noresize="true" />
    <frame src="${url!}" name="main" />
  </frameset>  
  <noframes>
   <body>您的浏览器不支持桢</body>
  </noframes>
 </frameset>
</html>