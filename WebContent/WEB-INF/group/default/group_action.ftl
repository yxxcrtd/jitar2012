<#if action_list?? >
<ul class="listul">
    <#list action_list as a >
    <li><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></li>
    </#list> 
</ul>
<div style='text-align:right'><a href='${SiteUrl}g/${group.groupName}/py/group_action_list.py'>&gt;&gt;全部活动</a></div>
</#if>