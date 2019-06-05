<script type='text/javascript'>
var JITAR_ROOT = '${SiteUrl}';
<#if SubjectUrlPattern??>
var SubjectUrl = "${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}";
<#else>
var SubjectUrl = JITAR_ROOT;
</#if>
</script>
<div class='navbar'>
	<div style='float:left;padding-left:10px'>
	<#if SubjectSiteNavList??>
	<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
	<#list SubjectSiteNavList as SiteNav>
		<#if SiteNav.isExternalLink >
			<a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if>
		<#else>
			<#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
				<a href='${SubjectRootUrl}${SiteNav.siteNavUrl}<#if unitId??>?unitId=${unitId}</#if>'><span>${SiteNav.siteNavName}</span></a><#if SiteNav_has_next> | </#if> 
			<#else>
				<a href='${SubjectRootUrl}${SiteNav.siteNavUrl}<#if unitId??>?unitId=${unitId}</#if>'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if> 
			</#if>
		</#if>
	</#list>
	<#else>
	配置错误，无法显示导航信息，请超级管理员<a href='${SiteUrl}manage/init_subject_nav.py'>点击此链接</a>。
	</#if>
	</div>
	<div style='float:right;padding-right:10px'>	
	<#if loginUser??>
	<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>我的首页</a> |
	<#if isAdmin??>
	<a href='${SiteUrl}subject/manage/admin_index.py?id=${subject.subjectId}'>学科管理</a> |
	</#if>
	<a href='${SiteUrl}logout.jsp'>注销登录</a>
	<#else>
	<a href='${SiteUrl}login?ruu=/k/${subjectCode!}'>登录系统</a>
	</#if>
	</div>
	<div style='float:right;font-weight:bold;display:none;padding-left:30px;' id='_msgcontainer'><a href='${SiteUrl}manage?url=message.py%3Fcmd%3Dinbox'>您有<span id='_msgtip' style='visibility:hidden;'></span>个新消息</a></div>
<script type='text/javascript'>
var msg_timer = null,msg_timer2 = null;
var msg_d;
function showMsg()
{
  new Ajax.Request('${SubjectRootUrl}py/getLoginUserNewMessageCount.py?'+ (Date.parse(new Date())), { 
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
</div>