<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>编辑/修改评论</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script type="text/javascript" src="js/common.js"></script>
  <script type="text/javascript" src="js/user.js"></script>
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
<link rel="stylesheet" type="text/css" href="${ContextPath}js/datepicker/calendar.css" />  
  <script>
function submit_reply() {
  $("commentContent").value = editor.getContent();
  $('submit_button').disabled = true;
}
  </script>
</head>
<body>
<h2>编辑/修改评论</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='article.action?cmd=list'>文章管理</a>
  &gt;&gt; <a href='article.action?cmd=comment_list'>评论管理</a>
  &gt;&gt; 编辑/修改评论
</div>
  
<form name='commentForm' method="post" action="video.action?cmd=save_edit_comment" onsubmit="submit_reply()">
  <input type='hidden' name='id' value='${comment.id}' />
  <#if __referer??>
    <input type='hidden' name='__referer' value='${__referer}' />
  </#if>
  <table cellspacing='1' class='listTable'>
  <!--
    <tr>
      <td>标题:</td>
      <td>${comment.title!?html}</td>
    </tr>
  -->  
    <tr>
      <td>作者:</td>
      <td>${comment.userName!?html} 发表于 ${comment.createDate}</td>
    </tr>
    <tr>
      <td colspan='2'>
        <br />
            <script id="commentContent" name="commentContent" type="text/plain" style="width:840px;height:200px;">
            ${comment.content!}
            </script>                          
            <script type="text/javascript">
                var editor = UE.getEditor('commentContent');
            </script>
                                
      </td>
    </tr>
  </table>
  <div class='funcButton'>
    <input id='submit_button' type='submit' class='button' value=' 提交修改 ' />
    <input type='button' class='button' value=' 返 回 ' onclick='window.history.back();' />
  </div>
</form>

 </body>
</html>
