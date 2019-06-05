<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>频道页面样式管理</h2>
<form method='post' action="channel.action">
<input type="hidden" name="cmd" value="saveskins"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<#if theme_list?? >
<table class='listTable' cellspacing='1'>
<#list theme_list as theme>
<tr>
<td style='width:70px'>样式${theme_index + 1}：</td><td><input type='radio' name='tmpl' value='${theme}' <#if channel.skin?? && channel.skin == theme> checked='checked' </#if>/></td>
<td>
<a target='_blank' href='${SiteUrl}channel/channel.action?channelId=${channel.channelId}&theme=${theme}'>预览效果</a>
</td>
</tr>
</#list>
</table>
<br/>
<input class='button' type='submit' value=' 保存设置 ' />
</#if>
<div style='padding:10px 0'>
 <br/>1.如果选择了新的页面样式,在“模板管理”中所设置的频道首页模板、频道页头、频道页脚、频道CSS将被新内容替换。 
 <br/>2.将制作好的样式作为一个单独的文件夹，拷贝放置在\WebRoot\css\channel;将布局结构文件放在\WebRoot\WEB-INF\channel\文件夹下。其他无需操作。注意<strong style='color:#f00'>不要使用中文、空格</strong>做文件夹的名字。
</div>
</form>
</body>
</html>