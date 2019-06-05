<html>
	<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>好友管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
	</head>
	
<body onLoad="javascript:document.add_form.blackName.focus();">
<h2>添加黑名单</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='friend.action?cmd=list'>好友管理</a>
  &gt;&gt; 添加黑名单
</div>

		<form name="add_form" action="friend.action?cmd=save_black" method="post">
			<table class="listTable" cellspacing="1" cellpadding="0">
				<tr>
					<td align="right" width="35%">用户名：</td>
					<td><input type="text" name="blackName" value="" size="30" onFocus="this.select();" onMouseOver="this.focus();" />( <font color="#FF0000">*</font> 登录用户名)</td>
				</tr>
				<tr>
					<td align="right">备注：</td>
					<td><input type="text" name="remark" value="" size="30" /></td>
				</tr>
			</table>				
			<div align="center">
				<input class="button" type="submit" value="添加黑名单" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="button" type="button" value="取消返回" onclick="window.location.href='friend.action?cmd=list_black'" />
			</div>
		</form>
	</body>
</html>
