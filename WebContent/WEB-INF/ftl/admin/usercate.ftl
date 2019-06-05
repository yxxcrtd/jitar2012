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
  
  function checkSelectedItem()
  {
  	var guids = document.getElementsByName("guid");
  	for(var i=0;i<guids.length;i++)
  	{
  	 if(guids[i].checked) return true;
  	}
  	alert("请先选择一个分类。");
  	return false;
  }
  
 </script>
</head>
<body>
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<h2>网站自定义内容分类管理</h2>
<input name='space_name' value='' style='width:200px' />
<input type='button' value='添加分类' class='button' onclick='doPost("add")' />
<hr/>
<table class="listTable" cellSpacing="1">
<thead>
<tr style='text-align:left'>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")'/></th>
<th>分类名称</th>
<th>创建日期</th>
<th>文章数</th>
<th>操作</th>
<th>发布文章</th>
</tr>
</thead>
<#list contentSpaceList as c>
<tr>
<td><input type='checkbox' name='guid' value='${c.contentSpaceId}' /></td>
<td>${c.spaceName}</td>
<td>${c.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
<td>${c.articleCount}</td>
<td><a href='content_space_edit.py?id=${c.contentSpaceId}'>修改</a></td>
<td><a href='content_space_article_edit.py?id=${c.contentSpaceId}'>发布文章</a></td>
<#--
<td><a href='content_space_article_list.py?id=${c.contentSpaceId}'>管理文章</a></td>
-->
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='删除分类' class='button' onclick='if(checkSelectedItem() && window.confirm("您真的要删除所选择的分类吗？\r\n\r\n删除分类，该分类下的所有文章也将被删除。")){doPost("delete")}' />
</div>
</form>
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</body>
</html>