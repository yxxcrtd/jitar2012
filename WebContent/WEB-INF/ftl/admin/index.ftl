<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>后台管理</title>
	</head>
 <frameset cols="180,6,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="?cmd=menu"  frameborder="no" noresize="true"  />
  <frame src="middle.jsp" scrolling="no" noresize="true" />
  <frameset rows="32,*" frameborder="no" framespacing="0" border="0">
    <frame src="top.jsp" name="topframe" frameborder="no" noresize="true" />
    <frame src="${url!}" name="main" />
  </frameset>  
  <noframes>
   <body>
    您的浏览器不支持帧。
   </body>
  </noframes>
 </frameset>
</html>