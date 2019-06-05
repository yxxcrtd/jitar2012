<div class='panel'>
  <div class='panel_head'>
  	<div class='panel_head_right'><a href='${SubjectRootUrl}py/placardList.py?id=${subject.subjectId}&unitId=${unitId!}&title=${webpart.displayName!?url}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
   <#if placard_list??>
	<ul class='item_ul'>
		<#list placard_list as p>
		<li><a href='${SubjectRootUrl}py/showSubjectPlacard.py?id=${subject.subjectId}&amp;placardId=${p.id}'>${p.title}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>