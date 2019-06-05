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
<!-- 文章搜索 -->
<#include 'preparecourse_search.ftl' >
<div style='height:8px;font-size:0'></div>

<table border='0' cellspacing='0' cellpadding='0' style='width:100%'>
  <tr style='vertical-align:top'>
    <td style='width:25%;padding-right:10px'>
      <#include 'preparecourse_cate_tree.ftl' >
    </td>
    
    <td style='width:75%'>
    	<div class='tab'>
			<div class='tab2' style='font-weight:bold;'>
				<#if !(showtype??)><#assign showtype="running" ></#if >
				<div style='border-left:0px' class="${(showtype == 'recommend')?string('cur','') }"><a href='preparecourse.py?id=${subject.subjectId}&showtype=recommend&levelGradeId=${levelGradeId!}&unitId=${unitId!}'>推荐的集备</a></div>
				<div class="${(showtype == 'finished')?string('cur','') }"><a href='preparecourse.py?id=${subject.subjectId}&showtype=finished&levelGradeId=${levelGradeId!}&unitId=${unitId!}'>已经结束的集备</a></div>
				<div class="${(showtype == 'running')?string('cur','') }"><a href='preparecourse.py?id=${subject.subjectId}&showtype=running&levelGradeId=${levelGradeId!}&unitId=${unitId!}'>正在进行的集备</a></div>
				<div class="${(showtype == 'new')?string('cur','')} "><a href='preparecourse.py?id=${subject.subjectId}&showtype=new&levelGradeId=${levelGradeId!}&unitId=${unitId!}'>计划进行的集备</a></div>
				<div class='spacer'></div>
			</div>
			<div class='tab_content'>
    
        <div class='orange_border'>
            <div class='tab_content' style='padding:10px;'>
		        <#if course_list?? >
		        <table class='res_table' cellspacing='0'>
		        <head>
		        <tr>
		        <td class='fontbold td_left'>备课名称</td>
		        <td class="td_middle" nowrap='nowrap'>主备人</td>
		        <td class="td_middle" nowrap='nowrap'>开始时间</td>
		        <td class="td_middle" nowrap='nowrap'>结束时间</td>
		        <!--
		        <td class="td_middle" nowrap='nowrap'>成员数</td>
		        <td class="td_middle" nowrap='nowrap'>访问数</td>
		        <td class="td_middle" nowrap='nowrap'>文章数</td>
		        <td class="td_middle" nowrap='nowrap'>资源数</td>
		        -->
		        <td class="td_middle" nowrap='nowrap'>个案数</td>
		        <td class="td_middle" nowrap='nowrap'>共案编辑数</td>
		        <td class="td_middle" nowrap='nowrap'>讨论数</td>
		        <td class="td_middle" nowrap='nowrap'>活动数</td>
		        </tr>
		        </head>    
		        <#list course_list as c >
		        <tr>
		        <td><a href='${SiteUrl}p/${c.prepareCourseId}/0/'>${c.title}</a>
					<#if c.recommendState??>
					<#if c.recommendState>
					<img border="0" src="${SiteUrl}manage/images/ico_rcmd.gif">
					</#if>
					</#if>
		        </td>
		        <#assign u = Util.userById(c.leaderId)>
		        <td><#if u??><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></#if></td>
		        <#--<td>${c.createDate?string('yyyy-MM-dd')}</td>-->
		        <td>${c.startDate?string('yyyy-MM-dd')}</td>
		        <td>${c.endDate?string('yyyy-MM-dd')}</td>
		        <!--
		        <td>${c.memberCount}</td>
		        <td>${c.viewCount}</td>
		        <td>${c.articleCount}</td>
		        <td>${c.resourceCount}</td>
		        -->
				<td>${privateCountList[c_index]}</td>
				<td>${editCountList[c_index]}</td>
		        <td>${c.topicCount}</td>
		        <td>${c.actionCount}</td>
		        </tr>
		        </#list>
		        </table>
		        </#if>
		        <div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div> 
            </div>
      	  </div>
      	</div>
      	</div>
      </td>  
    </tr>  
</table>

<div style='clear:both;'></div>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>