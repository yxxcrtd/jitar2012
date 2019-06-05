<a href="javascript:;" class="subjectText">学科导航<span class="arrow1"></span></a>
<div class="topSubjectBox">
<#if SubjectNav?? >
<#list SubjectNav as m >
<dl>
<dt>${m.gradeName}<span class="arrow5"></span></dt>
<dd><#list m.metaSubject as ms ><a href='${SiteUrl}go.action?subjectId=${ms.msubjId}&gradeId=${m.gradeId}'>${ms.msubjName}</a></#list></dd>
</dl>
</#list>
</#if>
</div>