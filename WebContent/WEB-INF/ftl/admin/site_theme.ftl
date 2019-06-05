<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
 <script type='text/javascript'>
 function setEnable(themeId)
 {
  document.forms[0].cmd.value = "enable";
  document.forms[0].usedtheme.value = themeId;
  document.forms[0].submit();
 }
 
 function doPost(arg)
 {
   if(arg == "delete")
   {
    if(!window.confirm("你真的要删除吗？\r\n\r\n注意：删除之后将不能恢复。"))
    {
    	return;
    }
   }
  document.forms[0].cmd.value = arg;
  document.forms[0].submit();
 }
 
 function checkFolder(txt)
 {  
   strtxt = txt.value
   str = '\\/:*?"<>\'';
   for(i=0;i<str.length;i++)
   {
	   if(strtxt.indexOf(str[i]) > -1)
	   {
	    alert("文件夹名称不合法。")
	    txt.focus()
	    break;
	   }
   }
 }
 
 function checkTheForm(of)
 {
 	g = of.elements["guid"];
 	for(i=0;i<g.length;i++){
 	if(g[i].checked) return true;
 	}
 	alert("请选择一个样式。");
 	return false;
 }
 </script>
</head>
<body>
<form method='post' action='site_theme.py'>
<#if sitetheme??>
<input type='hidden' name='siteThemeId' value='${sitetheme.siteThemeId}' />
</#if>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='usedtheme' value='' />
<h2>网站样式主题管理</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th style='width:17px'></th>
<th>样式名称</th>
<th>样式目录</th>
<th>预览</th>
<th>样式预览图</th>
<th>启用</th>
<th>修改</th>
</tr>
</thead>
<#list theme_list as t>
<#if t.status == 1>
<tr style='font-weight:bold'>
<#else>
<tr>
</#if>
<td><#if t.status != 1><input type='checkbox' name='guid' value='${t.siteThemeId}' /></#if></td>
<td>${t.title}</td>
<td>${t.folder}</td>
<td><a href='${SiteUrl}index.action?preview=${t.folder}' target='_blank'>查看预览</a></td>
<td><img src='${SiteUrl}theme/site/${t.folder}/preview.jpg' alt='预览图' /></td>
<td>
<#if t.status == 1>
<input type='radio' name='status' value='1' checked='checked' />
<#else>
<input type='radio' name='status' value='0' onclick='setEnable(${t.siteThemeId})' />
</#if>
</td>
<td><a href='site_theme.py?siteThemeId=${t.siteThemeId}#edit'>修改</a></td>
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='恢复默认样式' class='button' onclick='doPost("reset")'  />
<input type='checkbox' name='deletefile' value='1' />同时删除文件夹和文件
<input type='button' value=' 删  除 ' class='button' onclick='if(checkTheForm(this.form)) {doPost("delete");}' /> <span style='color:#F00'>【注意：】删除样式将会同时删除样式的物理文件夹，并且不可恢复。</span>
</div>
<br/>
<hr/>
<#if sitetheme??>
<a name='edit'></a>
<div>输入样式名称：<input maxlength='20' value='${sitetheme.title?html}' name='themeTitle' style='width:160px' /> <span style='color:red'>*</span> 起一个中文名字，方便记忆。</div>
<div>输入样式目录：<input maxlength='20' value='${sitetheme.folder?html}' name='themeFolder' style='width:160px' onblur='checkFolder(this)' /> <span style='color:red'>*</span> 注意必须是合法的文件夹的名字，而不是整个完整路径。</div>
<div>
<input type='button' value='保存修改' class='button' onclick='doPost("edit")' />
<span style='color:#F00'>
	使用方法：将样式放在一个独立的文件夹下，并以英文命名，然后拷贝到站点的/theme/site/文件夹下，然后再此页面配置。
</span>
</div>
<#else>
<div>输入样式名称：<input maxlength='20' name='themeTitle' style='width:160px' /> <span style='color:red'>*</span> 起一个中文名字，方便记忆。</div>
<div>输入样式目录：<input maxlength='20' name='themeFolder' style='width:160px' onblur='checkFolder(this)' /> <span style='color:red'>*</span> 注意必须是合法的文件夹的名字，而不是整个完整路径。</div>
<div>
<input type='button' value='添加新样式' class='button' onclick='doPost("new")' />
<span style='color:#F00'>
	使用方法：将样式放在一个独立的文件夹下，并以英文命名，然后拷贝到站点的/theme/site/文件夹下，然后再此页面配置。
</span>
</div>
</#if>

<br/>
<hr/>
<div>
<input type='button' value='导入样式' class='button' onclick='doPost("import")' />
<#if retMsg??>
<span style='color:red'>已经将未导入的样式导入完毕。共导入 ${retMsg} 个样式。</span>
<#else>
如果复制了很多样式文件夹，可以点这里导入到系统中。
</#if>
</div>
</form>
</body>
</html>