<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td class="right" style="padding-left:0">
<div class="rightcontainer" style="padding-left:0">
<#if user_list??>
<table border="0" cellspacing='1' class="datalist bordertable">
<tr>
<th><nobr></nobr></th>    
<th><nobr>注册日期</nobr></th>
<th><nobr>积分</nobr></th>
<th><nobr>文章数</nobr></th>
<th><nobr>资源数</nobr></th>
<th><nobr>评论数</nobr></th>    
</tr>
<#list user_list as user>
<tr>
	<td style="border:0">
		<table width="100%">
			<tr>
				<td width="64" style="border:0">
					<img src="${SSOServerUrl +'upload/'+ user.userIcon!''}" onerror="this.src='${ContextPath}images/default.gif'"  width='64' height='64' border='0' />
				</td>
				<td align='left' valign='top' style="border:0">
					<div style="line-height: 16px;"><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.trueName}</a></div>
					<div style="line-height: 16px;">标签: 
						<#list Util.tagToList(user.userTags!) as t> 
							<a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}'>${t}</a><#if t_has_next>,</#if>
						</#list>
					</div>
					<div style="line-height: 16px;">简介：${user.blogIntroduce!}</div>
				</td>
			</tr>
		</table>
	 </td>
	 <td style="border:0"><nobr>${user.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
	 <td style="text-align: center;border:0">${user.userScore}</td>
	 <td style="text-align: center;border:0">${user.articleCount}</td>
 	 <td style="text-align: center;border:0">${user.resourceCount}</td>
	 <td style="text-align: center;border:0">${user.commentCount}</td>
	</tr>
</#list>
</table>
    
<#if pager??>
<div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</#if>
</div>
</td>
</tr>
</table>