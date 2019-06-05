<div>
<#list online_gmlist as row>
 <span>ddd
 <a href='${SiteUrl}go.action?loginName=${row.loginName}' target='_blank'>${row.loginName}</a>
 </span>
 </#list>
</div>
