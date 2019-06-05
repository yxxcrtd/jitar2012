<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 工作室列表</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
</head>
<body>
<#include 'site_head.ftl'>
<div class="secMain mt25 clearfix">
    <div class="moreList border">
    	<h3 class="h3Head textIn"><span class="moreHead">${list_type}</span></h3>
        <div class="moreContent">
        	<table class="moreTable" cellpadding="0" cellspacing="0" border="0">
            	<thead>
                	<tr class="moreThead">
                    	<th width="45%">&nbsp;</th>
                        <th width="15%">注册日期</th>
                        <th width="10%">积分</th>
                        <th width="10%">文章数</th>
                        <th width="10%">资源数</th>
                        <th width="10%">评论数</th>
                    </tr>
                </thead>
                <tbody>
                   <#if user_list??>
                     <#list user_list as user>
		                	<tr <#if (user_index%2!=0)>class="liBg"</#if>> 
		                    	<td>
		                        	<dl class="moreDl">
		                            	<dt><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='50' height='50' border='0'/></a></dt>
		                                <dd>
		                                	<p><a href='${SiteUrl}go.action?loginName=${user.loginName}' class="moreDlName">${user.trueName}</a></p>
		                                    <p>标签:<#list Util.tagToList(user.userTags!) as t><a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a><#if t_has_next>,</#if></#list></p>
		                                    <p>简介:${user.blogIntroduce!}</p>
		                                </dd>
		                            </dl>
		                        </td>
		                        <td>${user.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
		                        <td>${user.userScore}</td>
		                        <td>${user.articleCount}</td>
		                        <td>${user.resourceCount}</td>
		                        <td>${user.commentCount}</td>
		                    </tr>
                    </#list>
                    </#if>
                </tbody>
            </table>
            <#include 'pager.ftl'>
        </div>
        <div style='height:15px;font-size:0;' style="background-color:white;"></div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
    </div>
</div>
<#include 'footer.ftl'/>
<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->

</body>
</html>
