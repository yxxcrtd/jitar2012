<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>修改域名</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type='text/javascript'>
 function ShowEdit(st)
 {
   document.getElementById('u'+st).style.display='none';
   document.getElementById('a'+st).style.display='none';
   document.getElementById('oForm').elements['unitName_new_' + st].style.display='';
   document.getElementById('oForm').elements['unitHref_new_' + st].style.display='';
 }
 
 function CancelEdit(st)
 {
   var f = document.getElementById('oForm');
   f.elements['unitName_new_' + st].style.display='none';
   f.elements['unitHref_new_' + st].style.display='none';
   document.getElementById('u'+st).style.display='';
   document.getElementById('a'+st).style.display='';
   f.elements['unitName_new_' + st].value=f.elements['unitName_old_' + st].value;
   f.elements['unitHref_new_' + st].value=f.elements['unitHref_old_' + st].value;
 }
 
 function doPost(st)
 {
  var f = document.getElementById('oForm');
  f.cmd.value = st;
  f.submit();
 }
  </script>
 </head>
<body>
<h2>修改区县域名、名称</h2>
<#if unit_list?? >
<form id='oForm' method='POST'>
<input name='cmd' type='hidden' value='' />
<table class='listTable' cellspacing="1" style='width:auto'>
<thead>
<tr>
<th style='width:20px'></th>
<th>区县名称</th>
<th>域名</th>
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<#list unit_list as a>
<tr>
<td><input type='checkbox' name='guid' value='${a.mashupPlatformId}' /></td>
<td style='width:40%'>
<input type='hidden' name='mashId' value='${a.mashupPlatformId}' />
<input type='text' name='unitName_old_${a.mashupPlatformId}' value='${a.platformName?html}' style='display:none;' />
<input type='text' name='unitName_new_${a.mashupPlatformId}' value='${a.platformName?html}' style='display:none;width:100%;' />
<span id='u${a.mashupPlatformId}'>${a.platformName?html?html}</span>
</td>
<td style='width:40%'>
<input type='text' name='unitHref_old_${a.mashupPlatformId}' value='${a.platformHref?html}' style='display:none;' />
<input type='text' name='unitHref_new_${a.mashupPlatformId}' value='${a.platformHref?html}' style='display:none;width:100%;' />
<span id='a${a.mashupPlatformId}'><a href='${a.platformHref?html}' target='_blank'>${a.platformHref?html}</a></span>
</td>
<td>
<#if a.platformState == 0>正常
<#elseif a.platformState == 1><span style='color:red;'>锁定</span>
<#else>未定义
</#if>
</td>
<td> 
<input class='button' type='button' value=' 编  辑 ' onclick='ShowEdit(${a.mashupPlatformId})' /> 
<input class='button' type='button' value='取消编辑' onclick='CancelEdit(${a.mashupPlatformId})' />
</td>
</tr>
</#list>
</table>
<input type='button' class='button' value='保存修改' class='button' onclick='doPost("save")'/>
<input type='button' class='button' value='删除区县' class='button' onclick='doPost("delete")' />
<input type='button' class='button' value='锁定区县' class='button' onclick='doPost("lock")' />
<input type='button' class='button' value='设为正常' class='button' onclick='doPost("approve")' />
</form>
<#else>
没有记录。
</#if>
</body>
</html>