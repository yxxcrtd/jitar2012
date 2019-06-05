<!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
    window.UEDITOR_USERLOGINNAME = visitor.name;
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>
<form method='post'>
<table style='width:100%'>
<tr>
<td style='width:68px;font-weight:bold;'>讨论标题<span style='color:#f00'>*</span>:</td><td><input name='ttitle' style='width:100%;' /></td>
</tr>
<tr>
<td style='font-weight:bold;'>讨论内容<span style='color:#f00'>*</span>:</td><td>
    <script id="tcontent" name="tcontent" type="text/plain" style="width:640px;height:300px;">
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('tcontent');
    </script>
      
</td>
</tr>
<tr>
<td></td><td>
<#if loginUser??>
	<input type='submit' value=' 提  交 ' />
<#else>
	请<a href='${SiteUrl}login.jsp' style='font-weight:bold;'>登录</a>后参与讨论。
</#if>
</td>
</tr>
</table>
</form>