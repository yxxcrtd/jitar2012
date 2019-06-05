<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName!}学科备课 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/subject/subject.css" /> 
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>  
 </head>
 <body>
 <#include 'site_subject_head.ftl'>

<div style="height:8px;font-size:0"></div>
<!-- 文章搜索 -->
<#include 'mengv1/subject/preparecourse/search.ftl' >
<div style='height:8px;font-size:0'></div>

<div id='main'>
    <div class='main_left'>
      <#include 'mengv1/subject/preparecourse/cate_tree.ftl' >
    </div>
    
    <div class='main_right'>
        <div class='orange_border'>
            <div class='tab_content' style='padding:10px;'>
                <div id="article_0"  style="display: block;">
        <#if course_list?? >
        <table class='res_table' cellspacing='0'>
        <head>
        <tr>
        <td class='fontbold td_left'>备课名称</td>
        <!--
        <td class="td_middle">发起人</td>
        -->
        <td class="td_middle">主备人</td>
        <td class="td_middle">创建时间</td>
        <td class="td_middle">开始时间</td>
        <td class="td_middle">结束时间</td>
        <td class="td_middle">成员数</td>
        <td class="td_middle">访问数</td>
        <td class="td_middle">文章数</td>
        <td class="td_middle">资源数</td>
        <td class="td_middle">讨论数</td>
        <td class="td_middle">活动数</td>
        </tr>
        </head>    
        <#list course_list as c >
        <tr>
        <td><a href='${SiteUrl}p/${c.prepareCourseId}/0/'>${c.title}</a></td>
        <!--
        <td><a href='${SiteUrl}go.action?loginName=${c.loginName}'>${c.trueName}</a></td>
        -->
        <#assign u = Util.userById(c.leaderId)>
        <td><#if u??><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></#if></td>
        <td>${c.createDate?string('yyyy-MM-dd')}</td>
        <td>${c.startDate?string('yyyy-MM-dd')}</td>
        <td>${c.endDate?string('yyyy-MM-dd')}</td>
        <td>${c.memberCount}</td>
        <td>${c.viewCount}</td>
        <td>${c.articleCount}</td>
        <td>${c.resourceCount}</td>
        <td>${c.topicCount}</td>
        <td>${c.actionCount}</td>
        </tr>
        </#list>
        </table>
        </#if>
        
                <div class='pgr'><#include 'inc/pager.ftl' ></div>            
            </div>
      </div>        
    </div>  
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >

</body>
</html>