<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
  <!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
    window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>

 <script type="text/javascript">
function openUpload()
{
 var url = "${SiteUrl}manage/userfm/index.jsp?Type=Image"
 window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
}

function SetUrl( url, width, height )
{
    if(url)
    {
        document.forms[0].so.value = url;
    }
}
</script>
</head> 
<body>
<h3>管理专题：</h3>
<form method='post'>
<table class='listTable'>
<tr>
<td style='width:100px;'><b>专题名称(<span style='color:#f00'>*</span>)：</b></td><td><input name='st' value='${specialSubject.title?html}' style='width:100%' /></td>
</tr>
<tr>
<tr>
<td><b>有效期限(<span style='color:#f00'>*</span>)：</b></td><td>
<input name="se" id="s_date"  class="Wdate" onClick="WdatePicker()" style="width:120px" value='${specialSubject.expiresDate?string('yyyy-MM-dd')}'/>
(格式:yyyy-MM-dd)</td>
</tr>
<tr>
<td><b>专题Logo：</b></td><td><input name='so' style='width:100%' value='${specialSubject.logo!}' /><br/><input type='button' value='选择或上传图片…' onclick='openUpload()' /> (<span style='color:red'>若不上载logo图片，系统会有默认图片，并以常规字体显示专题名称。上载的logo图片宽度请制作为999px，并在图片上写上专题名称。</span>)
</td>
</tr>
<tr>
<td><b>专题描述：</b><br/>
<span style='color:#f00'>不要多于2000个字符。</span>
</td><td>
    <script id="DHtml" name="sd" type="text/plain" style="width:1000px;height:500px;">
        ${specialSubject.description}
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>
</td>
</tr>
</table>
<div><input class='button' type='submit' value='保存修改' /></div>
</form>

