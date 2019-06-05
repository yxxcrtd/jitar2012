<#if resource_list??>
<ul class="ulist">
<#list resource_list as r>
<li>
<span><a href="${SiteUrl}go.action?loginName=${r.loginName}" title="${r.userTrueName!?html}">${Util.getCountedWords(r.userTrueName!?html,4)}</a> ${r.createDate?string("MM-dd")}</span>
<img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> <a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank' title='${r.title!?html}'>${Util.getCountedWords(r.title!?html,26)}</a></li>
</#list>
</ul>
</#if>