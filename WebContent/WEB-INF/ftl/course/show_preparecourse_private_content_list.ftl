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
    <div id='placerholder1' title='全部个案' style='display:none;padding:1px;'>    
	    <table width='100%' border='0'>
		<#list member_list as ugm>
		  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
		  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
		  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
		  </#if>
		  <tr>
		    <td>
			<a href="${SiteUrl}go.action?loginName=${ugm.loginName}" target="_blank">${userName}</a> 的<a href="${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_user_content.py?userId=${ugm.userId}" target="_blank">个案</a><#if (ugm.bestState??)><#if (ugm.bestState==true)><img border="0" src="${SiteUrl}manage/images/ico_best.gif"/></#if></#if> ${ugm.contentLastupdated?string('MM-dd HH:mm')}
		    </td>
		    </tr>
		</#list>
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