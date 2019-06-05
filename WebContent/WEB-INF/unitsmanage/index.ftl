<html>
 <head>
  <title>机构管理</title>
  <script>
  if (window.top != window) {
    window.top.location.href = window.location.href;
  }
  </script>
 </head>
 <frameset cols="180,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="index.py?cmd=menu&amp;unitId=${unit.unitId}" />
  <frameset rows='32,*' frameborder="no" framespacing="0" border="0">
  <frame src='index.py?cmd=head&amp;unitId=${unit.unitId}'/>
  <frame src="index.py?cmd=main&amp;unitId=${unit.unitId}" name="main" />
  </frameset>
  </frameset>  
  <noframes>
   <body>您的浏览器不支持桢</body>
  </noframes>
 </frameset>
</html>