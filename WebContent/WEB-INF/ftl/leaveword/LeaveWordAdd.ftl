<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>个人公告管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>

<body style="margin-top: 20px;">
	<h2>${(leaveword.id == 0)?string('添加','回复')}留言</h2>
	<form name="list_form" action="leaveword.action" medthod="post">
		<#if __referer??>
			<input type='hidden' name='__referer' value='${__referer}' />
 		</#if>
		<table class="listTable" cellspacing="1" >
			<tr>
				<td align="right" width="25%"><b>接收者标识:</b></td>
				<td>	
					<input type="text" name="receiverId"  />
				</td>
			</tr>
			<tr>
				<td align="right"><b>留言标题:</b></td>
				<td><input type='text' name="LeavewordTitle" size="38" maxLength="16" /></td>
			</tr>
			<tr>
				<td align="right" valign="top"><b>留言内容:</b></td>
				<td><textarea name="LeavewordContent" cols="50" rows="6" ></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td>		
					<input class="button" type='hidden' name='cmd' value='save_leaveword'" />
					<input class="button" type='hidden' name='leavewordId' value='${leaveword.id}' />	
					<input class="button" type="submit" value="${(leaveword.id == 0)?string(' 提  交 ',' 回   复 ')} " />&nbsp;&nbsp;
					<input class="button"type="button" value="取消返回" onclick="window.history.go(-1);" />
				</td>
			</tr>
		</table>
	</form>
</body>