<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript">
  function doAction(cmd)
  {
  	if(cmd == "Add")
  	{
  	  window.location='unit_child_edit.py?unitId=${unit.unitId}';
  	}
  	else if(cmd == "SelectAll")
  	{
  	  document.getElementById("_chk").checked = !document.getElementById("_chk").checked
  	  CommonUtil.SelectAll(document.getElementById("_chk"),"guid")
  	}
  	else
  	{
  	 alert('无效的命令。')
  	}
  }  
  
  function DeleteAction(s)
  {
   return window.confirm("你真的要删除机构  " + s + "  吗？");
  }
  </script>
</head>
<body>
<h2>机构名称：${unit.unitTitle}</h2>
<form method='post' id="F1">
<input type="hidden" name="cmd" value="" />
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' id='_chk' /></th>
<th>下属机构名称</th>
<th>下属机构网站名称</th>
<th>机构属性</th>
<th>本机构用户数</th>
<th>本机构文章数</th>
<th>本机构资源数</th>
<th>本机构图片数</th>
<th>本机构视频数</th>
<th>操作</th>
</tr>
</thead>
<#if childunitlist??>
<#list childunitlist as u>
<tr>
  <td><input type='checkbox' name='guid' value='${u.unitId}' /></td>
  <td><a href='${SiteUrl}go.action?unitId=${u.unitId}' target='_blank'>${u.unitTitle}</a></td>
  <td>${u.siteTitle}</td>
  <td>${u.unitType!?html}</td>
	<#assign unitDayCount = Util.getUnitDayCount(u.unitId) />
  <td><#if unitDayCount?? && unitDayCount != "">${unitDayCount.userCount}</#if></td>
  <td><#if unitDayCount?? && unitDayCount != "">${unitDayCount.articleCount + unitDayCount.historyArticleCount}</#if></td>
  <td><#if unitDayCount?? && unitDayCount != "">${unitDayCount.resourceCount}</#if></td>
  <td><#if unitDayCount?? && unitDayCount != "">${unitDayCount.photoCount}</#if></td>
  <td><#if unitDayCount?? && unitDayCount != "">${unitDayCount.videoCount}</#if></td>
  <td>
  <#if u.delState >
  <span style="color:red">已删除</span> 
  <#else>
  	<a href='unit_child_edit.py?unitId=${unit.unitId}&childUnitId=${u.unitId}'>修改</a>
	  <#if !u.hasChild>
	  | <a href="?cmd=delete&unitId=${unit.unitId}&childUnitId=${u.unitId}" onclick="return DeleteAction('${u.unitTitle!?js_string}')">删除</a> 
	  </#if>
  </#if>
  </td>
</tr>
</#list>
</#if>
</table>
<div>
<input class="button" type="button" value="添加下属机构" onclick='doAction("Add")' />
</div>
</form>
<div style="color:red">注意：删除下属机构，请先删除该机构下面的用户、文章、资源、话题讨论、投票等等该机构下的所有内容。</div>
</body>
</html>