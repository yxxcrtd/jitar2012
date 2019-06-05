<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>全部推荐资源列表 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="css/index.css" />
 </head>
 <body>
 <#include ('header.ftl') >
 <#include ('navbar.ftl') >
 <div class='containter'>
<div class='head_nav'>
 <#include ('site_subject_category_user_navbar.ftl') >
所有推荐资源
</div>
<div>	
  <#if resource_list??>
    <table class='lastlist' cellspacing='1'>
    <tr>
    <th><nobr>资源名称</nobr></th>    
    <th><nobr>上传人</nobr></th>
    <th><nobr>上传日期</nobr></th>
    <th><nobr>资源大小(字节)</nobr></th>
    <th><nobr>查看次数</nobr></th>
    <th><nobr>评论次数</nobr></th>
    <th><nobr>下载次数</nobr></th>
    </tr>
    <#list resource_list as resource>
      <#assign u = Util.userById(resource.userId)>
      <tr>
 		 <td class='list_title'><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title}</a></td>
 		 <td><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a></nobr></td>
 		 <td><nobr>${resource.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
 		 <td><nobr>${resource.size}</nobr></td>
 		 <td><nobr>${resource.viewCount}</nobr></td>
 		 <td><nobr>${resource.commentCount}</nobr></td>
 		 <td><nobr>${resource.downloadCount}</nobr></td> 		 
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

