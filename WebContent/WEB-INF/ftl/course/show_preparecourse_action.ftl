<#if action_list?? >
<ul class="listul">
    <#list action_list as a >
    <#--<li><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></li>-->
    <li><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a></li>
    </#list>
</ul>
<div style='text-align:right'>
<a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_action_list.py'>&gt;&gt;全部活动</a>
<#if isMember?? >
<a href='${SiteUrl}p/${prepareCourseId}/0/py/show_preparecourse_action_create.py'>发起活动</a>
</#if>
</div>
</#if>