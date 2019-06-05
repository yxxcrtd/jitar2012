<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>元学段管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body>
		<h2>元学段管理</h2>
		<form name="theForm" action="admin_grade" method="post">
			<table class="listTable" cellspacing="1">
				<thead>
					<tr>
						<th width="20%">元学段代码</th>
						<th width="30%">元学段名称</th>
						<th width="20%">操&nbsp;&nbsp;作</th>
					</tr>
				</thead>
				<tbody>
					<#list grade_list as grade>
					<tr>
						<td align="center">
							${grade.gradeId!}
						</td>
						<td align="center">
							${grade.gradeName!?html}
						</td>
						<td align="center">
							<a href="?cmd=edit&amp;gradeId=${grade.gradeId}">修改</a>&nbsp;&nbsp;
							<a href="?cmd=delete&amp;gradeId=${grade.gradeId}" onClick="return confirm('<@s.text name="groups.public.delConfirm" />');">删除</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<div class='funcButton'>
        <input type="button" class='button' value="添加元学段" onClick="javascript:document.add_grade_form.submit();" />
      </div>
		</form>
		
	<div style='display:none'>
	<form name="add_grade_form" action="admin_grade.action" method="get" >
		<input type='hidden' name='cmd' value='add' />
	</form>	
</div>
	</body>
</html>
