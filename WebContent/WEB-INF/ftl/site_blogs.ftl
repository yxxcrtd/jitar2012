<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>工作室 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}blogs.css" />
  <!--[if IE 6]>
	<style type='text/css'>
	.gzs_main_middle { float:left;width:463px;margin:0 8px; }
	</style>
 <![endif]-->
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
 <#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div id='main'>
	<div class='gzs_main_left'>
	  <!-- 名师工作室 -->
	  <#include 'mengv1/blogs/famous_teacher_list.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 学科带头人工作室 -->
		<#include 'mengv1/blogs/expert_blog_list.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 教研员工作室 -->
		<#include 'mengv1/blogs/jyy_blog_list.ftl' >		
	</div>
	
	<div class='gzs_main_middle'>
	  <!-- 最新工作室 -->
	  <#include 'mengv1/blogs/new_blog_list.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 热门工作室 -->
		<#include 'mengv1/blogs/hot_blog_list.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 推荐工作室 -->
		<#include 'mengv1/blogs/rcmd_blog_list.ftl' >
		<div style="height:8px;font-size:0"></div>

	</div>
	
	<div class='gzs_main_right'>
	  <!-- 文章搜索 -->
	  <#include 'mengv1/blogs/blogs_search.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 研修之星 -->
		<#include 'mengv1/blogs/jitar_star.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 工作室活跃度排行 -->
		<#include 'mengv1/blogs/blog_visit_list.ftl' >
		<div style="height:8px;font-size:0"></div>
		

		<!-- 工作室活积分排行 -->
		<#include 'mengv1/blogs/blog_score_list.ftl' >
		<div style="height:8px;font-size:0"></div>

		<!-- 站点统计 -->
		<#include 'mengv1/blogs/site_stat.ftl' >
		
	</div>
</div>

        
<div style='clear:both'></div>

<#include ('footer.ftl') >
</body>
</html>

