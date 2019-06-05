<#if leaveword_list??>
<table style='width:100%'>
<#list leaveword_list as leaveword>
<#assign u = Util.userById(leaveword.userId)>
  <#if (u.trueName??)><#assign userName = u.trueName >
  <#elseif (u.nickName??)><#assign userName = u.nickName >
  <#elseif (u.loginName??)><#assign userName = u.loginName >
  </#if>
<tr>
<td><a target="_blank" href="${SiteUrl}${leaveword.loginName!}" onmouseover="ToolTip.showUserCard(event,'${u.loginName!}','${userName!}','${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}')">${u.nickName!}</a>:${leaveword.title!?html}</td>
<td align='right'>${leaveword.createDate?string('MM-dd HH:mm')}</td>
</tr>
<tr>
<td colspan='2' style='border-bottom:1px solid #333;padding:4px 0'>${leaveword.content!?html}</td>
</tr>
</#list>
</table>
<#assign rnd = Util.uuid().toString().replace("-","")>
<div style='text-align:right;'><span style='cursor:pointer;' onclick='$("d${rnd}").style.display=""'><a href='' onclick='return false;'>添加留言</a></span> <span style='cursor:pointer;'>
<a href='${UserSiteUrl}py/user_leaveword_list.py'>全部留言</a>
</span></div>
<div style='display:none;' id='d${rnd}'>
	<form name="leaveword_form" action="${SiteUrl}manage/leaveword.action" method="post">
		<input type='hidden' name='cmd' value='save_leaveword' />
		<input type='hidden' name='redirect' value='true' />
		<input type='hidden' name='receiverId' value='${user.userId}' />
		<table width='100%'>
		<tr>
			<td><nobr>留言标题:</nobr></td>
			<td style='width:100%'>
				<input type='text' name='LeavewordTitle' value='' style='width:100%' maxlength="16"/>
			</td>
		</tr>
		<tr>
			<td valign="top"><nobr>留言内容:</nobr></td>
			<td><textarea  name="LeavewordContent" style='width:100%'></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><input type='submit' value='发送留言' onclick='return CommonUtil.checkClientLeaveWordForm(this.form);' /> <input type='button' onclick='$("d${rnd}").style.display="none"' value='关闭窗口' /></td>
		<tr>
		</table>
	</form>
</div>
</#if>