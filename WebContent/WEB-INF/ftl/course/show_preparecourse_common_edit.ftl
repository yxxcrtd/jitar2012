<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <#include 'common_script.ftl' >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
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
  <script type='text/javascript'>
  //<![CDATA[
  
  function op(m)
  {
    document.body.setAttribute('onbeforeunload','');
    document.getElementById("ff").optype.value=m;
    document.getElementById("ff").submit();
  }
  
function returnCheck()
{
  return '请不要直接关闭或者刷新浏览器，否则，备课编辑将处于锁定状态，别人将在一段时间内无法进行编辑。\r\n\r\n如果不需要保存编写的内容，请单击“撤销编辑”按钮';
}
//]]>
</script>

</head> 
<body<#if prepareCourse.contentType != 2 && prepareCourse.contentType != 3 && prepareCourse.contentType != 4 && prepareCourse.contentType != 100> onbeforeunload='return returnCheck(event)'</#if>>
    <#include 'func.ftl' >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include 'navbar.ftl' ></div>
    <#include '/WEB-INF/layout/layout_2.ftl' >
    <div id='placerholder1' title='编辑共案' style='display:none;padding:1px;'>
    <#if prepareCourse.contentType == 2 || prepareCourse.contentType == 3 || prepareCourse.contentType == 4 || prepareCourse.contentType == 5>
	   <div style='padding:4px 0;color:#f00;'>
	    本课共案采用打开 Word 直接编辑的方式，需要先安装 Word 插件，点击打开Word进行编辑，并同时锁定该共案，请编辑完毕务必点击“上传到集备”按钮，不管是否对该共案进行了编辑，否则，该共案将一直处在锁定状态，别人将无法进行编辑修改。
	   <br/><br/>
	   <#if prepareCourse.contentType==2>
	    <a href="${SiteUrl}download/EdustarCourse2003Setup_x86.zip" target="_blank">下载 Word 2003 插件(x86)</a>
	    <#elseif prepareCourse.contentType == 3>
	    <a href="${SiteUrl}download/EdustarCourse2007Setup_x86.zip" target="_blank">下载 Word 2007 插件</a>
	    <#elseif prepareCourse.contentType == 4>
	    <a href="${SiteUrl}download/EdustarCourse2010Setup_x86.zip" target="_blank">下载 Word 2010 插件(x86)</a> | 
	    <a href="${SiteUrl}download/EdustarCourse2010Setup_x64.zip" target="_blank">下载 Word 2010 插件(x64)</a>
	    </#if>
	     
	    <br/><br/><iframe src="javascript:void(0)" style="display:none" name="hideFrame"></iframe>
	    <div style="text-align:center;"><a href="upload_common_prepare_course.py" target="hideFrame" style="font-size:2em;font-weight:bold;">打开Word <#if prepareCourse.contentType==2>2003<#elseif prepareCourse.contentType == 3>2007<#elseif prepareCourse.contentType == 4>2010</#if>编辑共案</a></div>
	    </div>
	<#elseif prepareCourse.contentType == 100 >
		<#if prepareCourse.commonContent ?? && prepareCourse.commonContent != "">
		<div style="padding:4px 0">先下载 <a target="_blank" href="${SiteUrl}manage/DownloadCourseFile?p=${prepareCourse.prepareCourseId}&t=doc&f=1">下载原始文件</a> 文件，编辑完成后再这里上传，或者“取消上传并解除锁定”。</div>
		</#if>
	<form method="post" action="<#if prepareCourseFileServer??>${prepareCourseFileServer}<#else>${SiteUrl}</#if>manage/fileUpload.action?cmd=uploadpreparecourse" enctype="multipart/form-data">
	<input type="hidden" name="prepareCourseId" value="${EncryptPrepareCourseId!}" />
	<input type="hidden" name="returnType" value="page_common" />
	<#if userTicket??><input type="hidden" name="userTicket" value="${userTicket}" /></#if>
	请选择一个 Word或者PowerPoint 文件：<br/><input type="file" name="doc" size="80"/><br/><br/>
	<input type="submit" value="上传文件" onclick = "return checkFile(this.form)" />
	<input type="button" value="取消上传并解除锁定" onclick="window.location='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/unlock_preparecourse.py'" />
	<div id="info" style="display:none;color:red;font-weight:bold;padding:6px 0">正在上传文件，并进行转换，请稍候……</div>
	<div style="padding-top:6px;color:#f00">说明1：请选择<b>Microsoft Word或者Microsoft PowerPoint</b>文件；<br/>
	说明2：共案已经锁定，请务必完成上传或者点击“取消上传并解除锁定”按钮解除锁定。
	</div>
	<script>
	function checkFile(f)
	{
	 if(f.doc.value==''){
	 alert('请先选择一个文件。');
	 return false;
	 };
	 document.getElementById('info').style.display='';
	 return true;
	}
	</script>
    <#else>    
	    <div style='padding:4px 0;color:#f00'>
		<span style='float:left'>
		请注意：打开此页面将会使本共案处于锁定编辑状态，务必不要直接关闭本页面，如果不保存内容，请单击“退出编辑”按钮。
		</span>
		<span style='float:right'>
		<input type='button' value='保存共案' onclick='op(0)' />
		<input type='button' value='退出编辑' onclick='op(1)' />
		</span>
		</div>
        <br/><br/><br/><br/>
       <form method='post' id='ff'>
		<input type='hidden' name='optype' value='1' />
            <script id="commonContent" name="commonContent" type="text/plain" style="width:780px;height:700px;">
            ${prepareCourse.commonContent!}
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('commonContent');
            </script>
		</form>
		<div style='text-align:right'>
		<input type='button' value='保存共案' onclick='op(0)' />
		<input type='button' value='退出编辑' onclick='op(1)' />
		</div>
		
	</#if>
    </div>
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>