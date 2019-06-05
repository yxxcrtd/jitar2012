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
    document.getElementById("F1").cmd.value="Delete";
    <#if jitarColumn??>
    document.getElementById("F1").columnId.value="${jitarColumn.columnId}";
    </#if>
    document.getElementById("F1").submit();
   }
  }
  
  function AddUser(cid)
  {
   var s = window.showModalDialog("${SiteUrl}manage/common/user_select.action?type=multi&back=ModalDialogReturn",null,"dialogHeight:700px;dialogWidth:800px")
   if(s && s != "")
   {
    data = s.split("|||");
    ids = data[0].split(",");
    titles = data[1].split(",");
    document.getElementById("F1").cmd.value="Add";
    document.getElementById("F1").userid.value=ids;
    document.getElementById("F1").columnId.value=cid;
    document.getElementById("F1").submit();
   }
  }
  </script>
 </head>
 <body>
<h2><#if jitarColumn??>${jitarColumn.columnName} 栏目管理员<#else>全部特定栏目管理员</#if></h2>
<form method="POST" id="F1" action="column_user_edit.py">
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");' id='chk' /></th>
<th>登录名</th>
<th>真实姓名</th>
<#if !jitarColumn??><th>管理栏目</th></#if>
<th>管理员所在的机构</th>
</tr>
</thead>
<#if accessControlList??>
<#list accessControlList as ac>
<#assign u = Util.userById(ac.userId)>
<#assign unit = Util.unitById(u.unitId)>
<tr>
<td><input type='checkbox' name='guid' value='${ac.accessControlId}' /></td>
<td>${u.loginName}</td>
<td><a href='${SiteUrl}go.action?userId=${u.userId}' target='_blank'>${u.trueName!?html}</a></td>
<#if !jitarColumn??><td>${ac.objectTitle}</td></#if>
<td><a href='${SiteUrl}go.action?unitName=${unit.unitName}' target='_blank'>${unit.unitTitle!?html}</a></td>
</tr>
</#list>
</#if>
</table>
<div>
<input type='button' value='删除管理员' onclick='doDeleteAction()' />
<input type="hidden" name="cmd" value="" />
<#if jitarColumn??>
<input type="hidden" name="userid" value="" />
<input type="hidden" name="columnId" value="" />
<input type='button' value='添加管理员' onclick='AddUser(${jitarColumn.columnId})' />
</#if>
</div>
</form>
</body>
</html>