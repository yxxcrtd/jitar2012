<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
   <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
<style>
.title {padding:0 100px;padding-top:6px;font-size:16px;font-weight:bold;display:block;float:left; }
.normal{
background:#dfedff;
padding:0 6px;
padding-top:6px;float:left;
border-left:2px solid #a3a3a3;
border-top:2px solid #a3a3a3;
border-bottom:2px solid #2647a0;
height:22px;
font-size:13.7px;
font-weight:bold;
}
.current
{
background:#FFF;
padding:0 6px;
padding-top:6px;
float:left;
border-left:2px solid #a3a3a3;
border-top:2px solid #a3a3a3;
border-bottom:2px solid #FFF;
height:22px;
font-size:13.7px;
font-weight:bold;
color:red;
}
textarea{font-size:16px;}
.current a {color:red;text-decoration:none}
.normal a {text-decoration:none}
</style>
<script src="${SiteUrl}js/jitar/core.js"></script>
<script>
function insertAtCursor(textareaName, content)
{
  myField = document.getElementsByName(textareaName)[0];
  h = myField.scrollTop;
  myValue = "#["+content+"]"
  //IE support
  if (document.selection) 
  {
	myField.focus();
	sel = document.selection.createRange();
	sel.text = myValue;
  }
  //MOZILLA/NETSCAPE support
  else if (myField.selectionStart || myField.selectionStart == '0') 
  {
	var startPos = myField.selectionStart;
	var endPos = myField.selectionEnd;
	myField.value = myField.value.substring(0, startPos)+ myValue + myField.value.substring(endPos, myField.value.length);
  } 
  else 
  {
    myField.value += myValue;
  }
  myField.scrollTop = h;
}

function GetTemplateData(tpname,contentName){	
	url = "../manage/channel.action?cmd=gettemplate&name=" + tpname + "&channelId=${channel.channelId}&type=index&" + Date.parse(new Date());
	h = new window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("MSXML2.XMLHTTP");
	h.open("GET", url,true);
	h.setRequestHeader("Connection", "close");
	h.onreadystatechange = function(){
		if(h.readyState == 4){
			if(h.status == 200){
				document.getElementsByName(contentName)[0].value = h.responseText;
			}
		}
	};
	h.send(null);
}


</script>
</head>
<body>
<div style="border-bottom:2px solid #2647a0;height:30px">
<span class="title"><a href="${SiteUrl}channel/channel.action?channelId=${channel.channelId}" target="_blank">${channel.title!?html}</a></span>
<span class="<#if tab=="home">current<#else>normal</#if>"><a href="channel.action?cmd=edit&channelId=${channel.channelId}&tab=home">频道首页模板
</a></span>
<span class="<#if tab=="header">current<#else>normal</#if>"><a href="channel.action?cmd=edit&channelId=${channel.channelId}&tab=header">频道页头
</a></span>
<span class="<#if tab=="footer">current<#else>normal</#if>"><a href="channel.action?cmd=edit&channelId=${channel.channelId}&tab=footer">频道页脚
</a></span>
<span class="<#if tab=="logo">current<#else>normal</#if>"><a href="channel.action?cmd=edit&channelId=${channel.channelId}&tab=logo">频道 Logo
</a></span>
<span class="<#if tab=="css">current<#else>normal</#if>"><a href="channel.action?cmd=edit&channelId=${channel.channelId}&tab=css">频道 CSS
</a></span>
<span class="normal" style="border-top:2px solid #FFF;background:#FFF"></span>
</div>
<div style="padding:20px">
<#if tab == "home">
<form method="POST" action="channel.action">
<input type="hidden" name="cmd" value="save"/>
<input type="hidden" name="tab" value="home"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<div id="m1" onmousedown="DivUtil.dragStart(event,'m1')" style="position:absolute;top:200px;width:800px;left:100px;border:2px solid red;background:#EEE;padding:10px 4px;">
<span style='color:#F00'>可添加的模块：</span>
<#if module_list??>
<#list module_list as m>
<a href="#" onclick="insertAtCursor('indexPageTemplate','${m.displayName}');return false;">${m.displayName}</a>
</#list>
</#if>
</div>
<textarea name="indexPageTemplate" style="width:100%;height:500px">${channel.indexPageTemplate!?html}</textarea>
<input type="submit" value="修改频道首页模板" /> 
<input type="button" value="加载频道首页默认模板" onclick="GetTemplateData('indexPageTemplate','indexPageTemplate')" />
<!--
<input type="button" value="加载强区频道模板" onclick="GetTemplateData('indexPageTemplate2','indexPageTemplate')" />
-->
</form>
<#elseif tab == "header">
<form method="POST" action="channel.action">
<input type="hidden" name="cmd" value="save"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<input type="hidden" name="tab" value="header"/>
<div id="m1" onmousedown="DivUtil.dragStart(event,'m1')" style="position:absolute;top:200px;width:800px;left:100px;border:2px solid red;background:#EEE;padding:10px 4px;">
<span style='color:#F00'>可添加的模块：</span>
<#if module_list??>
<#list module_list as m>
<a href="#" onclick="insertAtCursor('headerTemplate','${m.displayName}');return false;">${m.displayName}</a>
</#list>
</#if>
</div>
<textarea name="headerTemplate" style="width:100%;height:500px">${channel.headerTemplate!?html}</textarea>
<input type="submit" value="修改频道页头模板" /> 
<input type="button" value="加载频道页头默认模板" onclick="GetTemplateData('headerTemplate','headerTemplate')" />
<!--
<input type="button" value="加载强区页头默认模板" onclick="GetTemplateData('headerTemplate2','headerTemplate')" />
-->
</form>
<#elseif tab == "footer">
<form method="POST" action="channel.action">
<input type="hidden" name="cmd" value="save"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<input type="hidden" name="tab" value="footer"/>
<textarea name="footerTemplate" style="width:100%;height:500px">${channel.footerTemplate!?html}</textarea>
<input type="submit" value="修改频道页脚模板" /> 
<input type="button" value="加载频道页脚默认模板" onclick="GetTemplateData('footerTemplate','footerTemplate')" />
<!--
<input type="button" value="加载强区页脚默认模板" onclick="GetTemplateData('footerTemplate2','footerTemplate')" />
-->
</form>
<#elseif tab == "css">
<form method="POST" action="channel.action">
<input type="hidden" name="cmd" value="save"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<input type="hidden" name="tab" value="css"/>
<textarea name="cssStyle" style="width:100%;height:500px">${channel.cssStyle!?html}</textarea>
<input type="submit" value="修改频道CSS样式表" /> 
<input type="button" value="加载频道默认CSS样式表" onclick="GetTemplateData('cssStyle','cssStyle')" />
<!--
<input type="button" value="加载强区CSS样式表" onclick="GetTemplateData('cssStyle2','cssStyle')" />
-->
</form>
<#elseif tab == "logo">
  <script type='text/javascript'>
  //<![CDATA[  
  function openUpload(t)
  {
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=" + t;
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        document.flogo.logo.value = url;
    }
  }
  //]]>
  </script>

<form method="POST" name="flogo" action="channel.action">
<input type="hidden" name="cmd" value="save"/>
<input type="hidden" name="channelId" value="${channel.channelId}"/>
<input type="hidden" name="tab" value="logo"/>
<input name="logo" value="${channel.logo!?html}" style="width:500px" />
<input type='button' value='查看输入的 Logo' onclick='window.open(this.form.logo.value,"_blank")' /><br/><br/>
<input type='button' value='上传图片…' onclick='openUpload("Image")' />
<input type='button' value='上传 Flash…' onclick='openUpload("Flash")' />
 (Flash、Logo 标准宽度为1000像素)<br/><br/>
<input type="submit" value="修改频道 Logo" /> (说明：有些模块不能重复使用，如果要显示相同的内容，可以新建一个设置相同的模块。)
</form>
</#if>
</div>