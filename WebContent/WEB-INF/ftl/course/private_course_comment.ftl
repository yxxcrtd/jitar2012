<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript">
  var checkStatus = true
  function selAll()
  {
     c = document.getElementsByName("guid")
     for(i = 0;i<c.length;i++)
     {
      c[i].checked = checkStatus;
     }
     checkStatus = !checkStatus
  }
  </script>
 </head> 
 <body>
 <div style='padding:2px'>
 <div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='${SiteUrl}manage/course/private_course_comment.py'>评论管理</a>
</div>
<form method='post'>
<table id="ListTable" class="listTable" cellspacing="1">
<thead>
<tr>
	<th style='width:17px'></th>
	<th>评论</th>
	<th style='width:300px'>评论者</th>
</tr>
</thead>
<tbody>
<#list comment_list as cmt>
<tr class="backNormalColor">
	<td><input name="guid" value="${cmt.prepareCoursePrivateCommentId}" type="checkbox" /></td>
	<td>
	${cmt.title?html}
	(<a href='${SiteUrl}manage/course/showUserCourse.py?prepareCourseId=${cmt.prepareCourseId}&userId=${cmt.commentedUserId}' target='_blank'>${cmt.pctitle?html}</a>)	
	</td>
	<td>
	<#assign u = Util.userById(cmt.commentUserId)>
	<#if u??>
	<a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a> 发表于：${cmt.createDate?string('yyyy-MM-dd HH:mm:ss')}
	</#if>
	</td>
</tr>
<tr>
<td colspan='3' style='background:#EEE;'>
${cmt.content!}
</td>
</tr>
</#list>
</tbody>
</table>
<input type='button' class='button' value=' 全  选 ' onclick='selAll()' />
<input type='button' class='button' value='删除评论' onclick='this.form.submit();' />
 <div style='text-align:center'>
 <#include "../action/pager.ftl">
 </div>
</div>
</body>
</html>