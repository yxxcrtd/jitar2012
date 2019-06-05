<ul>
<#if SiteNavList??>
   <#if head_nav??>
      <#assign crtnav=head_nav><#else><#assign crtnav=''>
   </#if>        
   <#list SiteNavList as SiteNav>
     <#if SiteNav.isExternalLink >
        <li><a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><span></span></li>
     <#else>
       <#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
          <li class="active"><a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><span></span></li>
         <#else>
          <li><a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><span></span></li>
         </#if>
     </#if>
   </#list>
<#else>
  <li>配置错误，无法显示导航信息。</li>
</#if>
</ul>