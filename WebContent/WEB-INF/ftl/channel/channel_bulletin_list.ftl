<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script>
  function doPost(s)
  {
  	document.getElementById("f").act.value=s;
  	document.getElementById("f").submit();
  }
  </script>
</head>
<body>
<h2>频道公告</h2>
<form method="POST" id="f" action="channel.action">
<input type="hidden" name="cmd" value="bulletins"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<table class='listTable' cellspacing='1'>
 <thead>
  <tr>
   	<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");' id='chk' /></th>
   	<th>标题</th>
	<th width='10%'>发表时间</th>
	<th width='5%'>隐藏</th>
	<th width='8%'>修改</th>
</tr>
</thead>
<#if placard_list??>
<tbody>
<#list placard_list as p>
<tr>
  	<td><input type='checkbox' name='guid' value='${p.id}' /></td>
  	<td><a href="../channel/channel.action?cmd=bulletin&channelId=${channel.channelId}&placardId=${p.id}" target="_blank">${p.title?html}</a></td>
  	<td>${p.createDate?string("yyyy-MM-dd")}</td>
  	<td><#if p.hide>隐藏</#if></td> 
  	<td><a href="channel.action?cmd=addbulletin&channelId=${channel.channelId}&placardId=${p.id}">修改</a></td> 	
</tr>
</#list>
</tbody>
</#if>
</table>
<input type="hidden" name="act" value="" />
<input type='button' class='button' value='全部选中' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid");' />
<input type='button' class='button' value='添加公告…' onclick="window.location.href='channel.action?cmd=addbulletin&channelId=${channel.channelId}'" />
<input type='button' class='button' value='删除公告' onclick="if(window.confirm('你真的要删除吗？删除之后将无法恢复。')){doPost('delete')}" />
<input type='button' class='button' value='显   示' onclick="doPost('show')" />
<input type='button' class='button' value='隐   藏' onclick="doPost('hide')" />
</form>
</body>
</html>