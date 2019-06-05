<#if topic_list?? >
<ul class='listul'>
<#list topic_list as t>
  <#assign u = Util.userById(t.userId)>
  <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a> ${t.createDate?string('MM-dd')}</span>
  <a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_stage_topic_detail.py?prepareCourseTopicId=${t.prepareCourseTopicId}' target='_blank'>${t.title!?html}</a></li>
 </#list>
</ul>
<div style='text-align:right'>
<a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_topic_list.py'>&gt;&gt;全部讨论</a>
<#if isMember?? >
<a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/preparecourse_topic_create.py'>发起讨论</a>
</#if>
</div>
<#else>
当前流程无讨论。
<#if isMember?? >
<div style='text-align:right'>
<a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/preparecourse_topic_create.py'>发起讨论</a>
</div>
</#if>
</#if>