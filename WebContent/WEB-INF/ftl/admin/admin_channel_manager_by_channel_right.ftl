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
     window.location.href="?<#if channel??>?channelId=${channel.channelId}</#if>"
   }
   else
   {
     window.location.href="?objectType="+s+"&<#if channel??>channelId=${channel.channelId}</#if>"
   }
  }
  </script>
 </head>
 <body>
<#if channel??>
 <h2>${channel.title?html} 的管理员</h2>
<#else>
  <h2>全部频道管理员</h2>
</#if>
<form method="GET" action="admin_channel_manager_by_channel_right.py" style='text-align:right'>
<#if subject??>
<input type='hidden' name="channelId" value="${channel.channelId}" />
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
<option value='11'<#if objectType == 11> selected='selected'</#if>>频道系统管理员</option>
<option value='12'<#if objectType == 12> selected='selected'</#if>>频道用户管理员</option>
<option value='13'<#if objectType == 13> selected='selected'</#if>>频道内容管理员</option>
</select>
</th>
<th>管理频道</th>
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
<td><a href='${SiteUrl}manage/channel/channel.action?cmd=manage&channelId=${ul.objectId}' target='_blank'>${ul.objectTitle}</a></td>
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
 <#if channel??>
 <input type='button' value='添加管理员' onclick='window.location.href="admin_channel_manager_add.py?channelId=${channel.channelId}"' />
 </#if>
 </div>
</body>
</html>