<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="../css/manage.css" /> 
</head>
<body>
<h2>删除个人空间模块</h2>
<div class='funcButton'>您可以删除个人空间中显示错误的模块。</div>
<#if widget_list?? && widget_list?size &gt; 0>
<form method='post'>
<table class='listTable' cellspacing='1'>
<thead>
<tr style="text-align:left">
  <th style="width:17px"></th>
  <th>模块名称</th>
  <th>创建日期</th>
</tr>
</thead>
<tbody>
<#list widget_list as w>
<tr>
<td><input type='checkbox' value='${w.id}' name="guid" /></td>
<td>${w.title!?html}</td>
<td>${w.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
</#list>
</table>
<input type="submit" value=" 删  除 " class="button" />
</form>
</#if>
</tbody>
</body>
</html>