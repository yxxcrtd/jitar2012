<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/activity.py?id=${subject.subjectId}&unitId=${unitId!}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
    <#if action_list??>
	<ul class='item_ul'>
		<#list action_list as a>
		<li><a target='_blank' href='${SiteUrl}manage/action/showAction.py?actionId=${a.actionId}'>${a.title!?html}</a></li> 
		</#list>
	</ul>    
	</#if>
	<div style='text-align:right'><a href='${ContextPath}createAction.action?ownerId=${subject.subjectId}&ownerType=subject'>发起活动</a></div>
  </div>
</div>