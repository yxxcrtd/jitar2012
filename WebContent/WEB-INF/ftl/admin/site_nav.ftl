<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
 <script type='text/javascript'>
function doPost(cmdtype)
{
 document.getElementById('oForm').cmd.value = cmdtype;
 document.getElementById('oForm').submit();
}
function setDisplay(s_id,s_display)
{
 document.getElementById('oForm2').siteId.value = s_id;
 document.getElementById('oForm2').siteDisplay.value = s_display;
 document.getElementById('oForm2').submit();
}
 </script>
</head>
<body>
<form method='post' id='oForm2' style='display:none'>
<input type='hidden' name='cmd' value='display' />
<input type='hidden' name='siteId' value='0' />
<input type='hidden' name='siteDisplay' value='0' />
</form>
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<h2>网站导航定制</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr style='text-align:left'>
<th style='width:17px'></th>
<th>导航名称</th>
<th>导航地址</th>
<th>是否显示</th>
<th>显示顺序</th>
<th>修改</th>
</tr>
</thead>
<#list siteNavItemList as nav>
<tr>
<td><#if nav.isExternalLink><input type='checkbox' name='guid' value='${nav.siteNavId}' /></#if></td>
<td>${nav.siteNavName}</td>
<td>
<#if nav.isExternalLink >
<a href='${nav.siteNavUrl}' target='_blank'>${nav.siteNavUrl}</a>
</#if>
</td>
<td>
<label>
<input type='radio' name='nav_${nav.siteNavId}' onclick='setDisplay(${nav.siteNavId},1)' <#if nav.siteNavIsShow>checked='checked'</#if>/>显示
</label>
<label>
<input type='radio' name='nav_${nav.siteNavId}' onclick='setDisplay(${nav.siteNavId},0)' <#if !nav.siteNavIsShow>checked='checked'</#if>/>隐藏
</label>
</td>
<td><input style='width:40px' maxlength='4' name='z_${nav.siteNavId}' value='${nav.siteNavItemOrder}' /><input type='hidden' name='nav_id' value='${nav.siteNavId}' /></td>
<td><a href='site_nav_add.py?siteNavId=${nav.siteNavId}'>修改</a></td>
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='删除导航' class='button' onclick='doPost("delete")' />
<input type='button' value='保存顺序' class='button' onclick='doPost("order")' />
<input type='button' value='添加导航' class='button' onclick='window.location.href="site_nav_add.py"' />
</div>
</form>
</body>
</html>