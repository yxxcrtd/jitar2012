<#if q_list??>
<ul class='listul'>
<#list q_list as q>
<li><a href='${SiteUrl}mod/questionanswer/question_getcontent.action?guid=${parentGuid}&amp;type=${parentType}&amp;qid=${q.questionId}&unitId=${unitId!}'>${q.topic}</a></li>
</#list>
</ul>
</#if>
<div style='text-align:right;'>
<a href='questioneditform.action?guid=${parentGuid}&amp;type=${parentType}&unitId=${unitId!}'>提出问题</a> | 
<a href='${SiteUrl}mod/questionanswer/question_manage_list.action?guid=${parentGuid}&amp;type=${parentType}&unitId=${unitId!}'>管理问题</a> | 
<a href='${UserSiteUrl}html/question_0.html'>全部问题</a>
</div>