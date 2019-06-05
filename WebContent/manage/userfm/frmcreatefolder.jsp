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

function SetCurrentFolder( resourceType, folderPath )
{
	oConnector.ResourceType = resourceType ;
	oConnector.CurrentFolder = folderPath ;
}

function CreateFolder()
{
	var sFolderName ;

	while ( true )
	{
		sFolderName = prompt( '请输入文件夹的名字:', '' ) ;

		if ( sFolderName == null )
			return ;
		else if ( sFolderName.length == 0 )
			alert( 'Please type the folder name' ) ;
		else
			break ;
	}

	oConnector.SendCommand( 'CreateFolder', 'NewFolderName=' + encodeURIComponent( sFolderName) , CreateFolderCallBack ) ;
}

function CreateFolderCallBack( fckXml )
{
	if ( oConnector.CheckError( fckXml ) == 0 )
		window.parent.frames['frmResourcesList'].Refresh() ;

	/*
	// Get the current folder path.
	var oNode = fckXml.SelectSingleNode( 'Connector/Error' ) ;
	var iErrorNumber = parseInt( oNode.attributes.getNamedItem('number').value ) ;

	switch ( iErrorNumber )
	{
		case 0 :
			window.parent.frames['frmResourcesList'].Refresh() ;
			break ;
		case 101 :
			alert( 'Folder already exists' ) ;
			break ;
		case 102 :
			alert( 'Invalid folder name' ) ;
			break ;
		case 103 :
			alert( 'You have no permissions to create the folder' ) ;
			break ;
		case 110 :
			alert( 'Unknown error creating folder' ) ;
			break ;
		default :
			alert( 'Error creating folder. Error number: ' + iErrorNumber ) ;
			break ;
	}
	*/
}

window.onload = function()
{
	window.top.IsLoadedCreateFolder = true ;
}
		</script>
	</head>
	<body bottomMargin="0" topMargin="0">
		<table height="100%" cellSpacing="0" cellPadding="0" width="100%" border="0">
			<tr>
				<td>
					<button type="button" style="WIDTH: 100%" onclick="CreateFolder();">
						<table cellSpacing="0" cellPadding="0" border="0">
							<tr>
								<td><img height="16" alt="" src="images/Folder.gif" width="16"></td>
								<td>&nbsp;</td>
								<td nowrap>创建新文件夹</td>
							</tr>
						</table>
					</button>
				</td>
			</tr>
		</table>
	</body>
</html>
