<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${ChatRoomName}</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
</head>
<frameset rows="30,*,100" frameborder="1" border="0" framespacing="0" name="topset">
  <frame name="topFrame" scrolling="NO" noresize src="head.py?groupId=${groupId}&prepareCourseId=${prepareCourseId}">
  <frameset rows="*" cols="80%,*" framespacing="0" frameborder="0" border="0" name="middleset">
   		<frame name="mychat" src="mychat.py?roomId=${roomId}">
        <frame name="user" src="user.py?roomId=${roomId}">
  </frameset>
  <frame name="manage" scrolling="NO" src="manage.py?roomId=${roomId}">
 </frameset>
<noframes>
</noframes>
</html>

