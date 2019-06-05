<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" /> 
  <title>专题列表 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
    <!--[if IE 6]>
	<style type='text/css'>
	.q_t_r {float:left;margin:0 10px;padding-top:2px;}
	</style>
 <![endif]-->  
  <script src='js/jitar/core.js'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='containter'>
  <div class='head_nav'><a href='index.action'>首页</a> &gt; 专题列表</div> 			
<div>
<#if specialsubject_list??>
<div style='clear:both;height:10px;'></div>
<div class='longtab' style='width:100%'>
   <div class='longtab_head'>
    <div class='longtab_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;全部专题列表</div>
  </div>
  <div class='longtab_content'>
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
	  <td><a href='specialSubject.action?specialSubjectId=${sl.specialSubjectId}'>${sl.title}</a></td>
	  <td>${sl.createDate?string('yyyy-MM-dd')}</td>
	  <td>${sl.expiresDate?string('yyyy-MM-dd')}</td>
	  <td><a href='${SiteUrl}manage/?url=article.action?cmd=input&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发表文章</a></td>
	  <td><a href='${SiteUrl}manage/?url=photo.action?cmd=upload&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发布图片</a></td>
	  </tr>
	</#list>		
	</table>
	  
  </div> 
  <div class='pgr' style='text-align:center'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>       
</div>
</#if>

<div style='clear:both;height:8px;'></div>

<#include '/WEB-INF/ftl/footer.ftl' >

</body>
</html>