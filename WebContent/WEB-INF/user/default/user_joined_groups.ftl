<table style='width:100%;' cellspacing='0' class='list_table'>
<thead>
<tr>
<td style='width:auto'>协作组名称</td>
<td>成员</td>
</tr>
</thead>
<#if group_list??>
<tbody>
<#foreach group in group_list>
 <tr>
 <td><a href='${SiteUrl}g/${group.groupName}' target='_blank'>${group.groupTitle}</a></td>
 <td>${group.userCount}</td>
 </tr>
 </#foreach>
 </tbody>
 </#if>
</table>
<div style="text-align: right;">
	<a href="${UserSiteUrl}py/user_group_list.py">&gt;&gt; 全部协作组</a>
</div>