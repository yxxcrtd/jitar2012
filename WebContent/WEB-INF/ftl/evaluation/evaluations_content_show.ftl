<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
<style>
  #t1 tr td,#t1 tr th{background:#fff;vertical-align:top;}
</style>
  <script src='js/jitar/core.js'></script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<#if evaluationContent??>
<script>
<#if evaluationContent.publishContent?? && evaluationContent.publishContent != "">
var content = ${evaluationContent.publishContent};
<#else>
var content = [];
</#if>
window.onload=function()
{
  for(i=0;i<content.length;i++)
  {
   field = content[i];
   tr = document.createElement("tr");
   th = document.createElement("th")
   td = document.createElement("td")
   td.colSpan = 3
   for(x in field)
   {
   th.innerHTML = x
   td.innerHTML = field[x]
   }
   tr.appendChild(th);
   tr.appendChild(td);
   document.getElementById("t1").tBodies[0].appendChild(tr);
  }
}
</script>
<table id="t1" border="0" cellspacing="1" cellpadding="4" style="background:#B0BEC7;margin:auto;width:800px;">
<tbody>
<tr>
<th style="width:100px;">评课名称：</th><td style="width:300px;">${evaluationContent.title!?html}</td>
<th style="width:100px;">学段：</th><td style="width:300px;"><#if evaluationContent.metaGradeId??><#assign grade = Util.gradeById(evaluationContent.metaGradeId) ><#if grade??>${grade.gradeName!}</#if></#if></td>
</tr>
<tr>
<th>授课人：</th><td>${evaluationContent.courseTeacherName!?html}</td>
<th>学科：</th><td><#if evaluationContent.metaSubjectId??><#assign subject = Util.subjectById(evaluationContent.metaSubjectId) ><#if subject??>${subject.msubjName!}</#if></#if></td>
</tr>
<tr>
<th>评课人：</th><td>${evaluationContent.publishUserName!?html}</td>
<th>评课时间：</th><td>${evaluationContent.createDate!?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
</tbody>
</table>
</#if>
<#include '/WEB-INF/ftl/footer.ftl' >
</body>
</html>