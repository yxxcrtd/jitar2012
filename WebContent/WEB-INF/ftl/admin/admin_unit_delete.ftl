<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
</head>
<body>
<h2>机构名称：${unit.unitTitle}</h2>

<div style='padding:20px;text-align:center;color:red;font-size:16px'>要删除机构，请先彻底删除该机构下的所有文章、资源、视频、用户等所有资源后再进行操作，删除机构操作不可恢复。删除的机构不能再创建相同名称的机构，请慎重操作。
<br/><br/><br/>

<a href='${SiteUrl}manage/admin_unit_main.py?cmd=Delete&unitId=${unitId!}&opunitId=${opunitId!}&confirm=1'>要确认删除，请按此链接，不删除请关闭窗口</a>。</div>
</body>
</html>