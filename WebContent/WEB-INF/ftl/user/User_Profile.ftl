<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>修改个人信息</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
 		<script type="text/javascript" src="../manage/js/upload.js"></script>
 		<script language="javascript">
		<!-- Begin
		function textCounter(field, countfield, maxlimit) {
			if (field.value.length > maxlimit)
				field.value = field.value.substring(0, maxlimit);
			else 
				countfield.value = maxlimit - field.value.length;
		}
		
    function uploadPhoto() {
        window.open ("UploadPhoto.jsp", "_blank");
    }     
    function setPhotoFile(imgfile){
        document.getElementById("userIconId").value = imgfile; 
        var img = document.getElementById("icon_image");
        img.src = "../"+imgfile;
    }
    
		// End -->
   </script>
	</head>
	
	<body>
		<h2>修改个人信息</h2>
		<div class="funcButton">
			您现在的位置：<a href="${SiteUrl}manage/" target="_top">个人控制面板</a> &gt;&gt; 修改个人信息
		</div>
		<#include "user_info_form.ftl">
	</body>
</html>
