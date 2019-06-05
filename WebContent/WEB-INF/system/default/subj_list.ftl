<div>
<ul>
<#list subject_list as subject>
  <li><a href='${SiteUrl}s/subject/${subject.subjectCode}'>${subject.subjectName}</a></li>
</#list>
</ul>
</div>

<div>DEBUG current_subject = ${current_subject!}</div>
