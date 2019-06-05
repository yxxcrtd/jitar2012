<div class='navbar'><div>
<font  style="float:right">
<#if loginUser??>
<#if AdminType != "">
<a href="${SiteUrl}manage/channel/channel.action?cmd=manage&channelId=${channel.channelId}">频道管理</a> <strong>|</strong> 
</#if>
<a href="${SiteUrl}go.action?loginName=${loginUser.loginName}">我的工作室</a> <strong>|</strong> 
<a href="${SiteUrl}logout.jsp?ru=${ru!}">退出登录</a>
<#else>
<a href="${SiteUrl}login.jsp?redUrl=${(SiteUrl + 'manage/channel/channel.action?cmd=manage&channelId=' + (channel.channelId)?string)?url}">用户登录</a>
</#if>
</font>
<#if SiteNavList??>
<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
<#list SiteNavList as SiteNav>
	<#if SiteNav.isExternalLink >
		<a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> <strong>|</strong> </#if>
	<#else>
 		<#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
 			<a href='${SiteUrl}${SiteNav.siteNavUrl}'><span>${SiteNav.siteNavName}</span></a><#if SiteNav_has_next> <strong>|</strong> </#if> 
 		<#else>
 			<a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> <strong>|</strong> </#if> 
 		</#if>
	</#if>	
</#list>
<#else>
配置错误，无法显示导航信息。
</#if>
</div></div>