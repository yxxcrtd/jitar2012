<#if resource_list?? >
<ul class='listul'>
<#list resource_list as r>
  <#assign u = Util.userById(r.userId)>
  <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a> ${r.createDate?string('MM-dd')}</span><a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank'>${r.resourceTitle!?html}</a></li>
 </#list>
</ul>
<div style='text-align:right'>
<a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_resource_list.py'>&gt;&gt;全部资源</a>
<#if isMember?? >
<a href='${SiteUrl}manage/resource.action?cmd=upload&amp;prepareCourseStageId=${prepareCourseStageId}'>发布资源</a>
</#if>
</div>
<#else>
当前流程无资源。
<#if isMember?? >
<div style='text-align:right'>
<a href='${SiteUrl}manage/resource.action?cmd=upload&amp;prepareCourseStageId=${prepareCourseStageId}'>发布资源</a>
</div>
</#if>
</#if>
