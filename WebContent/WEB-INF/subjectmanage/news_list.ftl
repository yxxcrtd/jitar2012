<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.forms[0].cmd.value=arg;
  	document.forms[0].submit();
  }
  
  function addNew()
  {
  	window.location.href = 'news_add.py?id=${subject.subjectId}'
  }
  function checkSelectedItem()
  {
    var guids = document.getElementsByName("guid");
    for(var i=0;i<guids.length;i++)
    {
     if(guids[i].checked) return true;
    }
    alert("请先选择一个图片新闻、学科动态。");
    return false;
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<h2>
学科图片新闻、学科动态管理
</h2>
<form method='post' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if subject_news_list??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' id='chk' /></th>
<th style='width:100%'>图片新闻、学科动态标题</th>
<th><nobr>发布人</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>阅读数</nobr></th>
<th><nobr>类别</nobr></th>
<th><nobr>状态</nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list subject_news_list as news >
<#assign user = Util.userById(news.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${news.newsId}' /></td>
<td><#if news.picture?? && news.picture != ""><span style='color:red;'>[图]</span></#if>
<#if SubjectUrlPattern??>
<a href='${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}py/showSubjectNews.py?id=${subject.subjectId}&newsId=${news.newsId}' target='_blank'>${news.title}</a>
<#else>
<a href='${SiteUrl}k/${subject.subjectCode}/py/showSubjectNews.py?id=${subject.subjectId}&newsId=${news.newsId}' target='_blank'>${news.title}</a>
</#if>
</td>
<td><nobr><#if user?? && user !=''><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></#if></nobr></td>
<td><nobr>${news.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${news.viewCount}</nobr></td>
<td><nobr>
<#if news.picture?? && news.picture != ''>
<span style='color:#f00'>图片新闻</span>
<#else>
学科动态
</#if>
</nobr>
</td>
<td><nobr>
<#if news.status?? && news.status == 0 >
已审核
<#else>
<span style='color:#f00'>待审核</span>
</#if>
</nobr>
</td>
<td><nobr><a href='news_add.py?id=${subject.subjectId}&newsId=${news.newsId}'>修改</a></nobr>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();' />
  <input class='button' type='button' value='添加图片新闻/学科动态' onclick="addNew()" />
  <input class='button' type='button' value='通过审核' onclick="if(checkSelectedItem()){doPost('approve')}" />
  <input class='button' type='button' value='取消审核' onclick="if(checkSelectedItem()){doPost('unapprove')}" />  
  <input class='button' type='button' value='删除选择' onclick="if(confirm('你真的要删除吗？')){if(checkSelectedItem()){doPost('delete')}}" />
</div>
</form>
</body>
</html>