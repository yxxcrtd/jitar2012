<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>好友管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
		<script type="text/javascript">
		<!--
		function selectUser() {
			url = '${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle'
			window.open(url,'_blank', 'left=100, top=50, height=450, width=800, toolbar=no, menubar=no, scrollbars=yes, resizable=1');
		}
		//-->
		</script>
	</head>
	
	<body onLoad="javascript:document.add_form.friendName.focus();">
		<h2>添加好友</h2>
		<div class='funcButton'>
		  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
		  &gt;&gt; <a href='friend.action?cmd=list'>好友管理</a>
		  &gt;&gt; 添加好友
		</div>

		<form name="add_form" action="friend.action?cmd=savemulti" method="post">
			<#if __referer??>
				<input type='hidden' name='__referer' value='${__referer}' />
	 		</#if>
			<table class="listTable"  cellspacing="1" cellpadding="0">
				<tr>
					<td align="right" width="30%"><b>好友用户名：</b></td>
					<td>
					<input type="hidden" id="uid" name="friendId" value="" />					
					<input type="button" onClick="selectUser()" value="选择用户" />
					<div id="utitle" style="padding:6px 0;"></div>
					</td>
				</tr>
				<tr>
					<td align="right"><b>备注：</b></td>
					<td><input type="text" name="remark" value="" size="30" /></td>
				</tr>
			</table>				
			<div align="center">
				<input class="button" type="submit" value="添加好友" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="button" type="button" value="取消返回" onClick="window.location.href='friend.action?cmd=list'" />
			</div></br>
		</form>
	</body>
</html>
