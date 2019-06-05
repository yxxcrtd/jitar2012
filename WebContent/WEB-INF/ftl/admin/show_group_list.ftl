<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理页</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<form method='GET' action='?gid=${gid}'>
输入关键字：<input name='k' value="${k!?html}" /><input type='submit' value=' 查  询 ' />
</form>
<#if group_list??>
<#list group_list as g>
<div style='padding:2px'><label><input type='radio' name='g' value='${g.groupId}' /><span style='color:red'>${g.groupId}</span> (${g.groupTitle})</label></div>
</#list>
<#include '/WEB-INF/ftl/pager.ftl' >
</#if>
<div style='text-align:center;padding:4px'><input type='button' value=' 确  定 ' onclick='GetGroup()' /> <input type='button' value=' 关   闭 ' onclick='window.close()' /> </div>
<script type='text/javascript'>
function GetGroup()
{
 gs = document.getElementsByName('g')
 for(i=0;i<gs.length;i++)
 {
  if(gs[i].checked)
  {
   window.opener.document.getElementById('${gid!}').value = gs[i].value;
  }
 } 
 window.close();
}
</script>
</body>
</html>