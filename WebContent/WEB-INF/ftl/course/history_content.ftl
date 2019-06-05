<#if prepareCourseEdit_list?? >
<table class="pc_table" cellspacing="1">
<#list prepareCourseEdit_list as e>
<#assign u = Util.userById(e.editUserId) >
<#if e.prepareCourseEditId == prepareCourse.prepareCourseEditId>
 <tr class='curbackground'>
<#else>
	<#if prepareCourseEdit?? && e.prepareCourseEditId == prepareCourseEdit.prepareCourseEditId>
	 <tr class='curbackgroundred'>
	<#else>
	 <tr>
	</#if>
</#if>
<td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
<td>${e.editDate?string('MM-dd HH:mm')}</td>
<td>
 <a href='show_preparecourse_history_content.py?prepareCourseEditId=${e.prepareCourseEditId}'>查看</a>  
</td>
</tr>
</#list>
</table>
</#if>