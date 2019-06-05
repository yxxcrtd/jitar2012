<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>${subject.subjectName}学科首页 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/subject/subject.css" />  
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
 <#include 'site_subject_head.ftl'>

<div style="height:8px;font-size:0"></div>

<div id='main'>
	<div class='main_left'>
    <!-- 教研员 -->
    <#include 'mengv1/subject/jiaoyanyuan.ftl' >
    <div style="height:8px;font-size:0"></div>

	  <!-- 学科名师 -->
	  <#include 'mengv1/subject/famous_teacher.ftl' >
    <div style="height:8px;font-size:0"></div>
		
		<!-- 学科带头人 -->
		<#include 'mengv1/subject/expertor.ftl' >    
		<div style="height:8px;font-size:0"></div>

    
    <!-- 学科统计 -->
    <#include 'mengv1/subject/stat.ftl' >
  </div> 	
</div>

<div class='main_right'>
	<!-- 上面2个 -->
	<div>
     <!-- 学科动态 -->
     <#include 'mengv1/subject/news.ftl' >

	  <!-- 学科公告 -->
	  <#include 'mengv1/subject/placard.ftl' >
	</div>
	<div style='clear:both;height:8px;'></div>
		
	<!-- 下面4个 -->
	<div>
		<div style='float:left;width:416px;'>
      <!-- 学科文章 -->
			<#include 'mengv1/subject/article.ftl' >
	  </div>
		
		<div style='float:right;width:335px'>
		  <!-- 学科工作室 -->
		  <#include 'mengv1/subject/blog.ftl' >
		</div>
		<div style='clear:both;height:8px;'></div>
		
		<div>
			<div style='float:left;width:416px;'>
			  <!-- 学科资源 -->
			  <#include 'mengv1/subject/resource.ftl' >
			</div>
			
			<div style='float:right;width:335px'>
			  <!-- 学科协作组 -->
			  <#include 'mengv1/subject/group.ftl' >
			</div>
	  </div>
	</div>
</div> 

<div style='clear:both;font-size:0;height:0'></div>

<#include 'footer.ftl' >

</body>
</html>
