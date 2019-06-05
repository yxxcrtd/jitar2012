<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>元学科管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body>
		<h2>
			<#if msubject.msubjId == 0>
				添加元学科
			<#else>
				修改元学科 
			</#if>
		</h2>
		<table align="center">
			<tr>
				<td colspan="2">
					<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
				</td>
			</tr>
		</table>						
		<form name="theForm" action="admin_msubject.action" method="post">
		<#if __referer??>
			<input type='hidden' name='__referer' value='${__referer}' />
 		</#if>
			<table class="listTable" cellspacing="1">
				<tbody>
					<tr>
						<td align="right" width="30%">
							<b>元学科名称：</b>
						</td>
						<td>
							<input type="text" name="msubjName" value="${msubject.msubjName!?html}" onMouseOver="this.select();" /> <font style="color: #FF0000;">*</font> 元学科的中文名字，必须给出，且不能和别的元学科名字重复。
						</td>
					</tr>
					<tr>
						<td align="right">
							<b>元学科代码：</b>
						</td>
						<td>
							<input type="text" name="msubjCode" value="${msubject.msubjCode!?html}" /> <font style="color: #FF0000;">*</font> 元学科代码，必须给出，且不能和别的元学科代码重复。
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" align="center" height="25">
							<input type="hidden" name="cmd" value="save" />
							<input type="hidden" name="msubjId" value="${msubject.msubjId}" />
							<input type="hidden" name="__referer" value="${__referer!?html}" />
							<input type="submit" class='button' value=" 确定${(msubject.msubjId==0)?string('添加', '修改')} " />
							<input type="button" class='button' value="取消返回" onclick="window.history.back()" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
