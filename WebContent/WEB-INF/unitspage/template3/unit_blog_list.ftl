<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />
  <script type='text/javascript' src='${SiteUrl}units/drag.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div id='container'>

<div style='height:8px;font-size:0;'></div>
		
<div class='containter'>
	<div style='font-weight:bold;padding:4px 0'>${list_type!'所有工作室列表'}</div>
	<div>
		<#if user_list??>
			<table class="lastlist" cellspacing="1">
				<tr>
					<th><nobr></nobr></th>    
					<th><nobr>注册日期</nobr></th>
					<th><nobr>积分</nobr></th>
					<th><nobr>访问数</nobr></th>
					<th><nobr>文章数</nobr></th>
					<th><nobr>资源数</nobr></th>
					<th><nobr>评论数</nobr></th>    
				</tr>

				<#list user_list as user>
					<tr>
						<td class="list_title">
							<table width="100%">
								<tr>
									<td width="64">
										<img src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" width='64' height='64' border='0'  onerror="this.src='${ContextPath}images/default.gif'"/>
									</td>
									<td align='left' valign='top'>
										<div style="line-height: 16px;"><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.trueName}</a></div>
										<#if user.userTags?? && user.userTags !="" >
                    <#assign tags = user.userTags.split(",")>
                    <div style="line-height: 16px;">标签: 
                      <#list tags as t> 
                        <a href=''${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a><#if t_has_next>,</#if>
                      </#list>
                    </div>
                  </#if>
								<div style="line-height: 16px;">简介：${user.blogIntroduce!}</div>
							</td>
						</tr>
				</table>
 		 </td>
 		 <td><nobr>${user.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
 		 <td style="text-align: center;">${user.userScore}</td>
 		 <td style="text-align: center;">${user.visitCount}</td>
 		 <td style="text-align: center;">${user.articleCount}</td>
     	 <td style="text-align: center;">${user.resourceCount}</td>
 		 <td style="text-align: center;">${user.commentCount}</td>
 		</tr>
 	</#list>
    </table>
	    <div class='pager'>
	      <#if pager??>
			<#include "/WEB-INF/ftl/pager.ftl">		
		  </#if>
	    </div>
	  </#if>  
	 </div>
 </div>

</div>

<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>