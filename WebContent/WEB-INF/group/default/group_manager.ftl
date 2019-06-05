<div>
 <ul>组长:
 <#if manager??>
     <#list manager as user>
     <li><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></li>
     </#list>
 <#else>
   当前没有组长
 </#if>
</ul> 
</div>
