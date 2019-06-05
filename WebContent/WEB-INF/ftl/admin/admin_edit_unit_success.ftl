<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>组织机构管理</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
</head>
<body>
<h2>操作成功。</h2>
<a href='' onclick='returnBack();return false'>返回机构列表</a>
<script>
function returnBack()
{
	window.parent.frames[0].location = "admin_unit_list.py?tmp=" + (new Date()).valueOf();
	window.location.href='admin_unit_main.py?unitId=${parentUnit.unitId}';
}
window.onload=function()
{
	window.parent.frames[0].location = "admin_unit_list.py?tmp=" + (new Date()).valueOf();
}
</script>
</body>
</html>