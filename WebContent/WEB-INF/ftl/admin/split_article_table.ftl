<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>文章分表管理</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<h2>文章分表管理</h2>
对文章进行分表，可以提高网站的性能，管理员应每年进行一次分表操作。分表是按照年份来进行的，每年的数据存放到一个表中。本功能没有采用自动化处理的原因是：使用手工处理，可以有选择地进行数据的操作，对于数据不多的年份，无需单独操作。分表操作按照完整的年份处理的，请不要对当前没有过去的日历年进行操作，因为操作之前，是要删除备份库中已经存在的该年份的文章的。此操作不能撤消，希望管理员注意。

<h3>当前数据中存在的年份（即供操作的年份，以排除当前年份）</h3>
<table class='listTable' cellspacing="1">
<thead>
<th>年份</th>
<th>文章数</th>
<th>&nbsp;</th>
</thead>
<#list ydata as yd>
<tr>
<td>${yd["year"]}</td>
<td>${yd["count"]}</td>
<td><a href="split_article_table.py?cmd=split&year=${yd["year"]}" onclick="return confirm('你真的要进行数据表的拆分吗？注意，此过程不可恢复。')">拆分到历史库</a>（此过程需要很长时间，几十分钟，<span style="color:#f00;font-weight:bold;">强烈建议到数据库管理器中执行存储过程 <span style="color:#09f;">EXEC CopyData ${yd["year"]}</span>）</span></td>
</tr>
</#list>
</table>
</body>
</html>