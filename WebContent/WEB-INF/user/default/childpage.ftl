<div id='toolbar'>
	<div style='float:right;padding-top:4px;padding-right:10px;'>
	<#if loginUser?? >
		<a href='${SiteUrl}'>网站首页</a> | 
		<#if UserUrlPattern??>
			<a href='${UserUrlPattern.replace('{loginName}',loginUser.loginName)}'>我的首页</a> | 
			<#else>
			<a href='${SiteUrl}${loginUser.loginName}'>我的首页</a> | 
		</#if>
		
  		<a href='${SiteUrl}manage'>后台管理</a> | 
  		<a href='${SiteUrl}logout.jsp'>退出登录</a>
	</#if>
	</div>
	<div class='innertoolbar'>    
    <#if loginUser?? >
    	欢迎您：${loginUser.nickName}，今天是：${Util.today()?string("yyyy年M月d日 E")}
    <#else>
    	<a href='${SiteUrl}'>网站首页</a> | 
    	<#if UserUrlPattern??>
			<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}'>个人首页</a> | 
			<#else>
			<a href='${SiteUrl}${user.loginName}'>个人首页</a> | 
		</#if>
    	<a href='${SiteUrl}register.action'>注册新帐户</a> | 
		<a href='${SiteUrl}login.jsp' donclick='LoginUI.showLogin();return false;'>通行证登录</a>
    </#if>
    </div>
    <div id='innerToolbar'></div>
</div>