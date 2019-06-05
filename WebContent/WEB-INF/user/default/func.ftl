<div id='toolbar'>
	<#if loginUser?? >
	<div style='float:right;'>
		<div style="padding:6px;text-align:right;">
			<a href='${SiteUrl}'>网站首页</a> |			
			<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>我的首页</a> | 			
			<#if loginUser.userId == user.userId>
			<a href='${SiteUrl}manage?url=${"article.action?cmd=input"?url}'>发表文章</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showModuleList(event,"u");return false;'>添加模块</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showSkin(event);return false;'>设置主题</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showColumn(event);return false;'>更换布局</a> | 
			<a href='${ContextPath}createAction.action?ownerId=${loginUser.userId}&ownerType=user'>发起活动</a> | 
			</#if>
			<a href='${SiteUrl}manage/'>空间管理</a> | 
			<a href='${SiteUrl}logout.jsp?ru=${ru!}/go.action%3FloginName%3D${user.loginName}'>退出登录</a>
		</div>
	</div>
	</#if>
	<div class='innertoolbar'>
    <#if loginUser?? >
    	欢迎您：${loginUser.trueName}，今天是：${Util.today()?string("yyyy年M月d日 E")}
    <#else>
     <a href='${SiteUrl}'>网站首页</a> |
     <#if UserUrlPattern??>
			<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}'>个人首页</a> | 
			<#else>
			<a href='${SiteUrl}go.action?loginName=${user.loginName}'>个人首页</a> | 
	 </#if>
     <a href='${SiteUrl}register.action'>注册新帐户</a> | 
     <a href='${SiteUrl}login?ruu=${SiteUrl}${user.loginName!}' donclick='LoginUI.showLogin();return false;'>通行证登录</a>
    </#if>
	</div>
	<div id='innerToolbar'></div>
</div>