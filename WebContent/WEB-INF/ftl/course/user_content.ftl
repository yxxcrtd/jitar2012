<#if user_list?? >
<table class="pc_table" cellspacing="1">
<#list user_list as u>
<tr>
<td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
<td>
 <a href='show_preparecourse_user_content.py?userId=${u.userId}'>查看</a>  
</td>
</tr>
</#list>
</table>
</#if>