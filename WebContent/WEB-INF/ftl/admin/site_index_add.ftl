<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
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
<form method='post' id='oForm' action='site_index_add.py'>
<h2>网站首页定制管理</h2>
<#if siteIndexPart??>
<input name='partId' type='hidden' value='${siteIndexPart.siteIndexPartId}' />
	<table class="listTable" cellSpacing="1">
	<tr>
	<td style='width:100px'>模块名称：</td><td><input name='moduleName' value='${siteIndexPart.moduleName?html}' style='width:300px' /> 页面中显示的文字。</td>
	</tr>
	<tr>
	<td>显示位置：</td><td>
	<#include "site_index_define.ftl" />
	<select name='moduleZone'>
	<#list position as p>
	<option value='${p_index+1}'<#if siteIndexPart.moduleZone== (p_index+1)> selected='selected'</#if>>${p}</option>
	</#list>
	</select> <a href='images/indexzone.jpg' target='_blank'>位置是指标准模块下的，查看位置定义说明，自己增删模块，扔按标准位置设置。</a>
	</td>
	</tr>
	<tr>
	<td>是否显示：</td><td>
	<label>
	<input type='radio' name='moduleDisplay' <#if siteIndexPart.moduleDisplay==1>checked='checked' </#if>value='1' />显示
	</label>
	<label>
	<input type='radio' name='moduleDisplay' <#if siteIndexPart.moduleDisplay==0>checked='checked' </#if>value='0' />隐藏
	</label>
	</td>
	</tr>
	<tr>
	<td>是否显示边框：</td><td>
	<label>
	<input type='radio' name='showBorder' <#if siteIndexPart.showBorder==1>checked='checked' </#if>value='1' />显示
	</label>
	<label>
	<input type='radio' name='showBorder' <#if siteIndexPart.showBorder==0>checked='checked' </#if>value='0' />不显示
	</label>
  </td>
	</tr>
  <tr>
  <td>显示顺序：</td><td><input name='moduleOrder' value='${siteIndexPart.moduleOrder}' /></td>
  </tr>
	<tr>
	<td>模块高度：</td><td><input name='moduleHeight' value='${siteIndexPart.moduleHeight}' />（单位：像素，如果值为0则为不限制。）</td>
	</tr>
	<tr>
  <td>模块宽度：</td><td><input name='moduleWidth' value='${siteIndexPart.moduleWidth}' />（单位：像素，如果值为0则为不限制。如果独立一行显示。此属性将被忽略。）</td>
  </tr>
	<tr>
	<td>模块内容：</td><td>	
            <script id="content" name="content" type="text/plain" style="width:980px;height:500px;">
            ${siteIndexPart.content!}
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('content');
            </script> 
            	
	</td>
	</tr>
	</table>
<#else>
	对象生成错误。
</#if>
<div style='padding:4px 0'>
<input type='submit' value=' 保  存 ' class='button' />
<input type='button' value=' 返  回 ' class='button' onclick='window.location.href="site_index.py"' />
</div>
<div>注意：可根据布局需要调整内容的宽度和高度，默认情况下，无需设置宽度。显示效果需要根据设置微调。</div>
</form>
</body>
</html>