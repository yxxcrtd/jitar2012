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
  <div class='head_nav' style='font-weight:bold;'><a href='index.py?id=${subject.subjectId}'>学科首页</a> &gt; ${Page_Title!?html}</div> 			
<div>

<div style='clear:both;height:10px;'></div>
<div class='panel'>
   <div class='panel_head'>
    <div class='panel_head_left' style='width:300px'>&nbsp;<img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${Page_Title!?html}</div>
  </div>
  <div class='panel_content'>
	
	<table class="lastlist" cellspacing="1">
      <tr>
      	<th style='width:100px'>协作组图标</th>
        <th>协作组名称</th>
        <th>创建时间</th>
        <th>成员数</th>
        <th>访问数</th>
        <th>文章数</th>
        <th>主题数</th>
        <th>活动数</th>
        <th>资源数</th>
      </tr>
    <#list group_list as group>
      <tr valign='top'>
        <td><span class='border_img'><a href='${ContextPath}go.action?groupId=${group.groupId}'><img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='96' height='96' border='0' /></a></span></td>
        <td class='bold'><a href='${ContextPath}go.action?groupId=${group.groupId}'>${group.groupTitle!?html}</a></td>
      	<td>${group.createDate?string('yyyy-MM-dd')}</td>
      	<td>${group.userCount}</td>
     	<td>${group.visitCount}</td>
      	<td>${group.articleCount}</td>
     	<td>${group.topicCount}</td>
     	<td>${group.actionCount}</td>
     	<td>${group.resourceCount}</td>
     </tr>
     <#if group.groupIntroduce?? && group.groupIntroduce != ''>
     <tr>
     	<td colspan='9'>简介：${group.groupIntroduce!}</td>
     </tr>
     </#if>
    </#list>
	</table>

  </div> 
  <div style='padding:20px 0;text-align:center'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
</div>

<div style='clear:both;height:8px;'></div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>