<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>元学科管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body>
		<h2>元学科管理</h2>
		<form name="theForm" action="admin_msubject" method="post">
			<table class="listTable" cellspacing="1">
				<thead>
					<tr>
						<th width="20%">ID</th>
						<th width="30%">元学科名称</th>
						<th width="30%">元学科代码</th>
						<th width="20%">操&nbsp;&nbsp;作</th>
					</tr>
				</thead>
				<tbody>
					<#list msub_list as msub>
					<tr>
						<td align="center">
							${msub.msubjId}
						</td>
						<td align="center">
							${msub.msubjName!?html}
						</td>
						<td align="center">
							${msub.msubjCode!?html}
						</td>
						<td align="center">
							<a href="?cmd=edit&amp;msubjId=${msub.msubjId}">修改</a>&nbsp;&nbsp;
							<a href="?cmd=delete&amp;msubjId=${msub.msubjId}" onClick="return confirm('<@s.text name="groups.public.delConfirm" />');">删除</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<div class='funcButton'>
        <input type="button" class='button' value="添加元学科" onClick="javascript:document.add_msubj_form.submit();" />
      </div>
		</form>
		
	<div style='display:none'>
	<form name="add_msubj_form" action="admin_msubject.action" method="get" >
		<input type='hidden' name='cmd' value='add' />
	</form>	
</div>
	</body>
</html>
