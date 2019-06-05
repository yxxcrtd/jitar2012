<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function doAction(param)
  {
  	document.forms[1].cmd.value = param;
  	document.forms[1].submit();
  }
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var sauditState = f.auditState.options[f.auditState.selectedIndex].value;
  	var qs = "auditState=" + sauditState +  "&type=filter"
  	var url = "unit_video.py?unitId=${unit.unitId}&";
  	window.location.href = url + qs;  	
  }   
  </script>
</head>
<body>
<h2>
本机构视频管理
</h2>
<form method='GET' action='unit_video.py'>
<input type='hidden' name='unitId' value='${unit.unitId}' />
<input type='hidden' name='cmd' value='' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='0'${(f=='0')?string(' selected="selected"','')}>标题、标签</option>
  <option value='1'${(f=='1')?string(' selected="selected"','')}>视频概要</option>
  <option value='2'${(f=='2')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='POST' id='oForm'>
<input type='hidden' name='cmd' value='' />
<#if video_list??>
<table class='listTable' cellspacing='1'>
<tr style='text-align:left;'>
<th></th>
<th>缩略图</th>
<th>视频信息</th>
<th width="10%">
<nobr>
<select name='auditState' onchange='doFilter()'>
	<option value=''>审核状态</option>
	<option value='0' ${(auditState=='0')?string(' selected="selected"','')}>已审</option>
	<option value='1' ${(auditState=='1')?string(' selected="selected"','')}>待审</option>
</select>
</nobr>      
</th>
</tr>
<#list video_list as v >
<tr style='vertical-align:top'>
<td style='width:17px'><input type='checkbox' name='guid' value='${v.videoId}' /></td>
<td style='width:120px'><a href="${SiteUrl}manage/video.action?cmd=show&amp;videoId=${v.videoId}" target="_blank"><img src="${v.flvThumbNailHref!}" border='0' /></a></td>
<td style='width:40%'>
标题：${v.title?html}<br/>
上传者：<a target='_blank' href='${SiteUrl}go.action?loginName=${v.loginName}'>${v.nickName?html}</a><br/>
创建时间：${v.createDate?string('MM-dd HH:mm:ss')}<br/>
IP来源：${v.addIp!}<br/>
标签：${v.tags!}<br/>
分类：${v.staple!}
</td>
<td>
<#if v.auditState  == 0 >
已审
<#else>
<span style='color:red'>待审</span>
</#if>
</td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='删除视频' onclick='doAction("delete")' />
	<input class='button' type='button' value='审核视频' onclick='doAction("approve")' />
	<input class='button' type='button' value='取消审核' onclick='doAction("unapprove")' />
</div>
<#if pager??>
	<#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
</body>
</html>
