<div>
<ul>
<#list resource_list as resource>
  <li><a href='${SiteUrl}manage/resource.action?cmd=view&amp;resourceId=${resource.resourceId}' 
    target='_blank'>${resource.title!?html}</a></li>
</#list>
</ul>
 <li>subject = ${subject}
 <li>pager = ${pager}
</div>