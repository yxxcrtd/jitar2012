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

<div id='main'>
	<div class='blog_main_left'>
	 <!-- 推荐协作组 -->
	 <#include 'group_rcmd_list.ftl' >
     <div style="height:8px;font-size:0"></div>
          
     <!-- 热门协作组 -->
     <#include 'group_hot_list.ftl' >
     <div style="height:8px;font-size:0"></div>
     
     <!-- 协作组活跃度排行 -->
     <#include 'group_active_list.ftl'>
	</div>
	
	<div class='blog_main_right'>
    <!-- 协作组搜索 -->
    <#include 'group_search.ftl' >
		<div style="height:8px;clear:both;"></div>
		
		<!-- 协作组主列表 -->
		<div class='q_r'>
		  <div class='q_r_head'>
		    <table border='0' style='width:630px;' align='right'>
		      <tr>
		        <td class='r1 bold'>协作组名称</td>
		        <td class='r2 bold'>创建时间</td>
		        <td class='r3 bold'>成员数</td>
		        <td class='r4 bold'>访问数</td>
		        <td class='r5 bold'>文章数</td>
		        <td class='r6 bold'>主题数</td>
		        <td class='r7 bold'>活动数</td>
		        <td class='r8 bold'>资源数</td>
		      </tr>
		    </table>
		  </div>
		    <div style='padding:4px;'>
		      <table style='width:100%' border='0'>
        <#list group_list as group>
		      <tr valign='top'>
		        <td><span class='border_img'><a href='${ContextPath}go.action?groupId=${group.groupId}'><img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='96' height='96' border='0' /></a></span></td>
		        <td>
		          <table border='0' style='width:630px;' align='right'>
		            <tr>
		              <td class='r1 bold'><a href='${ContextPath}go.action?groupId=${group.groupId}'>${group.groupTitle!?html}</a></td>
		              <td class='r2'>${group.createDate?string('yyyy-MM-dd')}</td>
		              <td class='r3'>${group.userCount}</td>
		              <td class='r4'>${group.visitCount}</td>
		              <td class='r5'>${group.articleCount}</td>
		              <td class='r6'>${group.topicCount}</td>
		              <td class='r7'>0</td>
		              <td class='r8'>${group.resourceCount}</td>
		            </tr>
		          </table>
		          <div style='clear:both;padding:6px;padding-left:12px'>简介：${group.groupIntroduce!}
		          </div>
		        </td>
		      </tr>
        </#list>
		      </table>
		    </div>
		    <if group_has_next><div class='spr'></div></if>
		</div>
		
		<div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>

	</div>
</div> 

<div style='clear:both;font-size:0;height:0'></div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">

</body>
</html>
