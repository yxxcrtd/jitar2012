<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title><@s.text name="groups.usermgr.updpwd" /></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
		<script type="text/javascript">
	    <!--
	    function check() {
	    	var question = document.update_form.question;
	    	var answer = document.update_form.answer;   	
	    	if (question.value == "") {
	    		window.alert("请输入您要找回密码的问题！");
	    		question.focus();
	    		return false;
			}
	    	if (answer.value == "") {
	    		window.alert("请输入您要找回密码的答案！");
	    		answer.focus();
	    		return false;
			}
	    	return true;
	    }
	    //-->
	    </script>
	</head>
	
    <body onLoad="javascript:document.update_form.answer.focus();">
        <h2>修改找回密码的问题和答案</h2>
        <div class='funcButton'>
            您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a> &gt;&gt; 修改用户找回密码的问题和答案
        </div>
		
		<form name="update_form" action="?cmd=answer" method="post">
			<table class="listTable" cellspacing="1" cellpadding="0">
				<tbody>
					<tr>
						<td align="right" width="40%"><b><@s.text name="groups.usermgr.username" /></b></td>
						<td>
						  <input type="text" value="${loginName!?html}" readonly="true" disabled="true" />
						</td>
					</tr>
					<tr>
						<td align="right"><b>当前问题：</b></td>
						<td>
							<@s.textfield name="question" value="${question!?html}" />
						</td>
					</tr>
					<tr>
						<td align="right"><b>当前答案：</b></td>
						<td><@s.textfield name="answer" value="" /></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2" align="center">
                            <@s.hidden name="loginName" value="${loginName!?html}" />
							<input class="button" type="hidden" name="type" value="<@s.property value='type' />" />
							<input class="button" type="submit" value="<@s.text name='groups.public.ok' /><@s.text name='groups.public.update' />" onClick="return check(this);" />&nbsp;&nbsp;
							<input class="button" type="button" value="<@s.text name="groups.public.cancel" />" onclick="window.history.go(-1);" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
