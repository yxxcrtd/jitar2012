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
<div class='r_s'>
<!-- 文章搜索表单 -->
<form  method='get' action="video_list.action">
<input type="hidden" name="categoryId" value="${categoryId!}"/>
<input type="hidden" name="type" value="search"/>
<table align='center' border='0'>
<tr valign='top'>
<td>
 关键字：<input class='s_input' name='k' value="${k!?html}" style='width:200px;' />   
 搜索类别：<select name='f'>
         <option value='0'<#if f?? && f=="0"> selected='selected'</#if>>标题名、标签名</option>
         <option value='1'<#if f?? && f=="1"> selected='selected'</#if>>描述</option>
         <option value='2'<#if f?? && f=="2"> selected='selected'</#if>>发布人</option>
         </select>
   </td>
   <td><input type='image' src='${SiteThemeUrl}b_s.gif' /></td>
  </tr>
  </table>
</form>
</div>
<div style='height:8px;font-size:0'></div>
<div id='main'>
    <div class='main_left'>
        <div class='res1'>
            <div class='res1_c'>
    <script type="text/javascript">
	  d = new dTree("d");
	  d.add(0,-1,"<b>视频分类</b>","videos.action");
	  <#if root_cates??>
		  <#list root_cates as c>
		  <#if c.parentId??>
		  	d.add(${c.categoryId},${c.parentId},"${c.categoryName}","videos.action?categoryId=${c.categoryId}");
		  <#else>
		  	d.add(${c.categoryId},0,"${c.categoryName}","videos.action?categoryId=${c.categoryId}");
		  </#if>
		  </#list>
	  </#if>
	  document.write(d);
	  d.openAll();
	</script>
            </div>
        </div>
    </div>
    <div class='main_right'>        
				<div class='gly'>
				  <div class='gly_head'>
				    <div class='gly_head_right'><a href='video_list.action?type=new&categoryId=${categoryId!}'>更多…</a></div>
				    <div class='gly_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;最新上传</div>
				  </div>
				  <div class='gly_content'>
				  <#if new_video_list??>
    <table align='center'>
    <tr>
    <td align='left' valign='top'>
<#if new_video_list??>
	<#if new_video_list.size() &gt; 0 >
	    <#-- 定义要显示的列数 columnCount -->
	    <#assign columnCount = 5>
	    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
	    <#if new_video_list.size() % columnCount == 0>
	    	<#assign rowCount = (new_video_list.size() / columnCount) - 1>
	    <#else>
	    	<#assign rowCount = (new_video_list.size() / columnCount)>
	    </#if>
	    <#-- 输出表格 -->
      <table width="100%" cellSpacing="1" align="center">
	    <#-- 外层循环输出表格的 tr -->
	    <#list 0..rowCount as row >
	    <tr valign='top'>
	    <#-- 内层循环输出表格的 td  -->
	    <#list 0..columnCount - 1 as cell >
	        <td align="center" width="20%">
	        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
	        <#if new_video_list[row * columnCount + cell]??>                     
	            <#assign p = new_video_list[row * columnCount + cell]> 
		        <table border='0' width='150' align='left' style='table-layout:fixed;'>
		        <tr align='center'>
		         <td style='height:110px;vertical-align:top;'>
		          <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img border=0 src="${p.flvThumbNailHref!?html}"/>  
		          </a>
		          </td>
		        </tr>
		        <tr align='center'>
		         <td><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><#if (p.title?length lt 22)>${p.title!?html}<#else>${p.title[0..21]!?html}</#if></a></td>
		        </tr>
		        </table>
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
	      
     </td>
     </tr>
     </table>
</#if>
				  </div> 
		  </div>
		  <div style='clear:both;height:10px;'></div>
		  <div class='gly'>
				  <div class='gly_head'>
				    <div class='gly_head_right'><a href='video_list.action?type=hot&categoryId=${categoryId!}'>更多…</a></div>
				    <div class='gly_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;热门排行</div>
				  </div>
				  <div class='gly_content'>
				  <#if hot_video_list??>
    <table align='center'>
    <tr>
    <td align='left' valign='top'>
    
<#if hot_video_list??>
	<#if hot_video_list.size() &gt; 0 >
	    <#-- 定义要显示的列数 columnCount -->
	    <#assign columnCount = 5>
	    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
	    <#if hot_video_list.size() % columnCount == 0>
	    	<#assign rowCount = (hot_video_list.size() / columnCount) - 1>
	    <#else>
	    	<#assign rowCount = (hot_video_list.size() / columnCount)>
	    </#if>
	    <#-- 输出表格 -->

      <table width="100%" cellSpacing="1" align="center">
	    <#-- 外层循环输出表格的 tr -->
	    <#list 0..rowCount as row >
	    <tr valign='top'>
	    <#-- 内层循环输出表格的 td  -->
	    <#list 0..columnCount - 1 as cell >
	        <td align="center" width="20%">
	        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
	        <#if hot_video_list[row * columnCount + cell]??>                     
	            <#assign p = hot_video_list[row * columnCount + cell]> 
		        <table border='0' width='150' align='left' style='table-layout:fixed;'>
		        <tr align='center'>
		         <td style="height:110px;vertical-align:top;">
		          <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><img border=0 src="${p.flvThumbNailHref!?html}"/>  
		          </a>
		          </td>
		        </tr>
		        <tr align='center'>
		         <td><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}'><#if (p.title?length lt 22)>${p.title!?html}<#else>${p.title[0..21]!?html}</#if></a></td>
		        </tr>
		        </table>
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
     </td>
     </tr>
     </table>
</#if>
        </div> 
		  </div>
  </div>
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >
</body>
</html>