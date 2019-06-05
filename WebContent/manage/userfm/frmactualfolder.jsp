<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.util.*"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%
User u = WebUtil.getLoginUser(session);
if(u == null)
{
 response.sendRedirect("/login.jsp");
 return;
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<link href="browser.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">

function OnResize()
{
	divName.style.width = "1px" ;
	divName.style.width = tdName.offsetWidth + "px" ;
}

function SetCurrentFolder( resourceType, folderPath )
{
	document.getElementById('tdName').innerHTML = folderPath ;
}

window.onload = function()
{
	window.top.IsLoadedActualFolder = true ;
}

		</script>
	</head>
	<body bottomMargin="0" topMargin="0">
		<table height="100%" cellSpacing="0" cellPadding="0" width="100%" border="0">
			<tr>
				<td>
					<table cellSpacing="0" cellPadding="0" width="100%" border="0" style='border: 1px solid gray'>
						<tr>
							<td><img height="32" alt="" src="images/FolderOpened32.gif" width="32"></td>
							<td>&nbsp;</td>
							<td id="tdName" width="100%" nowrap class="ActualFolder">/</td>
							<td>&nbsp;</td>
							<td><img height="8" src="images/ButtonArrow.gif" width="12"></td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
