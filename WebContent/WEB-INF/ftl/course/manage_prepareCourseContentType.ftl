<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 </title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
 </head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<#if prepareCourse??>
	<h3>修改备课共案类型</h3>
	<div style="color:red;font-weight:bold;">重要说明：更改共案文档类型，保存将覆盖原来的共案内容，一般情况下，只在第一次编写共案的时候进行设置。</div>
	<form method="post" action="?prepareCourseId=${prepareCourse.prepareCourseId!0}" style="padding:10px 0;">
	<label><input type="radio" name="contentType" value="1"<#if prepareCourse.contentType == 1> checked="checked"</#if> />直接在线编辑 HTML 格式</label><br/>
	<label><input type="radio" name="contentType" value="2"<#if prepareCourse.contentType == 2> checked="checked"</#if> />直接从网站打开 Word 2003 文档格式(.doc)</label><br/>
	<label><input type="radio" name="contentType" value="3"<#if prepareCourse.contentType == 3> checked="checked"</#if> />直接从网站打开 Word 2007 文档格式(.docx)</label><br/>
	<label><input type="radio" name="contentType" value="4"<#if prepareCourse.contentType == 4> checked="checked"</#if> />直接从网站打开 Word 2010 文档格式(.docx)</label><br/>
	<label><input type="radio" name="contentType" value="100"<#if prepareCourse.contentType == 100> checked="checked"</#if> />从网站下载文件，编辑完成后再上传(支持 .doc、.docx、ppt、pptx文件）</label><br/><br/>
	<input type="button" class="button" value="保存修改" onclick="if(window.confirm('更改共案文档类型，将覆盖原来的共案内容，共案历史也可能不能显示，一般情况下，只在第一次编写共案的时候进行设置。\r\n\r\n您真的要修改共案内容格式类型吗？？')){this.form.submit();}" />
	</form>
	<div>关于文档格式的说明：由于微软的 Word 软件本身存在版本之间的兼容性问题，所以，请根据各位老师使用的 Word 版本进行选择。Word 2010 的文件在保存成  Word 2003版本的文件时，公式等会保存成图片格式，造成再编辑时无法对公式进行更改。所以，对于需要重新编辑的内容，就必须重新做了。</div>
<#else>
	<h3>无效的备课</h3>
</#if>
</body>
</html>



 