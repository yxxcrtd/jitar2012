<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.util.*"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

User u = WebUtil.getLoginUser(session);
if(u == null)
{
 response.sendRedirect(basePath + "login.jsp");
 return;
}

%>
<html>
	<head>
		<meta http-equiv="expires" content="0">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script type="text/javascript" src="../manage/js/upload.js"></script>
		<script type="text/javascript">
        function uploadPhoto() {
            //window.showModelessDialog("/Personal/UploadPhoto", "", "center:no;dialogLeft:50px;dialogTop:10px;scroll:0;status:0;help:0;resizable:0;dialogWidth:900px;dialogHeight:600px");
            //window.showModalDialog("/Personal/UploadPhoto", window, "center:no;dialogLeft:50px;dialogTop:10px;scroll:0;status:0;help:0;resizable:0;dialogWidth:900px;dialogHeight:600px");
            window.open ("UploadPhoto.jsp", "_blank");
        }	
        function setPhotoFile(imgfile){
        	
        }  
        </script>
	</head>

	<body>
	<!-- 
		<form name="formupload" method="post" enctype="multipart/form-data"   action="user.action?cmd=uploadheadimg" onSubmit="return check();">
			<table style="left: 0; top: 0; border: 0 solid navy; position: absolute;">
				<tr>
					<td width="100%" bgColor="#FFFFFF" valign="top">
						<input type="hidden" name="userId" value="<%=request.getParameter("userId")%>" />
						<input type="file" id="uploadFile" name="file" size="32" />
						<input type="submit" id="btnUpload" name="btnUpload"
							value="上&nbsp;&nbsp;传" />
					</td>
				</tr>
			</table>
		</form>
    -->
    <!-- 
            <table style="left: 0; top: 0; border: 0 solid navy; position: absolute;">
                <tr>
                    <td width="100%" bgColor="#FFFFFF" valign="top">
                        <input type="hidden" name="userId" value="<%=u.getUserId()%>" />
                        <input type="button" id="btnUpload" name="btnUpload" value="上传剪切头像"  onclick="uploadPhoto();"/>
                    </td>
                </tr>
            </table>
		-->
	</body>
</html>
