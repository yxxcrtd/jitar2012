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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>文件管理</title>
    <link rel="stylesheet" type="text/css" href="browser.css" media="screen" />
    <script type="text/javascript" src="fckxml.js"></script>
    <script language="javascript">
function GetUrlParam( paramName )
{
  var oRegex = new RegExp( '[\?&]' + paramName + '=([^&]+)', 'i' ) ;
  var oMatch = oRegex.exec( window.top.location.search ) ;

  if ( oMatch && oMatch.length > 1 )
    return decodeURIComponent( oMatch[1] ) ;
  else
    return '' ;
}
//alert(window.opener.parent.document.URL);
//var currentuserID=0;
//try
//{
//	alert(window.opener.opener.document.all["userid"].value); 
//}
//catch(ex)
//{}

var oConnector = new Object() ;
oConnector.CurrentFolder  = '/' ;

var sConnUrl = GetUrlParam( 'Connector' ) ;
if (!sConnUrl) sConnUrl = 'fileman.action'; 

// Gecko has some problems when using relative URLs (not starting with slash).
if ( sConnUrl.substr(0,1) != '/' && sConnUrl.indexOf( '://' ) < 0 )
  sConnUrl = window.location.href.replace( /index.jsp.*$/, '' ) + sConnUrl ;

oConnector.ConnectorUrl = sConnUrl + ( sConnUrl.indexOf('?') != -1 ? '&' : '?' ) ;

var sServerPath = GetUrlParam( 'ServerPath' ) ;
if ( sServerPath.length > 0 )
  oConnector.ConnectorUrl += 'ServerPath=' + encodeURIComponent( sServerPath ) + '&' ;

oConnector.ResourceType   = GetUrlParam( 'Type' ) ;
oConnector.ShowAllTypes   = ( oConnector.ResourceType.length == 0 ) ;

if ( oConnector.ShowAllTypes )
  oConnector.ResourceType = 'File' ;

/* 向服务器发送命令, 包括 GetFolders, GetFiles, FileUpload etc. */
oConnector.SendCommand = function( command, params, callBackFunction ) {
  // alert('oConnector.SendCommand command = ' + command + ', params = ' + params + ', callBackFunction = ' + callBackFunction);
  var sUrl = this.ConnectorUrl + 'Command=' + command ;
  sUrl += '&Type=' + this.ResourceType ;
  sUrl += '&CurrentFolder=' + encodeURIComponent( this.CurrentFolder ) ;

  if ( params ) sUrl += '&' + params ;

  // Add a random salt to avoid getting a cached version of the command execution
  sUrl += '&uuid=' + new Date().getTime() ;

  var oXML = new FCKXml() ;

  if ( callBackFunction )
    oXML.LoadUrl( sUrl, callBackFunction ) ;  // Asynchronous load.
  else
    return oXML.LoadUrl( sUrl ) ;

  return null ;
}

oConnector.CheckError = function( responseXml )
{
  var iErrorNumber = 0 ;
  var oErrorNode = responseXml.SelectSingleNode( 'Connector/Error' ) ;

  if ( oErrorNode )
  {
    iErrorNumber = parseInt( oErrorNode.attributes.getNamedItem('number').value, 10 ) ;

    switch ( iErrorNumber )
    {
      case 0 :
        break ;
      case 1 :  // Custom error. Message placed in the "text" attribute.
        alert( oErrorNode.attributes.getNamedItem('text').value ) ;
        break ;
      case 101 :
        alert( '文件夹已经存在' ) ;
        break ;
      case 102 :
        alert( '非法的文件夹名字' ) ;
        break ;
      case 103 :
        alert( '没有创建文件夹的权限' ) ;
        break ;
      case 110 :
        alert( '在创建文件夹的时候发生未知错误' ) ;
        break ;
      default :
        alert( '请求发生错误. 错误编号: ' + iErrorNumber ) ;
        break ;
    }
  }
  return iErrorNumber ;
}

var oIcons = new Object() ;

oIcons.AvailableIconsArray = [
  'ai','avi','bmp','cs','dll','doc','exe','fla','gif','htm','html','jpg','js',
  'mdb','mp3','pdf','png','ppt','rdp','swf','swt','txt','vsd','xls','xml','zip' ] ;

oIcons.AvailableIcons = new Object() ;

for ( var i = 0 ; i < oIcons.AvailableIconsArray.length ; i++ )
  oIcons.AvailableIcons[ oIcons.AvailableIconsArray[i] ] = true ;

oIcons.GetIcon = function( fileName )
{
  var sExtension = fileName.substr( fileName.lastIndexOf('.') + 1 ).toLowerCase() ;

  if ( this.AvailableIcons[ sExtension ] == true )
    return sExtension ;
  else
    return 'default.icon' ;
}

function OnUploadCompleted( errorNumber, fileUrl, fileName, customMsg )
{
  window.frames['frmUpload'].OnUploadCompleted( errorNumber, fileUrl, fileName, customMsg ) ;
}

    </script>
  </head>
  
  <frameset cols="150,*" class="Frame" framespacing="3" bordercolor="#f1f1e3" frameborder="yes">
    <frameset rows="50,*" framespacing="0">
      <frame name="frmResourceType" src="frmresourcetype.jsp" scrolling="no" frameborder="0">
      <frame name="frmFolders" src="frmfolders.jsp" scrolling="auto" frameborder="1">
    </frameset>
    <frameset rows="50,*,50" framespacing="0">
      <frame name="frmActualFolder" src="frmactualfolder.jsp" scrolling="no" frameborder="0">
      <frame name="frmResourcesList" src="frmresourceslist.jsp" scrolling="auto" frameborder="1">
      <frameset cols="150,*,0" framespacing="0" frameborder="no">
        <frame name="frmCreateFolder" src="frmcreatefolder.jsp" scrolling="no" frameborder="0">
        <frame name="frmUpload" src="frmupload.jsp" scrolling="no" frameborder="1">
        <frame name="frmUploadWorker" src="javascript:void(0)" scrolling="no" frameborder="0">
      </frameset>
    </frameset>
  </frameset>
</html>
