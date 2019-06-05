<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var sch = f.schannel.options[f.schannel.selectedIndex].value;
  	var qs = "schannel=" + sch + "&type=filter"
  	var url = "channelresource.action?channelId=${channel.channelId}&";
  	window.location.href = url + qs;  	
  }
function checkSelectedItem()
{
  gs = document.getElementsByName("guid");
  for(i=0;i<gs.length;i++)
  {
   if(gs[i].checked) return true;
  }
  
  alert("请选择一个资源。");
  return false;
}
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>频道资源管理</h2>
<form method='GET' action='channelresource.action'>
<input name='channelId' type='hidden' value='${channel.channelId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>资源标题</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
  <option value='unitTitle'${(f=='unitTitle')?string(' selected="selected"','')}>用户所属机构</option>
</select>
<select name='schannel'>
 <option value=''>资源频道分类</option>
 <#if channel_resource_categories??>
 <#list channel_resource_categories.all as c >
 <#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
    <option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
 </#list>
 </#if>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if resource_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>资源标题</th>
<th><nobr>上传者</nobr></th>
<th><nobr>所在机构</nobr></th>
<th><nobr>上传时间</nobr></th>
<th><nobr>大小</nobr></th>
<th><nobr>
<select name='schannel' onchange='doFilter()'>
<option value=''>频道资源分类</option>
<#if channel_resource_categories??>
<#list channel_resource_categories.all as c >
<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
<option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
</#list>
</#if>
</select>
</nobr></th>
</tr>
</thead>
<#list resource_list as r>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${r.channelResourceId}' onclick='SetRowColor(event)' /></td>
<td>
<img src='${Util.iconImage(r.href!)}' />
<a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank'>${r.title!}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${r.loginName!}' target='_blank'>${r.userTrueName!}</a></nobr></td>
<td><nobr><a href='${SiteUrl}go.action?unitName=${r.unitName!}' target='_blank'>${r.unitTitle!}</a></nobr></td>
<td><nobr>${r.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>
${Util.fsize(r.fsize!)}
</nobr></td>
<td><nobr>
<#if r.channelCateId??>
<#assign cate = Util.getCategory(r.channelCateId)>
<#if cate??>
${cate.name!?html}
</#if>
</#if>
</nobr></td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从频道中删除' onclick="if(checkSelectedItem() && confirm('你确定要删除这些资源吗？'))doPost('remove')" />
  <select name="newCate">
  <option value="">取消分类</option>
  <#if channel_resource_categories??>
    <#list channel_resource_categories.all as c >
    <#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
    <option value='${cp}'>${c.treeFlag2} ${c.name!?html}</option>
    </#list>
    </#if>
  </select>
  <input class='button' type='button' value='转移到选定分类' onclick="if(checkSelectedItem() && confirm('真的要对这些资源进行重新分类吗？')){doPost('recate');}" />
</div>
</form>
</body>
</html>