<#if action_list?? >
<ul class="listul">
    <#list action_list as a >
    <li><span>[<#if a.ownerType == 'user' >个人<#elseif a.ownerType == 'group' >群组<#elseif a.ownerType == 'subject'>学科<#elseif a.ownerType == 'preparecourse' >集备<#else>未知</#if>]</span>
    <a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></li>
    </#list> 
</ul>
<div style='text-align:right'><a href='${UserSiteUrl}py/user_joined_actions.py'>&gt;&gt;全部活动</a></div>
</#if>