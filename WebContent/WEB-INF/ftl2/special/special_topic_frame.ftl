<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>教研专题 - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
		<#include "/WEB-INF/ftl2/common/favicon.ftl" />
		<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
		<link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
		<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
		<script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script> 
	</head>

	<body>
		<#include "../site_head.ftl">
		
		<div class="secMain mt25 clearfix">
			<#if specialSubject??>
				<#if specialSubject.logo??>
					<div><img src='${specialSubject.logo}' /></div>
				<#else>
					<div class="default_logo">
						<div class="inner">
							<h3 class="h3Head textIn">
								<span class="moreHead">
									<a href='index.action'>首页</a> &gt; 
									<a href='${SiteUrl}specialSubject.action'>专题</a> &gt; 
									<a href="${SiteUrl}specialSubject.action?specialSubjectId=${specialSubject.specialSubjectId!}">${specialSubject.title!}</a> &gt; 
									[placeholder_title] 
								</span>
							</h3>
						</div>
					</div>
				</#if>
			</#if>
			<div style='height:8px;font-size:0;'></div>
			[placeholder_content]
		</div>
		<#include "../footer.ftl">
	</body>
</html>