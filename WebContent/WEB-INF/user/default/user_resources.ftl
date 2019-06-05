<#if resource_list??>
<ul class='listul'>
<#list resource_list as resource>
  <li><span style='float:right'>${resource.createDate?string("MM-dd HH:mm")}</span><a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}' 
    target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' />
     ${Util.getCountedWords(resource.title!?html,28)}</a>
</#list>
</ul>
</#if>
<div style='text-align:right'><a href='${UserSiteUrl}rescate/0.html'>&gt;&gt;全部资源</a></div>
