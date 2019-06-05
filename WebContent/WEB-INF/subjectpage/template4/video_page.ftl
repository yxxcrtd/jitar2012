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

<div style="height:8px;font-size:0"></div>
<div id='container'>
<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
<tr style='vertical-align:top;'>
<td style='width:20%;'>
  <div class='orange_border'>
  <div class='tree'>
	<script type="text/javascript">
	  d = new dTree("d");
	  d.add(0,-1,"<b>视频分类</b>","video.py?id=${subject.subjectId}&type=${type!}&unitId=${unitId!}&title=${"全部分类"?url}");
	  <#if root_cates??>
		  <#list root_cates as c>
		  <#if c.parentId??>
		  	d.add(${c.categoryId},${c.parentId},"${c.categoryName}","video.py?id=${subject.subjectId}&type=${type!}&categoryId=${c.categoryId}&unitId=${unitId!}&title=${c.categoryName?url}");
		  <#else>
		  	d.add(${c.categoryId},0,"${c.categoryName}","video.py?id=${subject.subjectId}&type=${type!}&categoryId=${c.categoryId}&unitId=${unitId!}&title=${c.categoryName?url}");
		  </#if>
		  </#list>
	  </#if>
	  document.write(d);
	  d.openAll();
	</script>
  </div>
  </div>
</td><td style='padding-left:10px'>
<div class='tab'>
    
      <div class='tab2'>
      <#if !(type??)><#assign type="new" ></#if >
      <label class='tab_label_1'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${Page_Title!?html}</label>
      <div class="" style='font-size:0;'></div>
      </div>
  
        <div class='tab_content' style='padding:10px;'>
          <div style="display: block;">
            
    <#if video_list?? && video_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 5>
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
            <#assign p = video_list[row * columnCount + cell]>                  
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' title="${p.title!?html}"><img border=0 src="${p.flvThumbNailHref!?html}"/></a><br />
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' title="${p.title!?html}"><#if (p.title?length lt 18)>${p.title!?html}<#else>${p.title[0..17]!?html}</#if></a>
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
	</#if>
            
        <div class='pgr'>
		<#if pager??>
			<#include "/WEB-INF/ftl/pager.ftl">		
		</#if>
		</div>
    </div>
</td>
</tr>
</table>

</div>

<div style='clear:both;'></div>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>