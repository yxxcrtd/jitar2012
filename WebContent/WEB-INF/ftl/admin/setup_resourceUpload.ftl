<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>资源上载设置</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>
<body>

<h2>资源上载设置</h2>
<li>如果不选中任何资源类型，则上载类型不限制
<form name="setup_form" action="?" method="post" >
  <input type='hidden' name='cmd' value='save' />
<table class="listTable" cellspacing="1">
	<tr>
		<td align="right" width='20%'><b>资源上载文件不能超过：</b></td>
		<td>
			<input type="text" name="filesize" value="${size}" size="10" />M
		</td>
	</tr>
	<tr>
		<td align="right" width='20%'><b>允许上载文件类型：</b></td>
		<td>
		  <#if param.indexOf("audio/mpeg")!=-1 || param.indexOf("audio/x-mpeg")!=-1>
	 	  <input type="checkbox" checked name="filetype" value="audio/mpeg,audio/x-mpeg">*.abs,*.mp3
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="audio/mpeg,audio/x-mpeg">*.abs,*.mp3
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("audio/basic")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="audio/basic">*.au
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="audio/basic">*.au
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("video/x-msvideo")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="video/x-msvideo">*.avi
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="video/x-msvideo">*.avi
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("image/bmp")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="image/bmp">*.bmp
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="image/bmp">*.bmp
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("image/gif")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="image/gif">*.gif
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="image/gif">*.gif
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("image/jpg")!=-1 || param.indexOf("image/jpeg")!=-1 || param.indexOf("image/pjpeg")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="image/jpg,image/jpeg,image/pjpeg">*.jpeg,*.jpg
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="image/jpg,image/jpeg,image/pjpeg">*.jpeg,*.jpg
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("image/x-png")!=-1 || param.indexOf("image/png")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="image/x-png,image/png">*.png
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="image/x-png,image/png">*.png
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("image/tiff")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="image/tiff">*.tif
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="image/tiff">*.tif
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("audio/x-midi")!=-1 || param.indexOf("audio/midi")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="audio/x-midi,audio/midi">*.mid,*.midi
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="audio/x-midi,audio/midi">*.mid,*.midi
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("video/quicktime")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="video/quicktime">*.mov
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="video/quicktime">*.mov
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("video/mp4")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="video/mp4">*.mp4
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="video/mp4">*.mp4
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("video/mpeg")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="video/mpeg">*.mpeg,*.mpg
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="video/mpeg">*.mpeg,*.mpg
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("audio/x-mpeg")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="audio/x-mpeg">*.mpega
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="audio/x-mpeg">*.mpega
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/x-shockwave-flash")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/x-shockwave-flash">*.swf
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/x-shockwave-flash">*.swf
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("text/plain")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="text/plain">*.txt
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="text/plain">*.txt
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/pdf")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/pdf">*.pdf
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/pdf">*.pdf
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("audio/x-wav")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="audio/x-wav">*.wav
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="audio/x-wav">*.wav
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("video/x-ms-wmv")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="video/x-ms-wmv">*.wmv
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="video/x-ms-wmv">*.wmv
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/vnd.rn-realmedia")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/vnd.rn-realmedia">*.rm
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/vnd.rn-realmedia">*.rm
	 	  </#if>
	 	  <br/>
	  
	 	  
	 	  <#if param.indexOf("application/zip")!=-1 ||  param.indexOf("application/x-zip-compressed")!=-1>
	 	  <input type="checkbox" checked name="filetype" value="application/zip,application/x-zip-compressed">*.zip
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/zip,application/x-zip-compressed">*.zip
	 	  </#if>
	 	  
	 	  <br/>
	 	  <#if param.indexOf("application/octet-stream")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/octet-stream">*.rar, *.gsp
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/octet-stream">*.rar, *.gsp
	 	  </#if>
	 	  <br/>
	 	  
	 	  <#if param.indexOf("application/xml")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/xml">*.xml
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/xml">*.xml
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/msword")!=-1 || param.indexOf("application/vnd.ms-word")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/msword,application/vnd.ms-word">*.doc
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/msword,application/vnd.ms-word">*.doc
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/powerpoint")!=-1 || param.indexOf("application/vnd.ms-powerpoint")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/powerpoint,application/vnd.ms-powerpoint">*.ppt
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/powerpoint,application/vnd.ms-powerpoint">*.ppt
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/vnd.ms-excel")!=-1 || param.indexOf("application/msexcel")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/vnd.ms-excel,application/msexcel">*.xls
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/vnd.ms-excel,application/msexcel">*.xls
	 	  </#if>
	 	  <br/>
	 	  <#if param.indexOf("application/java-archive")!=-1 >
	 	  <input type="checkbox" checked name="filetype" value="application/java-archive">*.jar
	 	  <#else>
	 	  <input type="checkbox" name="filetype" value="application/java-archive">*.jar
	 	  </#if>
		</td>
	</tr>
	<tr>
	  <td></td>
	  <td>
		  <input class="button" type="submit" value="保  存" />
		  <input class="button" type="button" value=" 返 回 " onclick="window.history.back()" />
	  </td>
	</tr>
</table>
</form>

</body>
</html>
