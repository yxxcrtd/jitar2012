<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName?html} - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/changeurlyear.js"></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">
<div style="height:8px;font-size:0"></div>
<div id='container'>

<div class='orange_border' style='padding:4px;'>
<form method='get' style='text-align:center;'>
  <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId}' />
  <input type='hidden' name='gradeId' value='${gradeId!}' />
  关键字：<input class='s_input2' name='k' value="${k!?html}" />
  选择类型：<select name='type'>
        <#if !(type??)><#assign type="new" ></#if >
        <option value='new'${(type == 'new')?string(' selected="selected"','')}>最新发布</option>
        <option value='rcmd'${(type == 'rcmd')?string(' selected="selected"','')}>编辑推荐</option>
        <option value='hot'${(type == 'hot')?string(' selected="selected"','')}>最高人气</option>
        <option value='cmt'${(type == 'cmt')?string(' selected="selected"','')}>评论最多</option>
      </select> 
<#if blog_cates?? >
  选择分类：<select name='categoryId'>
            <option value=''>所有分类</option>          
          <#list blog_cates.all as c >
            <#if categoryId?? >
	            <#if c.categoryId == categoryId >
	               <option value='${c.categoryId}' selected='selected'>${c.treeFlag2 + c.name}</option>
	            <#else>
	               <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
	            </#if>
            <#else>
                <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
            
          </#list>
          </select>
</#if>
  <input type='image' src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/b_s.gif' align='absmiddle' />
</form>
</div>

<div style="height:8px;font-size:0"></div>
<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
<tr style='vertical-align:top;'>
<td style='width:20%;'>
<div class='orange_border'>
  <div class='tree'>
  <#if blog_cates??>
	<#if !(type??)><#assign type="new" ></#if>
    <script type="text/javascript">
    d = new dTree("d");
    d.add(0,-1,"<b>文章分类</b>","${SubjectRootUrl}py/article.py?id=${subject.subjectId}&type=${type}<#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>");
    <#list blog_cates.all as c >
      <#if c.parentId??>
      	  d.add(${c.id},${c.parentId},"${c.name}","${SubjectRootUrl}py/article.py?id=${subject.subjectId}&&categoryId=${c.categoryId}&type=${type}<#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>");	
      <#else>
     	  d.add(${c.id},0,"${c.name}","${SubjectRootUrl}py/article.py?id=${subject.subjectId}&categoryId=${c.categoryId}&type=${type}<#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>");
      </#if>      
    </#list >
    document.write(d);
  </script>
  </#if>
  </div>
  </div>
</td><td style='padding-left:10px'>
<div class='tab'>
	<div id="article_" class='tab2' style='font-weight:bold;'>
	<#if !(type??) || type == ''><#assign type="new" ></#if >
		<div class="${(type == 'rcmd')?string('cur','')}" style='border-left:0'><a href='${SubjectRootUrl}py/article.py?type=rcmd<#if categoryId??>&categoryId=${categoryId!}</#if><#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>'>编辑推荐</a></div>
		<div class="${(type == 'new')?string('cur','')}"><a href='${SubjectRootUrl}py/article.py?type=new<#if categoryId??>&categoryId=${categoryId!}</#if><#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>'>最新发布</a></div>
		<div class="${(type == 'hot')?string('cur','')}"><a href='${SubjectRootUrl}py/article.py?type=hot<#if categoryId??>&categoryId=${categoryId!}</#if><#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>'>最高人气</a></div>
		<div class="${(type == 'cmt')?string('cur','')}"><a href='${SubjectRootUrl}py/article.py?type=cmt<#if categoryId??>&categoryId=${categoryId!}</#if><#if unitId??>&unitId=${unitId!}</#if><#if year??>&year=${year!}</#if>'>评论最多</a></div>
		<#if backYearList??>
          <span style="float:right;padding:1px 10px 0 0;">
          <select onchange="getHistoryArticle(this.value)" style="height:20px">
          <option value="">历史文章</option>
          <#list backYearList as y>
          <option value="${y.backYear}"<#if year??><#if year==y.backYear> selected="selected"</#if></#if>>${y.backYear}年</option>
          </#list>
          </select>
          </span>
          </#if>
	</div>

  	<div class='tab_content' style='padding:10px;'>
  	<!-- 主文章列表 -->
    	<div id="article_0"  style="display: block;">
	    	<table border="0" cellspacing='0' class='res_table' width='100%'>
	    	<thead>
		  	<tr>
		  	<td class='td_left' style='padding-left:10px;'>标题</td>
		  	<td class='td_middle'>作者</td>
		  	<td class='td_middle'>发表日期</td>
		  	<td class='td_middle'>阅读数</td>
		  	<td class='td_middle'>评论数</td>
		  	</tr>
		  	</thead>
		  	<tbody>
		  	<#list article_list as a > 	
		  	  <#assign u = Util.userById(a.userId) >
			 <tr>
			  	<td style='padding-left:10px;'>
			  	<#if a.typeState == false>[原创]<#else>[转载]</#if><a href='${ContextPath}go.action?articleId=${a.articleId}'<#if a.title?length &gt; 36> title="${a.title}"</#if>>${Util.getCountedWords(a.title!?html,36)}</a></td>
			  	<td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'<#if u.trueName?length &gt; 3> title="${u.trueName}"</#if>>${Util.getCountedWords(u.trueName!?html,3)}</a></td>
			  	<td>${a.createDate?string('yyyy-MM-dd')}</td>
			  	<td>${a.viewCount}</td>
			  	<td>${a.commentCount}</td>
			  </tr>
			 </#list>
			
		  	</tbody>
	      </table>
       	<div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl'></div>		      
    </div>
</div>
</td>
</tr>
</table>

</div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>