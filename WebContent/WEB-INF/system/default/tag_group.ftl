<div>
<ul>
<#list group_list as group>
  <li><a href='${SiteUrl}g/${group.groupName}' 
    target='_blank'>${group.groupTitle}</a></li>
</#list>
</ul>
</div>

<h3>DEBUG</h3>
<li>tag = ${tag}
<li>pager = ${pager!}
