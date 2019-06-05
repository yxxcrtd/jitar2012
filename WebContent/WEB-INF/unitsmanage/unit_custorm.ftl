<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  function addLink()
  {
  	window.location.href='add_custorm_module.py?unitId=${unit.unitId}';
  }
  function addSysModule()
  {
  	window.location.href='add_sys_module.py?unitId=${unit.unitId}';
  }
  function addContentSpaceModule()
  {
   window.location.href='add_contentspace_module.py?unitId=${unit.unitId}';
  }
  function doAction(param)
  {
    if(param == "delete")
    {
    	if( confirm("您真的要删除吗？") == false)
    	{
    	 return;
    	}
    }
  	document.forms[0].cmd.value = param;
  	document.forms[0].submit();
  }
  </script>
</head>
<body>
<h2>
自定义模块管理
</h2>
<form method='post' style='padding-left:20px'>
<input name='cmd' type='hidden' />
<#if webpart_list??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th></th>
<th>模块名称</th>
<th>位置</th>
<th>是否显示</th>
<th>模块类型</th>
<th>操作</th>
</thead>
</tr>

<#list webpart_list as webpart >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${webpart.unitWebpartId}' /></td>
<td>${webpart.moduleName}</td>
<td>
<#if webpart.webpartZone == 1>
顶部
<#elseif webpart.webpartZone = 2>
下部
<#elseif webpart.webpartZone = 3>
左
<#elseif webpart.webpartZone = 4>
中
<#elseif webpart.webpartZone = 5>
右
<#else>
未定义
</#if>
</td>
<td><#if webpart.visible>显示<#else>隐藏</#if></td>
<td>
<#if webpart.partType == 0>
自定义内容
<#elseif webpart.partType == 1>
系统分类内容
<#elseif webpart.partType == 2>
自定义分类内容
<#else>
未定义的类型
</#if>
</td>
<td>
<#if webpart.partType == 0>
<a href='add_custorm_module.py?unitId=${unit.unitId}&amp;webpartId=${webpart.unitWebpartId}'>修改</a>
<#elseif webpart.partType == 1>
<a href='add_sys_module.py?unitId=${unit.unitId}&amp;webpartId=${webpart.unitWebpartId}'>修改</a>
<#elseif webpart.partType == 2>
<a href='add_contentspace_module.py?unitId=${unit.unitId}&amp;webpartId=${webpart.unitWebpartId}'>修改</a>
<#else>
未定义的类型
</#if>
</td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='添加自定义内容模块' onclick='addLink()' />
	<input class='button' type='button' value='添加系统分类模块' onclick='addSysModule()' />
	<input class='button' type='button' value='添加自定义分类模块' onclick='addContentSpaceModule()' />
	<input class='button' type='button' value='删除模块' onclick='doAction("delete")' />	
	<input class='button' type='button' value='设为显示' onclick='doAction("visible")' />
	<input class='button' type='button' value='设为隐藏' onclick='doAction("hidden")' />
</div>
</form>
</body>
</html>
