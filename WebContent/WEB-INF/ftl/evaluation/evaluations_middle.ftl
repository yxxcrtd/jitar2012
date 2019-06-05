<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <style>
  html,body{padding:0;margin:0;border:0;width:auto;}
  </style>
  <script src='js/jitar/core.js'></script>
  <script>
  function resizeIframe()
  {
  	h = Math.max(document.body.offsetHeight,document.documentElement.offsetHeight);
  	window.parent.document.getElementById("_middle").style.height = h + 20 + "px";
  }
  </script>
 </head>
<body onload="resizeIframe()">
<#if content_list??>
  <table border="0" cellspacing='0' class='res_table'>
   <thead>
   <tr>
    <td class='td_left' style='padding-left:10px'>评课名称</td>
    <td class='td_middle' nowrap='nowrap'>授课人</td>
    <td class='td_middle' nowrap='nowrap'>评课人</td>
    <td class='td_middle' nowrap='nowrap'>学科/学段</td>
    <td class='td_middle' nowrap='nowrap'>评课时间</td>
   </tr>
   </thead>
   <tbody>
   <tr><td colspan='5' style='padding:4px;'></td></tr>
<#list content_list as c>
   <tr>
    <td style='padding-left:10px'><a target="_blank" href='${SiteUrl}evaluations_content_show.py?evaluationContentId=${c.evaluationContentId}'>${c.title!?html}</a></td>
    <td nowrap='nowrap'>${c.courseTeacherName!?html}</td>
    <td nowrap='nowrap'>${c.publishUserName!?html}</td>
    <td>${c.msubjName!}/${c.gradeName!}</td>
    <td>${c.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
</#list>
    </tbody>
    </table>
    <div class='pgr'><#include "/WEB-INF/ftl/inc/pager.ftl" ></div>
</#if> 
</body>
</html>