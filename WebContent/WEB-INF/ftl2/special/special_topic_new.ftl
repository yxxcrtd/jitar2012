<form method="post" action="new_topic.action">
<input name="cmd" value="save" type="hidden" />
<input name="backUrl" value="" type="hidden" />
<input name="guid" value="${parentGuid!}" type="hidden" />
<input name="type" value="${parentType!}" type="hidden" />
<table style='width:600px' cellpadding="0" cellspacing="0">
		<tr>
			<td style='width: 80px;font-weight:bold;'>
			讨论标题<span style='color:#f00'>*</span>:
			</td>
			<td>
				<input name='ttitle' id="ttitle" maxlength="32" style='width:500px; height: 25px; outline: none; border: 1px solid #000000;' />
			</td>
		</tr>
	<tr>
<td style='font-weight:bold;' colspan="2">讨论内容<span style='color:#f00'>*</span>:</td>
</tr>
<tr>
<td colspan="2">
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
    <script id="tcontent" name="tcontent" type="text/plain" style="width:560px;height:300px;">
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('tcontent');
    </script>
</td>
</tr>
<tr>
<td></td>
<td style="text-align: center;">
	<#if loginUser??>
		<input type="button" onclick="subData(this.form)" value=' 提  交 ' class="specialSubmit" />
	<#else>
		请<a href='${SiteUrl}login.jsp' style='font-weight:bold;'>登录</a>后参与讨论。
	</#if>
</td>
</tr>
</table>
</form>

<script type='text/javascript'>
function subData(f) {
	if ("" == f.ttitle.value) {
		alert("请输入讨论标题！");
		return false;
	}      

    var content = editor.getContent();
  
    var len = content.length;
    if (len == 0) {
		  alert("请输入讨论内容！");
      	return false;
    }
    f.backUrl.value=window.location.href;

    f.submit();
    }
</script>