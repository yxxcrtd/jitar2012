<div class='panel'>
  <div class='panel_head'>
  	<div class='panel_head_right'><a href='${SubjectRootUrl}py/newsList.py?id=${subject.subjectId}&type=text&unitId=${unitId!}&title=${webpart.displayName!?url}'>更多…</a></div>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
   <#if subject_text_news??>
	<ul class='item_ul'>
		<#list subject_text_news as p>
		<li><a href='${SubjectRootUrl}py/showSubjectNews.py?id=${subject.subjectId}&newsId=${p.newsId}'>${p.title}</a></li> 
		</#list>
	</ul>    
	</#if>
  </div>
</div>