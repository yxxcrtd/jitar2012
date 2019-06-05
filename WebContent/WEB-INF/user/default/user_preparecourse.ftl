<#if course_list?? >
<ul class="listul">
    <#list course_list as p >
    <li>
    <a href='${SiteUrl}p/${p.prepareCourseId}/0/'>${p.title!?html}</a></li>
    </#list> 
</ul>

<div style='text-align:right'><a href='${UserSiteUrl}py/user_preparecourse_list.py'>&gt;&gt;我发起的全部备课</a></div>

</#if>