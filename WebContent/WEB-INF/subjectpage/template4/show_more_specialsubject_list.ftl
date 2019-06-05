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

<div style='height:8px;font-size:0;'></div>
<div class='containter'>
  <div class='head_nav'><a href='index.py?id=${subject.subjectId}'>学科首页</a> &gt; 所有${subject.subjectName!?html}学科专题列表</div> 			
<div>

<div style='clear:both;height:10px;'></div>
<div class='panel'>
   <div class='panel_head'>
    <div class='panel_head_left' style='width:300px'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;${subject.subjectName!?html}学科专题列表</div>
  </div>
  <div class='panel_content'>
 <table class="lastlist" cellspacing="1">
	  <tr>
	  <th style='font-weight:bold;width:600px'>专题名称</th>
	  <th style='font-weight:bold'>创建时间</th>
	  <th style='font-weight:bold'>有效时间</th>
	  <th style='font-weight:bold'>发布文章</th>
	  <th style='font-weight:bold'>发布图片</th>
	  </tr>	
	<#list specialsubject_list as sl>		  
	  <tr>
	  <td><a href='specialsubject.py?id=${subject.subjectId}&specialSubjectId=${sl.specialSubjectId}'>${sl.title}</a></td>
	  <td>${sl.createDate?string('yyyy-MM-dd')}</td>
	  <td>${sl.expiresDate?string('yyyy-MM-dd')}</td>
	  <td><a href='${SiteUrl}manage/?url=article.action?cmd=input&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发表文章</a></td>
	  <td><a href='${SiteUrl}manage/?url=photo.action?cmd=upload&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发布图片</a></td>
	  </tr>
	</#list>		
	</table>

  </div> 
  <div style='text-align:center;padding:10px 0'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
</div>


<div style='clear:both;height:8px;'></div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>