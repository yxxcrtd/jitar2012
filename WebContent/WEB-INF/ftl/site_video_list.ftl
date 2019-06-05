<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include 'webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}gallery.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <!--[if IE 6]>
	<style type='text/css'>
  .gly_main_left { float:left;width:660px; margin:0 10px;background:#ebf4ff;border:1px solid #859dbf;}
	</style>
 <![endif]-->
  
  <script type="text/javascript" src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>  
 </head>
 <body>
 <#include 'site_head.ftl'>
<div style='height:8px;font-size:0'></div>
<div id='main'>      
<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'></div>
    <div class='gly_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;${t!?html}</div>
  </div>
  <div class='gly_content'>

<#if video_list??>

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
            <#assign video = video_list[row * columnCount + cell]>                  
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'><img border=0 src="${video.flvThumbNailHref!?html}"/></a><br />
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'><#if (video.title?length lt 22)>${video.title!?html}<#else>${video.title[0..21]!?html}</#if></a>
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>
</#if>

  </div>
  <#if pager??>
  <div class='pgr'><#include 'inc/pager.ftl' ></div> 
  </#if>
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >
</body>
</html>