<#assign grpName="协作组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
    <#else>
        <#assign grpName="协作组">
    </#if>
</#if>
<div id='toolbar'>
	<#if loginUser?? >
	<div style='float:right;'>
		<div style="padding:6px;text-align:right;">
			<a href='${SiteUrl}' target=_top>网站首页</a> | 
			<a href='${SiteUrl}g/${group.groupName}' target=_top>${grpName}首页</a> | 
			<a href='${SiteUrl}go.action?loginName=${loginUser.loginName}' target=_top>我的个人空间</a> | 
			<#if visitor_role?default('') == 'admin'>
			<#if !page.isSystemPage >
			<#if isKtGroup=="1">
			     <a href='javascript:void(0)' onclick='DivUtil.showModuleList(event,"k");return false;'>添加模块</a> |
			<#elseif isKtGroup=="2">
			     <a href='javascript:void(0)' onclick='DivUtil.showModuleList(event,"b");return false;'>添加模块</a> |
			<#else>
			     <a href='javascript:void(0)' onclick='DivUtil.showModuleList(event,"g");return false;'>添加模块</a> |
			</#if> 
			<a href='javascript:void(0)' onclick='DivUtil.showSkin(event);return false;'>设置主题</a> | 
			<a href='javascript:void(0)' onclick='DivUtil.showColumn(event);return false;'>更换布局</a> | 
			</#if>
			<a href='${ContextPath}createAction.action?ownerId=${group.groupId}&ownerType=group'>发起活动</a> | 
			<#--<a href='${SiteUrl}manage/course/createPreCourse.py?groupId=${group.groupId}'>发起备课</a> | -->
			<a href='${SiteUrl}manage/group.py?cmd=manage&groupId=${group.groupId}'>进入${grpName}管理</a> | 
			</#if>
			<a href='${SiteUrl}logout.jsp' target=_top>退出登录</a>
		</div>
	</div>
	</#if>
	<div class='innertoolbar'>
	    <#if loginUser?? >
	    	欢迎您：${loginUser.nickName}，今天是：${Util.today()?string("yyyy年M月d日 E")}
	    <#else>
	    	<a href='${SiteUrl}'>网站首页</a> | 
	    	<a href='${SiteUrl}g/${group.groupName}'>本组首页</a> | 
	    	<a href='${SiteUrl}register.action'>注册新帐户</a> | 
			<a href='${SiteUrl}login?ruu=${SiteUrl}g/${group.groupName}' donclick='LoginUI.showLogin();return false;'>通行证登录</a>
	    </#if>
	</div>
	<div id='innerToolbar'></div>
</div>