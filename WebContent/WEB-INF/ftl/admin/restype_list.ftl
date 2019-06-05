<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>资源类型管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body style="margin-top: 20px;">
		<h2>
			资源类型管理
		</h2>
		<form name="theForm" action="admin_subject.action" method="post">
			<table class="listTable" cellspacing="1">
				<thead>
					<tr>
						<th width="10%">
							类型ID
						</th>
						<th width="20%">
							类型名称
						</th>
						<th width="50%">
							类型代码
						</th>
						<th width="20%">
							操&nbsp;&nbsp;作
						</th>
					</tr>
				</thead>
				<tbody>
					<#list resType_list as resType>
					<tr>
						<td align="center">
							${resType.tcId}
						</td>
						<td   style="padding-left: 10px;" align="left">
							<a href="?cmd=list&amp;tcParent=${resType.tcId}">${resType.tcTitle!?html}</a>
						</td>
						<td  style="padding-left: 10px;"  align="left">
							${resType.tcCode!?html}
						</td>
						<td align="center">
							<a href="?cmd=list&amp;tcParent=${resType.tcId}">下级</a>&nbsp;&nbsp;
							<a href="?cmd=add&amp;resTypetId=${resType.tcId}&tcParent=${tcParent!0}">修改</a>&nbsp;&nbsp;
							<a href="?cmd=delete&amp;resTypeId=${resType.tcId}" onClick="return confirm('<@s.text name="groups.public.delConfirm" />');">删除</a>
						</td>
					</tr>
					</#list>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<input type="button" value="添加资源分类 " onClick="javascript:window.location='?cmd=add<#if tcParent!=0>&tcParent=${tcParent}</#if>'" />
							<#if tcParent!=0>
							<input type="button" value="返回上级" onClick="javascript:window.location='?cmd=list'" />
							</#if>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
