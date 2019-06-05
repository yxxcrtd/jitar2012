<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
 <script type='text/javascript'> 
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
 
 </script>
<script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<form method='post' action='system_plugin.py'>
<#if plugin??>
<input type='hidden' name='pluginId' value='${plugin.pluginId}' />
</#if>
<input type='hidden' name='cmd' value='' />
<h2>网站插件模块管理</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th style='width:17px'><input type='checkbox' onclick="CommonUtil.SelectAll(this,'plugin_id')" /></th>
<th>插件模块英文名称</th>
<th>插件模块中文名称</th>
<th>图标文件名</th>
<th>预览图标</th>
<th>是否启用</th>
<th>修改</th>
</tr>
</thead>
<#if plugin_list??>
<#list plugin_list as p>
<#if p.enabled == 1>
<tr style='font-weight:bold'>
<#else>
<tr>
</#if>
<td><input type='checkbox' name='plugin_id' value='${p.pluginId}' /></td>
<td>${p.pluginName}</td>
<td>${p.pluginTitle}</td>
<td>${p.icon!}</td>
<td>
<#if p.icon?? >
<img src='${SiteUrl}js/jitar/moduleicon/${p.icon}' />
</#if>
</td>
<td>
<#if p.enabled == 1>
是
<#else>
否
</#if>
</td>
<td><a href='system_plugin.py?pluginId=${p.pluginId}#edit'>修改</a></td>
</tr>
</#list>
</#if>
</table>
<div>
<input type='button' value=' 启  用 ' class='button' onclick='doPost("enabled")'  />
<input type='button' value=' 禁  用 ' class='button' onclick='doPost("disabled")'  />
<input type='button' value=' 删  除 ' class='button' onclick='doPost("delete")'  />
<div style='color:red;padding-top:6px'>注意：1，删除插件将删除所有使用该插件已经创建的内容。2，只有软件开发商才可以更改插件的“英文名称”，更改“英文名称”将可能导致异常后果。</div>
</div>
<br/>
<hr/>
<#if plugin??>
<a name='edit'></a>
<div>插件模块英文名称：<input maxlength='20' value='${plugin.pluginName?html}' name='pluginName' style='width:160px' /></div>
<div>插件模块中文名称：<input maxlength='20' value='${plugin.pluginTitle?html}' name='pluginTitle' style='width:160px' /></div>
<div>
<input type='button' value='保存修改' class='button' onclick='doPost("edit")' />
</div>
<#else>
<div>插件模块英文名称：<input maxlength='20' name='pluginName' style='width:160px' /></div>
<div>插件模块中文名称：<input maxlength='20' name='pluginTitle' style='width:160px'/></div>
<div>插件模块显示图标：<input maxlength='125' name='pluginIcon' style='width:160px'/></div>
<div>
<input type='button' value='添加新插件模块' class='button' onclick='doPost("new")' />
(显示图标请先放置到网站的 \js\jitar\moduleicon\ 文件夹下面。这里只填写文件名。 )
</div>
</#if>

</form>
</body>
</html>