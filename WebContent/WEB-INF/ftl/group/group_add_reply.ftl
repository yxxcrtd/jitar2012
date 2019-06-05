<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>回复</title>
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
  &gt;&gt; <span>回复主题: ${topic.title!?html}</span>
</div>
  	
<form name='replyForm' action="groupBbs.action" method="post" onsubmit='return check();'>
 	<input type='hidden' name='cmd' value='save_reply'/>
 	<input type='hidden' name='topicId' value='${topic.topicId}' />
 	<input type='hidden' name='groupId' value='${group.groupId}' />
 		<#if __referer??>
			<input type='hidden' name='__referer' value='${__referer!}' />
 		</#if>
    <table class="listTable" cellspacing='1' style="width:1024px">
    	<tr>
    		<td align="right" width='100'><b>回复标题:</b></td>
    		<td>
    		  <input type="text" name="title" size="60" />
    		  <font color='red'>*</font> 必须填写标题.
    		</td>
    	</tr>		
    	</table>
<div style="position:relative;height:504px;">
    <div style="padding:8px 0;height:500px;width:96px;float:left"><b>回复内容：</b></div>
    <div style="position:absolute;top:0;left:108px">
      <script id="content" name="content" type="text/plain" style="width:980px;height:420px;">
         ${topic.content!}
      </script>
      <script type="text/javascript">
       var editor = UE.getEditor('content');
      </script>   
    </div>
</div>

<table class="listTable" cellspacing='1' style="width:1024px">
    <tr>
    	<td width='100'></td>
    	<td>
    		<input type="submit" class='button' value="  发  表  " />&nbsp;&nbsp;
    		<input type='button' class='button' value=" 返 回 " onclick="window.history.back()" />
    	</td>
    </tr>
    </table>
  </form>
  </body>
</html>
