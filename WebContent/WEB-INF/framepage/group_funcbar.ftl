<div id='toolbar'>
	<#if loginUser?? >
	<div style='float:right;'>
		<div style="padding:6px;text-align:right;">
			<a href='${SiteUrl}'>网站首页</a> | 
			<a href='${SiteUrl}g/${group.groupName}'>协作组首页</a> | 
			<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}'>我的首页</a> | 
			<#if visitor_role?default('') == 'admin'>
			<#if !page.isSystemPage >
			<a href='javascript:void(0)' onclick='DivUtil.showModuleList(event,"g");return false;'>添加模块</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showSkin(event);return false;'>设置主题</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showColumn(event);return false;'>更换布局</a> | 
			</#if>
			<a href='${ContextPath}createAction.action?ownerId=${group.groupId}&ownerType=group'>发起活动</a> | 
			<a href='${SiteUrl}manage/group.py?cmd=manage&groupId=${group.groupId}'>进入协作组管理</a> | 
			</#if>
			<a href='${SiteUrl}logout.jsp?redUrl=${SiteUrl!?url}go.action%3FloginName=${loginUser.loginName}'>退出登录</a>
		</div>
	</div>
	</#if>
	<div class='innertoolbar'>
	    <#if loginUser?? >
	    	欢迎您：${loginUser.nickName}，今天是：${currentDate}
	    <#else>
	    	<a href='${SiteUrl}'>网站首页</a> | 
	    	<a href='${SiteUrl}g/${group.groupName}'>本组首页</a> | 
	    	<a href='${SiteUrl}register.action'>注册新帐户</a> | 
			<a href='${SiteUrl}login.jsp' donclick='LoginUI.showLogin();return false;'>通行证登录</a>
	    </#if>
	</div>
	<div id='innerToolbar'></div>
</div>