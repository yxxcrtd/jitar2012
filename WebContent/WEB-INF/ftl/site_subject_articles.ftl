<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName!}学科文章 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/subject/subject.css" /> 
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
  
 </head>
 <body>
 <#include 'site_subject_head.ftl'>

<div style="height:8px;font-size:0"></div>
<!-- 文章搜索 -->
<#include 'mengv1/subject/articles/search.ftl' >
<div style='height:8px;font-size:0'></div>

<div id='main'>
	<div class='main_left'>
	  <!-- 文章分类 -->
	  <#include 'mengv1/subject/articles/cate_tree.ftl' >
	</div>
	
	<div class='main_right'>
		<div class='orange_border'>
			<div id="article_" class='tab2'>
			<#if !(type??) || type == ''><#assign type="new" ></#if>
				<div class="${(type == 'rcmd')?string('cur','')}" style='border-left:0'><a href='subjectArticles.py?type=rcmd&subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${categoryId!}'>编辑推荐</a></div>
				<div class="${(type == 'new')?string('cur','')}"><a href='subjectArticles.py?type=new&subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${categoryId!}'>最新发布</a></div>
				<div class="${(type == 'hot')?string('cur','')}"><a href='subjectArticles.py?type=hot&subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${categoryId!}'>最高人气</a></div>
				<div class="${(type == 'cmt')?string('cur','')}"><a href='subjectArticles.py?type=cmt&subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${categoryId!}'>评论最多</a></div>
			</div>
	
		  	<div class='tab_content' style='padding:10px;'>
          <!-- 主文章列表 -->
		    	<div id="article_0"  style="display: block;">
			    	<table border="0" cellspacing='0' class='sub_res_table' width='100%'>
			    	<thead>
				  	<tr>
				  	<td class='sub_td_left' style='padding-left:10px;'>标题</td>
				  	<td class='sub_td_middle'>作者</td>
				  	<td class='sub_td_middle'>发表日期</td>
				  	<td class='sub_td_middle'>阅读数</td>
				  	<td class='sub_td_middle'>评论数</td>
				  	</tr>
				  	</thead>
				  	<tbody>
				  	<#list article_list as a > 	
				  	  <#assign u = Util.userById(a.userId) >
	  				 <tr>
					  	<td style='padding-left:10px;'><#if a.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${a.title!?html}</a></td>
					  	<td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName}</a></td>
					  	<td>${a.createDate?string('yyyy-MM-dd')}</td>
					  	<td>${a.viewCount}</td>
					  	<td>${a.commentCount}</td>
					  </tr>
					 </#list>
	  				
				  	</tbody>
			      </table>
		       	<div class='pgr'><#include 'inc/pager.ftl' ></div>		      
		    </div>
	  </div>		
	</div>	
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >

</body>
</html>