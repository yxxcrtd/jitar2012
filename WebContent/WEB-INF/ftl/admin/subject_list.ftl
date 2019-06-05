<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>学科管理</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<script>
function PostData(cmd)
{
  document.theForm.action='admin_subject.action?cmd=' + cmd;
  document.theForm.submit();
}
</script>
</head>	
<body>
<h2>学科管理</h2>
<form name="theForm" action="admin_subject.action?cmd=order" method="post">
<table class="listTable" cellspacing="1">
<thead>
<tr>
	<th>ID</th>
	<th>学科名称</th>
	<th>学科代码</th>
	<th style='width:30px'>显示顺序</th>
	<th>自定义链接</th>
	<th>操&nbsp;&nbsp;作</th>
</tr>
</thead>
<tbody>
<#list subject_list as subject>
<tr>
	<td align="center">
		${subject.subjectId}
	</td>
	<td>
	<a href="${SiteUrl}go.action?id=${subject.subjectId}" target="_blank">${subject.subjectName!?html}</a>
	</td>
	<td>
		${subject.subjectCode!?html}
	</td>
	<td>
		<input type='hidden' name='subjID' value='${subject.subjectId}' />
		<input name='order_${subject.subjectId}' value= '${subject.orderNum}' />
	</td>
	<td>
		<input style='width:98%' name='shortcutTarget_${subject.subjectId}' value= '${subject.shortcutTarget!}' />
	</td>
	<td align="center">
		<a href="?cmd=edit&amp;subjectId=${subject.subjectId}">修改</a>&nbsp;&nbsp;
		<a href="?cmd=delete&amp;subjectId=${subject.subjectId}" onClick="return confirm('<@s.text name="groups.public.delConfirm" />');">删除</a>
	</td>
</tr>
</#list>
</tbody>
</table>
<div class='funcButton' style='padding:10px'>
<input type="button" class='button' value="添加学科" onClick="javascript:window.location='?cmd=add'" />
<input type="button" class='button' onClick="PostData('order')" value="保存顺序" /> 
<input type="button" class='button' onClick="PostData('link')" value="保存自定义链接" /> 
<a href='${SiteUrl}manage/clearall_cache.py'>清空缓存看效果</a>
</div>
</form>
</body>
</html>
