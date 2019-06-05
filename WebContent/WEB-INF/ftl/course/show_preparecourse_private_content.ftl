<table width='100%' border='0'>
<#list member_list as ugm>
  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
  </#if>
  <tr> 
    <td> 
	<a href="${SiteUrl}go.action?loginName=${ugm.loginName}" target="_blank">${userName}</a> 的<a href="${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_user_content.py?userId=${ugm.userId}" target="_blank">个案</a><#if (ugm.bestState??)><#if (ugm.bestState==true)><img border="0" src="${SiteUrl}manage/images/ico_best.gif"/></#if></#if> ${ugm.contentLastupdated?string('MM-dd HH:mm')}
    </td>
    </tr>
</#list>
</table>
<div style='text-align:right'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_private_content_list.py'>全部个案</a></div>
