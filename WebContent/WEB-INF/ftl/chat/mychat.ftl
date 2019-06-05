<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
 <script src="../js/jitar/core.js"></script>
   <script language="javascript">
   var lastId=${lastId};
   var __TimerHanlder = window.setInterval("__GetUserMsg()",5000);
   function __GetUserMsg()
   {
	  var url = 'getMsg.py?roomId=${roomId}&lastId='+ lastId +'&tmp=' + Math.random();
	  new Ajax.Request(url, {
	    method: 'get',
	    onSuccess: function(xport) { 
	        var s = xport.responseText;
	        var ipos=s.indexOf("|");
	        var s1=s.substring(0,ipos);
	        var s2=s.substring(ipos+1);
	        lastId=s1;
	        //alert(lastId);
	        //alert(s2);
	        //document.getElementById("chatmsgtop").outerHTML=s2;
	        s2 = s2.replace(/\[face_(\d{3})\]/g,"<img src='${SiteUrl}images/face/$1.gif'>")
	        var d=document.createElement("DIV");
	        d.innerHTML=s2;
	        document.getElementById("chatmsgtop").appendChild(d);
	      }
	  });
   }

  var timeRun=false;
  var timeID;
  
  function chatScroll(){
  this.scroll(0,9999999);
  if(timeRun) clearTimeout(timeID);
  timeID=setTimeout('chatScroll()',500);
  timeRun=true;
  }
  
  function scrollStop(){
  if(timeRun) {clearTimeout(timeID); timeRun=false;}
  else chatScroll(); 
  }
  
  function rop(){
  setTimeout('rop()',10000);
  }
  
  chatScroll();
  
  rop();
  
  
  </script>
</head>
<body>
	<#if msgList??>
		<#list msgList as msg>
			<li>
				<font color="${msg.senderColor!}">${msg.senderName}</font>对<font color="${msg.receiverColor!}">${msg.receiverName}</font>说：[${msg.sendDate}]<br/>
				<font color="${msg.senderColor!}">${msg.talkContent}</font>
				<#if msg.faceImg??>
					<#if msg.faceImg!="">
					   <#assign imgs = msg.faceImg?split("|")>					  
					   <#list imgs as m>
					    <#if m != "">
						<img border="0" src="${m}">
						</#if>
						</#list>
					</#if>
				</#if>
				<br/><br/>
			</li>
		</#list> 
	</#if>
	<div id="chatmsgtop"></div>
   </body>
</html>
