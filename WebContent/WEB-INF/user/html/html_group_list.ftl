<table style='width:100%;' cellspacing='0' class='list_table'>
<thead>
<tr>
<td style='width:auto'>协作组名称</td>
<td>成员</td>
<td>文章</td>
<td>主题</td>
<td>资源</td>
<td>访问</td>
</tr>
</thead>
<tbody>
<#foreach group in group_list>
 <tr>
 <td><a href='${SiteUrl}g/${group.groupName}' target='_blank'>${group.groupTitle}</a></td>
 <td>${group.userCount}</td>
 <td>${group.articleCount}</td>
 <td>${group.topicCount}</td>
 <td>${group.resourceCount}</td>
 <td>${group.visitCount}</td>
 </tr>
 </#foreach>
 </tbody>
</table>