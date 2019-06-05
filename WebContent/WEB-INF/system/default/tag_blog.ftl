<div>
<ul>
<#list user_list as user>
  <li><a href='${SiteUrl}go.action?loginName=${user.loginName}' 
    target='_blank'>${user.loginName}</a></li>
</#list>
</ul>
</div>

<h3>DEBUG</h3>
<li>tag = ${tag}
<li>pager = ${pager!}
