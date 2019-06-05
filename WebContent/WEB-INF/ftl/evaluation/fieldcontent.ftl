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
    
<#if field_list??>
<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:450px">
<#list field_list as t >
<tr>
<td style="width:140px;text-align:right;size:12px;font-weight: bold;">
<br/><br/>
${t.fieldsCaption!?html}
</td>
<td>
<input type="hidden" name="fieldname${t.fieldsId}" value="${t.fieldsCaption!?html}"/>

    <script id="DHtml${t.fieldsId}" name="fieldcontent${t.fieldsId}" type="text/plain" style="width:880px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml${t.fieldsId}');
    </script>  
    
</td>
</tr>
</#list>
<tr>
    <td></td>
    <td>
        <input type="submit" name="btnSave" value="保存评课内容" onclick="return checkInput(this.form)"/>
    </td>
</tr>
</table>
</#if>