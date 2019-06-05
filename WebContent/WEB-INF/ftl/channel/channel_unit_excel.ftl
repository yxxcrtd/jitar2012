<table border="1">
<thead>
<tr>
<th colspan="6">
${channel.title} 
<#if startDate != "">
开始时间：${startDate}
</#if>
<#if endDate != "">
结束时间：${endDate}
</#if>
</th>
</tr>
<tr>
<th>机构名称</th>
<th>用户数</th>
<th>文章数</th>
<th>资源数</th>
<th>视频数</th>
<th>图片数</th>
</tr>
</thead>
<#if stat_list?? >
<tbody>
<#list stat_list as t>
<tr>
<td>${t.unitTitle!}</td>
<td>${t.userCount}</td>
<td>${t.articleCount}</td>
<td>${t.resourceCount}</td>
<td>${t.videoCount}</td>
<td>${t.photoCount}</td>
</tr>
</#list>
</tbody>
<#else>
<tbody>
<td colspan="6">无数据</td>
</tbody>
</#if>
</table>