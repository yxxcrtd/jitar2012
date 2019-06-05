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
<h4>${question.topic}</h4>
<div style='font-weight:bold;'><a href='${SiteUrl}go.action?userId=${question.createUserId}'>${question.createUserName?html}</a> 于 ${question.createDate?string('yyyy/MM/dd')} 提问</div>
<div>${question.questionContent}</div>
<#if answer_list??>
<h4>对该问题的解答：</h4>
<#list answer_list as a>
<div>解答人：<a href='${SiteUrl}go.action?userId=${a.answerUserId}'>${a.answerUserName}</a> 解答时间：${a.createDate?string('yyyy-MM-dd HH:mm:ss')}</div>
<div>${a.answerContent}</div>
</#list>
</#if>
<div style='padding:10px 0'>解答该问题 <span style="color:red">*</span></div>
<form method='post'>
        <script id="DHtml" name="content" type="text/plain" style="width:640px;height:300px;">
        </script>                          
        <script type="text/javascript">
            var editor = UE.getEditor('DHtml');
        </script>
                        
<input type='submit' value='发布解答' />
</form>