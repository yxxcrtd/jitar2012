<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <style type='text/css'>
  html,body{overflow:hidden;font-weight:bold;padding:4px;margin:0;}
  a{text-decoration:none;}
  </style>
</head>
<body>
<div style='float:right'>
<a href="${SiteUrl}" target='_blank'>总站首页</a> | 
<a href="${SiteUrl}go.action?unitId=${unit.unitId}" target='_blank'>机构网站首页</a> | 
<a href="${SiteUrl}units/manage/index.py?unitId=${unit.unitId}" target='_top'>机构管理首页</a> | 
<a href="${SiteUrl}help/xxhelpindex.html" target='main'>机构管理使用帮助</a> | 
<a href="${SiteUrl}logout.jsp?ru=${ru!}" target='_top'>退出登录</a>
</div>
<div style='float:left'><span style='color:#F00;padding-right:50px;'>机构名称：${unit.unitTitle}</span> 欢迎您：${loginUser.trueName!?html}</div>
<div style='float:left;display:none;padding-left:10px;' id='_msgcontainer'><a target="_top" href='${SiteUrl}manage?url=message.action%3Fcmd%3Dinbox' target="_blank">您有 <span id='_msgtip' style='visibility:hidden;color:#F00;'></span> 个新消息</a></div>
<script type='text/javascript'>
var msg_timer = null,msg_timer2 = null;
var msg_d;
function showMsg()
{
  new Ajax.Request('${SiteUrl}jython/getLoginUserNewMessageCount.py?'+ (Date.parse(new Date())), { 
      method: 'get',
      onSuccess: function(xport) {
      if(msg_timer2) window.clearInterval(msg_timer2);
      msg_d=document.getElementById('_msgtip');
      var txt = xport.responseText.replace(/ /g,'').replace(/\r/g,'').replace(/\n/g,'');
      if(txt == '')
      {
        document.getElementById('_msgcontainer').style.display='none';
      }
      else
      { 
        document.getElementById('_msgcontainer').style.display='';
        msg_d.innerHTML = txt;
        msg_timer2 = window.setInterval("msg_d.style.visibility=msg_d.style.visibility=='hidden'?'visible':'hidden';",600);      
      }
      },
      onException: function(xport, ex) {document.getElementById('_msgcontainer').style.display='none';}        
    }
  );
}
window.setTimeout('showMsg()',3000);
msg_timer = window.setInterval('showMsg()',600000);
window.onunload = function(){window.clearInterval(msg_timer);window.clearInterval(msg_timer2);}
</script>
</body>
</html>