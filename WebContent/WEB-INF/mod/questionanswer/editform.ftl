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
<#if question?? >
<input type='hidden' name='questionId' value='${question.questionId}' />
<div>问题名称 <span style="color:red">*</span>：<input name='quesition_title' style='width:80%' value='${question.topic!?html}' /></div>
<div>问题描述 <span style="color:red">*</span>：
</div>
<#else>
<div>问题名称 <span style="color:red">*</span>：<input name='quesition_title' style='width:80%' value='' /></div>
<div>问题描述 <span style="color:red">*</span>：
</div>
</#if>

        <script id="quesition_content" name="quesition_content" type="text/plain" style="width:540px;height:300px;">
        <#if question?? >${question.questionContent!}</#if>
        </script>                          
        <script type="text/javascript">
            var editor = UE.getEditor('quesition_content');
        </script>

<div><input type='submit' value=' 提  问 ' /></div>
</form>
