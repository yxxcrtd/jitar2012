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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="browser.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="common.js"></script>
		<script language="javascript">
function SetResourceType( type ) {
	window.parent.frames["frmFolders"].SetResourceType( type ) ;
}

var aTypes = [
	['File', '文件'],
	['Image', '图片'],
	['Flash', 'Flash'],
	['Media', '媒体']
] ;

window.onload = function() {
  try {
		for ( var i = 0 ; i < aTypes.length ; i++ ) {
			if ( oConnector.ShowAllTypes || aTypes[i][0] == oConnector.ResourceType )
				AddSelectOption( document.getElementById('cmbType'), aTypes[i][1], aTypes[i][0] ) ;
		}
	} catch(ex) {
	  alert('ex = ' + ex);
	}
}
		</script>
	</head>
	<body bottomMargin="0" topMargin="0">
		<table height="100%" cellSpacing="0" cellPadding="0" width="100%" border="0">
			<tr>
				<td nowrap>
					资源类型<BR>
					<select id="cmbType" style="WIDTH: 100%" onchange="SetResourceType(this.value);">
					</select>
				</td>
			</tr>
		</table>
	</body>
</html>
