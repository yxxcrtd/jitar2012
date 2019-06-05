<div class='headnav'>
<#if SubjectNav?? >
<#list SubjectNav as m >
<div><span>${m.gradeName}</span>&nbsp;&nbsp;<#list m.metaSubject as ms ><a href='${SiteUrl}go.action?subjectId=${ms.msubjId}&gradeId=${m.gradeId}'>${ms.msubjName}</a><#if ms_has_next > | </#if></#list></div>
</#list>
</#if>
</div>