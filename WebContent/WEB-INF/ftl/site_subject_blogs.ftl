<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${grade.gradeName!?html}${subject.metaSubject.msubjName!?html}工作室 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/subject/subject.css" />  
  <script src='js/jitar/core.js'></script>  
  <script src='js/jitar/select.js'></script> 
 </head>
 <body>
 <#include 'site_subject_head.ftl'>

<div style="height:8px;font-size:0"></div>

<div id='main'>
  <div class='blog_main_left'>
      <!-- 教研员工作室 -->
      <#include 'mengv1/subject/blogs/jiaoyanyuan.ftl' >
      
     <!-- 名师工作室 -->
     <#include 'mengv1/subject/blogs/famous.ftl' >
     <div style="height:8px;font-size:0"></div>

     <!-- 学科带头人工作室 -->
     <#include 'mengv1/subject/blogs/expertor.ftl' >
     <div style="height:8px;font-size:0"></div>

      
      <!-- 工作室访问排行 -->
      <#include 'mengv1/subject/blogs/hot_list.ftl' >
        
  </div>
  <div class='blog_main_right'>
    <!-- 工作室搜索 -->
    <#include 'mengv1/subject/blogs/search.ftl' >
    <div style="height:8px;font-size:0"></div>
    
    <!-- 工作室主列表显示区域(带分页) -->
    <div class='orange_border'>
  <#if blog_list?? >
    <#list blog_list as u >
    <table border='0' cellspacing='10' class='list_table'>
        <tr align='center'>
          <td style='width:140px;padding:10px;'>
      <span class='blog_logo'><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" 
              width='96' height='96' border='0' /></a></span>
          </td>
          <td align='left' style='width:100%;'>
            <div>
               <b><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.blogName!?html}</a></b> - ${u.trueName} (创建时间: ${u.createDate}, 文章数：${u.myArticleCount!0 + u.therArticleCount!0},  资源数： ${u.resourceCount}, 图片数：${u.photoCount!}, 发表评论数：${u.commentCount}, 点击数:${u.visitCount})
            </div>
            <div>标签：<#list Util.tagToList(u.tags!'') as t><a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a>${t_has_next?string(', ','')}</#list></div>
            <div>所属学段：
            <#if meta_Grade??>
              <#list meta_Grade as metagrade>
                <#if ((u.gradeId >=metagrade[0]) && (u.gradeId <(metagrade[0]+1000)))>
                  ${metagrade[1]}
                </#if> 
              </#list>
            </#if>
             
           	 所属机构：${u.unitName!?html}</div>
            <div>简介：${u.blogIntroduce!}</div>
          </td>
        </tr>
      </table>
    </#list>
      
      <div style='padding:16px;text-align:right;'><#include 'inc/pager.ftl' ></div>
    </#if>
      </div>
    <div style="height:8px;font-size:0"></div>
      
  
    
  </div>
</div> 

<div style='clear:both;font-size:0;height:0'></div>

<#include 'footer.ftl' >

</body>
</html>
