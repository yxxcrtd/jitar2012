<table border="1" cellspacing="4">
  <tr>
    <th colspan="9" height="30">协作组 ${group.groupTitle!} 的统计数据：${beginDate!} 至 ${endDate!}</th>
  </tr>
  <tr>
    <th>组长真实姓名</th>
    <th>所在机构</th>
    <th>学科学段</th>
    <th>发布文章数</th>
    <th>精华文章数</th>
    <th>发布资源数</th>
    <th>精华资源数</th>
    <th>发布主题数</th>
    <th>回复主题数</th>
  </tr>
<tbody>
<#list data_list as m>
	<tr>
		<td><a href='${SiteUrl}go.action?loginName=${m.loginName}' target='_blank'>${m.trueName!?html}</a></td>		
		<td><a href='${SiteUrl}go.action?unitName=${m.unitName!?url}' target='_blank'>${m.unitTitle!?html}</a></td>
		<td>
			<#if m.metaSubjectTitle??>${m.metaSubjectTitle!?html}</#if>
			<#if m.gradeTitle??>${m.gradeTitle!?html}</#if>
		</td>
		<td>${m.articleCount}</td>
		<td>${m.bestArticleCount}</td>
		<td>${m.resourceCount}</td>
		<td>${m.bestResourceCount}</td>
		<td>${m.topicCount}</td>
		<td>${m.replyCount}</td>
  </tr>
</#list>
</tbody>
</table>