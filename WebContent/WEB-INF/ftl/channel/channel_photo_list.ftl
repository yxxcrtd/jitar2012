<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
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
    var url = "channelphoto.action?channelId=${channel.channelId}&";
    window.location.href = url + qs;    
}
function checkSelectedItem()
{
  gs = document.getElementsByName("guid");
  for(i=0;i<gs.length;i++)
  {
   if(gs[i].checked) return true;
  }
  
  alert("请选择一个图片。");
  return false;
}
  </script>
</head>
<body>
<h2>频道图片管理</h2>
<div style="text-align: right; width: 100%;">
<form action="channelphoto.action" method="get">
<input type="hidden" name="channelId" value="${channel.channelId}" />
关键字：
<input type="text" size="16" name="k" value="${k!?html}" />
<#if !(f??)><#assign f = '0'></#if>
<select name="f">
<option value="title" ${(f == 'title')?string('selected', '')}>图片标题</option>
<option value="uname" ${(f == 'uname')?string('selected', '')}>上传用户</option>
<option value='unitTitle'${(f=='unitTitle')?string(' selected="selected"','')}>用户所属机构</option>
</select>
<select name="schannel">
<option value="">频道图片分类</option>
<#if channel_photo_categories??>
<#list channel_photo_categories.all as c >
<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
<option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
</#list>
</#if>
</select>
<input type="submit" class="button" value="检  索" />
</form>
</div>
<form id="oForm" action="?channelId=${channel.channelId}" method="post">
<input type="hidden" name="cmd" value="" />
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th style="width:17px"><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th width="7%">图片</th>
<th>图片标题</th>
<th>登录名/真实姓名</th>
<th>所在机构</th>
<th>上传日期</th>
<th><select name='schannel' onchange='doFilter()'>
<option value=''>频道图片分类</option>
<#if channel_photo_categories??>
<#list channel_photo_categories.all as c >
<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
<option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
</#list>
</#if>
</select>
</th>
</tr>
</thead>
<tbody>
<#if photoList??>
<#list photoList as photo>
<tr>
<td style="text-align: center;">
<input type="checkbox" name="guid" value="${photo.channelPhotoId}" onclick='SetRowColor(event)'/></td>
<td style="text-align: center;">
<a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" target="_blank">
<img src="${Util.thumbNails(photo.href! SiteUrl + 'images/default.gif')}" width="64" border="0" title="${photo.title!?html}" />
</a>
</td>
<td style="padding-left: 10px;">
${photo.title}
</td>
<td style="padding-left: 10px;">
<a href="${SiteUrl}go.action?loginName=${photo.loginName!}" title="访问 ${photo.loginName!} 的个人空间" target="_blank">${photo.loginName}</a><br>
<div style="line-height: 6px;">
<br />
</div>
<a href="${SiteUrl}go.action?profile=${photo.loginName!}" title="访问 ${photo.userTrueName!?html} 的个人档案" target="_blank">${photo.userTrueName!?html}</a>
</td>
<td><nobr><a href='${SiteUrl}go.action?unitName=${photo.unitName!}' target='_blank'>${photo.unitTitle!}</a></nobr></td>
<td style="padding-left: 10px;">${photo.createDate!?string("yyyy-MM-dd HH:mm:ss")}
</td>
<td style="text-align: center;">
<#if photo.channelCate??>
<#assign cate = Util.getCategory(photo.channelCate)>
<#if cate??>
${cate.name!?html}
</#if>
</#if>
</td>
</tr>
</#list>
<#else>
<tr>
<td colspan="6" style="text-align:center">无记录。</td>
</tr>
</#if>
</tbody>
</table>
<#if photoList?? && photoList?size != 0>
<div style="width: 100%; text-align: right; margin: 3px auto 3px;">
<#include "/WEB-INF/ftl/inc/pager.ftl">
</div>
<div class="funcButton">
<input class="button" type="button" value="全部选择" onClick="${"chk"}.click();SetRowColorByName();" />
<input class="button" type="button" value="从频道中移除" onClick="if(checkSelectedItem() && confirm('你真的要删除这些图片吗？')){doPost('remove');}" />
<select name="newCate">
  <option value="">取消分类</option>
  <#if channel_photo_categories??>
    <#list channel_photo_categories.all as c >
    <#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
    <option value='${cp}'>${c.treeFlag2} ${c.name!?html}</option>
    </#list>
    </#if>
  </select>
  <input class='button' type='button' value='转移到选定分类' onclick="if(checkSelectedItem() && confirm('真的要对这些图片进行重新分类吗？')){doPost('recate');}" />
</div>
</#if>
</form>
</body>
</html>