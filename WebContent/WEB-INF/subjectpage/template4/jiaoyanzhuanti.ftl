<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/specialsubject.py?id=${subject.subjectId}&unitId=${unitId!}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
    <#if ss_list??>
	<ul class='item_ul'>
		<#list ss_list as ss>
		<li><a href='py/specialsubject.py?id=${subject.subjectId}&amp;specialSubjectId=${ss.specialSubjectId}'>${ss.title}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>