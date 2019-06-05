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
    <div id='placerholder1' title='全部视频' style='display:none;padding:1px;'>    
	    <table width='100%' border='0'>
		<#list pcv_list as pcv>
		  <#if (pcv.trueName??)><#assign userName = pcv.trueName >
		  <#elseif (pcv.nickName??)><#assign userName = pcv.nickName >
		  <#elseif (pcv.loginName??)><#assign userName = pcv.loginName >
		  </#if>
		  <tr>
		    <td width="25%">
			<a href="${SiteUrl}manage/video.action?cmd=show&videoId=${pcv.videoId}" target="_blank"><img src="${pcv.flvThumbNailHref!}" /></a>
		    </td>
		    <td valign="top">
				<li>视频标题：${pcv.videoTitle}
				<li>发布者：${userName}
				<li>发布时间：${pcv.createDate}			    	
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