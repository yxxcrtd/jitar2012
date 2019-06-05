<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加修改新闻</title>
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
  
<style>
  .commentReply {
    border: 1px solid gray;
    background-color: #ececec;
    margin:6px 20px 6px 6px;
    padding: 2px;
}
</style>
</head>
<body>
<h2>${(jitarColumnNews??)?string('修改' + jitarColumn.columnName,'添加' + jitarColumn.columnName)}新闻</h2>
<form action='?' method='post' name="newsForm">
<#if urlReferer??>
<input name="urlReferer" type="hidden" value="${urlReferer}" />
</#if>
<input name="columnId" type="hidden" value="${jitarColumn.columnId}" />
<#if jitarColumnNews??>
<input name="newsId" type="hidden" value="${jitarColumnNews.columnNewsId}" />
</#if>
<table class='listTable' cellspacing='1'>
<tr>
<td align="right" style="width:80px"><b>新闻标题:<span style="color:#f00">*</span></b></td>
<td>
<input type='text' name='title' value="<#if jitarColumnNews??>${jitarColumnNews.title!?html}</#if>" size='120' /> 
</td> 
</tr>
<tr>
<td align="right" valign='top'><b>公告内容:<span style="color:#f00">*</span></b></td>
<td colspan="2">

    <script id="columnContentArea" name="columnContent" type="text/plain" style="width:1000px;height:600px;">
    <#if jitarColumnNews??>${jitarColumnNews.content!?html}</#if>
    </script>                          
    <script type="text/javascript">
        var editor = UE.getEditor('columnContentArea');
    </script>
</td>
</tr>
<#if jitarColumn.hasPicture>
<tr style="vertical-align:top;">
<td align="right"><b>主图片:</b></td>
<td>
<input type='text' name='picture' value="<#if jitarColumnNews??>${jitarColumnNews.picture!?html}</#if>" size='120' /> <input type='button' value='浏览服务器' onclick='browse_server()' />
<br/>
<#if jitarColumnNews?? && jitarColumnNews.picture?? && jitarColumnNews.picture != "">
<img id="pictureImage" src="${jitarColumnNews.picture}" />
<#else>
<img id="pictureImage" style="display:none" />
</#if>
</td> 
</tr>
<#if jitarColumn.description?? && jitarColumn.description != "">
<tr>
<td></td>
<td style="color:#f00">${jitarColumn.description}</td>
</tr>
</#if>
</#if>
<tr>
<td align="right"><b>是否发布：</b></td>
<td><#if jitarColumnNews??><input type="checkbox" name="published" value="1" ${jitarColumnNews.published?string('checked="checked" ','')}/><#else><input type="checkbox" name="published" value="1" checked="checked" /></#if></td>
</tr>
<tr>
<td></td>
<td>
<input class="button" type="submit" value="${(jitarColumnNews??)?string(' 修  改 ',' 添  加 ')} " />
<input class="button" type="button" value=" 返  回 " onclick="window.history.back()" />
</td>
</tr>
</table>
</form>

<script>
function browse_server() {
  url = './userfm/index.jsp?Type=Image';
  var left = (window.screen.width - 720)/2;
  var top = (window.screen.height - 540)/2 - 40;
  var winStyle = 'width=720,height=540,location=no,menubar=no,resizable=yes,scrollbars=no,status=yes,toolbar=no';
  winStyle += ',left=' + left + ',top=' + top;
  window.open(url, 'imageBrowser', winStyle);
}
function SetUrl(encodeUrl, url) {
  var ff = document.newsForm;  
  ff.picture.value = url;
  document.getElementById('pictureImage').style.display = "";
  document.getElementById('pictureImage').src = url;
}
</script>
</body>