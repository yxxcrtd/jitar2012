<div class='navbar'>
<div>
	<#if SiteNavList??>
	<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
	<#list SiteNavList as SiteNav>
 		<#if SiteNav.isExternalLink >
 			<a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if>
 		<#else>
	 		<#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
	 			<a href='${SiteUrl}${SiteNav.siteNavUrl}'><span>${SiteNav.siteNavName}</span></a><#if SiteNav_has_next> | </#if> 
	 		<#else>
	 			<a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if> 
	 		</#if>
 		</#if>
	</#list>
	<#else>
	配置错误，无法显示导航信息。
	</#if>
</div>
</div>