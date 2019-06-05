<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>资源类型管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body style="margin-top: 20px;">
		<h2>
			<#if resType.tcId == 0>
				添加资源类型
			<#else>
				修改资源类型
			</#if>
		</h2>
		<table align="center">
			<tr>
				<td colspan="2">
					<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
				</td>
			</tr>
		</table>
		<form name="theForm" action="admin_resType.action" method="post">
			<table class="listTable" cellspacing="1">
				<tbody>
					<tr>
						<td align="right" width="20%">
							<b>
								上级类型：
							</b>
						</td>
						<td>
					<select name="tcParent" style="width: 155px;">
						  <option value='0'>(做为一级分类)</option>
						<#list resType_list as val>
					    <option value='${val.tcId}' <#if val.tcId == resType.tcParent!0>selected='selected'</#if>
					      >${val.tcTitle?html}</option>
					</#list>
						</select><font style="color: #FF0000;">*</font> 。
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							<b>
								类型名称：
							</b>
						</td>
						<td>
							<input type="text" name="tcTitle" value="${resType.tcTitle!?html}" onMouseOver="this.select();" /> <font style="color: #FF0000;">*</font> 资源类型的中文名字，必须给出，且不能和别的资源类型名字重复。
						</td>
					</tr>
					<tr>
						<td align="right">
							<b>
								类型代码：
							</b>
						</td>
						<td>
							<input type="text" name="tcCode" value="${resType.tcCode!?html}" /> <font style="color: #FF0000;">*</font> 资源类型代码，必须给出，且不能和别的资源类型代码重复。
						</td>
					</tr>
					<tr>
						<td align="right">
							<b>
								类型排序：
							</b>
						</td>
						<td>
							<input type="text" name="tcSort" value="${resType.tcSort!?html}" /> 
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" align="center" height="25">
							<input type="hidden" name="cmd" value="save" />									
							<input type="hidden" name="tcId" value="${resType.tcId}" />
							<input type="hidden" name="__referer" value="${__referer!?html}" />
							<input class="button" type="submit" value="确定${(resType.tcId==0)?string('添加', '修改')}" onclick="if(this.form.tcTitle.value=='' || this.form.tcCode.value==''){alert('请输入完整');return false;}" />
							<input class="button" type="button" value="取消返回" onclick="window.history.back()" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
