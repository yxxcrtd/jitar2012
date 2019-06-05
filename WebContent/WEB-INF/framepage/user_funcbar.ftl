<div id='toolbar'>
	<div style='float:right;padding-top:4px;padding-right:10px;'>
	<#if loginUser?? >
		<a href='${SiteUrl}'>网站首页</a> | 
		<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>我的首页</a> | 
  		<a href='${SiteUrl}manage'>后台管理</a> | 
  		<a href='${SiteUrl}logout.jsp?redUrl=${SiteUrl}go.action?loginName=${loginUser.loginName}'>退出登录</a>
	</#if>
	</div>
	<div class='innertoolbar'>    
    <#if loginUser?? >
    	欢迎您：${loginUser.nickName}，今天是：${currentDate}
    <#else>
    	<a href='${SiteUrl}'>网站首页</a> | 
    	<a href='${SiteUrl}go.action?loginName=${user.loginName}'>个人首页</a> |
    	<a href='${SiteUrl}register.action'>注册新帐户</a> | 
		<a href='${SiteUrl}login.jsp' donclick='LoginUI.showLogin();return false;'>通行证登录</a>
    </#if>
    </div>
    <div id='innerToolbar'></div>
</div>