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
<h2>自定义分类文章管理</h2>
<form method='GET' action='unit_contentspace_article_list.py'>
<input name='unitId' value='${unit.unitId}' type='hidden'/>
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
<option value='title'<#if f=='title'> selected='selected'</#if>>文章标题</option>
<option value='createUserId'<#if f=='createUserId'> selected='selected'</#if>>作者 id</option>
<option value='createUserLoginName'<#if f=='createUserLoginName'> selected='selected'</#if>>作者登录名</option>
</select>
<select name='contentSpaceId'>
<option value=''>全部文章分类</option>
<#if category_tree??>
<#list category_tree.all as c>
<option value='${c.categoryId}'<#if contentSpaceId==c.categoryId> selected='selected'</#if>>${c.treeFlag2} ${c.name?html}</option>
</#list>
</#if>
</select>
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
<th>
<select onchange='window.location.href="unit_contentspace_article_list.py?unitId=${unit.unitId}&contentSpaceId=" + this.options[this.selectedIndex].value'>
<option value=''>全部文章分类</option>
<#if category_tree??>
<#list category_tree.all as c>
<option value='${c.categoryId}'<#if contentSpaceId==c.categoryId> selected='selected'</#if>>${c.treeFlag2} ${c.name?html}</option>
</#list>
</#if>
</select>
</th>
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
<a href='preview_contentspace_article.py?unitId=${unit.unitId}&contentSpaceArticleId=${a.contentSpaceArticleId}' target='_blank'>${a.title!?html}</a></td>
<td>${a.spaceName}</td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}(${user.loginName})</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td><nobr>${a.viewCount}</nobr></td>
<td><nobr><a href='unit_content_space_article_add.py?unitId=${unit.unitId}&contentSpaceArticleId=${a.contentSpaceArticleId}'>修改</a></nobr></td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<div class='pager'>
  <#include "/WEB-INF/ftl/pager.ftl">
</div>
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='添加文章' onclick='window.location.href="unit_content_space_article_add.py?unitId=${unit.unitId}"' />
  <input class='button' type='button' value='彻底删除' onclick="if(window.confirm('您真的要删除这些文章吗？')){doPost('delete');}" />
   移动分类到：<select name='new_cate'>
<#if category_tree??>
<#list category_tree.all as c>
<option value='${c.categoryId}'>${c.treeFlag2} ${c.name?html}</option>
</#list>
</#if>
  </select>
  <input class='button' type='button' value='执行移动分类' onclick="doPost('move_cate')" />
</div>

</form>
</body>
</html>