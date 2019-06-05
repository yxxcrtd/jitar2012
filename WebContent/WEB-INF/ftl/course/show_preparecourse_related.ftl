<table class='listTable' border="0" cellspacing="0" style="width:100%">
<#list preparecourse_list as pc>
    <tr>
    <td>
    <a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a>
	</td>
	<td align="right">
	<#assign leader = Util.userById(pc.leaderId)>
	<a title="主备人:${leader.trueName}" href='${SiteUrl}go.action?loginName=${leader.loginName}' target='_blank'>${leader.trueName}</a>
	</td>
	</tr>
</#list>
</table>
<br/><br/>
<div style='text-align:right'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_related_list.py'>全部相关集备</a></div>