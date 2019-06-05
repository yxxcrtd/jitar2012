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
    <#include ('func.ftl') >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include ('navbar.ftl') ></div>
    <#include ('/WEB-INF/layout/layout_2.ftl') >
    <div id='placerholder1' title='${prepareCourse.title!?html}的相关集备' style='display:none;padding:1px;'>    
<table class='listTable' width='100%' border='0' cellspacing="1">
  <thead>
  <tr>
    <th width='35%'>备课名称</th>
    <th>创建人</th>    
    <th>主备人</th>
    <th>学科</th>
    <th>学段</th>
    <th>开始时间</th>
  </tr>
  </thead>
  <tbody>
<#list preparecourse_list as pc>
  <tr>
    <td><a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a></td>
<#assign c = Util.userById(pc.createUserId)>
<td><a href='${SiteUrl}go.action?loginName=${c.loginName}' target='_blank'>${c.trueName?html}</a></td>
<#assign leader = Util.userById(pc.leaderId)>
<td><a href='${SiteUrl}go.action?loginName=${leader.loginName}' target='_blank'>${leader.trueName}</a></td>
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>${pc.startDate?string("yyyy-MM-dd")}</td>
  </tr>
  </#list>
  </tbody>
</table>
    <#include ('pager.ftl') >
    </div>    
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>