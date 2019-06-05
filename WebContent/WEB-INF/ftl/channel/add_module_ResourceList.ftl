<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script src="${SiteUrl}js/jitar/gettemplate.js?20110222"></script>
</head>
<body>
<form method="POST">
<#if module??>
<table style="width:100%">
<tr>
<td style="width:60px;">模块名称：</td><td><input name="moduleDisplayName" value="${module.displayName!?html}" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）</td>
</tr>
<tr>
<td>显示条数：</td><td><input name="showCount" value="${module.showCount!?html}" /></td>
</tr>
<tr style="vertical-align:top">
<td>显示模板：</td><td><textarea style="width:100%;height:400px;" name="template">${module.template!?html}</textarea></td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="保存模块" />
<input type="button" value="加载默认模板" onclick="GetTemplate('${moduleType!?js_string}',${channel.channelId})" />
</td>
</tr>
</table>
<#else>
<table style="width:100%">
<tr>
<td style="width:60px;">模块名称：</td><td><input name="moduleDisplayName" value="上传资源列表" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）</td>
</tr>
<tr>
<td>显示条数：</td><td><input name="showCount" value="20" /></td>
</tr>
<tr style="vertical-align:top">
<td>显示模板：</td><td><textarea style="width:100%;height:400px;" name="template"></textarea></td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="添加模块" />
<input type="button" value="加载默认模板" onclick="GetTemplate('${moduleType!?js_string}',${channel.channelId})" />
</td>
</tr>
</table>
</#if>
</form>