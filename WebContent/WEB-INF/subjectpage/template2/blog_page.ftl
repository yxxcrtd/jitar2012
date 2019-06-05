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
      <!-- 教研员工作室 -->
      <#include 'blog_jiaoyanyuan.ftl' >
      <div style="height:8px;font-size:0"></div>
     <!-- 名师工作室 -->
     <#include 'blog_famous.ftl' >
     <div style="height:8px;font-size:0"></div>

     <!-- 学科带头人工作室 -->
     <#include 'blog_expertor.ftl' >
     <div style="height:8px;font-size:0"></div>
      
      <!-- 工作室访问排行 -->
      <#include 'blog_hot_list.ftl' >
        
  </div>
  <div class='blog_main_right'>
    
    <!-- 工作室主列表显示区域(带分页) -->
    <div class='orange_border'>
  <#if blog_list?? >
    <#list blog_list as u >
    <table border='0' cellspacing='10' class='list_table'>
        <tr align='center'>
          <td style='width:140px;padding:10px;'>
      <span class='blog_logo'><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'"  width='96' height='96' border='0' /></a></span>
          </td>
          <td align='left' style='width:100%;'>
            <div>
               <b><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.blogName!?html}</a></b> - ${u.trueName} (创建时间: ${u.createDate}, 文章数：${u.myArticleCount!0 + u.therArticleCount!0},  资源数： ${u.resourceCount}, 图片数：${u.photoCount!}, 发表评论数：${u.commentCount}, 点击数:${u.visitCount})
            </div>
            <div>标签：<#list Util.tagToList(u.tags!'') as t><a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a>${t_has_next?string(', ','')}</#list></div>
            <div>           
            所属机构：<#if u.unitId??><#assign unit=Util.unitById(u.unitId)><#if unit??><a href="${SiteUrl}transfer.py?unitName=${unit.unitName}">${unit.unitTitle!?html}</a></#if></#if></div>
            <div>简介：${u.blogIntroduce!}</div>
          </td>
        </tr>
      </table>
    </#list>
      
      <div style='padding:16px;text-align:right;'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
    </#if >
      </div>
    <div style="height:8px;font-size:0"></div>
        
  </div>
</div> 

<div style='clear:both;font-size:0;height:0'></div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">

</body>
</html>