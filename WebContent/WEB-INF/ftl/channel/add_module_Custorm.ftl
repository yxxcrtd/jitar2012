<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
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
</head>
<body>
<form method="POST">
<#if module??>
<table class='listTable' cellspacing='1' id='listTable'>
<tr>
<th style="width:80px">模块名称：</th><td><input name="moduleDisplayName" value="${module.displayName!?html}" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）</td>
</tr>
<tr style="vertical-align:top">
<th>模块内容：</th><td>

    <script id="moduleContent" name="moduleContent" type="text/plain" style="width:980px;height:480px;">
    ${module.content!}
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('moduleContent');
    </script> 
            
</td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="保存模块" />
</td>
</tr>
</table>
<#else>
<table>
<tr>
<th>模块名称：</th><td><input name="moduleDisplayName" value="" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）
</td>
</tr>
<tr style="vertical-align:top">
<th>模块内容：</th><td>
    <script id="moduleContent" name="moduleContent" type="text/plain" style="width:980px;height:480px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('moduleContent');
    </script> 


</td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="添加模块" />
</td>
</tr>
</table>
</#if>
</form>
</body>
</html>