<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName!?html} - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
   
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/dtree.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">
<div style="height:4px;font-size:0"></div>
<#if specialSubject?? && specialSubject.logo?? >
<div><img src='${specialSubject.logo}' /></div>
<#else>
<#if specialSubject??>
<div class='sp_logo'><div class='sp_inner'>${specialSubject.title}</div></div>
<#else>
<div class='sp_logo'><div class='sp_inner'>没有专题</div></div>
</#if>
</#if>
<div style='height:8px;font-size:0;'></div>
<div class='containter'>
  <div class='head_nav'><a href='index.py?id=${subject.subjectId}'>学科首页</a> &gt; <a href='${SubjectRootUrl}specialsubject/?specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> &gt; 专题文章</div> 			
<div>
<div style='clear:both;height:10px;'></div>
<div class='panel'>
   <div class='panel_head'>
    <div class='panel_head_left' style='width:300px'>&nbsp;<img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;<a href='${SiteUrl}subject/specialsubject.py?id=${subject.subjectId}&specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> 的所有专题文章</div>
  </div>
  <div class='panel_content'>
		 <ul class='listitem_ul'>		  
		<#list article_list as sa>		  
		  <li><span><a href='${ContextPath}go.action?userId=${sa.userId}'>${sa.userTrueName!?html}</a> ${sa.createDate?string('yyyy-MM-dd')}</span>
			<#if sa.typeState == false>[原创]<#else>[转载]</#if><a href='${ContextPath}go.action?articleId=${sa.articleId}' target='_blank'>${sa.title!?html}</a>
		  </li>
		</#list>
		</ul>
	  </div> 
	  <div style='text-align:center;padding:10px 0'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
	</div>
	
<div style='clear:both;height:8px;'></div>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>