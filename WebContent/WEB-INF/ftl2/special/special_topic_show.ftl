<style>
.commentTable {width:100%;background:pink; margin:0 0 6px 0;}
.commentLeft{ width:60px; background:#fff; text-align:center; vertical-align:top; padding:10px;} 
.commentLeft img{ width:64px;height:64px;border:0px; }
.commentRight{background:#FFF;width:100%;padding:6px;}
.commentHeader{ width:100%; border-bottom:1px solid #DDD; padding-bottom:4px;}
.commentContent {  padding: 3px; border: 1px solid #dddddd; background-color: #ececec; margin:5px 0px 2px 0px;}
.commentTitle { font-weight:bold; }
</style>
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
<a name='top'></a>
<table border="0" cellspacing="1" cellpadding="3" class="commentTable">
<tr>
<td class="commentRight">
<div style='float:right'><a href='${SiteUrl}go.action?userId=${plugInTopic.createUserId}' target="_blank">${plugInTopic.createUserName!?html}</a> 于 ${plugInTopic.createDate?string('yyyy-MM-dd HH:mm:ss')} 发起</div>
<div style='float:left;font-weight:bold'>${plugInTopic.title!?html}</div>
</td>
</tr>
<tr>
<td class="commentRight">${plugInTopic.topicContent}</td>
</tr>
</table>
<#if topic_reply_list?? >
<#list topic_reply_list as reply>
<#assign u = Util.userById(reply.createUserId) >
<table border="0" cellspacing="1" cellpadding="3" class="commentTable">
<tr>
<td class="commentLeft">
<img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' style='margin-bottom:4px'/>
<a href="${SiteUrl}go.action?loginName=${u.loginName!}" target="_blank">${u.nickName!?html}</a>		
</td>
<td class="commentRight">
<div class="commentHeader">				
<span style="float: right">${reply.createDate?string('yyyy-MM-dd HH:mm:ss')} <a href="#top">[TOP]</a></span>
<span style='clear:both;font-weight:bold;'>${reply.title?html}</span>
</div>
<div class="commentContent">${reply.replyContent}</div>
</td>
</tr>
</table>
</#list>
<div class='pager'>
<#include "../pager.ftl">
</div>
</#if>
<#if loginUser?? >
<form method='post'>
回复标题(<span style='color:#F00'>*</span>)：<input style='width:400px' name='commentTitle' value='RE:${plugInTopic.title?html}' />

<br/>
回复内容(<span style='color:#F00'>*</span>)：<br/>

    <script id="content" name="content" type="text/plain" style="width:640px;height:300px;">
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('content');
    </script>
                        
<div style='text-align:center'>
<input value="发表回复" type="submit" class="specialSubmit" />
</div>
</form>
</#if>