<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>全部推荐文章列表 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="css/index.css" />
 </head>
 <body>
 <#include ('header.ftl') >
 <#include ('navbar.ftl') >
 <div class='containter'> 
<div class='head_nav'><#include ('site_subject_category_user_navbar.ftl') >全部推荐文章</div>
  <div>  
  <#if rcmd_article_list??>
    <table class='lastlist' cellspacing='1'>
    <tr>
    <th><nobr>文章标题</nobr></th>
    <th><nobr>作者</nobr></th>
    <th><nobr>发布日期</nobr></th>
    <th><nobr>查看次数</nobr></th>
    <th><nobr>评论次数</nobr></th>
    </tr> 
    <#list rcmd_article_list as article>
      <#assign u = Util.userById(article.userId)>
      <tr>
 		 <td class='list_title'><a href='${SiteUrl}go.action?loginName=${u.loginName}/article/${article.articleId}.html' target='_blank'>${article.title!?html}</a></td>
 		 <td><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a></nobr></td> 
 		 <td><nobr>${article.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
 		 <td><nobr>${article.viewCount}</nobr></td>
 		 <td><nobr>${article.commentCount}</nobr></td>		 
 		</tr>
 	  </#list>
    </table>  
    
	    <div class='pager'>
	      <#include ('inc/pager.ftl') >
	    </div>
	  </#if>  
	 </div>
 </div>
<div style='clear:both'></div>
<#include ('footer.ftl') >
</body>
</html>