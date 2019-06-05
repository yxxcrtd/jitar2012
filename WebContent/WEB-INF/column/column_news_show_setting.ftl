<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>栏目管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>栏目管理</h2>
<form name='newsForm' id='newsForm' action='?' method='post'>  
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:20px"></th>
      <th>栏目名称</th>
      <th>允许不登录查看内容</th>
    </tr>
  </thead>
  <tbody>
  <#list columnList as c>
    <tr>
      <td><input type='checkbox' name='columnId' value='${c.columnId}' /></td>
      <td><a target="_blank" href="${SiteUrl}moreColumnNews.py?columnId=${c.columnId}">${c.columnName!?html}</a></td>
      <td><#if c.anonymousShowContent>允许不登录用户查看内容<#else><span style="color:#f00">禁止不登录用户查看内容</span></#if></td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='funcButton'>
  <input name="cmd" type="hidden" value="" />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all();' />
  <input type='button' class='button' value='允许不登录用户查看内容' onclick='doPost("enable")' />
  <input type='button' class='button' value='禁止不登录用户查看内容' onclick='doPost("disable")' />
</div>
</form>
<div style="padding:10px 0;color:#f00">注意：内网IP为172，192，10，127开头的用户在不登录的情况下仍然是可以阅读的。</div>
<script>
function doPost(x)
{
  document.forms[0].cmd.value=x;
  document.forms[0].submit();
}
function select_all() {
  var ids = document.getElementsByName('columnId');
  if (ids == null || ids.length == 0) return;
  var set_checked = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      set_checked = true;
    }
  }
  
  if (set_checked == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
</script>
</body>
</html>
