<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加修改群组公告</title>
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
      
  <script type="text/javascript">
//取fck内容的长度
function GetMessageLength(str)
{
    return editor.getContentLength(); 

}  
//取fck内容
function GetMessageContent(str)
{
    return editor.getContent();
}  
  
     function checkData(frm)
    {
        if(frm.title.value=="")
        {
            alert("请输入公告标题");
            return false;
        }
        if(GetMessageLength("placardContent")=='0')
        {
            alert('请输入公告内容');       
            return false;
        }        
        return true;   
    }
</script>    
  </head>
  
<body>
<h2>${(placard.id == 0)?string('添加','修改')}公告</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='userPlacard.action?cmd=list'>个人公告管理</a>
  &gt;&gt; ${(placard.id == 0)?string('添加','修改')}公告
</div>
	
<form name='placardForm' action='userPlacard.py' method='post' onsubmit='return checkData(this);'>
	<#if __referer??>
		<input type='hidden' name='__referer' value='${__referer}' />
 	</#if>
  <table class='listTable' cellspacing='1' style="width:1024px">
		<tr>
			<td align="right" style='width:100px'>
			  <b>公告标题(<font color='red'>*</font>):</b>
			</td>
			<td>
				<input type='text' name="title" value='${placard.title!?html}' size='80' />
			</td>
		</tr>
		</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:560px;width:1022px">
    <div style="padding:8px;height:560px;width:92px;float:left"><b>公告内容：</b>(<font color='red'>*</font>):</div>
    <div style="position:absolute;top:0;left:107px">
      <script id="placardContent" name="placardContent" type="text/plain" style="width:900px;height:480px;">
            ${placard.content!}
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('placardContent');
            </script>     
    </div>
</div>
  <table class='listTable' cellspacing='1' style="width:1024px">
		<tr>
			<td align="right" style='width:100px'></td>
			<td>
				<input class="button" type='hidden' name='cmd' value='save_placard' />
				<input class="button" type='hidden' name='placardId' value='${placard.id}' />
				<input class="button" type="submit" value="${(placard.id == 0)?string(' 添 加 ',' 修 改 ')} " />
				<input class="button" type="button" value=" 返 回 " onclick="window.history.back()" />
			</td>
		</tr>
	  </table>
	</form> 
</body>
</html>
