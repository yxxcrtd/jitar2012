<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>分类：${category.name} - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="css/index.css" />
  <link rel="stylesheet" type="text/css" href="css/dtree.css" />
  <script type="text/javascript">
  //<![CDATA[
  function DocumentInit()
  {
   var d1 = document.getElementById('twocolumn_left');
   var d3 = document.getElementById('twocolumn_right');
   var h1 = parseInt(d1.offsetHeight,10);
   var h3 = parseInt(d3.offsetHeight,10);
   var h = Math.max(h1,h3);
   d1.style.height = d3.style.height = h + 'px';
  }  
  //]]>
  </script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
 </head>
 <body onload='DocumentInit()'>
 <#include ('header.ftl') >
 <#include ('navbar.ftl') >
<div class='containter'>
	<div id='twocolumn_left'>
	
	<div style='padding:10px;'>
	<script type="text/javascript">
	d = new dTree("d");
	d.add(0,-1,"<b>全部系统分类</b>","");
	<#if syscate_tree??>
	   <#list syscate_tree.root as category>
	   <#if category.parentId??>
	   	d.add(${category.id},${category.parentId},"${category.name}","showCategory.action?categoryId=${category.id}");
	   	<#else>
	   	d.add(${category.id},0,"${category.name}","showCategory.action?categoryId=${category.id}");
	   	</#if>
	    </#list>
	 </#if>
  	document.write(d);
	d.openAll();
	</script>
	</div>
	
	</div>
	<div id='twocolumn_right'>
			 <table class='border_table' cellspacing='1' cellpadding='0'>
			 <tr>
			 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showCategoryArticle.action?categoryId=${category.id}'>更多&gt;&gt;</a></span><span class='redtext'> ${category.name}</span>下的所有文章</td>
			 </tr>
			 <tr>
			 <td style='padding:10px 0 10px 28px;'>			 
			  <#if cate_article_list??>
				    <ul>
				    <#list cate_article_list as article>
				      <#assign u = Util.userById(article.userId)>
				      <li>[${article.createDate?string('MM-dd')}] 
				        <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a> [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>]</li>
				    </#list>
				    </ul>
				  </#if>
			  </td>
			 </tr>
			 </table>
			 
			 <div style='padding:5px'></div>
			 
			 <table class='border_table' cellspacing='1' cellpadding='0'>
			 <tr>
			 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showCategoryResource.action?categoryId=${category.id}'>更多&gt;&gt;</a></span><span class='redtext'> ${category.name}</span>下的所有资源</td>
			 </tr>
			 <tr>
			 <td style='padding:10px 0 10px 28px;'>			 
			  <#if cate_resource_list??>
				   <ul>
				    <#list cate_resource_list as resource>
				      <#assign u = Util.userById(resource.userId)>
				      <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!?string}</a></li>
				    </#list>
				    </ul>
				  </#if>
			  </td>
			 </tr>
			 </table>
			 
			 
			 <div style='padding:5px'></div>
			 
			 <table class='border_table' cellspacing='1' cellpadding='0'>
			 <tr>
			 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showCategoryResource.action?categoryId=${category.id}'>更多…</a></span><span class='redtext'> ${category.name}</span>下的所有相册</td>
			 </tr>
			 <tr>
			 <td style='padding:10px 0 10px 28px;'>			 
			  <#if cate_resource_list??>
				   <ul>
				    <#list cate_resource_list as resource>
				      <#assign u = Util.userById(resource.userId)>
				      <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!?string}</a></li>
				    </#list>
				    </ul>
				  </#if>
			  </td>
			 </tr>
			 </table>
	
	</div>
</div>
<div style='clear:both'></div>
<#include ('footer.ftl') >
</body>
</html>