<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
 <div class='containter'>
 <div class='head_nav'><#include ('site_subject_category_user_navbar.ftl') >工作室查询列表</div> 
 <div>  
  <#if user_list??>
    <table class='lastlist' cellspacing='1'>
    <tr>
      <th><nobr></nobr></th>    
      <th><nobr>注册日期</nobr></th>
      <th><nobr>访问次数</nobr></th>
      <th><nobr>文章数</nobr></th>
      <th><nobr>资源数</nobr></th>
      <th><nobr>评论数</nobr></th>    
    </tr>
    <#list user_list as user>
      <tr>
     <td class='list_title'>
       <table width='100%'><tr>
         <td width='64'>
           <img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' />
         </td>
         <td align='left' valign='top'>
         <b><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.nickName}</a></b><br/>
         <div>标签: <#list Util.tagToList(user.userTags!) as t> 
            <a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a><#if t_has_next>,</#if>
           </#list></div>
         <div>简介：${user.blogIntroduce!}</div>
         </td>
       </tr></table>
     </td>
     <td><nobr>${user.createDate?string('yyyy-MM-dd')}</nobr></td>
     <td align='right'>${user.visitCount}</td>
     <td align='right'>${user.articleCount}</td>
       <td align='right'>${user.resourceCount}</td>
     <td align='right'>${user.commentCount}</td>
    </tr>
  </#list>
    </table>
      <div class='pager'>
        <#include ('inc/pager.ftl') >
      </div>
    </#if>  
   </div>
 </div>
<div style="clear: both;"></div>   
<#include 'footer.ftl'>
</body>
</html>

