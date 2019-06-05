<#if prepareCourse?? >
<div>
  <div><b>${prepareCourse.title!}</b></div>
  <#assign u = Util.userById(prepareCourse.leaderId) >
  <div>主备人:<a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName?html}</a></div>
  <#assign uu = Util.userById(prepareCourse.createUserId) >
  <div>发起人:<a href='${SiteUrl}go.action?loginName=${uu.loginName}' target='_blank'>${uu.trueName?html}</a></div>
  <div>成员:${prepareCourse.memberCount!?default('0')}人</div>
  <div>文章:${prepareCourse.articleCount!?default('0')}篇</div>
  <div>资源:${prepareCourse.resourceCount!?default('0')}个</div>
  <div>学段:<#if gradeLevel??>${gradeLevel.gradeName!?html}</#if></div>
  <div>年级:<#if grade??>${grade.gradeName!?html}</#if></div>
  <div>学科:<#if subject??>${subject.msubjName!?html}</#if></div>
  <div>创建日期:${prepareCourse.createDate!?string('yyyy年M月d日')}</div>
  <div>开始日期:${prepareCourse.startDate!?string('yyyy年M月d日')}</div>
  <div>结束日期:${prepareCourse.endDate!?string('yyyy年M月d日')}</div>
  <div>标签:<#list Util.tagToList(prepareCourse.tags!) as tag>
            <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
            </#list>
  </div>
  <div>备课任务: <br/>  
  ${prepareCourse.description!}
  </div>
  <div>
	<#if ("0" != Util.isOpenMeetings())>
		<#if (1 == Util.IsVideoJibei(prepareCourse.prepareCourseId))>
			<br/>
			<a class="linkButton icoVideo" href="${Util.isOpenMeetings()}&amp;jibeiid=${prepareCourse.prepareCourseId!}" target="_blank">进入备课会议室</a>
		</#if>
	</#if>
  </div>
  </div>
</div>
</#if>

