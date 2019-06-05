<div>
<ul>
<#list resource_list as resource>
  <li><a href='${SiteUrl}manage/resource.action?cmd=view&amp;resourceId=${resource.resourceId}' 
    target='_blank'>${resource.title!?html}</a> [${resource.createDate?string('MM-dd hh:mm')}]</li>
</#list>
</ul>
</div>

<h3>DEBUG</h3>
<li>tag = ${tag}
<li>pager = ${pager!}
