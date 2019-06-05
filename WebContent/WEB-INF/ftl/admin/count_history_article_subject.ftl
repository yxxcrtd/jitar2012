<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>文章管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>所有学科列表</h2>
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:120px">学科名称</th>
      <th style="width:120px">历史库文章数</th>
      <th>统计历史库文章</th>
    </tr>
  </thead>
  <tbody>
  <#list subject_list as subject>
  <tr>
  <td><a href='${SiteUrl}go.action?subjectId=${subject.metaSubject.msubjId}&gradeId=${subject.metaGrade.gradeId}' target="_blank">${subject.subjectName}</a></td>
  <td>${subject.historyArticleCount}</td>  
  <td><a href="?subjectId=${subject.metaSubject.msubjId}&gradeId=${subject.metaGrade.gradeId}">统计本学科历史文章数</a></td>
  </tr>
  </#list>
  </tbody>
  </table>
</body>
</html>
