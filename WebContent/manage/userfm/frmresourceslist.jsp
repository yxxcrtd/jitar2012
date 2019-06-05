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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="browser.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="common.js"></script>
	<script type="text/javascript">

var oListManager = new Object() ;
var gCurrentFolder = '/';

oListManager.Clear = function()
{
	document.body.innerHTML = '' ;
}

function ProtectPath(path)
{
	path = path.replace( /\\/g, '\\\\') ;
	path = path.replace( /'/g, '\\\'') ;
	return path ;
}

oListManager.GetFolderRowHtml = function( folderName, folderPath )
{
	// Build the link to view the folder.
	var sLink = '<a href="#" onclick="OpenFolder(\'' + ProtectPath( folderPath ) + '\');return false;">' ;
  var delLink = '<a href="#" onclick="DeleteFolder(\'' + ProtectPath( folderName ) + '\');return false;">';
	return '<tr>' +
			'<td width="16">' +
				sLink +
				'<img alt="" src="images/Folder.gif" width="16" height="16" border="0"><\/a>' +
			'<\/td><td nowrap colspan="2">&nbsp;' +
				sLink +
				folderName +
				'<\/a>' +
		'<\/td><td width="48">' + delLink + '删除</a></td><\/tr>' ;
}

oListManager.GetFileRowHtml = function( fileName, fileUrl, fileSize, sCurrentFolderPath )
{
	// Build the link to view the folder.
	var sLink = '<a href="#" onclick="OpenFile(\'' + ProtectPath( fileUrl ) + '\');return false;">' ;
  var delLink = '<a href="#" onclick="DeleteFile(\'' + ProtectPath( fileName ) + '\');return false;">';

	// Get the file icon.
	var sIcon = oIcons.GetIcon( fileName ) ;

	return '<tr>' +
			'<td width="16">' +
				sLink +
				'<img alt="" src="images/icons/' + sIcon + '.gif" width="16" height="16" border="0"><\/a>' +
			'<\/td><td>&nbsp;' +
				sLink +
				fileName +
				'<\/a>' +
			'<\/td><td align="right" nowrap>&nbsp;' +
				fileSize +
				' KB' +
    '<\/td><td width="48" align="center">' + delLink + '删除</a></td><\/tr>' ;
}

function OpenFolder( folderPath )
{
	// Load the resources list for this folder.
	window.parent.frames['frmFolders'].LoadFolders( folderPath ) ;
}

function OpenFile( fileUrl )
{
  // alert('OpenFile for window.top.opener fileUrl = ' + fileUrl);
	// window.top.opener.SetUrl( encodeURI( fileUrl ) ) ;
	window.top.opener.SetUrl(encodeURI(fileUrl), fileUrl);
	window.top.close();
	window.top.opener.focus();
}

// 删除一个文件夹.
function DeleteFolder( folderName ) {
  if (confirm('您是否确定要删除文件夹 ' + folderName + 
      '?\r\n\r\n提示: 如果该文件夹是系统使用的, 稍后可能还会自动创建出来.') == false) 
    return;
  
  window.parent.frames['frmFolders'].DeleteFolder( folderName ) ;
}

// 删除一个文件.
function DeleteFile( filePath ) {
  if (confirm('您是否确定要删除文件 ' + filePath + 
      '?\r\n\r\n警告: 如果该文件是上传的图片、资源、文章中插入的图片等, 则该文件删除之后不可被恢复.') == false) 
    return;

  if (gCurrentFolder == '/photo/') {
    if (confirm('警告: 您试图从 photo 目录下删除文件, 该目录用于存放您个人上传的相册, \r\n' +
        '不正确的删除可能导致您的相册图片丢失, 请再次确认您的操作.') == false)
      return;
  } else if (gCurrentFolder == '/resource') {
    if (confirm('警告: 您试图从 resource 目录下删除文件, 该目录用于存放您个人上传的资源, \r\n' +
        '不正确的删除可能导致您的资源丢失, 请再次确认您的操作.') == false)
      return;
  }
  
  window.parent.frames['frmFolders'].DeleteFile( filePath ) ;
}

function LoadResources( resourceType, folderPath )
{
	oListManager.Clear() ;
	oConnector.ResourceType = resourceType ;
	oConnector.CurrentFolder = folderPath ;
	oConnector.SendCommand( 'GetFoldersAndFiles', null, GetFoldersAndFilesCallBack ) ;
}

function Refresh()
{
	LoadResources( oConnector.ResourceType, oConnector.CurrentFolder ) ;
}

function GetFoldersAndFilesCallBack( fckXml )
{
	if ( oConnector.CheckError( fckXml ) != 0 )
		return ;

	// Get the current folder path.
	var oFolderNode = fckXml.SelectSingleNode( 'Connector/CurrentFolder' ) ;
	if ( oFolderNode == null )
	{
		alert( '服务器没有返回正确的 XML 数据. 请联系管理员检查服务器上面的配置.' ) ;
		return ;
	}
	var sCurrentFolderPath = oFolderNode.attributes.getNamedItem('path').value ;
	gCurrentFolder = sCurrentFolderPath;
	var sCurrentFolderUrl	= oFolderNode.attributes.getNamedItem('url').value ;

//	var dTimer = new Date() ;

	var oHtml = new StringBuilder( '<table id="tableFiles" cellspacing="1" cellpadding="0" width="100%" border="0">' ) ;

	// Add the Folders.
	var oNodes ;
	oNodes = fckXml.SelectNodes( 'Connector/Folders/Folder' ) ;
	for ( var i = 0 ; i < oNodes.length ; i++ )
	{
		var sFolderName = oNodes[i].attributes.getNamedItem('name').value ;
		oHtml.Append( oListManager.GetFolderRowHtml( sFolderName, sCurrentFolderPath + sFolderName + "/" ) ) ;
	}

	// Add the Files.
	oNodes = fckXml.SelectNodes( 'Connector/Files/File' ) ;
	for ( var j = 0 ; j < oNodes.length ; j++ )
	{
		var oNode = oNodes[j] ;
		var sFileName = oNode.attributes.getNamedItem('name').value ;
		var sFileSize = oNode.attributes.getNamedItem('size').value ;

		// Get the optional "url" attribute. If not available, build the url.
		var oFileUrlAtt = oNodes[j].attributes.getNamedItem('url') ;
		var sFileUrl = oFileUrlAtt != null ? oFileUrlAtt.value : sCurrentFolderUrl + sFileName ;

		oHtml.Append( oListManager.GetFileRowHtml( sFileName, sFileUrl, sFileSize, sCurrentFolderPath ) ) ;
	}

	oHtml.Append( '<\/table>' ) ;

	document.body.innerHTML = oHtml.ToString() ;

//	window.top.document.title = 'Finished processing in ' + ( ( ( new Date() ) - dTimer ) / 1000 ) + ' seconds' ;

}

window.onload = function()
{
	window.top.IsLoadedResourcesList = true ;
}
	</script>
</head>
<body class="FileArea">
  <h3>等待文件列表加载完成......</h3>
</body>
</html>
