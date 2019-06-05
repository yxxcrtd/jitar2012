<#if course_list?? >
<table style='width:100%' cellspacing='3'>
<tr>
<th>备课名称</th><th>创建时间</th><th>成员数</th><th>文章数</th><th>资源数</th>
<th>活动数</th><th>讨论数</th><th>回复数</th>
</tr>
<#list course_list as p >
<tr>
<td><a href='${SiteUrl}p/${p.prepareCourseId}/0/'>${p.title!?html}</a></td>
<td>${p.createDate?string('yyyy-MM-dd')}</td>
<td>${p.memberCount}</td>
<td>${p.articleCount}</td>
<td>${p.resourceCount}</td>
<td>${p.actionCount}</td>
<td>${p.topicCount}</td>
<td>${p.topicReplyCount}</td>
</tr>
</#list> 
</table>
</#if> 