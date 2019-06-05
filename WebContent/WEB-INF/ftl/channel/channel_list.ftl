<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
function doPost(arg)
{
  document.getElementById("f").cmd.value=arg;
  document.getElementById("f").submit();
}
function deletechannel(id){
    if(confirm("确认删除？")==false){ return false;}
    document.location.href="channel.action?cmd=deletechannel&channelId=" + id;
}
</script>
</head>
<body>
<h2>频道列表</h2>
<form method="POST" id="f">
<table border='1' class='listTable'>
<thead>
<tr>
<th>频道名称</th>
<th>操作</th>
</tr>
</thead>
<#if channel_list??>
<#list channel_list as c>
<tr>
<td><a href='${SiteUrl}channel/channel.action?channelId=${c.channelId}' target="_blank">${c.title!?html}</a></td>
<td><a href='channel.action?cmd=add&channelId=${c.channelId}'>修改</a> | <a href='#' onclick='deletechannel(${c.channelId});'>删除</a></td>
</tr>
</#list>
</#if>
</table>
<div style="padding:10px 0">
<input type='button' value='添加新频道…' onclick="window.location.href='channel.action?cmd=add&channelId=0'" />
</div>
</form>
</body>
</html>