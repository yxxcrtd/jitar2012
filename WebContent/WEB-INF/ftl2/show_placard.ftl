<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${placard.title!?html}</title>
 <#include "/WEB-INF/ftl2/common/favicon.ftl" />
	<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/htmlpager.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject1.js" type="text/javascript" charset="utf-8"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='containter' style='margin:0 auto;width:1000px;background:#fff'>
 <div style='padding:0 20px;'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>
    ${placard.title!?html}</div>
  <div style='text-align:center;padding:10px;'>发布时间：${placard.createDate}</div>
  <div style='padding:10px 40px;font-size:14px;line-height:150%;'>
    ${placard.content!}
    <br/><br/>
  </div>
 </div>
</div>
<#include 'footer.ftl' >
</body>
</html>
