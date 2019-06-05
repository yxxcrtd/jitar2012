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
  <div class='head_nav'><a href='index.py?id=${subject.subjectId}'>学科首页</a> &gt; <a href='${SubjectRootUrl}specialsubject/?specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> &gt; 专题视频</div> 			
<div>
<#if video_list??>
<div style='clear:both;height:10px;'></div>
<div class='panel'>
   <div class='panel_head'>
    <div class='panel_head_left' style='width:300px'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;<a href='${SiteUrl}subject/specialsubject.py?id=${subject.subjectId}&specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> 的所有专题视频</div>
  </div>
  <div class='panel_content'>
 <#if video_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 6>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if video_list.size() % columnCount == 0>
    <#assign rowCount = (video_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (video_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center">                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr valign='top'>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if video_list[row * columnCount + cell]??>                     
            <#assign v = video_list[row * columnCount + cell]>            
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target="_blank"><img onload="CommonUtil.reFixImg(this,128,128)" src="${SiteUrl}manage/showImage?flvThumbNailHref=${v.flvThumbNailHref!?html}" border="0" vspace='4' /></a>      
            <br />            
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target="_blank">${v.title!?html}</a>
         
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>

  </div> 
  <div class='pgr' style='margin:20px'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
</div>
</#if >

<div style='clear:both;height:8px;'></div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>