<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title><@s.text name="groups.usermgr.updpwd" /></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />	
	</head>
	
	<body>
		<h2>
			<@s.text name="groups.usermgr.updpwd" />
		</h2>
		
		<div class='funcButton'>
  			您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>&gt;&gt;修改密码
		</div>
		
		<table class="listTable" cellspacing="1" cellpadding="0">
			<tbody>
				<tr>
					<td align="right" width="40%"><b><@s.text name="groups.usermgr.username" /></b></td>
					<td>
					  <input type="text" id="username" value="${user.loginName!?html}" readonly="true" disabled="true" />
					</td>
				</tr>
				<tr>
					<td align="right"><b><@s.text name="groups.usermgr.old.password" /></b></td>
					<td>
						<@s.password id="oldpassword" name="oldpassword" onfocus="this.select();" />
						<span id="oldpasswordTip"></span>
					</td>
				</tr>
				<tr>
					<td align="right"><b><@s.text name="groups.usermgr.new.password" /></b></td>
					<td>
						<@s.password id="newpassword" name="newpassword" onfocus="this.select();" />
						<span id="newpasswordTip"></span>
					</td>
				</tr>
				<tr>
					<td align="right"><b><@s.text name="groups.usermgr.renew.password" /></b></td>
					<td>
						<@s.password id="renewpassword" name="renewpassword" />
						<span id="renewpasswordTip"></span>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2" align="center">
                        <@s.hidden name="loginName" value="${user.loginName!?html}" />
						<input class="button" type="hidden" name="type" value="<@s.property value='type' />" />
						<input class="button" type="submit" value="<@s.text name='groups.public.ok' /><@s.text name='groups.public.update' />" onClick="check();" />&nbsp;&nbsp;
						<input class="button" type="button" value="<@s.text name="groups.public.cancel" />" onclick="window.history.go(-1);" />
					</td>
				</tr>
			</tfoot>
		</table>
        <script language="javaScript" src="../js/jquery.js"></script>
        <script language="javaScript" src="../js/updpwd.js"></script>
	</body>
</html>
