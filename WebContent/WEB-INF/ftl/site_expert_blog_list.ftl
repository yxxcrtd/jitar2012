<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>全部学科带头人列表 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="css/index.css" />
 </head>
 <body>
 <#include ('header.ftl') >
 <#include ('navbar.ftl') >
 <div class='containter'> 
 <div class='head_nav'><#include ('site_subject_category_user_navbar.ftl') >全部学科带头人</div> 
  <div>	
  <#if expert_user_list??>
    <table class='lastlist' cellspacing='1'>
    <tr>
    <th><nobr>昵称</nobr></th>    
    <th><nobr>注册日期</nobr></th>
    <th><nobr>最后登录日期</nobr></th>
    <th><nobr>博客访问次数</nobr></th>
    <th><nobr>加入协作组数</nobr></th>
    <th><nobr>文章数</nobr></th>
    <th><nobr>评论数</nobr></th>
    <th><nobr>资源数</nobr></th>  
    <th><nobr>文件数</nobr></th>
    <th><nobr>好友数</nobr></th>
    </tr>
    <#list expert_user_list as user>
      <tr>
 		 <td class='list_title'><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.nickName}</a></td>
 		 <td><nobr>${user.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
 		 <td><nobr>缺失</nobr></td>
 		 <td><nobr>${user.visitCount}</nobr></td>
 		 <td><nobr>缺失</nobr></td>
 		 <td><nobr>${user.articleCount}</nobr></td>
 		 <td><nobr>${user.commentCount}</nobr></td>
 		 <td><nobr>${user.resourceCount}</nobr></td>
 		 <td><nobr>${user.uploadFileCount}</nobr></td>
 		 <td><nobr>缺失</nobr></td>
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

