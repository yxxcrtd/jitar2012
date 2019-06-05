<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <#if (subject??)>
  	<#if grade??>
  		<title>${grade.gradeName!?html}${subject.metaSubject.msubjName!?html}资源 <#include ('webtitle.ftl') ></title>
  	<#else>
  		<title>${subject.metaSubject.msubjName!?html}资源 <#include ('webtitle.ftl') ></title>
  	</#if>
  <#else>
  	<#if grade??>
	  	<title>${grade.gradeName!?html}资源 <#include ('webtitle.ftl') ></title>
  	<#else>
  		<title>资源 <#include ('webtitle.ftl') ></title>
  	</#if>
  </#if>
  <link rel="stylesheet" type="text/css" href="css/subject/subject.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <!--[if IE 6]>
  	<style type='text/css'>
.res_main_middle{float:left;width:608px;overflow:hidden;}
	</style>
 <![endif]-->
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
 </head>
 <body>
 <#include 'site_subject_head.ftl'>

<div style="height:8px;font-size:0"></div>
<!-- 资源搜索表单 -->
<#include 'mengv1/subject/resources/search.ftl' >

<div style='height:8px;font-size:0;'></div>
<div id='main'>
	<div class='res_main_left'>
    <!-- 资源分类 -->
		<#include 'mengv1/subject/resources/res_cates1.ftl' >
    </div>
  
	<div class='res_main_middle'>
    <!-- 资源列表 -->
		<div id="jitar_res_" class='res_tab2' style='position:relative;z-index:90;'>
		<#if !(type??) ><#assign type = "new" ></#if>
			<label></label>
			<#if (subject??)>
			<div class="${(type == 'rcmd')?string('cur', '') }"><a href='subjectResources.py?type=rcmd&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${categoryId!}'>网编推荐</a></div>
      		<div class="${(type == 'new')?string('cur', '') }"><a href='subjectResources.py?type=new&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${categoryId!}'>最新发布</a></div>
      		<div class="${(type == 'hot')?string('cur', '') }"><a href='subjectResources.py?type=hot&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${categoryId!}'>最高人气</a></div>
      		<div class="${(type == 'cmt')?string('cur', '') }"><a href='subjectResources.py?type=cmt&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${categoryId!}'>评论最多</a></div>
      		<#else>
			<div class="${(type == 'rcmd')?string('cur', '') }"><a href='subjectResources.py?type=rcmd&gradeId=${gradeId!}&categoryId=${categoryId!}'>网编推荐</a></div>
      		<div class="${(type == 'new')?string('cur', '') }"><a href='subjectResources.py?type=new&gradeId=${gradeId!}&categoryId=${categoryId!}'>最新发布</a></div>
      		<div class="${(type == 'hot')?string('cur', '') }"><a href='subjectResources.py?type=hot&gradeId=${gradeId!}&categoryId=${categoryId!}'>最高人气</a></div>
      		<div class="${(type == 'cmt')?string('cur', '') }"><a href='subjectResources.py?type=cmt&gradeId=${gradeId!}&categoryId=${categoryId!}'>评论最多</a></div>
      		</#if>
      <div style='width:255px;border-right:1px solid #eeccaa'></div>
		</div>			
	  <div class='res_tab_content'>
	   	<div id="jitar_res_4"  style="display: none;"></div>
	    <div id="jitar_res_0"  style="display: block;">		    
	    	<table border="0" cellspacing='0' class='res_table'>
	    	<thead>
		  	<tr>
		  	<td class='td_left' style='padding-left:10px;'>标题</td>
		  	<td class='td_middle'>年级</td>
		  	<td class='td_middle'>类型</td>
		  	<td class='td_middle'>大小</td>
		  	<td class='td_middle'>上传者</td>
		  	<td class='td_right'>上传日期</td>
		  	</tr>
		  	</thead>
		  	<tbody>
		  	<!-- 资源列表 -->
		  		<#list resource_list as r>
			    <tr>
				  	<td style='padding-left:10px;'><a href='showResource.action?resourceId=${r.resourceId}'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${r.title!?html}</a></td>
				  	<td>${r.gradeName!?html}</td>
				  	<td>${r.scName!?html}</td>
				  	<td align='right'>${Util.fsize(r.fsize!)}</td>
				  	<td><a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${r.nickName!?html}</a></td>
				  	<td>${r.createDate?string('yyyy-MM-dd')}</td>
				  	</tr>
			    </#list> 
		  	</tbody>
	      </table>
	      <div class='pgr'><#include 'inc/pager.ftl' ></div>
	    </div>
	  </div>
	</div>
	
	<div class='res_main_right'>
    <!-- 资源卓越贡献者 -->
    <#include 'mengv1/subject/resources/gongxian.ftl' >
		<div style="height:8px;font-size:0"></div>
		
		<!-- 排行榜 -->
		<#include 'mengv1/subject/resources/sorter.ftl' >
	</div>
</div>


<div style='clear:both;'></div>
<#include 'footer.ftl' >

</body>
</html>