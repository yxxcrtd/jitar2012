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
 <div class='head_nav'><#include ('site_subject_category_user_navbar.ftl') > <a href='ktgroups.action'>课题组</a> > 课题成果[文章] </div> 
  <div>
    <#if article_list??>
    <table class="lastlist" cellspacing="1">
     <tr>
     <th><nobr>文章名称</nobr></th>
     <th><nobr>所在课题</nobr></th>
     <th style="width:120px"><nobr>发表日期</nobr></th>
    </tr>
    <#list article_list as article>
    <tr>
     <td class="list_title">
        <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
    </td>
    <td style="text-align: center;"><nobr>${article.groupTitle}</nobr></td>
    <td><nobr>${article.createDate?string('yyyy-MM-dd')}</nobr></td> 
    </tr>
    </#list>
</table>
<div class='pager'>
  <#include 'inc/pager.ftl' >
</div>
  </#if>  
 </div>
 </div>
<div style="clear: both;"></div>   
<#include 'footer.ftl'>
</body>
</html>