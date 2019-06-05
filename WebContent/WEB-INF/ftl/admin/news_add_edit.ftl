<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加/修改${displayName!}</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script type='text/javascript' src='${SiteUrl}js/jquery.js'></script>
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
<h2>${(news.newsId == 0)?string('添加','修改')}${displayName!}</h2>
<form name='newsForm' action='?' method='post'>
<#if __referer??>
	<input type='hidden' name='__referer' value='${__referer}' />
</#if>
	<table class='listTable' cellspacing='1'>
	<tr>
		<td align="right" style="width:100px"><b>${displayName!}标题:</b></td>
		<td>
			<input id = 'news_title' type='text' name='title' value='${news.title!?html}' size='120' maxLength="120" />
			<font color='red'>*</font> 必须填写标题，并且至少${Util.JitarConst.MIN_TITLE_LENGTH}个字.
		</td>
	</tr>
	<tr>
    <td align="right"><b>发布位置:</b></td>
    <td>
      <select name='subjectId'>
      <#if admin??>
        <#if admin=='1'>
        <option value='0'>站点主页</option>
        </#if>  
      </#if>
      <#if subject_list??>
      <#list subject_list as s>
        <option value='${s.subjectId}' ${(s.subjectId==news.subjectId)?string('selected','')}>${s.subjectName}</option>
      </#list>
      </#if>
      </select>
    </td>
	</tr>
	</table>
	<div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:504px;">
  	<div style="text-align:right;padding:8px;height:480px;width:96px;float:left"><b>内容:</b></div>
  	<div style="position:absolute;top:0;left:110px">
      <script id="content" name="content" type="text/plain" style="width:1000px;height:400px;">
      ${news.content!}
      </script>                          
      <script type="text/javascript">
          var editor = UE.getEditor('content');
      </script>
  	</div>
</div>
<table class='listTable' cellspacing='1'>
 <#if newsType??>
  <tr>
    <td align='right' style="width:100px"><b>图片链接:</b></td>
    <td>
      <input type='text' name='picUrl' size='60' value='${news.picture!?html}' />
      <input type='button' value='浏览服务器' onclick='browse_server()' />&nbsp;&nbsp;<span style="color: f00;">图片格式必须是jpg，且文件名不能含中文字符</span>
    </td>
  <tr>
    <td></td>
    <td>
     <div><img id='pictureImage' src=""></div>
   </td>
  </tr>
  </#if>
  <tr>
    <td align="right" valign='top'><b>审核状态:</b></td>
    <td>
      <input type='radio' name='audit' value='0' ${(news.status == 0)?string('checked', '')} />审核通过
      <input type='radio' name='audit' value='1' ${(news.status == 1)?string('checked', '')} />待审核
    </td>
  </tr> 
	<tr>
		<td></td>
		<td>
			<input type='hidden' name='cmd' value='save' />
			<input type='hidden' name='type' value='${newsType!}' />
			<input type='hidden' name='newsId' value='${news.newsId}' />
			<input type='hidden' name='title_min_length' value='${Util.JitarConst.MIN_TITLE_LENGTH}'/>
			<input type='hidden' name='title_max_length' value='${Util.JitarConst.MAX_TITLE_LENGTH}'/>
			<input class="button" type="submit" value="${(news.newsId == 0)?string(' 添  加 ',' 修  改 ')} " onclick="if(this.form.title.value==''){alert('请输入标题。');return false;}" />
			<input class="button" type="button" value=" 返  回 " onclick="window.history.back()" />
		</td>
	</tr>
</table>
</form>

<script>
$('#news_title').bind('blur',function(){
    var min_length = 1;
    var max_length =  128;
    var text = $(this).val();
    if(text.length < min_length || text.length > max_length){
       alert('标题至少为'+min_length+'个汉字最多为'+max_length+'个汉字');
    }
})
    
	function browse_server() {
	  url = './userfm/index.jsp?Type=Image';
	  var left = (window.screen.width - 720)/2;
	  var top = (window.screen.height - 540)/2 - 40;
	  var winStyle = 'width=720,height=540,location=no,menubar=no,resizable=yes,scrollbars=no,status=yes,toolbar=no';
	  winStyle += ',left=' + left + ',top=' + top;
	  window.open(url, 'imageBrowser', winStyle);
	}
	function SetUrl(encodeUrl, url) {
	  var form = document.newsForm;
	  form.picUrl.value = url;
	  validateUrl(url);  //验证图片的大小及格式,图片的大小不能小于320*240px,格式只能为jpg
	}
	
	function validateUrl(url){
	  	var img = new Image();
	  	img.src = url;
	  	var pattern = /\jpg$|\jpeg$|\JPG|\JPEG$/i;
	  	var chinese = /[\u4E00-\u9FA5]/i; 
	  	var allow = 'jpg,jpeg,JPG,JPEG';
	  	var mark  = url.substr(url.lastIndexOf('.')+1);
	  	   img.onload=function(){
		  	    if(chinese.test(url)){
		  	        document.getElementById('pictureImage').src = "";
				    document.newsForm.picUrl.value = "";
		  	    	alert("图片地址中不能包括中文!");
		  	    }else if(!pattern.test(Trim(mark))){
		  	       document.getElementById('pictureImage').src = "";
				   document.newsForm.picUrl.value = "";
		  	       alert('你选择的图片的格式不正确,允许的图片格式有:'+allow);
		  		}else{
		  		    var img_height = ${Util.JitarConst.IMG_HEIGHT_CONTROL};
		  		    var img_width = ${Util.JitarConst.IMG_WIDTH_CONTROL};
			  		if(img.height<img_height || img.width<img_height){
				  		document.getElementById('pictureImage').src = "";
				  		document.newsForm.picUrl.value = "";
			  			alert('您选择的图片大小不符合要求,图片大小至少为'+img_width+'*'+img_height+'px!');
				  	}else{
				  	    $.ajax({url: '${ContextPath}manage/getThumbnailImageUrl?cmd='+url}).done(function(data) {
				  	    document.getElementById('pictureImage').src = data;
				  	    });
				  	}
		  		}
	  	}
	}
	
	function LTrim(str){
	    var i;
	    for(i=0;i<str.length;i++)
	    {
	        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
	    }
	    str=str.substring(i,str.length);
	    return str;
	}
	function RTrim(str)
	{
	    var i;
	    for(i=str.length-1;i>=0;i--)
	    {
	        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
	    }
	    str=str.substring(0,i+1);
	    return str;
	}
	
	function Trim(str)
	{
	    return LTrim(RTrim(str));
	}
<#if newsType?? && news?? && news.picture?? && news.picture?size &gt;0 >
$.ajax({url: '${ContextPath}manage/getThumbnailImageUrl?cmd=${news.picture!}&t=' + (new Date()).getTime()}).done(function(data) {
  $('#pictureImage').attr("src",data);
});
</#if>
</script>
	
</body>
</html>
