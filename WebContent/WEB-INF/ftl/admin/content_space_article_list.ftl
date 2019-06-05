<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>自定义分类[<span style='color:#f00'>${contentSpace.spaceName}</span>]文章管理</h2>
<form method='GET' action='content_space_article_list?id=${contentSpace.contentSpaceId}'>
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<input type='submit' value='搜索文章' />
</div>
</form>
<form method='POST' id='oForm'>
<input name='cmd' type='hidden' value='' />
<#if article_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>文章标题</th>
<th><nobr>文章作者</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>阅读数</nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list article_list as a >
<#assign user = Util.userById(a.createUserId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.contentSpaceArticleId}' onclick='SetRowColor(event)' /></td>
<td>
<a href='preview_article.py?contentSpaceArticleId=${a.contentSpaceArticleId}' target='_blank'>${a.title!?html}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>${a.viewCount}</nobr></td>
<td><nobr><a href='content_space_article_edit.py?id=${contentSpace.contentSpaceId}&contentSpaceArticleId=${a.contentSpaceArticleId}'>修改</a></nobr></td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='添加文章' onclick='window.location.href="content_space_article_edit.py?id=${contentSpace.contentSpaceId}"' />
  <input class='button' type='button' value='彻底删除' onclick="if(window.confirm('您真的要删除这些文章吗？')){doPost('delete');}" />
</div>
</form>
</body>
</html>