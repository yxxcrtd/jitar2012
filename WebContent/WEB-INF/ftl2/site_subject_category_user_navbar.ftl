<a href='index.action'>首页</a> &gt; 
<#if subject??>
  <a href='showSubject.py?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'>${subject.subjectName}</a> &gt;
</#if>
<#if category??>
  <a href='showCategory.action?subjectId=${(subject!).metaSubject.msubjId!}&gradeId=${gradeId!}&amp;categoryId=${category.categoryId}'>${category.name}</a> &gt;
</#if>
<#if user??>
  <a href='${user.loginName}&gradeId=${gradeId!}'>${user.nickName}</a> &gt; 
</#if>
