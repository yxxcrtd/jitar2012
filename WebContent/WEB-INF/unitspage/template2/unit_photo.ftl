<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div id='container'>
<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
<tr style='vertical-align:top;'>
<td style='width:20%;'>
  <div class='tree1'>
  <div class='tree1_c'>
	<script type="text/javascript">
	  d = new dTree("d");
	  d.add(0,-1,"<b>图片分类</b>","unit_photo.py?unitId=${unit.unitId}&type=${type!}");
	  <#if photo_cates??>
		  <#list photo_cates as c>
		  <#if c.parentId??>
		  	d.add(${c.categoryId},${c.parentId},"${c.name}","unit_photo.py?unitId=${unit.unitId}&type=${type}&categoryId=${c.categoryId}");
		  <#else>
		  	d.add(${c.categoryId},0,"${c.categoryName}","unit_photo.py?unitId=${unit.unitId}&type=${type}&categoryId=${c.categoryId}");
		  </#if>
		  </#list>
	  </#if>
	  document.write(d);
	  d.openAll();
	</script>
  </div>
  </div>
</td><td style='padding-left:10px'>
<div class='tab_outer'>    
      <div id="article_" class='tab'>
      <#if !(type??)><#assign type="new" ></#if>
      <label class='tab_label_1'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/j.gif' />&nbsp;本校图片</label>
      <div class="" style='font-size:0;'></div>
      </div>
  
        <div class='tab_content' style='padding:10px;'>
          <div style="display: block;">
            
    <#if photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 5>
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
            <#if UserUrlPattern??>
            <a href='${UserUrlPattern.replace('{loginName}',photo.loginName)}photo/${photo.photoId}.html'><img onload="CommonUtil.reFixImg(this,128,128)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
            <a href='${UserUrlPattern.replace('{loginName}',photo.loginName)}photo/${photo.photoId}.html'>${photo.title!?html}</a>
            <#else>
            <a href='${SiteUrl}${photo.loginName}/photo/${photo.photoId}.html'><img onload="CommonUtil.reFixImg(this,128,128)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
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

<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>

