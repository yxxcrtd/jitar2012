<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
  <title>添加/修改公告</title> 
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
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
        if(frm.placard_title.value=="")
        {
            alert("请输入公告标题");
            return false;
        }

    if(GetMessageLength("content")=='0')
    {
        alert('请输入公告内容');       
        return false;
    }

        return true;   
    }
  </script>  
</head> 
<body> 
<h2>
<#if placard??>
修改公告
<#else>
添加公告
</#if>
</h2>
<form method='post' onsubmit='return checkData(this);'>
	<table class='listTable' cellspacing='1' style="width:1024px"> 
	<tr> 
		<td align="right" style='width:100px'><b>公告标题(<font color='red'>*</font>):</b></td> 
		<td>
		<#if placard??>
			<input type='text' name='placard_title' value='${placard.title?html}' size='120' maxLength="120" />
		<#else>
			<input type='text' name='placard_title' value='' size='120' maxLength="120" />
		</#if> 
		</td> 
	</tr>	
	</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:504px;">
    <div style="text-align:right;padding:8px;height:480px;width:96px;float:left"><b>公告内容(<font color='red'>*</font>):</b></div>
    <div style="position:absolute;top:0;left:108px">
       <script id="DHtml" name="content" type="text/plain" style="width:900px;height:400px;">
                        <#if placard??>${placard.content!}</#if>
                        </script>                          
                        <script type="text/javascript">
                            var editor = UE.getEditor('DHtml');
                        </script>
    </div>
</div>
<table class='listTable' cellspacing='1' style="width:1024px">
	<tr> 
		<td align="right" style='width:100px'></td> 
		<td> 
			<input class="button" type="submit" value=" 保  存 " />
		</td> 
	</tr> 
</table> 
</form> 
 
<script> 
function browse_server() {
  url = '${SiteUrl}manage/userfm/index.jsp?Type=Image';
  var left = (window.screen.width - 720)/2;
  var top = (window.screen.height - 540)/2 - 40;
  var winStyle = 'width=720,height=540,location=no,menubar=no,resizable=yes,scrollbars=no,status=yes,toolbar=no';
  winStyle += ',left=' + left + ',top=' + top;
  window.open(url, 'imageBrowser', winStyle);
}
function SetUrl(encodeUrl, url) {
  var form = document.forms[0];  
  form.picUrl.value = url;
  document.getElementById('pictureImage').src = url;
}
</script> 
	
</body> 
</html> 