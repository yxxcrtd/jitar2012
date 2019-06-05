<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${channel.title!?html}后台管理</title>
<script>
if (window.top != window) {
window.top.location.href = window.location.href;
}
</script>
</head>
<frameset cols="180,*" frameborder="no" framespacing="0" border="0" id="fst">
<frame src="?cmd=channelmenu&channelId=${channel.channelId}"  name='left' />
<frameset rows='32,*' frameborder="no" framespacing="0" border="0">
<frame src='?cmd=head&channelId=${channel.channelId}'/>
<frame src="?cmd=main&channelId=${channel.channelId}" name="main" />
</frameset>
</frameset>  
<noframes>
<body>您的浏览器不支持桢</body>
</noframes>
</html>