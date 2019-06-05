<html>
<body>
<div id="_loginstatus">
<#if loginUser?? >
<div style='float:right;'>
	<div style="padding:6px;text-align:right;">
		<a href='${SiteUrl}'>网站首页</a> |			
		<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>我的首页</a> | 			
		<#if loginUser.userId == user.userId>
		<a href='${SiteUrl}manage?url=${"article.action?cmd=input"?url}'>发表文章</a> | 
		<a href='${SiteUrl}createAction.action?ownerId=${loginUser.userId}&ownerType=user'>发起活动</a> | 
		</#if>
		<a href='${SiteUrl}manage/'>空间管理</a> | 
		<a href='${SiteUrl}logout.jsp?redUrl=${SiteUrl}go.action%3FloginName%3D${loginUser.loginName}'>退出登录</a>
	</div>
</div>
</#if>
<div class='innertoolbar'>
<#if loginUser?? >
	欢迎您：${loginUser.nickName}，今天是：${Util.today()?string("yyyy年M月d日 E")}
<#else>
 <a href='${SiteUrl}'>网站首页</a> |
 <#if UserUrlPattern??>
		<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}'>个人首页</a> | 
		<#else>
		<a href='${SiteUrl}go.action?loginName=${user.loginName}'>个人首页</a> | 
 </#if>
 <a href='${SiteUrl}register.action'>注册新帐户</a> | 
 <a href='${SiteUrl}login.jsp' donclick="this.href+=encodeURIComponent(window.parent.location.href)">通行证登录</a>
 <#--<a href='${SiteUrl}login.jsp?redUrl=' onclick="this.href+=encodeURIComponent(window.parent.location.href)">通行证登录</a>-->
</#if>
</div>
<div id='innerToolbar'></div>
</div>
<script type="text/javascript">
window.onload=function()
{
 window.parent.document.getElementById("toolbar").innerHTML = document.getElementById("_loginstatus").innerHTML;
 <#if loginUser?? >
 window.parent.visitor = { id: "${loginUser.userId}", name: "${loginUser.loginName!?js_string}", nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}'};
 <#else>
 window.parent.visitor = { id: null, name: null, nickName: null, role: 'guest' };
 </#if>
 window.parent.user = {id: "${user.userId}",name: '${user.loginName!?js_string}',nickName:'${user.nickName!?js_string}'};
 window.parent.init(); 
 }
</script>
</body>
</html>