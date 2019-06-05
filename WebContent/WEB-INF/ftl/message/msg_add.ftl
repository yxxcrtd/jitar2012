<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>短消息</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />  	
    <script type="text/javascript">
    function checkData(frm)
    {
        if(frm.messageReceiver.value=="")
        {
            alert("请选择接收者");
            return false;
        }
        if(frm.messageTitle.value=="")
        {
            alert("请输入短消息标题");
            return false;
        }   
        if(frm.messageContent.value=="")
        {
            alert("请输入短消息内容");
            return false;
        } 
        return true;   
    }
  </script>		  
	</head>

	<body onLoad="javascript:document.add_form.messageReceiver.focus();">	
		<h2>写短消息</h2>
		<div class='pos'>
			您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
			&gt;&gt; <a href='?cmd=inbox'>短消息管理</a>
			&gt;&gt; <span>写短消息</span>
		</div>

		<form name="add_form" action="?" method="post" onsubmit='return checkData(this);'>
			<input type='hidden' name='cmd' value='save' />
			
			<#if __referer??>
				<input type='hidden' name='__referer' value='${__referer}' />
			</#if>
	
			<table class="listTable" cellspacing="1" >
				<tbody>
					<tr>
						<td align="right" width="25%"><b>接收者(<font color='red'>*</font>)：</b></td>
						<td>
							<input type='text' name="messageReceiver" /><!-- readonly="#myDisable" -->
							<select name="friendList" onChange="messageReceiver.value=this.value">
								<option value="">直接选择好友:</option>
									<#list friend_list as f>
										<option value='${f.loginName!}'>${f.loginName!}&nbsp;(${f.nickName!?html})</option>
									</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><b>短消息标题(<font color='red'>*</font>)：</b></td>
						<td><input type='text' name="messageTitle" size="51" maxLength="50" /></td>
					</tr>
					<tr>
						<td align="right" valign="center"><b>短消息内容(<font color='red'>*</font>)：</b></td>
						<td><textarea rows="6" cols="50" name="messageContent"></textarea></td>
					</tr>
				</tbody>				
				<tfoot>
					<tr>
						<td></td>
						<td>
							<div class='funcButton'>							
								<input class="button" type="submit" value=" 发送短消息 " />
								<input class="button" type="button" value=" 取  消 "  onClick="window.history.back();" />
						  </div>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
