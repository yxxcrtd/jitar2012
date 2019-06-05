<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <#if specialSubject??>
  <title>专题图片：${specialSubject.title?html} - <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
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
  <div class='head_nav'><a href='index.action'>首页</a> &gt; <a href='${SiteUrl}specialSubject.action?specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> &gt; 专题图片</div> 			
<div>
<#if photo_list??>
<div style='clear:both;height:10px;'></div>
<div class='longtab' style='width:100%'>
   <div class='longtab_head'>
    <div class='longtab_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;<a href='${SiteUrl}specialSubject.action?specialSubjectId=2'>${specialSubject.title?html}</a> 的所有专题图片</div>
  </div>
  <div>
 <#if photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 6>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if photo_list.size() % columnCount == 0>
    <#assign rowCount = (photo_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (photo_list.size() / columnCount)>
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
        <#if photo_list[row * columnCount + cell]??>                     
            <#assign photo = photo_list[row * columnCount + cell]>                  
            <img onload="CommonUtil.reFixImg(this,128,128)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' /><br />
            <#if UserUrlPattern??>
            <a href='${UserUrlPattern.replace('{loginName}',photo.loginName)}photo/${photo.photoId}.html'>${photo.title!?html}</a>
            <#else>
            <a href='${SiteUrl}${photo.loginName}/photo/${photo.photoId}.html'>${photo.title!?html}</a>
            </#if>
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
</#if>

<div style='clear:both;height:8px;'></div>

<#include ('/WEB-INF/ftl/footer.ftl') >

</body>
</html>