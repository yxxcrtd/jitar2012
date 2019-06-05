<div class='navbar'>
<div style='float:left;padding-left:20px'>
<#if UnitSiteNavList??>
<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
<#list UnitSiteNavList as SiteNav>
	<#if SiteNav.isExternalLink >
		<a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> <span class='splitbg'></span><p>|</p> </#if>
	<#else>
		<#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
			<a href='${UnitRootUrl}${SiteNav.siteNavUrl}'><span>${SiteNav.siteNavName}</span></a><#if SiteNav_has_next> <span class='splitbg'></span><p>|</p> </#if> 
		<#else>
			<a href='${UnitRootUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> <span class='splitbg'></span><p>|</p> </#if> 
		</#if>
	</#if>
</#list>
<#else>
配置错误，无法显示导航信息，请超级管理员<a href='${SiteUrl}manage/init_unit_nav.py'>点击此链接</a>。
</#if>
</div>
<div style='float:right;padding-right:20px' id="__login_info"></div>
<div style='float:right;font-weight:bold;;padding-left:30px;' id='__msg_count'></div>
<script type='text/javascript'>
var msg_timer = null,msg_timer2 = null;
var msg_div, show = true, msg_count;
function showMsg()
{
  new Ajax.Request('${UnitRootUrl}py/getUnitLoginUser.py?unitId=${unit.unitId}'+ (new Date()).valueOf(), { 
      method: 'get',
      onSuccess: function(xport) {
      if(msg_timer2) window.clearInterval(msg_timer2);
      msg_div=document.getElementById('__login_info');
      msg_count=document.getElementById('__msg_count');
      var txt = xport.responseText.replace(/ /g,'').replace(/\r/g,'').replace(/\n/g,'');
      var data = JSON.parse(txt);
      txt = "";
      
      if(data.LoginStatus == "0")
      { 
        msg_count.innerHTML = "";
      	txt = "<a href='${SiteUrl}login.jsp?ru=' onclick='this.href+=encodeURIComponent(window.location.href)'>登录系统</a>";
      }
      else
      {
      	txt += " <a href='${SiteUrl}go.action?userId=" + data.LoginStatus + "'>我的工作室</a> |";
      	
      	if(data.CanManage == "1")
      	{
      	  txt += " <a href='${SiteUrl}units/manage/index.py?unitId=${unit.unitId}'>机构管理</a> |";
      	}      	
      	txt += " <a href='${SiteUrl}logout.jsp'>注销登录</a>";
      }      
      msg_div.innerHTML = txt;      
      msg_timer2 = window.setInterval(function(){
      	if(data.MessageCount != "0")
      	{
	      if(show)
		  msg_count.innerHTML = "<a href='${SiteUrl}manage?url=message.action%3Fcmd%3Dinbox'>您有 <font style='color:red;'>" + data.MessageCount + "</font> 个新消息</a>";
		  else
		  msg_count.innerHTML = "<a href='${SiteUrl}manage?url=message.action%3Fcmd%3Dinbox'>您有 <font style='visibility:hidden;'>" + data.MessageCount + "</font> 个新消息</a>";
	    }
	    else
	    {
	      msg_count.innerHTML = "";
	    }
        show = !show;      	
      },600);
 	 
      },
      onException: function(xport, ex) {msg_div.innerHTML = "";msg_count.innerHTML = "";},
      onFailure: function(xport, ex) {msg_div.innerHTML = "";msg_count.innerHTML = "";}
    }
  );  
}
window.setTimeout('showMsg()',3000);
msg_timer = window.setInterval('showMsg()',600000);
window.onunload = function(){window.clearInterval(msg_timer);window.clearInterval(msg_timer2);}
</script>
</div>