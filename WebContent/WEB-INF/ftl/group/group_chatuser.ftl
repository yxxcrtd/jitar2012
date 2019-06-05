<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="../css/chatroom.css" />
<body bgcolor="#ffffff" leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">
<iframe name="hiddenframe" style="display:none"></iframe>
<table border="2" cellpadding="0" cellspacing="0" width="100%" height="100%" style="border:1px">
	
	<tr>
	<td height="30" colspan="3" class="tableCaption">管理</td>
	</tr>
	<tr>
		<td height="120" colspan="2">
		<table border="1" cellpadding="0" cellspacing="0" width="100%" height="120" style="table-layout:fixed;background-color:#faebd7">
		<form name="seluser">
		<input type="hidden" name="userId" value="">
		<tr><td height="25">对下列人员管理: </td></tr>
		<tr>
			<td height="30" align="left">
			<input readonly style="height:18px;width:110px" type="text" name="UserName" value="点击人员进行选择">
			</td>
		</tr>
		<tr><td height="65">		
		<li><a href="#" onclick="slient(1);">禁止发言</a>&nbsp;&nbsp;
		<a href="#" onclick="slient(0);">允许发言</a>
		<li><a href="#" onclick="aband();">禁止进入讨论室</a>
		<li><a href="#" onclick="quitchat()">踢出讨论室</a>
		</td></tr>
		</table>
		</td>
	</tr>
	<tr>
	<td height="30" colspan="3" class="tableCaption">人员列表&nbsp;&nbsp;在线[<font id="pnum" color=red>0</font>]人</td>
	</tr>
	<tr>
	<td colspan="3">
	<table id="tb" style="display:none" height=1>
	<tr>
	<td id="chatuser" userid="" username=""></td>
	</tr>
	</table>
</form>
	
<div id="newuser"></div>
</table>
</body>
</html>