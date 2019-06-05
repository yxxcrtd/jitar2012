<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>回复短消息</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body> <!--onLoad="javascript:document.reply_form.messageReceiver.focus();"-->	
<h2>回复消息</h2>
<div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='?cmd=inbox'>短消息管理</a>
  &gt;&gt; <span>回复消息</span>
</div>

<form  action="?" method="post">
  <input type='hidden' name='cmd' value='send' />
	<input type="hidden" name="messageId" value=${message.id}>
	<#if __referer??>
		<input type='hidden' name='__referer' value='${__referer}' />
	</#if>
	<table class="listTable" cellspacing="1" >
		<tbody>
			<tr>
				<td align="right" width="25%"><b>接收者:</b></td>
				<td>
				  <input type='hidden' name='messageReceiver' value='${user.loginName}' />
					<input type="text" readonly='readonly' value="${user.nickName!?html}(${user.loginName!})" />
				</td>
			</tr>
			<tr>
				<td align="right"><b>短消息标题:</b></td>
				<td><input type='text' name="messageTitle" size="38" maxLength="50" /></td>
			</tr>
			<tr>
				<td align="right" valign="top"><b>短消息内容:</b></td>
				<td><textarea type='textarea' name="messageContent" cols="50" rows="6"></textarea></td>
			</tr>
		</tbody>				
		<tfoot>
			<tr>
				<td></td>
				<td>							
					<input class="button" type="submit" value=" 发送回复 " />
					<input class="button" type="button" value=" 返 回 " onclick="window.history.back();" />
				</td>
			</tr>
		</tfoot>
	</table>
	<table class="listTable" cellspacing="1">
		<tr>
			<td align="right"><h3>您要回复的短消息</h3></td>
			<td></td>
		</tr>
		<tr>
			<td align="right" width="25%" ><b>短消息标题：</b></td>
			<td>${message.title!?html}</td>
		</tr>
		<tr>
			<td  align="right"><b>短消息内容：</b></td>
			<td>${message.content!}</td>
		</tr>
	</table>
</form>
</body>
</html>
