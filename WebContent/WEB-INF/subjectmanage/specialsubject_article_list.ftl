<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var srcmd = f.rcmdstate.options[f.rcmdstate.selectedIndex].value;
  	var qs = "sr=" + srcmd + "&type=filter"
  	var url = "specialsubject_article_list.py?id=${subject.subjectId}&specialSubjectId=${specialSubject.specialSubjectId}&";
  	window.location.href = url + qs;  	
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>${specialSubject.title?html} 专题文章管理</h2>
<form method='GET' action='specialsubject_article_list.py'>
<input name='id' type='hidden' value='${subject.subjectId}' />
<input name='specialSubjectId' type='hidden' value='${specialSubject.specialSubjectId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>文章标题</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
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
<th><nobr>发布人</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>修改</nobr></th>
</tr>
</thead>
<#list article_list as a >
<#assign user = Util.userById(a.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${a.articleGuid}' onclick='SetRowColor(event)' /></td>
<td>
<a href='preview_article.py?articleId=${a.articleId}' target='_blank'>${a.title!?html}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.userTrueName}</a></nobr></td>
<td><nobr>${a.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr><a href='${SiteUrl}manage/admin_article.py?cmd=edit&articleId=${a.articleId}'>修改</a></nobr>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从专题中删除' onclick="doPost('remove')" />
</div>
</form>
</body>
</html>