<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form method='post' action='channel.action'>
<input type="hidden" name="cmd" value="saveaddchannel"/>
<#if channel??>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<#else>
<input type="hidden" name="channelId" value="0"/>
</#if>
<h2><#if channel??>修改频道名称<#else>添加新频道</#if></h2>
<table>
<tr>
<td>请输入频道名称：</td><td><input name='title' value="<#if channel??>${channel.title!?html}</#if>" /></td>
</tr>
</table>
<input type="submit" value="<#if channel??>修改频道名称<#else>添加频道</#if>" />
</form>
</body>
</html>