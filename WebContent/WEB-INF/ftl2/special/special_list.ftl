<!doctype html>
<title>专题列表</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>

<#include "../site_head.ftl">

<div class="secMain mt25 clearfix">
    <div class="moreList border">
    	<h3 class="h3Head textIn"><span class="moreHead"><a href='index.action'>首页</a> &gt; 专题列表</span></h3>
        <div class="moreContent">
        	<table class="moreTable" cellpadding="0" cellspacing="0" border="0">
            	<thead>
                	<tr class="moreThead">
                        <th width="60%">专题名称</th>
                        <th width="10%">创建时间</th>
                        <th width="10%">有效时间</th>
                        <th width="10%">发布文章</th>
                        <th width="10%">发布图片</th>
                    </tr>
                </thead>
                <tbody>
					<#if specialsubject_list??>
						<#list specialsubject_list as sl>		  
						  <tr>
							  <td height="40" style="text-align: left;"><a href='specialSubject.action?specialSubjectId=${sl.specialSubjectId}'>${sl.title}</a></td>
							  <td>${sl.createDate?string('yyyy-MM-dd')}</td>
							  <td>${sl.expiresDate?string('yyyy-MM-dd')}</td>
							  <td><a href='${SiteUrl}manage/article.action?cmd=input&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发表文章</a></td>
							  <td><a href='${SiteUrl}manage/photo.action?cmd=upload&amp;specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发布图片</a></td>
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