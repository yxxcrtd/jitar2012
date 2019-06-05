<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>文章协作组列表</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>文章已经发布到下列协作组</h2>
<table class='listTable' cellspacing='1' style="width:100%">
  <thead>
    <tr>
      <th width='60%'>协作组名称</th>
      <th width='20%'>加入时间</th>
      <th width='20%'>从组中删除</th>
    </tr>
  </thead>
  <tbody>
  <#list group_list as group>
    <tr>
      <td>${group.groupTitle}</td>
      <td>${group.pubDate?string('yyyy-MM-dd')}</td>
      <td><a href="?articleId=${articleId}&groupArticleId=${group.id}">从该组删除</a></td>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div style="padding:10px;text-align:right">
<input type="button" value="关闭本页面" onclick="window.parent.close()"/>
</div>
</body>
</html>
