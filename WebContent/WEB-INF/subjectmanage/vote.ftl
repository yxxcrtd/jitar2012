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
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<h2>学调查与投票管理</h2>
<form method='POST' id='oForm' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if resource_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>资源标题</th>
<th><nobr>上传者</nobr></th>
<th><nobr>上传时间</nobr></th>
<th><nobr>大小</nobr></th>
<th><nobr>
  <select name='sc' onchange='doFilter()'>
    <option value=''>资源分类</option>
    <#if resource_categories??>
  	<#list resource_categories.all as c >
    <option value='${c.categoryId}' ${(ss == c.categoryId?string)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
   </#list>
   </#if>
  </select>
</nobr></th>
<th><nobr>
<select name='approvestate' onchange='doFilter()'>
<option value=''${(sa == "")?string(" selected='selected'","")}>审核状态</option>
<option value='0'${(sa == "0")?string(" selected='selected'","")}>已审核</option>
<option value='1'${(sa == "1")?string(" selected='selected'","")}>待审核</option>
</select>
</nobr></th>
<th><nobr>
<select name='deletestate' onchange='doFilter()'>
<option value=''${(sd == "")?string(" selected='selected'","")}>删除状态</option>
<option value='1'${(sd == "1")?string(" selected='selected'","")}>待删除</option>
<option value='0'${(sd == "0")?string(" selected='selected'","")}>正常资源</option>
</select>
</nobr></th>
<th><nobr><select name='rcmdstate' onchange='doFilter()'>
<option value=''${(sr == "")?string(" selected='selected'","")}>推荐状态</option>
<option value='1'${(sr == "1")?string(" selected='selected'","")}>推荐资源</option>
<option value='0'${(sr == "0")?string(" selected='selected'","")}>正常资源</option>
</select></nobr></th>
<th><nobr><select name='sharestate' onchange='doFilter()'>
<option value='-1'${(sm == "-1")?string(" selected='selected'","")}>发布状态</option>
  <option value='1000'${(sm == "1000")?string(" selected='selected'","")}>完全公开</option>
  <option value='500'${(sm == "500")?string(" selected='selected'","")}>组内公开</option>
  <option value='0'${(sm == "0")?string(" selected='selected'","")}>私有</option>
</select></nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list resource_list as r>
<#assign user = Util.userById(r.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${r.resourceId}' onclick='SetRowColor(event)' /></td>
<td>
<img src='${Util.iconImage(r.href)}' />
<a href='${Util.url(r.href)}' target='_blank'>${r.title}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${r.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${Util.fsize(r.fsize)}</nobr></td>

<td><nobr>${r.sysCateName!?html}</nobr></td>
<td><nobr>
<#if r.auditState == 0 >
已审核
<#elseif r.auditState == 1 >
<span style='color:#f00'>待审核</span>
<#else>
未定
</#if>
</nobr></td>
<td><nobr><#if r.delState>待删除</#if></nobr></td>
<td><nobr><#if r.recommendState>推荐</#if></nobr></td>
<td><nobr>
<#if r.shareMode == 1000>
完全公开
<#elseif r.shareMode == 500>
组内公开
<#elseif r.shareMode == 0>
私有
<#else>
未定义
</#if>
</nobr></td>
<td><nobr><a href='${SiteUrl}manage/admin_resource.py?cmd=edit&resourceId=${r.resourceId}'>修改</a></nobr>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从学科中删除' onclick="doPost('remove')" />
  <input class='button' type='button' value='彻底删除' onclick="if(window.confirm('你真的要删除吗？\r\n\r\n彻底删除将真正删除文章，不可恢复。')){doPost('real_delete');}" />
  <select name='cmdtype'>
   <option value=''>选择一项操作</option>
   <option value='approve'>通过审核</option>
   <option value='unapprove'>取消审核</option>
   <option value='rcmd'>设为推荐</option>
   <option value='unrcmd'>取消推荐</option>
   <option value='delete'>设为待删除</option>
   <option value='undelete'>取消待删除</option>
  </select>
  <input class='button' type='button' value='执行选择的操作' onclick="doPost('select_type')" />
</div>
</form>
<script type='text/javascript'>
function SetRowColor(evt)
{
  var oCheckBox = Platform.isIE ? window.event.srcElement : evt.target;
  var oTr = oCheckBox;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {  
	oTr.style.backgroundColor=oCheckBox.checked?'#e6f4b2':'#FFFFFF';
  }  
}

function SetRowColorByName()
{
  var oCheckBoxs = document.getElementsByName('guid');
  for(i=0;i<oCheckBoxs.length;i++)
  {
     var oTr = oCheckBoxs[i];
     while(oTr.tagName && oTr.tagName != "TR")
     oTr = oTr.parentNode
     if(oTr)
	  {
		oTr.style.backgroundColor=oCheckBoxs[i].checked?'#e6f4b2':'#FFFFFF';
	  }  
  }  
}

function colorTable()
{
  var t = document.getElementById('listTable')
  for(var i = 1;i<t.rows.length;i++)
  {
  	addEventHandler(t.rows[i],"mouseover",Mouse_Over);
  	addEventHandler(t.rows[i],"mouseout",Mouse_Out);
  }  
}

function Mouse_Over(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {
  	oTr.setAttribute('oldColor',oTr.style.backgroundColor);
  	oTr.style.backgroundColor='#dec8f1';
  }
}

function Mouse_Out(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode
  if(oTr)
  {
  	oTr.style.backgroundColor = oTr.getAttribute('oldColor');
  }
}

function addEventHandler(oTarget, sEventType, fnHandler) {
　　if (oTarget.addEventListener) {
　　　　oTarget.addEventListener(sEventType, fnHandler, false);
　　} else if (oTarget.attachEvent) {
　　　　oTarget.attachEvent("on" + sEventType, fnHandler);
　　} else {
　　　　oTarget["on" + sEventType] = fnHandler;
　　}
};
//colorTable();
//
</script>
</body>
</html>