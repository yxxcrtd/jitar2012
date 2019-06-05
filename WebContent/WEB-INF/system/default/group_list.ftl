<div>
<ul>
<#list group_list as group>
  <li><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle!?html}</a></li>
</#list>
</ul>
<li>DEBUG: pager = ${pager!}
</div>
