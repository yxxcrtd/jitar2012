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
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;'>
    <#if photo??>
    <h2>${photo.title!?html}</h2>
    <div>
      <img src='${Util.url(photo.href!)}' alt='${photo.title!?html}' />
    </div>
    <div style='padding-top:10px;text-align:center;padding:0 150px'>
    <div style='font-weight:bold;text-align:left;padding:10px 0;'>图片相关信息：</div>
    <table border='0' cellspacing='1' cellpadding='3' class='res_detail' style='text-align:left;'>
    	<tr>
    		<td width='60'>图片作者：</td><td>
    		<#assign u = Util.userById(photo.userId)>
    		<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${photo.userNickName!?html}</a></td>
    	</tr>
    	<tr>
    		<td>加入日期：</td><td>${photo.createDate!?html}</td>
    	</tr>    	
    	<tr>
    		<td>图片分类：</td><td><#if category??>${category.name!?html}</#if></td>
    	</tr>
    	<tr>
    		<td>图片标签：</td>
    		<td>
    			<#list Util.tagToList(photo.tags!) as t> 
	 		      <a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a><#if t_has_next>,</#if>
	 		     </#list>    		
    		</td>
    	</tr>
    	<tr>
    		<td>访问次数：</td><td>${photo.viewCount}</td>
    	</tr>
    	<tr>
    		<td>图片描述：</td><td>${photo.summary}</td>
    	</tr>
    </table>
    </#if>
    </div>
  </div>
 </div>
</div>

 <#include 'footer.ftl' >

</body>
</html>
