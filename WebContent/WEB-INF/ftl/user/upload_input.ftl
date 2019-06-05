<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title><@s.text name="groups.profile.headimg.upload" /></title>
		<link rel="stylesheet" type="text/css" href="../css/user.css">
	</head>
	
	<body>
		<table cellpadding="0" width="100%" cellspacing="8px" border="0" align="center">
			<tr>
				<td>
					<@s.fielderror cssStyle="color: #FF0000; font-weight:bold;" />
				</td>
				<td>
					<a href="javascript:window.history.go(-1);">点此重新上传！</a>
				</td>
			</tr>
		</table>
	</body>
</html>
