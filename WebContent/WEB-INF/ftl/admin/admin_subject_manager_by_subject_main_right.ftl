<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type='text/javascript'>
  function doDeleteAction()
  {
   if(window.confirm("你真的要删除这些管理员吗？"))
   {
    document.getElementById("F1").submit();
   }
  }
  
  function doFilterAction(oS)
  {
   var s = oS.options[oS.selectedIndex].value
   if(s == "")
   {
     window.location.href="admin_subject_manager_by_subject_main_right.py<#if subject??>?subjectId=${subject.subjectId}</#if>"
   }
   else
   {
     window.location.href="admin_subject_manager_by_subject_main_right.py?objectType="+s+"&<#if subject??>subjectId=${subject.subjectId}</#if>"
   }
  }
  </script>
 </head>
 <body>
<#if subject??>
 <h2>${subject.subjectName?html} 的管理员</h2>
<#else>
  <h2>全部学科管理员</h2>
</#if>
<form method="GET" action="admin_subject_manager_by_subject_main_right.py" style='text-align:right'>
<#if subject??>
<input type='hidden' name="subjectId" value="${subject.subjectId}" />
</#if>
<#if !k??><#assign k=""></#if>
<#if !f??><#assign f=""></#if>
输入关键字：<input name='k' value="${k!?html}" />
<select name='f'>
<option value="trueName"<#if f=="trueName"> selected="selected"</#if>>真实姓名</option>
<option value="loginName"<#if f=="loginName"> selected="selected"</#if>>登录名</option>
</select>
<input type='submit' value="搜索管理员" />
</form>
<form method="POST" id="F1">
<#if admin_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");' id='chk' /></th>
<th>登录名</th>
<th>真实姓名</th>
<th>
<select onchange="doFilterAction(this)">
<option value=''>管理级别</option>
<option value='7'<#if objectType == 7> selected='selected'</#if>>学科系统管理员</option>
<option value='8'<#if objectType == 8> selected='selected'</#if>>学科用户管理员</option>
<option value='9'<#if objectType == 9> selected='selected'</#if>>学科内容管理员</option>
</select>
</th>
<th>管理学科</th>
<th>管理员所在的机构</th>
</tr>
</thead>
<#list admin_list as ul>
<#assign unit2=Util.unitById(ul.unitId)>
<tr>
<td><input type='checkbox' name='guid' value='${ul.accessControlId}' /></td>
<td>${ul.loginName}</td>
<td><a href='${SiteUrl}go.action?userId=${ul.userId}' target='_blank'>${ul.trueName!?html}</a></td>
<td>${ObjectType[ul.objectType]}</td>
<td><a href='${SiteUrl}go.action?id=${ul.objectId}' target='_blank'>${ul.objectTitle}</a></td>
<td><a href='${SiteUrl}go.action?unitName=${unit2.unitName}' target='_blank'>${unit2.unitTitle!?html}</a></td>
</tr>
</#list>
</table>
</#if>
</form>
<#if pager??>
<div style="text-align:center;padding:10px 0;">
<#include "/WEB-INF/ftl/inc/pager.ftl">
</div>
</#if>
 <div>
 <input type='button' value='删除管理员' onclick='doDeleteAction()' />
 <#if subject??>
 <input type='button' value='添加管理员' onclick='window.location.href="admin_subject_manager_add.py?subjectId=${subject.subjectId}"' />
 </#if>
 </div>
</body>
</html>