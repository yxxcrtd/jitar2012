<html>
 <head>
  <title>${subject.subjectName}学科管理管理</title>
  <script>
  if (window.top != window) {
    window.top.location.href = window.location.href;
  }
  </script>
 </head>
 <frameset cols="180,*" frameborder="no" framespacing="0" border="0" id="fst">
  <frame src="admin_index.py?cmd=menu&amp;id=${subject.subjectId}" />
  <frameset rows='32,*' frameborder="no" framespacing="0" border="0">
  <frame src='admin_index.py?cmd=head&amp;id=${subject.subjectId}'/>
  <frame src="admin_index.py?cmd=main&amp;id=${subject.subjectId}" name="main" />
  </frameset>
  </frameset>
  <noframes>
   <body>您的浏览器不支持桢</body>
  </noframes>
 </frameset>
</html>