<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>

 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<#if prepareCourse?? >
<div class='course_title'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/'>${prepareCourse.title!}</a> 的共案</div>
<#assign u = Util.userById(prepareCourseEdit.editUserId) >
<div class="box">
  <div class="box_head">
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;查看共案（撰写人：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName?html}</a>）</div>
  </div>
  <div class="box_content" id='div1'>
    ${prepareCourseEdit.content!}
</div>
</div>
</#if>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>