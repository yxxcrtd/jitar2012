<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="styleSheet" type="text/css" href="${SiteThemeUrl!}common.css" />
		<link rel="styleSheet" type="text/css" href="${SiteThemeUrl!}index.css" />
		<script language="javaScript">
		<!--
		function reSizeframe() {
			self.parent.document.getElementById('regUser').innerHTML = "${userList?size!0}";
			self.parent.document.getElementById('guestUser').innerHTML = "${guestList?size!0}";
		}
		//-->
		</script>
	</head>

	<body onload="reSizeframe();">
		<div class="dist">
			<div class="dist_head">
				<div class="dist_head_left">&nbsp;<img src="${SiteThemeUrl!}j.gif" />&nbsp;当前在线</div>
				<div style="padding-top: 4px;">
					&nbsp;&nbsp;当前在线总人数:&nbsp;${userCount!0}&nbsp;&nbsp;&nbsp;注册用户:&nbsp;${userList?size!0}&nbsp;&nbsp;&nbsp;游客:&nbsp;${guestList?size!0}
					&nbsp;&nbsp;&nbsp;最高在线总人数:&nbsp;${highest!0}&nbsp;&nbsp;&nbsp;发生在:&nbsp;${appearTime!?html}
				</div>
			</div>
			<div class="dist_content">
				<div style="text-align: left;" class="left_img">
					<div id="online">
						<img src="${SiteUrl!}images/index/online_admin.gif" />&nbsp;系统管理员&nbsp;&nbsp;
						<img src="${SiteUrl!}images/index/online_supermod.gif" />&nbsp;其他管理员&nbsp;&nbsp;
						<img src="${SiteUrl!}images/index/online_moderator.gif" />&nbsp;教师&nbsp;&nbsp;
						<img src="${SiteUrl!}images/index/online_member.gif" />&nbsp;学生&nbsp;&nbsp;
						<img src="${SiteUrl!}images/index/online_unknow.gif" />&nbsp;未设置&nbsp;&nbsp;
					</div>
					<div id="online_list">
						<#if userList??>
							<#list userList as user>
								<div class="userList">
									<#if ("admin" == user.username! || "" != user.superAdmin!)>${user.superAdmin!}<img src="${SiteUrl!}images/index/online_admin.gif" />
										<#elseif (2 == user.positionId! || 1 == user.isAdmin!)><img src="${SiteUrl!}images/index/online_supermod.gif" />
										<#elseif (3 == user.positionId!)><img src="${SiteUrl!}images/index/online_moderator.gif" />
										<#elseif (5 == user.positionId! || 4 == user.positionId!)><img src="${SiteUrl!}images/index/online_member.gif" />
										<#else><img src="${SiteUrl!}images/index/online_unknow.gif" />
									</#if>
									<a href="${SiteUrl!}go.action?loginName=${user.username!}" target="_blank" title="${user.trueName!}">${user.trueName!}</a>
								</div>
								<#if ("s" == ss)>
									<#if (12 == user_index)>
										<div class="userList">
										<img src="${SiteUrl!}images/index/online.gif" width="16" height="16" />
										<a href="manage/online.action" target="_blank">查看全部用户&gt;&gt;</a>
										<#break />
										</div>
									</#if>
								</#if>
							</#list> 
						</#if>
					</div>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</body>
</html>
