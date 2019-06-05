<!doctype html>
<html>
 <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<#if specialSubject??>
			<title>专题讨论：${specialSubject.title?html} - <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
		<#else>
			<title><#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
		</#if>
		<#include "/WEB-INF/ftl2/common/favicon.ftl" />
		<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
		<script src='${SiteUrl}js/jitar/core.js'></script>
	</head>

	<body>
		<#include "../site_head.ftl">
		
		<div class="secMain mt25 clearfix">
			<#if specialSubject?? && specialSubject.logo??>
				<div><img src='${specialSubject.logo}' /></div>
			<#else>
				<#if specialSubject??>
					<div class='default_logo'><div class='inner'>${specialSubject.title}</div></div>
				<#else>
					<div class='default_logo'><div class='inner'>没有专题</div></div>
				</#if>
			</#if>
				
		    <div class="moreList border">
		    	<h3 class="h3Head textIn"><span class="moreHead"><a href='index.action'>首页</a> &gt; <a href='${SiteUrl}specialSubject.action?specialSubjectId=${specialSubject.specialSubjectId}'>${specialSubject.title?html}</a> &gt; 专题讨论</span></h3>
		        <div class="moreContent">
		        	<table class="moreTable" cellpadding="0" cellspacing="0" border="0">
		            	<thead>
		                	<tr class="moreThead">
		                        <th width="65%">专题名称</th>
		                        <th width="20%">创建时间</th>
		                        <th width="15%">用户</th>
		                    </tr>
		                </thead>
		                <tbody>
							<#if topic_list??>
								<#list topic_list as t>		  
								  <tr>
									  <td height="40" style="text-align: left;"><a href='${SiteUrl}mod/topic/show_topic.action?guid=${specialSubject.objectGuid}&type=specialsubject&topicId=${t.plugInTopicId}' target='_blank' title="${t.title!}">${Util.drawoffHTML(t.title, 46)}</a></td>
									  <td>${t.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
									  <td><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName!?html}</a></td>
								  </tr>
								</#list>
							</#if>
		                </tbody>
		            </table>
		            <#include "../pager.ftl">
		        </div>
		        <div style='height:15px;font-size:0;' style="background-color:white;"></div>
		        <div class="imgShadow"><img src="skin/default/images/imgShadow.jpg" class="imgShadowSize9" /></div>
		    </div>
		</div>
		
		<#include "../footer.ftl">
	</body>
</html>