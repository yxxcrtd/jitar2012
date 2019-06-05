<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>分类维护</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
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
<form method='post' action="channel.action">
<input type="hidden" name="cmd" value="addbulletin"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<input type="hidden" name="placardId" value="${placard.id}"/>
<table class='listTable' cellspacing='1'>
	<tr>
		<td align="right" style="width:100px"><b>公告标题:</b></td>
		<td>
			<input type='text' name='title' value='<#if placard??>${placard.title?html}</#if>' size='80' />
		</td>
	</tr>
	</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:504px;">
    <div style="text-align:right;padding:8px;height:480px;width:96px;float:left"><b>公告内容:</b></div>
    <div style="position:absolute;top:0;left:108px">
       <script id="placardContent" name="placardContent" type="text/plain" style="width:980px;height:400px;">
            <#if placard??>${placard.content!}</#if>
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('placardContent');
            </script> 
    </div>
</div>
<table class='listTable' cellspacing='1'>

	<tr>
		<td style="width:100px"></td>
		<td>
		 <#if placard?? && placard.id &gt; 0>
      <input class="button" type="submit" value=" 修  改 " />
			<#else>
      <input class="button" type="submit" value=" 添  加  " />
			</#if>
			<input class="button" type="button" value=" 返  回 " onclick="window.history.back()" />
		</td>
	</tr>
  </table>
</form>    
</body>
</html>