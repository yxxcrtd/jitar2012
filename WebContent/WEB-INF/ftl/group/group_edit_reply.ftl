<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>编辑回复</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
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
<script>
function check() {
  var title = document.replyForm.title.value;
  if (title == null || title == '') {
    alert('请输入回复标题.');
    return false;
  }
  return true;
}
</script>

</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}'>协作组论坛</a> 
  &gt;&gt; <span>编辑回复: ${reply.title!?html}</span>
</div>
	
<form name='replyForm' action="groupBbs.action" method="post" onsubmit='return check();'>
 	<input type='hidden' name='cmd' value='save_reply'/>
 	<input type='hidden' name='topicId' value='${topic.topicId}' />
 	<input type='hidden' name='groupId' value='${group.groupId}'>
 	<input type='hidden' name='replyId' value='${reply.replyId}'>
<#if __referer??>
	<input type='hidden' name='__referer' value='${__referer}' />
</#if>
  <table class='listTable' border='0' cellspacing='1'>
  	<tr>
  		<td align='right' width='20%'><b>标题：</b></td>
  		<td>
  		  <input type="text" name="title" size="75" value='${reply.title!?html}' />
  		  <font color='red'>*</font> 必须填写标题.
  		</td>
  	</tr>												
  	<tr>
  		<td align='right' valign='top'><b>内容：</b></td>
  		<td> 
                <script id="content" name="content" type="text/plain" style="width:1000px;height:400px;">
                ${reply.content!}
                </script>                          
                <script type="text/javascript">
                    var editor = UE.getEditor('content');
                </script> 
                    		
		</td>
  	</tr>
  	<tr>
  	  <td></td>
  		<td>
  		  <input class="button" type="submit" value="  修  改  " />
  		  <input class="button" type="button" value=" 返 回 " onclick="window.history.back()" />
  		</td>
  	</tr>
  </table>
</form>
  
  </body>
</html>
