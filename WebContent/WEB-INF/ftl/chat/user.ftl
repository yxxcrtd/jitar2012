<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script src="../js/jitar/core.js"></script>
   <script language="javascript">
   var __TimerHanlder = window.setInterval("__GetUser()",5000)
   function __GetUser()
   {
	  var url = 'getUserList.py?roomId=${roomId}&tmp=' + Math.random();
	  new Ajax.Request(url, {
	    method: 'get',
	    onSuccess: function(xport) { 
	        var s = xport.responseText;
	        document.getElementById("chatusers").innerHTML=s;
	      }
	  });   	
   }
	function selectUser(userId,userName)
	{
		self.parent.manage.selectChatUser(userId,userName);
	}
	
   </script>
</head>
<body>
	<div style="text-align:center">
    <FONT SIZE='2' COLOR='#0000FF'>---本聊天室在线人员---</FONT><br/><br/>
    <FONT SIZE='2' COLOR='#000000'>
    <div id="chatusers">
	<#if userList??>
		<#list userList as u>
			<a href="#" onclick="selectUser('${u.userId!}','${u.userName!}');return false;" title="${u.userName!}">${u.userName!}</a><br/>
		</#list> 
	</#if>
	</div>
    </FONT>
    </div>
   </BODY>
</HTML>
