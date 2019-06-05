<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <#include ('common_script.ftl') >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
</head> 
<body>
    <#include 'func.ftl' >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include 'navbar.ftl' ></div>
    <#include '/WEB-INF/layout/layout_2.ftl' >
    <div id='placerholder1' title='更改个案内容类型' style='display:none;padding:1px;'>
    <#if prepareCourseMember??>
    <form method='post'>
    <input type="hidden" name="cmd" value="contentype" />
	<label><input type="radio" name="contentype" value="1"<#if prepareCourseMember.contentType==1> checked="checked"</#if> />在线 HTML 编辑</label><br/><br/>
	<label><input type="radio" name="contentype" value="2"<#if prepareCourseMember.contentType==2> checked="checked"</#if> />直接从网站打开 Word 2003 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="3"<#if prepareCourseMember.contentType==3> checked="checked"</#if> />直接从网站打开 Word 2007 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="4"<#if prepareCourseMember.contentType==4> checked="checked"</#if> />直接从网站打开 Word 2010 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="100"<#if prepareCourseMember.contentType==100> checked="checked"</#if> />从网站下载文件，编辑完成后再上传(支持 .doc、.docx、ppt、pptx文件）</label>
	<div style='text-align:right'>
	<input type='submit' value='保存设置' />
	</div>
	</form>
	<div style="padding-top:10px">关于文档格式的说明：由于微软的 Word 软件本身存在版本之间的兼容性问题，所以，请根据您自己使用的 Word 版本进行选择。Word 2010 的文件在保存成  Word 2003版本的文件时，公式等会保存成图片格式，造成再编辑时无法对公式进行更改。所以，对于需要重新编辑的内容，就必须重新做了。</div>
	<#else>
	不是改备课的成员。
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