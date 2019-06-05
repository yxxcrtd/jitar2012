<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <#if specialSubject??>
  <title>专题讨论：${specialSubject.title?html} - <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <#else>
  <title><#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  </#if>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:6px;font-size:0;'></div>
<#if specialSubject?? && specialSubject.logo?? >
<div><img src='${specialSubject.logo}' /></div>
<#else>
<#if specialSubject??>
<div class='default_logo'><div class='inner'>${specialSubject.title}</div></div>
<#else>
<div class='default_logo'><div class='inner'>没有专题</div></div>
</#if>
</#if>
<div style='height:8px;font-size:0;'></div>
<div class='containter'>
  <div class='head_nav'><a href='index.action'>首页</a> &gt; <a href='${SiteUrl}specialSubject.action?specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> &gt; 专题讨论</div> 			
<div>
<#if topic_list??>
<div style='clear:both;height:10px;'></div>
<div class='longtab' style='width:100%'>
   <div class='longtab_head'>
    <div class='longtab_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;<a href='${SiteUrl}specialSubject.action?specialSubjectId=2'>${specialSubject.title?html}</a> 的所有专题讨论</div>
  </div>
  <div class='longtab_content'>  
	<ul class='news_new_item_ul'>		  
	<#list topic_list as t>		  
	  <li><span><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName!?html}</a> ${t.createDate?string('yyyy-MM-dd HH:mm:ss')}</span>
		<a href='${SiteUrl}mod/topic/show_topic.action?guid=${specialSubject.objectGuid}&type=specialsubject&topicId=${t.plugInTopicId}' target='_blank'>${t.title!?html}</a>
	  </li>
	</#list>
	</ul>
	  
  </div> 
  <div style='margin:20px;text-align:center'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
</div>
</#if>

<div style='clear:both;height:8px;'></div>

<#include '/WEB-INF/ftl/footer.ftl' >

</body>
</html>