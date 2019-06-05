<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
</head>
<body>
<h2>该用户还有创建的群组，需要先进行转让群组或删除群组才能删除该用户。该用户创建的群组列表如下：</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
 <th>群组名称</th>
 <th>创建时间</th>
 <th>成员数</th>
 <th>文章数</th>
 <th>资源数</th>
 <th>主题数</th>
 <th>主题讨论数</th>
 <th>活动数</th>
 <th>集备数</th>
</tr>
<#if glist??>
<#list glist as g>
<tr>
 <td><a target="_blank" href="${SiteUrl}g/${g.groupName?url}">${g.groupTitle!?html}</a></td>
 <td>${g.createDate!?string("yyyy-MM-dd")}</td>
 <td>${g.userCount}</td>
 <td>${g.articleCount}</td>
 <td>${g.resourceCount}</td> 
 <td>${g.topicCount}</td> 
 <td>${g.discussCount}</td>
 <td>${g.actionCount}</td> 
 <td>${g.courseCount}</td>
</tr>
</#list>
</#if>
</table>
</form>
</body>
</html>