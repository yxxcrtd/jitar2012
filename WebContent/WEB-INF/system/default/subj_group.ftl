<ul>
<#list group_list as group>
  <li><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle!?html}</a></li>
</#list>
</ul>
<div>pager = ${pager}</div>
