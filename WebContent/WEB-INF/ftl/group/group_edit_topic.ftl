    <#assign grpName="协作组">
    <#assign grpShowName="小组">
    <#if isKtGroup??>
        <#if isKtGroup=="1">
            <#assign grpName="课题组"> 
            <#assign grpShowName="课题">
        <#else>
            <#assign grpName="协作组">
            <#assign grpShowName="小组">
        </#if>
    </#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>编辑主题</title>
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
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}'>${grpName}论坛</a>
  &gt;&gt; <span>编辑主题 '${topic.title!?html}'</span> 
</div>

 	<form action="groupBbs.action" method="post">
 		<input type='hidden' name='cmd' value='save_topic' />
 		<input type='hidden' name='groupId' value="${group.groupId}" />
 		<input type='hidden' name='topicId' value="${topic.topicId}" />
 		<#if __referer??>
			<input type='hidden' name='__referer' value='${__referer!}' />
 		</#if>
    <table class='listTable' border='0' cellspacing='1' style="width:1024px">
    	<tr>
    		<td width='100' align='right'><b>标题：</b></td>
    		<td>
    		  <input type="text" name="title" size="75" value='${topic.title!?html}' />
    		  <font color='red'>*</font> 必须填写标题.
    		</td>
    	</tr>
</table>
<div style="position:relative;height:504px;">
    <div style="padding:8px 0;height:500px;width:96px;float:left"><b>内容：</b></div>
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
    		<td align='right' style="width:100px"><b>标签：</b></td>
    		<td>
    		  <input type="text" name="tags" size='60' value='${topic.tags!?html}' /> (以 ',' 逗号隔开多个标签)
    		</td>
    	</tr>
    	<tr>
    	  <td></td>
    		<td>
    		  <input class='button' type="submit" value="  修  改  " />
    		  <input class="button" type="button" value=" 返 回 " onclick="window.history.back()"/>
    		</td>
    	</tr>
    </table>
  </form>
  </body>
</html>
