<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
 <title>管理</title>
 <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
 <script type='text/javascript'>
  function doPost(cmdtype)
  {
    document.getElementById('oForm').cmd.value = cmdtype;
    document.getElementById('oForm').submit();
  }
 </script>
</head>
<body>
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<h2>学科网站自定义内容分类管理</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr style='text-align:left'>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")'/></th>
<th>分类名称</th>
<th>创建日期</th>
<th>文章数</th>
<th>操作</th><#--
<th>发布文章</th>-->
</tr>
</thead>
<#list category_tree.all as category>
<tr>
<td><input type='checkbox' name='guid' value='${category.id}' /></td>
<td>
   ${category.treeFlag2!} ${category.name} 
   <#if (category.childNum >   0)>(${category.childNum}) </#if>
</td>
<td>${category.extendedObject.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
<td>${category.extendedObject.articleCount}</td>
<td><a href='content_space_edit.py?id=${subject.subjectId}&contentSpaceId=${category.extendedObject.contentSpaceId}'>修改</a></td>
<#--<td><a href='content_space_article_edit.py?id=${subject.subjectId}&contentSpaceId=${category.extendedObject.contentSpaceId}'>发布文章</a></td>-->
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='添加分类' class='button' onclick="document.location.href='content_space_edit.py?id=${subject.subjectId}&contentSpaceId=0';" />
<input type='button' value='删除分类' class='button' onclick='if(window.confirm("您真的要删除所选择的分类吗？\r\n\r\n删除分类，该分类下的所有文章也将被删除。")){doPost("delete")}' />
</div>
</form>
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</body>
</html>