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
    <div id='placerholder1' title='教研活动' style='display:none;padding:1px;'>    
         <#if action_list??>
         <table border='0' width='100%' cellspacing='1' class='table1'>
         <thead>
         <tr>
         <td style='width:100%'>活动标题</td>
         <td><nobr>总人数</nobr></td>
         <td><nobr>创建时间</nobr></td>
         <td><nobr>活动开始时间</nobr></td>
         </tr>
         </thead>
         <tbody>
         <#list action_list as a>
         <tr>
         <td><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a></td>
         <td><nobr><#if a.userLimit == 0 >不限<#else>${a.userLimit}</#if></nobr></td>         
         <td><nobr>${a.createDate}</nobr></td>
         <td><nobr>${a.startDateTime}</nobr></td>
         </tr>
         </#list>
         </tbody>
         </table>
        </#if>
        
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