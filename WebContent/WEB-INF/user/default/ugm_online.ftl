<#if online_list?size == 0>
<div>当前没有协作组成员在线</div>
<#else >
<div>
<#list online_list as user>
  <span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.loginName}</a></span> 
</#list>
</div>
</#if>