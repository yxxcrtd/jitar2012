<html>
<script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
<body>
<div id="loginstatus_head">
<#if loginUser?? >	
	<a href='${SiteUrl}manage/?url=article.action%3Fcmd%3Dinput'>发布文章</a> |		
	<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>个人空间</a> | 
	<#if loginUser.unitId?? && loginUser.unitId != 1>
	<#assign unit = Util.unitById(loginUser.unitId)>
	<a href='${SiteUrl}go.action?unitId=${loginUser.unitId}'>我的机构</a> | 
	</#if>
	<#if platformType?? && platformType == '2'>
	<#if SiteConfig?? && SiteConfig["topsite_url"]?? && SiteConfig["topsite_url"] !='' >
	<a href='${SiteConfig["topsite_url"]!}mashup/userenter.py?g=${Util.getEncryptGuid(loginUser.userGuid)}&u=${SiteUrl}&t=<#if loginUser.trueName??>${loginUser.trueName!?url}<#else>${loginUser.loginName?url}</#if>&n=<#if unit??>${unit.unitName?url}</#if>'>进入市平台</a> |
	</#if>
	</#if>
	<a href='${SiteUrl}manage'>后台管理</a> | 
	<#if isSysAdmin?? && unit??>
	<a href='${SiteUrl}units/manage/index.py?unitId=${unit.unitId!}'>机构管理</a> | 
	</#if>
	<a href='${SiteUrl}logout.jsp?ru=${ru!}'>退出登录</a>
	<span style='font-weight:bold;display:none;padding-left:30px;' id='_msgcontainer'><a href='${SiteUrl}manage?url=message.action%3Fcmd%3Dinbox'>您有 <span id='_msgtip' style='visibility:hidden;'></span> 个新消息</a></span>
	</div>
<script>
var msg_timer = null,msg_timer2 = null;
var msg_d;
function showMsg()
{
  new Ajax.Request('${SiteUrl}jython/getLoginUserNewMessageCount.py?'+ (Date.parse(new Date())), { 
      method: 'get',
      onSuccess: function(xport) {
      if(msg_timer2) window.clearInterval(msg_timer2);
      msg_d=window.parent.document.getElementById('_msgtip');
      var txt = xport.responseText.replace(/ /g,'').replace(/\r/g,'').replace(/\n/g,'');
      if(txt == '')
      {
        window.parent.document.getElementById('_msgcontainer').style.display='none';
      }
      else
      { 
        window.parent.document.getElementById('_msgcontainer').style.display='';
        msg_d.innerHTML = txt;
        msg_timer2 = window.setInterval("msg_d.style.visibility=msg_d.style.visibility=='hidden'?'visible':'hidden'",600);      
      }
      },
      onException: function(xport, ex) {window.parent.document.getElementById('_msgcontainer').style.display='none';}        
    }
  );
}
window.setTimeout('showMsg()',3000);
msg_timer = window.setInterval('showMsg()',600000);
window.onunload = function(){window.clearInterval(msg_timer);window.clearInterval(msg_timer2);}
</script>
<#else>
	<#if passportURL?? >
		<#if passportURL=="" >
			<a href='${SiteUrl}login.jsp'>登录系统</a>
		<#else>
			<a href='${SiteUrl}zdsoft/login.jsp'>登录系统</a>
		</#if>
	<#else>	
	<!--
		<a href='${SiteUrl}login.jsp'>登录系统</a>
	-->
	</#if>
</div>
</#if>
<script type="text/javascript">
window.parent.document.getElementById("loginstatus_head").innerHTML = document.getElementById("loginstatus_head").innerHTML;
</script>
</body>
</html>