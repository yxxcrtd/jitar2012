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
		<title>文件上传</title>
		<link href="browser.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="common.js"></script>
		<script type="text/javascript">

function SetCurrentFolder( resourceType, folderPath )
{
	var sUrl = oConnector.ConnectorUrl + 'Command=FileUpload' ;
	sUrl += '&Type=' + resourceType ;
	sUrl += '&CurrentFolder=' + encodeURIComponent( folderPath ) ;
	document.getElementById('frmUpload').action = sUrl ;
}

function OnSubmit()
{
	if ( document.getElementById('NewFile').value.length == 0 )
	{
		alert( '请点击浏览按钮从您的计算机中选择一个要上传的文件.' ) ;
		return false ;
	}

	// Set the interface elements.
	document.getElementById('eUploadMessage').innerHTML = '上传一个新文件到该文件夹 (正在上传中, 请等待上传完成...)' ;
	document.getElementById('btnUpload').disabled = true ;
	return true ;
}

// 这里被修改为和 fckeditor image_dialog 中 OnUploadCompleted() 一致.
function OnUploadCompleted( errorNumber, fileUrl, fileName, data )
{
    //alert('OnUploadCompleted err=' + errorNumber + ',url=' + fileUrl + ',fileName = '+fileName +',data='+data);
    // Reset the Upload Worker Frame.
    window.parent.frames['frmUploadWorker'].location = 'javascript:void(0)' ;

    // Reset the upload form (On IE we must do a little trick to avoid problems).
    if ( document.all )
        document.getElementById('NewFile').outerHTML = '<input id="NewFile" name="NewFile" style="WIDTH: 100%" type="file">' ;
    else
        document.getElementById('frmUpload').reset() ;

    // Reset the interface elements.
    document.getElementById('eUploadMessage').innerHTML = '上传一个新文件到该文件夹' ;
    document.getElementById('btnUpload').disabled = false ;

    switch ( errorNumber )
    {
        case 0 :
            window.parent.frames['frmResourcesList'].Refresh() ;
            window.top.opener.SetUrl(fileUrl,fileUrl);
            window.top.close();
            break ;
        case 1 :    // Custom error.
            //alert( data ) ;
            break ;
        case 201 :
            window.parent.frames['frmResourcesList'].Refresh() ;
            window.top.opener.SetUrl(fileUrl,fileUrl);
            alert( '已经存在一个和您上传的文件同名的文件. 您上传的新文件已经被改名为 "' + fileName + '"' ) ;
            window.top.close();
            break ;
        case 202 :
            alert( '非法的文件' ) ;
            break ;
        default :
            alert( '在文件上传过程中发生错误. 错误编号: ' + errorNumber + ', 错误描述为: ' + data ) ;
            break ;
    }
}


window.onload = function()
{
	window.top.IsLoadedUpload = true ;
}
		</script>
	</head>
<body bottommargin="0" topmargin="0">
	<form name="frmUpload" id="frmUpload" action="" target="frmUploadWorker" method="post" enctype="multipart/form-data" onsubmit="return OnSubmit();">
<table height="100%" cellspacing="0" cellpadding="0" width="100%" border="0">
	<tr>
		<td nowrap="nowrap">
			<span id="eUploadMessage">上传到文件到该文件夹</span><br>
			<table cellspacing="0" cellpadding="0" width="100%" border="0">
				<tr>
					<td width="100%"><input id="NewFile" name="NewFile" style="WIDTH: 100%" type="file"></td>
							<td nowrap="nowrap">&nbsp;<input id="btnUpload" type="submit" value="上传"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
