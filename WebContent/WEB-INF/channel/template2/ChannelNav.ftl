<div class='navbar'>
<div>
<#if SiteNavList??>
<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
        <#-- 定义要显示的列数columnCount -->
        <#assign columnCount = 2>
        <#-- 计算显示当前记录集需要的表格行数 rowCount -->
        <#if SiteNavList.size() % columnCount == 0>
            <#assign rowCount = (SiteNavList.size() / columnCount)?int >
            <#assign colspan = 0>
        <#else>
            <#assign rowCount = (SiteNavList.size() / columnCount)?int+1>
            <#assign colspan = 1>
        </#if>

 <table border='0' width='100%' style='table-layout:fixed;'>
<#list SiteNavList as SiteNav>
 
    <#if SiteNav_index == rowCount || SiteNav_index == 0>
      <tr align='center' height='30'>
    </#if>
 <#if colspan == 1 &&  SiteNav_index == 0>
    <td rowspan='2' style='vertical-align:middle;'>
 <#else>     
 <td style='vertical-align:middle;'>
 </#if>
    <#if SiteNav.isExternalLink >
        <a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a>
    <#else>
        <#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav>
            <a href='${SiteUrl}${SiteNav.siteNavUrl}'><span>${SiteNav.siteNavName}</span></a>
        <#else>
            <a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a>
        </#if>
    </#if>  
</td>
<#if SiteNav_index + 1 == rowCount >
</tr>
</#if>
</#list>
</tr>
</table>
<#else>
配置错误，无法显示导航信息。
</#if>
</div>
</div>
