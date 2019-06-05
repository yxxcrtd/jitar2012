<#if actionErrors?? >
<ul>
<#list actionErrors as error>
  <li>${error}</li>
</#list>
</ul>
</#if>