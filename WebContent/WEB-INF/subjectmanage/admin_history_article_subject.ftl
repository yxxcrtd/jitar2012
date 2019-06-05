<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>文章管理</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript" src='${SiteUrl}manage/js/admin_article.js'></script>
</head>
<body>
<h2>历史文章管理：${year} 年</h2>
<form action='?' method='get' style="text-align:right">
<input type='hidden' name='backYear' value='${year}'/>
<input type='hidden' name='id' value='${subject.subjectId}'/>
  关键字：<input type='text' name='k' value="${k!?html}" size='12' />
<#if !(f??)><#assign f = 'title'></#if>
  <select name='f'>
    <option value='title'${(f == 'title')?string(' selected', '')}>文章标题</option>
    <option value='uname'${(f == 'uname')?string(' selected', '')}>用户真实姓名</option>
    <option value='lname'${(f == 'lname')?string(' selected', '')}>用户登录名</option>
  </select>
<#if article_categories??>
  <#if !(sc??)><#assign sc = 0></#if>
  <select name='sc'>
    <option value=''>所属文章分类</option>
  <#list article_categories.all as c >
    <option value='${c.categoryId}' ${(sc == c.categoryId)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
  </#list>
  </select>
</#if>
  <input type='submit' class='button' value='查找' />
</form>
<form method='post'>
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:17px">&nbsp;</th>
      <th>标题</th>
      <th>发表日期</th>
      <th width="120">作者</th>
      <th>所属分类</th>
    </tr>
  </thead>
  <tbody>
  <#if article_list?size == 0>
  <tr>
    <td colspan='5' style='padding:12px' align='center' valign='center'>没有找到符合条件的文章.</td>
  </tr>
  </#if>
<#list article_list as article>
<tr>
<td>
<input type='checkbox' name='articleId' value='${article.articleId}' /></td>
<td>
<#if article.typeState == false>[原创]<#else>[转载]</#if>
<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
<#if article.recommendState!false><img src='images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
</td>
<td><nobr>${article.createDate!string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
<td>
<a href='${SiteUrl}go.action?loginName=${article.loginName!}' target='_blank'>${article.userTrueName!?html}</a>
</td>
<td><nobr>
<#if article.sysCateId??><#assign cate = Util.getCategory(article.sysCateId)>
<#if cate??>${cate.name!?html}</#if>
</#if>
</nobr>
</td>
</tr>
</#list>
</tbody>
</table>

<div class='pager'>
  <#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>

<div class='funcButton'>
  <input type='button' class='button' value='全部选中' onclick='select_all();' ondblclick='select_all();' />
  <input type='submit' class='button' value='彻底删除' onclick='return confirm("该操作不可恢复。你真的要删除吗？");' />
</div>
</form>
</body>
</html>