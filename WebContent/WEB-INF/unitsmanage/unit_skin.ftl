<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>机构页面样式管理</h2>
<form method='post'>
<#if theme_list?? >
<table class='listTable' cellspacing='1'>
<#list theme_list as theme>
<tr>
<td style='width:70px'>样式${theme_index + 1}：</td><td><input type='radio' name='tmpl' value='${theme}' <#if unit.themeName?? && unit.themeName ==theme>checked='checked' </#if>/></td>
<td>
<#if UnitUrlPattern??>
<a target='_blank' href='${UnitUrlPattern.replace('{unitName}',unit.unitName)}?theme=${theme}'>预览效果</a>
<#else>
<a target='_blank' href='${SiteUrl}d/${unit.unitName}/?theme=${theme}'>预览效果</a>
</#if>
</td>
</tr>
</#list>
</table>
<br/>
<input class='button' type='submit' value=' 保存设置 ' />
</#if>
<div style='padding:10px 0'>
  将制作好的样式作为一个单独的文件夹，拷贝放置在\WebRoot\theme\units\文件夹下即可。其他无需操作。注意<strong style='color:#f00'>不要使用中文、空格</strong>做文件夹的名字。
</div>
</form>
</body>
</html>