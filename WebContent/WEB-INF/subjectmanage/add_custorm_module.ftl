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
<h2>
添加自定义内容模块
</h2>
<form method='post' style='padding-left:20px'>
<table class='listTable' cellspacing='1' style="width:1024px">
<tr>
<td style='width:100px'>模块名称：</td><td>
<input name='moduleName' style='width:99%' value='${subjectWebpart.moduleName!}' />
</td>
</tr>
<tr>
<td>模块区域：</td><td>
<select name='webpartZone'>
<option value='1'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 1> selected='selected'</#if>>顶部</option>
<option value='2'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 2> selected='selected'</#if>>下部</option>
<option value='3'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 3> selected='selected'</#if>>左</option>
<option value='4'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 4> selected='selected'</#if>>中</option>
<option value='5'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 5> selected='selected'</#if>>右</option>
</select>
</td>
</tr>

</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;border-bottom: 1px solid #E6DBC0;position:relative;height:560px;width:1022px">
    <div style="padding:8px 2px;height:560px;width:94px;float:left">模块内容:</div>
    <div style="position:absolute;top:0;left:110px">
    <script id="DHtml" name="content" type="text/plain" style="width:840px;height:480px;">
    ${subjectWebpart.content!}
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>
    </div>
</div>

<div style='padding:6px'>
<input class='button' type='submit' value=' 保  存 ' />
</div>
</form>
</body>
</html>
