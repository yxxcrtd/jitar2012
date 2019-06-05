<#if hasdefault == '1'>
	<table style='width:100%'>
	<tr>
	<td class='fontbold' style='width:68px;'>计划标题：</td><td>${prepareCoursePlan.title?html}</td>
	</tr>
	<tr>
	<td class='fontbold'>开始时间：</td><td>${prepareCoursePlan.startDate?string('yyyy-MM-dd')}</td>
	</tr>
	<tr>
	<td class='fontbold'>结束时间：</td><td>${prepareCoursePlan.endDate?string('yyyy-MM-dd')}</td>
	</tr>
	<tr>
	<td class='fontbold'>参与人员：</td><td>组内所有成员</td>
	</tr>
	<tr>
	<td class='fontbold' style='vertical-align:top'>计划任务：</td><td>${prepareCoursePlan.planContent}</td>
	</tr>
	</table>
	<div style='text-align:right'><a href='${SiteUrl}g/${group.groupName}/py/group_preparecourseplan_list.py'>更多备课计划</a></div>
	<h3>当前计划下的所有集备</h3>
	<#if pc_list??>
	<table style='width:100%' cellspacing='2' border='0'>
	<tr>
	  <th>课题</th><th>主备人</th><th>开始时间</th><th>结束时间</th>
	</tr>
	<#list pc_list as pc>
	<#assign u = Util.userById(pc.leaderId)>
	 <tr>
	  <td style='width:100%' class='list_icon'><a href='${SiteUrl}p/${pc.prepareCourseId}/0/'>${pc.title?html}</a></td>
	  <td><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></nobr></td>
	  <td><nobr>${pc.startDate?string('yyyy/MM/dd')}</nobr></td>
	  <td><nobr>${pc.endDate?string('yyyy/MM/dd')}</nobr></td>
	</tr>
	</#list>
	</table>
	<div style='text-align:right'>
	<a href='${SiteUrl}manage/course/group_course_plan_edit.py?groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}#bottom'>发起集备</a> | 
	<a href='${SiteUrl}g/${group.groupName}/py/show_group_preparecourseplan.py?planId=${prepareCoursePlan.prepareCoursePlanId}'>更多集备</a>
	</div>
	<#else>
	当前备课计划下没有集备。
	</#if>
<#else>
	<#if plan_list??>
	<ul class="listul">
	<#list plan_list as plan>
	  <li><a href='${SiteUrl}g/${group.groupName}/py/show_group_preparecourseplan.py?planId=${plan.prepareCoursePlanId}'>${plan.title?html}</a></li>
	</#list>
	</ul>
	<div style='text-align:right'><a href='${SiteUrl}g/${group.groupName}/py/group_preparecourseplan_list.py'>更多备课计划</a></div>
	<#else>
	当前没有备课计划。
	</#if>
</#if>